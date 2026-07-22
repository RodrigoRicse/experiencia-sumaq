# Changelog

Todos los cambios relevantes de Experiencia Sumaq se documentan en este archivo.

El formato sigue [Keep a Changelog](https://keepachangelog.com/es-ES/1.1.0/) y el proyecto utiliza [Semantic Versioning](https://semver.org/lang/es/).

## [Unreleased]

### Corregido

- Se hicieron explícitas las garantías de no nulidad en operaciones funcionales para evitar diagnósticos ambiguos de JDT.
- El error de acceso se limpia al editar las credenciales y el cierre de sesión utiliza el toast global fuera del formulario.

### Cambiado

- El resumen del checkout permite aumentar, reducir y quitar productos sin perder los datos escritos en el formulario.
- El campo de observaciones solo puede redimensionarse verticalmente y conserva límites adecuados para el diseño responsive.

### Pruebas

- Se añadieron pruebas MVC y de integración para la edición del carrito y su protección CSRF.

### Pendiente

- Pruebas end-to-end en navegador y de carga.
- Monitoreo centralizado y despliegue productivo con HTTPS.

## [0.9.0] - 2026-07-21

### Añadido

- Dockerfile multietapa con compilación y ejecución Java 21.
- Servicio de aplicación en Docker Compose con healthcheck y volumen de logs.
- Configuración Logback con rotación por fecha y tamaño.
- Scripts PowerShell para inicio, parada, salud, backup y restauración.
- Planes técnicos de despliegue, monitoreo y mantenimiento.
- Reportes iniciales de pruebas y seguridad.
- Actualizaciones automáticas de dependencias mediante Dependabot.

### Cambiado

- GitHub Actions actualizado a acciones basadas en Node.js 24.
- Cierre graceful y cookies seguras por defecto en el perfil `prod`.

### Pruebas

- 32 pruebas automatizadas aprobadas.
- Construcción multietapa y ejecución con Temurin Java 21 verificadas.
- Aplicación y MySQL verificados en estado `healthy`.
- Backup y restauración real validados conservando los datos.

### Seguridad

- Contenedor ejecutado con usuario sin privilegios y `no-new-privileges`.
- Healthcheck público; información y métricas restringidas a administración.
- Secretos excluidos del contexto de construcción mediante `.dockerignore`.

## [0.6.0] - 2026-07-21

### Añadido

- Panel administrativo responsive conectado a MySQL.
- Creación y edición de productos con validaciones del servidor.
- Cambio de disponibilidad sin eliminar el historial de productos.
- Reporte de pedidos, ingresos aprobados, ticket promedio y ventas por categoría.
- Búsqueda de productos en el navegador sin dependencias externas.

### Pruebas

- Pruebas de servicios para productos y reportes.
- Pruebas del controlador administrativo y formularios inválidos.
- Verificación del acceso exclusivo del rol `ADMINISTRADOR`.
- Flujo administrativo completo validado con MySQL real.

### Seguridad

- Recursos administrativos protegidos por rol y CSRF.
- Rutas de imágenes limitadas a recursos locales permitidos.
- Los pagos rechazados se excluyen de los indicadores de ventas.

## [0.5.0] - 2026-07-21

### Añadido

- Página de acceso personalizada para el personal interno.
- Panel de cocina con pedidos pendientes, en preparación y listos.
- Acciones para iniciar y completar la preparación de pedidos.
- Búsqueda en caja mediante código único de recojo.
- Verificación del pago y confirmación de entrega desde caja.
- DTO de consulta separados del modelo persistente para las vistas operativas.

### Pruebas

- Pruebas de servicio y controladores para los flujos de cocina y caja.
- Validación de acceso cruzado entre los roles `COCINA` y `CAJA`.
- Recorrido completo cliente, cocina y caja validado con MySQL real.

### Seguridad

- Rutas internas protegidas por rol mediante Spring Security.
- Cambios de estado protegidos con solicitudes POST y tokens CSRF.
- Contraseñas internas verificadas desde MySQL mediante BCrypt.

## [0.4.0] - 2026-07-21

### Añadido

- Carrito persistente por sesión.
- Actualización y eliminación de cantidades.
- Checkout con validaciones del servidor.
- Pago simulado aprobado o rechazado.
- Código único de recojo y pantalla de confirmación.
- Manejo centralizado de errores web.

### Corregido

- Los pedidos con pago rechazado quedan cancelados y no ingresan al flujo de cocina.

### Pruebas

- Cobertura de carrito, entradas inválidas y pago rechazado.
- Validación HTTP completa con sesión, CSRF y MySQL.

### Seguridad

- Formularios protegidos mediante CSRF.
- Precios y disponibilidad recalculados en el servidor.

## [0.3.0] - 2026-07-21

### Añadido

- Inicio y menú responsive migrados a Thymeleaf.
- Catálogo por categorías conectado a MySQL.
- Recursos SVG locales y navegación accesible.

### Seguridad

- Autenticación del personal mediante usuarios MySQL y BCrypt.
- Acceso público limitado al flujo del cliente y healthcheck.
- Rutas internas preparadas para autorización por roles.

## [0.2.0] - 2026-07-21

### Añadido

- Entidades, relaciones y repositorios Spring Data JPA.
- Servicios de productos, pedidos, estados, pagos y códigos de recojo.
- DTO con Jakarta Validation.
- Pruebas iniciales del dominio y servicios.

## [0.1.0] - 2026-07-21

### Añadido

- Prototipo React/Vite preservado como referencia inmutable.
- Base Spring Boot con Java 21 y Maven Wrapper.
- Docker Compose con MySQL, volumen persistente y healthcheck.
- Migraciones Flyway iniciales y datos locales de demostración.
- Estructura por capas y configuración por variables de entorno.

[Unreleased]: https://github.com/RodrigoRicse/experiencia-sumaq/compare/v0.9.0...HEAD
[0.9.0]: https://github.com/RodrigoRicse/experiencia-sumaq/compare/v0.6.0...v0.9.0
[0.6.0]: https://github.com/RodrigoRicse/experiencia-sumaq/compare/v0.5.0...v0.6.0
[0.5.0]: https://github.com/RodrigoRicse/experiencia-sumaq/compare/v0.4.0...v0.5.0
[0.4.0]: https://github.com/RodrigoRicse/experiencia-sumaq/compare/v0.3.0...v0.4.0
[0.3.0]: https://github.com/RodrigoRicse/experiencia-sumaq/compare/v0.2.0...v0.3.0
[0.2.0]: https://github.com/RodrigoRicse/experiencia-sumaq/compare/v0.1.0...v0.2.0
[0.1.0]: https://github.com/RodrigoRicse/experiencia-sumaq/releases/tag/v0.1.0
