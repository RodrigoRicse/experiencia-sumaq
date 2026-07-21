[CmdletBinding()]
param(
    [switch]$NoBuild,
    [ValidateRange(10, 600)]
    [int]$WaitSeconds = 180
)

$ErrorActionPreference = 'Stop'
. (Join-Path $PSScriptRoot '_common.ps1')

$projectRoot = Get-SumaqRoot
Read-SumaqEnvironment -ProjectRoot $projectRoot | Out-Null
Assert-DockerAvailable

Push-Location $projectRoot
try {
    $arguments = @('compose', 'up', '-d')
    if (-not $NoBuild) {
        $arguments += '--build'
    }
    & docker @arguments
    if ($LASTEXITCODE -ne 0) {
        throw 'No fue posible iniciar los servicios.'
    }
    & (Join-Path $PSScriptRoot 'health-check.ps1') -WaitSeconds $WaitSeconds
} finally {
    Pop-Location
}
