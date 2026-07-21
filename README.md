# Experiencia Sumaq

[![CI](https://github.com/RodrigoRicse/experiencia-sumaq/actions/workflows/ci.yml/badge.svg)](https://github.com/RodrigoRicse/experiencia-sumaq/actions/workflows/ci.yml)

Versión actual: `0.9.0`.

Aplicación web para gestionar el catálogo, los pedidos y la operación de cocina, caja y administración de Experiencia Sumaq.

El prototipo React/Vite está preservado sin modificaciones en `prototipo-original/` y se utiliza exclusivamente como referencia visual y funcional.

Consulta [CHANGELOG.md](CHANGELOG.md) para revisar las versiones y [CONTRIBUTING.md](CONTRIBUTING.md) antes de proponer cambios.

## Estado actual

Implementado:

- Spring Boot 4.1.0 con compilación Java 21.
- Maven Wrapper 3.9.16.
- Arquitectura por capas en `com.sumaq`.
- MySQL 8.4 mediante Docker Compose, volumen persistente y healthcheck.
- Flyway con migraciones versionadas V1, V2, V3 y V4.
- Entidades, relaciones, restricciones y repositorios JPA.
- Servicios para productos, registro de pedidos, códigos de recojo y cambios de estado.
- Pago simulado aprobado o rechazado.
- Inicio y catálogo responsive migrados a Thymeleaf con imágenes locales.
- Carrito persistente por sesión con modificación de cantidades y validación de disponibilidad.
- Checkout público con validaciones, pago simulado, código de recojo y confirmación.
- Autenticación del personal basada en usuarios MySQL y contraseñas BCrypt.
- Acceso personalizado para el personal con autorización por roles.
- Panel de cocina con pedidos pendientes, en preparación y listos.
- Búsqueda y entrega de pedidos desde caja mediante código de recojo.
- Administración de productos con creación, edición y cambio de disponibilidad.
- Reporte básico de pedidos, ingresos aprobados y ventas por categoría.
- Actuator incorporado y preparado para `health`, `info` y `metrics`.
- Dockerfile multietapa con Java 21 y usuario sin privilegios.
- Docker Compose con aplicación, MySQL, healthchecks y volúmenes persistentes.
- Logs rotados con Logback y configuración mediante variables de entorno.
- Scripts PowerShell de operación, salud, backup y restauración.
- Planes de despliegue, monitoreo, mantenimiento, pruebas y seguridad.
- Dependabot para Maven, Docker y GitHub Actions.
- Pruebas del dominio, servicios, controladores y autorización por roles.

Pendiente:

- Automatización de pruebas end-to-end en navegador y pruebas de carga.
- Proxy HTTPS, gestor externo de secretos y monitoreo centralizado para producción.
- Escaneo automático de vulnerabilidades de la imagen de contenedor.

## Requisitos

- Java 21 o un JDK posterior compatible.
- Docker Desktop con Docker Compose.
- Acceso a Internet en la primera ejecución para descargar Maven y sus dependencias.

El proyecto genera bytecode Java 21. También fue compilado, probado y ejecutado correctamente con el JDK 26.0.1 instalado en el equipo.

## Variables de entorno

Copia `.env.example` como `.env` y reemplaza todos los valores de ejemplo antes de levantar MySQL:

```powershell
Copy-Item .env.example .env
```

Docker Compose lee `.env` automáticamente. Spring Boot recibe sus credenciales exclusivamente mediante variables de entorno. En PowerShell:

```powershell
$env:DB_USER="experiencia_sumaq"
$env:DB_PASSWORD="tu_clave_local"
$env:SPRING_PROFILES_ACTIVE="local"
```

Variables principales:

| Variable | Uso |
| --- | --- |
| `MYSQL_DATABASE` | Base creada por MySQL |
| `MYSQL_USER` | Usuario de desarrollo de MySQL |
| `MYSQL_PASSWORD` | Contraseña del usuario MySQL |
| `MYSQL_ROOT_PASSWORD` | Contraseña administrativa del contenedor |
| `MYSQL_PORT` | Puerto publicado, normalmente 3306 |
| `DB_URL` | URL JDBC utilizada por Spring |
| `DB_USER` | Usuario JDBC |
| `DB_PASSWORD` | Contraseña JDBC |
| `SPRING_PROFILES_ACTIVE` | Perfil `local`, `test` o `prod` |
| `SERVER_PORT` | Puerto HTTP, normalmente 8080 |

No confirmes `.env`, `application-local.yml`, credenciales ni archivos locales.

## Ejecución local

1. Configura `.env` y las variables para Spring.
2. Levanta MySQL:

```powershell
docker compose up -d mysql
docker compose ps
```

3. Ejecuta las pruebas:

```powershell
.\mvnw.cmd clean test
```

4. Inicia la aplicación:

```powershell
.\mvnw.cmd spring-boot:run
```

La aplicación usará `http://localhost:8080`.

Rutas públicas principales:

- `/`: inicio.
- `/menu`: catálogo por categorías.
- `/pedido/checkout`: datos del cliente y pago simulado.
- `/pedido/confirmacion/{codigo}`: resultado y código de recojo.

Rutas del personal:

- `/login`: acceso del personal.
- `/cocina`: gestión de preparación para `COCINA` y `ADMINISTRADOR`.
- `/caja`: búsqueda y entrega para `CAJA` y `ADMINISTRADOR`.
- `/admin`: productos y reporte básico para `ADMINISTRADOR`.

Para detener MySQL sin eliminar sus datos:

```powershell
docker compose stop mysql
```

## Ejecución con Docker

Configura `.env` y levanta la aplicación completa:

```powershell
.\scripts\start.ps1
```

El comando construye la imagen Java 21, inicia MySQL y espera el healthcheck. Alternativamente:

```powershell
docker compose up -d --build
docker compose ps
```

Comprobar salud y detener sin eliminar datos:

```powershell
.\scripts\health-check.ps1
.\scripts\stop.ps1
```

Si Windows bloquea la ejecución de scripts, antepón `powershell -ExecutionPolicy Bypass -File`; por ejemplo: `powershell -ExecutionPolicy Bypass -File .\scripts\start.ps1`.

Para producción establece `SPRING_PROFILES_ACTIVE=prod`, utiliza una base separada sin datos demo, define secretos propios y publica la aplicación detrás de HTTPS. `APP_DB_URL` permite sobrescribir la URL JDBC interna de Compose.

## Empaquetado

```powershell
.\mvnw.cmd clean package
```

El JAR ejecutable se genera en `target/experiencia-sumaq-0.9.0.jar`.

## Base de datos y Flyway

Base: `experiencia_sumaq`.

- `V1__crear_tablas.sql`: tablas, claves, restricciones e índices.
- `V2__insertar_catalogos.sql`: roles, estados y categorías.
- `V3__insertar_datos_demo.sql`: usuarios y productos solo para el perfil local.
- `V4__actualizar_catalogo_visual.sql`: textos e imágenes locales del catálogo.

Hibernate usa `ddl-auto=validate`; Flyway es la autoridad del esquema.

Relaciones principales:

- `Rol` 1—N `Usuario`.
- `Categoria` 1—N `Producto`.
- `Cliente` 1—N `Pedido`.
- `EstadoPedido` 1—N `Pedido`.
- `Pedido` 1—N `DetallePedido`.
- `Producto` 1—N `DetallePedido`.
- `Pedido` 1—1 `Pago`.

## Usuarios locales de demostración

Los tres usuarios usan la contraseña local `Sumaq2026!`, almacenada únicamente como hash BCrypt en la migración demo.

| Usuario | Rol |
| --- | --- |
| `admin` | ADMINISTRADOR |
| `cocina` | COCINA |
| `caja` | CAJA |

No utilices estas credenciales en producción. El perfil `prod` no carga V3.

## Estados de pedido

Flujo permitido:

```text
PENDIENTE -> EN_PREPARACION -> LISTO -> ENTREGADO
     |              |
     +----------> CANCELADO
```

Un pedido solo puede pasar a `ENTREGADO` si su pago está aprobado.

## Pruebas

La suite actual comprueba:

- Cálculo del total de un pedido.
- Generación de código de recojo.
- Registro de pedido y pago simulado.
- Validación de disponibilidad de producto.
- Cambio válido de estado.
- Consulta operativa de pedidos para cocina y caja.
- Controladores de cocina y caja con protección CSRF.
- Restricciones de acceso entre los roles `COCINA` y `CAJA`.
- Creación y validación de productos administrativos.
- Cálculo del reporte de ventas aprobadas.
- Controlador administrativo y restricción del rol `ADMINISTRADOR`.
- Acceso público a `health` y protección administrativa de `info` y `metrics`.
- Arranque del contexto, migraciones Flyway y validación JPA con el perfil `test`.

Ejecución:

```powershell
.\mvnw.cmd test
```

## Monitoreo

Actuator está configurado para exponer:

- `/actuator/health`
- `/actuator/info`
- `/actuator/metrics`

`health` es público; `info` y `metrics` están restringidos al rol `ADMINISTRADOR`.

Los logs se escriben en consola y en el volumen Docker `app_logs`. La rotación conserva 14 días, limita cada archivo a 10 MB y el conjunto a 500 MB. Consulta [docs/plan-monitoreo.md](docs/plan-monitoreo.md).

## Arquitectura

- `controller`: controladores MVC y formularios.
- `service`: casos de uso, transacciones y reglas de negocio.
- `repository`: DAO mediante Spring Data JPA.
- `model`: entidades y objetos del dominio.
- `dto`: datos de entrada y salida con Jakarta Validation.
- `config`: seguridad y configuración transversal.
- `exception`: errores del dominio y manejo centralizado.
- `templates`: vistas Thymeleaf por área.
- `static`: CSS, JavaScript e imágenes locales.
- `db/migration`: migraciones versionadas.
- `docs`: documentación técnica progresiva.
- `scripts`: automatización operativa progresiva.

## Backup y restauración

Crear una copia consistente:

```powershell
.\scripts\backup-db.ps1
```

Restaurar una copia ubicada en `database/backups/`:

```powershell
.\scripts\restore-db.ps1 -BackupFile .\database\backups\experiencia_sumaq-AAAAMMDD-HHMMSS.sql
```

La restauración exige confirmación explícita. Consulta [docs/plan-mantenimiento.md](docs/plan-mantenimiento.md) antes de usarla.
