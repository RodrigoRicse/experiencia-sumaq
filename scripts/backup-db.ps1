[CmdletBinding()]
param()

$ErrorActionPreference = 'Stop'
. (Join-Path $PSScriptRoot '_common.ps1')

$projectRoot = Get-SumaqRoot
$environment = Read-SumaqEnvironment -ProjectRoot $projectRoot
Assert-DockerAvailable
$backupDirectory = Join-Path $projectRoot 'database\backups'
New-Item -ItemType Directory -Path $backupDirectory -Force | Out-Null
$timestamp = Get-Date -Format 'yyyyMMdd-HHmmss'
$backupFile = Join-Path $backupDirectory "experiencia_sumaq-$timestamp.sql"
$dockerPath = (Get-Command docker).Source

$arguments = @(
    'compose', 'exec', '-T',
    '-e', "MYSQL_PWD=$($environment['MYSQL_PASSWORD'])",
    'mysql', 'mysqldump',
    '--single-transaction', '--routines', '--triggers', '--no-tablespaces', '--default-character-set=utf8mb4',
    '-u', $environment['MYSQL_USER'], $environment['MYSQL_DATABASE']
)

Push-Location $projectRoot
try {
    $process = Start-Process -FilePath $dockerPath -ArgumentList $arguments -NoNewWindow -Wait -PassThru -RedirectStandardOutput $backupFile
    if ($process.ExitCode -ne 0) {
        throw "mysqldump finalizó con código $($process.ExitCode)."
    }
    if ((Get-Item -LiteralPath $backupFile).Length -eq 0) {
        throw 'El archivo de backup quedó vacío.'
    }
    Write-Host "Backup creado: $backupFile"
} finally {
    Pop-Location
}
