# Changelog

Todos los cambios relevantes de Experiencia Sumaq se documentan en este archivo.

El formato sigue [Keep a Changelog](https://keepachangelog.com/es-ES/1.1.0/) y el proyecto utiliza [Semantic Versioning](https://semver.org/lang/es/).

## [Unreleased]

### Pendiente

- Paneles operativos de cocina y caja.
- Administración de productos y reporte básico.
- Dockerfile y automatización operativa.

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

[Unreleased]: https://github.com/RodrigoRicse/experiencia-sumaq/compare/v0.4.0...HEAD
[0.4.0]: https://github.com/RodrigoRicse/experiencia-sumaq/compare/v0.3.0...v0.4.0
[0.3.0]: https://github.com/RodrigoRicse/experiencia-sumaq/compare/v0.2.0...v0.3.0
[0.2.0]: https://github.com/RodrigoRicse/experiencia-sumaq/compare/v0.1.0...v0.2.0
[0.1.0]: https://github.com/RodrigoRicse/experiencia-sumaq/releases/tag/v0.1.0
