Set-StrictMode -Version Latest

function Get-SumaqRoot {
    return (Resolve-Path (Join-Path $PSScriptRoot '..')).Path
}

function Read-SumaqEnvironment {
    param([string]$ProjectRoot = (Get-SumaqRoot))

    $environmentFile = Join-Path $ProjectRoot '.env'
    if (-not (Test-Path -LiteralPath $environmentFile -PathType Leaf)) {
        throw "No existe $environmentFile. Copia .env.example como .env y configura sus valores."
    }

    $values = @{}
    foreach ($line in Get-Content -LiteralPath $environmentFile) {
        $trimmed = $line.Trim()
        if ($trimmed.Length -eq 0 -or $trimmed.StartsWith('#') -or -not $trimmed.Contains('=')) {
            continue
        }
        $parts = $trimmed -split '=', 2
        $values[$parts[0].Trim()] = $parts[1].Trim()
    }

    foreach ($required in @('MYSQL_DATABASE', 'MYSQL_USER', 'MYSQL_PASSWORD')) {
        if (-not $values.ContainsKey($required) -or [string]::IsNullOrWhiteSpace($values[$required])) {
            throw "Falta la variable $required en .env."
        }
    }
    return $values
}

function Assert-DockerAvailable {
    if (-not (Get-Command docker -ErrorAction SilentlyContinue)) {
        throw 'Docker no está disponible en PATH.'
    }
    & docker compose version | Out-Null
    if ($LASTEXITCODE -ne 0) {
        throw 'Docker Compose no está disponible.'
    }
}
