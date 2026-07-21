[CmdletBinding()]
param(
    [string]$Url = 'http://localhost:8080/actuator/health',
    [ValidateRange(0, 600)]
    [int]$WaitSeconds = 0
)

$ErrorActionPreference = 'Stop'
$deadline = (Get-Date).AddSeconds($WaitSeconds)

do {
    try {
        $response = Invoke-RestMethod -Uri $Url -Method Get -TimeoutSec 5
        if ($response.status -eq 'UP') {
            Write-Host "Experiencia Sumaq está disponible: $Url"
            exit 0
        }
    } catch {
        if ((Get-Date) -ge $deadline) {
            throw "El healthcheck no respondió correctamente en $Url. $($_.Exception.Message)"
        }
    }
    Start-Sleep -Seconds 3
} while ((Get-Date) -lt $deadline)

throw "El healthcheck no alcanzó el estado UP en $WaitSeconds segundos."
