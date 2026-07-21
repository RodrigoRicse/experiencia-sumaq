# Reporte de seguridad

Fecha de referencia: 21 de julio de 2026.

## Controles implementados

- Autenticación del personal con Spring Security y BCrypt.
- Autorización por roles `ADMINISTRADOR`, `COCINA` y `CAJA`.
- Flujo del cliente público y rutas internas protegidas.
- CSRF activo en operaciones que modifican estado.
- Jakarta Validation y reglas de dominio del lado del servidor.
- Credenciales obtenidas mediante variables de entorno.
- Perfil `prod` sin migración de usuarios de demostración.
- Contenedor de aplicación ejecutado con usuario sin privilegios y `no-new-privileges`.
- Cookies de sesión `HttpOnly`, `SameSite=Lax` y `Secure` por defecto en producción.
- Logs sin contraseñas ni información completa de pagos.

## Riesgos y decisiones

- Las credenciales de demostración son solo para `local`; deben cambiarse o excluirse en cualquier entorno compartido.
- Actuator `health` es público y muestra detalles únicamente cuando existe autorización.
- `info` y `metrics` requieren el rol administrador.
- El archivo `.env` no se versiona, pero sigue siendo un secreto local que requiere permisos adecuados.
- Docker Compose es apropiado para el MVP; no reemplaza un gestor de secretos ni una plataforma de alta disponibilidad.

## Pendientes

- Automatizar análisis de dependencias y de imagen.
- Añadir límites de intentos de autenticación y política de bloqueo.
- Configurar cabeceras CSP específicas y TLS en el proxy de producción.
- Rotar claves y documentar respuesta ante incidentes.

Este documento registra controles técnicos, pero no constituye una auditoría de seguridad independiente.
