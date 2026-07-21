# Experiencia Sumaq

Aplicación web para gestionar el catálogo, los pedidos y la operación de cocina, caja y administración de Experiencia Sumaq.

El prototipo React/Vite está preservado sin modificaciones en `prototipo-original/` y se utiliza exclusivamente como referencia visual y funcional.

## Estado actual

Implementado:

- Spring Boot 4.1.0 con compilación Java 21.
- Maven Wrapper 3.9.16.
- Arquitectura por capas en `com.sumaq`.
- MySQL 8.4 mediante Docker Compose, volumen persistente y healthcheck.
- Flyway con migraciones V1, V2 y V3.
- Entidades, relaciones, restricciones y repositorios JPA.
- Servicios para productos, registro de pedidos, códigos de recojo y cambios de estado.
- Pago simulado aprobado o rechazado.
- Inicio y catálogo responsive migrados a Thymeleaf con imágenes locales.
- Carrito persistente por sesión con modificación de cantidades y validación de disponibilidad.
- Checkout público con validaciones, pago simulado, código de recojo y confirmación.
- Autenticación del personal basada en usuarios MySQL y contraseñas BCrypt.
- Actuator incorporado y preparado para `health`, `info` y `metrics`.
- Pruebas iniciales del dominio y validación del contexto.

Pendiente:

- Paneles de cocina, caja y administración.
- Página de acceso personalizada para el personal.
- Dockerfile de la aplicación y healthcheck del servicio web.
- Scripts de operación, backup y restauración.
- Documentación formal de despliegue, monitoreo, mantenimiento y seguridad.

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

Para detener MySQL sin eliminar sus datos:

```powershell
docker compose stop mysql
```

## Empaquetado

```powershell
.\mvnw.cmd clean package
```

El JAR ejecutable se genera en `target/experiencia-sumaq-0.0.1-SNAPSHOT.jar`.

## Base de datos y Flyway

Base: `experiencia_sumaq`.

- `V1__crear_tablas.sql`: tablas, claves, restricciones e índices.
- `V2__insertar_catalogos.sql`: roles, estados y categorías.
- `V3__insertar_datos_demo.sql`: usuarios y productos solo para el perfil local.

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

La política definitiva de acceso se incorporará con la configuración de seguridad: `health` será público y `info`/`metrics` estarán restringidos a administración.

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

Los procedimientos y scripts `backup-db.ps1` y `restore-db.ps1` están planificados para la etapa de mantenimiento. El volumen `mysql_data` conserva los datos cuando el contenedor se detiene o recrea.
