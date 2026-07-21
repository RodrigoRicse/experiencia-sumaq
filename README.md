# Experiencia Sumaq

Aplicación web para la gestión de pedidos de Experiencia Sumaq. La nueva base usa Java 21, Spring Boot, Thymeleaf, Spring Data JPA, Flyway y MySQL.

## Estado

La estructura base de Spring Boot y Maven Wrapper está creada. El modelo, las migraciones, Docker Compose y el MVP se incorporarán en las siguientes etapas.

El prototipo React/Vite se conserva sin modificaciones en `prototipo-original/` y se utiliza únicamente como referencia visual y funcional.

## Requisitos

- Java 21 o un JDK posterior compatible.
- Docker Desktop con Docker Compose.
- Acceso a Internet en la primera ejecución para descargar dependencias Maven.

El proyecto compila con nivel Java 21. El equipo actual dispone de JDK 26; Spring Boot 4.1.0 declara compatibilidad hasta Java 26, pero la verificación efectiva se registra al ejecutar Maven Wrapper.

## Verificación inicial

En Windows:

```powershell
.\mvnw.cmd --version
.\mvnw.cmd test
```

En Linux o macOS:

```bash
./mvnw --version
./mvnw test
```

## Configuración

Las credenciales se suministrarán mediante variables de entorno. Usa `.env.example` como referencia y no confirmes archivos `.env` ni `application-local.yml`.

## Estructura

- `src/main/java/com/sumaq`: arquitectura por capas.
- `src/main/resources/templates`: vistas Thymeleaf.
- `src/main/resources/static`: CSS, JavaScript e imágenes locales.
- `src/main/resources/db/migration`: migraciones Flyway.
- `database`: recursos auxiliares de base de datos.
- `docs`: documentación técnica progresiva.
- `scripts`: automatización operativa progresiva.
- `prototipo-original`: referencia React/Vite inmutable.

## Pendiente

- Docker Compose y MySQL.
- Migraciones V1, V2 y V3.
- Entidades, repositorios, servicios y pruebas del dominio.
- Migración de vistas y recursos visuales.
- Flujos de cliente, cocina, caja y administración.
- Seguridad por roles, monitoreo, despliegue y documentación operativa.
