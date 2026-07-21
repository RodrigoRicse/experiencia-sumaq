# Plan de monitoreo

## Señales disponibles

- `/actuator/health`: salud de aplicación y base de datos; acceso público para healthchecks.
- `/actuator/info`: información de la aplicación; requiere `ADMINISTRADOR`.
- `/actuator/metrics`: métricas de JVM, HTTP y conexiones; requiere `ADMINISTRADOR`.
- `docker compose ps`: estado y healthcheck de contenedores.
- Logs de consola y `/app/logs/experiencia-sumaq.log` dentro del volumen `app_logs`.

## Política de logs

Logback rota archivos diariamente o al alcanzar 10 MB, conserva 14 días y limita el conjunto a 500 MB. Se registran:

- creación de pedidos;
- cambios de estado y entregas;
- errores no controlados con referencia correlacionable.

No se deben registrar contraseñas, tokens CSRF, datos completos de tarjetas ni cuerpos de formularios sensibles.

## Revisión operativa

- Diaria: salud de servicios y errores `ERROR` recientes.
- Semanal: uso de disco de volúmenes, reinicios y tiempos de respuesta.
- Mensual: tendencia de memoria, conexiones, almacenamiento y dependencias.

## Alertas sugeridas

- Healthcheck distinto de `UP` durante más de dos minutos.
- Cinco reinicios de un contenedor en diez minutos.
- Uso de disco superior al 80 %.
- Tasa sostenida de respuestas HTTP 5xx.
- Fallo del backup programado.

## Pendientes

- Recolección centralizada con OpenTelemetry o Prometheus.
- Dashboard y alertas externas.
- Correlación mediante identificador por solicitud.
