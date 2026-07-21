# Plan de mantenimiento

## Operación habitual

```powershell
.\scripts\start.ps1
.\scripts\health-check.ps1
.\scripts\stop.ps1
```

`stop.ps1` conserva los volúmenes. Los datos solo deben eliminarse mediante una operación explícita y previamente respaldada.

Si la política local de PowerShell bloquea los scripts, ejecutarlos con `powershell -ExecutionPolicy Bypass -File <script>`.

## Backups

Crear un backup consistente:

```powershell
.\scripts\backup-db.ps1
```

Los archivos se guardan en `database/backups/`, carpeta excluida de Git. Recomendación inicial:

- backup diario;
- retención local de 7 copias;
- copia cifrada fuera del servidor;
- prueba mensual de restauración en un entorno aislado.

Restaurar una copia:

```powershell
.\scripts\restore-db.ps1 -BackupFile .\database\backups\experiencia_sumaq-AAAAMMDD-HHMMSS.sql
```

El script acepta únicamente archivos dentro de `database/backups` y exige escribir `RESTAURAR`. La opción `-Force` debe reservarse para automatización controlada.

## Actualizaciones

- Revisar mensualmente Java, Spring Boot, MySQL e imágenes base.
- Ejecutar `mvnw clean package` y CI antes de actualizar una etiqueta.
- Aplicar cambios de esquema exclusivamente con migraciones Flyway nuevas.
- No modificar migraciones que ya fueron aplicadas.

## Recuperación

1. Detener escrituras de la aplicación.
2. Conservar una copia del estado actual.
3. Restaurar el último backup verificado.
4. Levantar servicios y comprobar Flyway y Actuator.
5. Recorrer un pedido de prueba y documentar el incidente.
