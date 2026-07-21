[CmdletBinding()]
param()

$ErrorActionPreference = 'Stop'
. (Join-Path $PSScriptRoot '_common.ps1')

$projectRoot = Get-SumaqRoot
Assert-DockerAvailable
Push-Location $projectRoot
try {
    & docker compose stop
    if ($LASTEXITCODE -ne 0) {
        throw 'No fue posible detener los servicios.'
    }
    Write-Host 'Servicios detenidos. Los volúmenes y datos se conservaron.'
} finally {
    Pop-Location
}
