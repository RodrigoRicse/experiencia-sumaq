[CmdletBinding()]
param(
    [Parameter(Mandatory)]
    [string]$BackupFile,
    [switch]$Force
)

$ErrorActionPreference = 'Stop'
. (Join-Path $PSScriptRoot '_common.ps1')

$projectRoot = Get-SumaqRoot
$environment = Read-SumaqEnvironment -ProjectRoot $projectRoot
Assert-DockerAvailable
$backupRoot = [IO.Path]::GetFullPath((Join-Path $projectRoot 'database\backups'))
$resolvedBackup = [IO.Path]::GetFullPath($BackupFile)
$allowedPrefix = $backupRoot.TrimEnd([IO.Path]::DirectorySeparatorChar) + [IO.Path]::DirectorySeparatorChar

if (-not $resolvedBackup.StartsWith($allowedPrefix, [StringComparison]::OrdinalIgnoreCase)) {
    throw "El archivo debe estar dentro de $backupRoot."
}
if (-not (Test-Path -LiteralPath $resolvedBackup -PathType Leaf)) {
    throw "No existe el backup: $resolvedBackup"
}
if (-not $Force) {
    $confirmation = Read-Host "La restauración reemplazará datos actuales. Escribe RESTAURAR para continuar"
    if ($confirmation -cne 'RESTAURAR') {
        throw 'Restauración cancelada.'
    }
}

$dockerPath = (Get-Command docker).Source
$arguments = @(
    'compose', 'exec', '-T',
    '-e', "MYSQL_PWD=$($environment['MYSQL_PASSWORD'])",
    'mysql', 'mysql', '--default-character-set=utf8mb4',
    '-u', $environment['MYSQL_USER'], $environment['MYSQL_DATABASE']
)

Push-Location $projectRoot
try {
    $process = Start-Process -FilePath $dockerPath -ArgumentList $arguments -NoNewWindow -Wait -PassThru -RedirectStandardInput $resolvedBackup
    if ($process.ExitCode -ne 0) {
        throw "La restauración finalizó con código $($process.ExitCode)."
    }
    Write-Host "Restauración completada desde: $resolvedBackup"
} finally {
    Pop-Location
}
