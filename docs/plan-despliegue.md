# Plan de despliegue

## Objetivo

Desplegar Experiencia Sumaq como una aplicación Spring Boot Java 21 y MySQL 8.4 mediante Docker Compose, con configuración externa, datos persistentes y comprobaciones de salud.

## Componentes

- `app`: JAR ejecutable construido en una imagen multietapa y ejecutado con un usuario sin privilegios.
- `mysql`: base MySQL con volumen `mysql_data`.
- `app_logs`: volumen de logs rotados de la aplicación.
- `sumaq_network`: red privada entre ambos servicios.

## Preparación

1. Instalar Docker Desktop y Docker Compose.
2. Copiar `.env.example` como `.env`.
3. Reemplazar todas las claves de ejemplo y revisar puertos.
4. Para producción usar `SPRING_PROFILES_ACTIVE=prod`, una base separada, secretos distintos y HTTPS en el proxy inverso.
5. Generar un backup antes de actualizar una instalación existente.

Los perfiles `local` y `prod` no deben compartir el mismo esquema: `local` registra la migración V3 con usuarios demo, mientras `prod` la excluye deliberadamente. Puede sobrescribirse la URL interna de Compose mediante `APP_DB_URL`.

## Despliegue local demostrable

```powershell
.\scripts\start.ps1
```

El script ejecuta `docker compose up -d --build` y espera a que `/actuator/health` informe `UP`.

Verificación manual:

```powershell
docker compose ps
docker compose logs app --tail 100
.\scripts\health-check.ps1
```

Cuando la política local de PowerShell impida ejecutar archivos `.ps1`, usar `powershell -ExecutionPolicy Bypass -File .\scripts\start.ps1` (y el mismo prefijo para los demás scripts).

## Actualización

1. Crear backup con `backup-db.ps1`.
2. Obtener la versión validada desde `main` o una etiqueta estable.
3. Ejecutar `docker compose build --pull app`.
4. Ejecutar `docker compose up -d`.
5. Confirmar healthcheck y recorrer inicio, pedido, cocina, caja y administración.

## Rollback

1. Volver a la etiqueta anterior.
2. Reconstruir y levantar `app` sin eliminar `mysql_data`.
3. Restaurar el backup únicamente si una migración de datos impide volver atrás.

Nunca usar `docker compose down -v` durante un rollback: elimina los volúmenes persistentes.

## Pendientes para producción

- Proxy inverso con TLS y renovación automática de certificados.
- Gestor externo de secretos.
- Registro de imágenes en un repositorio privado o controlado.
- Estrategia de alta disponibilidad y recuperación fuera del equipo local.
