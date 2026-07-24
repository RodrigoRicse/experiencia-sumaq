# Reporte de pruebas

Fecha de referencia: 24 de julio de 2026.

## Cobertura automatizada actual

Resultado final de `1.0.0`: 39 pruebas, 0 fallos, 0 errores y 0 pruebas omitidas.

La cobertura final incluye edición dinámica del carrito, feedback de login, páginas HTTP 403, 404 y 5xx, protección de Actuator y publicación controlada de la información de la aplicación.

La suite incluye pruebas para:

- cálculo de total y código de recojo;
- disponibilidad de productos y carrito;
- registro, pago simulado y transición de pedidos;
- consultas y controladores de cocina y caja;
- administración de productos y reporte de ventas;
- validaciones inválidas, CSRF y acceso por roles;
- arranque del contexto, Flyway, JPA y renderizado Thymeleaf.

Comando de aceptación:

```powershell
.\mvnw.cmd clean package
```

## Validaciones manuales realizadas

- Flujo cliente → cocina → caja con MySQL real.
- Creación, edición y disponibilidad de producto administrativo con MySQL real.
- Autenticación correcta e incorrecta, acceso denegado por rol y validaciones del checkout.
- Endpoints Actuator `health`, `info` y `metrics` con sus restricciones de acceso.

## Validaciones de despliegue

- `docker compose config`: correcto.
- Imagen multietapa: construida correctamente con Temurin Java 21.0.11.
- MySQL y aplicación: estado `healthy`.
- Healthcheck HTTP: respuesta `UP`.
- Ejecución de aplicación: usuario no privilegiado `sumaq`.
- Backup y restauración: completados correctamente con el MySQL local.

## Mejoras futuras fuera del alcance 1.0.0

- Pruebas de repositorio dedicadas con Testcontainers/MySQL.
- Pruebas end-to-end automatizadas en navegador.
- Pruebas de carga y concurrencia.
- Escaneo automatizado de dependencias y contenedores.
