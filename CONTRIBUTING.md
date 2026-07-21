# Contribuir a Experiencia Sumaq

## Flujo de trabajo

1. Actualiza `main` y comprueba que esté limpia.
2. Crea una rama con alcance único.
3. Implementa cambios pequeños y añade o actualiza pruebas.
4. Ejecuta `./mvnw clean package` o `.\mvnw.cmd clean package`.
5. Publica la rama y crea un pull request hacia `main`.
6. Integra únicamente cuando CI termine correctamente.

No realices desarrollo funcional directamente en `main`.

## Nombres de ramas

- `feature/nombre-funcionalidad`
- `fix/nombre-correccion`
- `test/nombre-prueba`
- `docs/nombre-documentacion`
- `chore/nombre-configuracion`

Usa minúsculas, palabras breves y guiones.

## Commits

Utiliza Conventional Commits:

- `feat:` funcionalidad nueva.
- `fix:` corrección de errores.
- `test:` pruebas nuevas o modificadas.
- `docs:` documentación.
- `chore:` configuración y mantenimiento.
- `refactor:` cambios internos sin modificar comportamiento.
- `security:` mejoras de seguridad.

Cada commit debe representar una unidad comprensible y verificable.

## Verificación local

```powershell
Copy-Item .env.example .env
docker compose up -d mysql
.\mvnw.cmd clean package
```

Las pruebas automatizadas usan el perfil `test` y H2 en modo compatible con MySQL. Antes de integrar cambios de persistencia, valida también las migraciones con MySQL local.

## Seguridad y datos locales

Nunca confirmes:

- Archivos `.env` o credenciales.
- Datos personales o pagos reales.
- `target`, `node_modules`, logs o backups.
- Configuraciones locales del IDE.

Los secretos se proporcionan exclusivamente mediante variables de entorno. Los datos y contraseñas demo no deben utilizarse en producción.

## Versiones

Actualiza `CHANGELOG.md` y `VERSION` cuando prepares una versión. Crea etiquetas anotadas únicamente desde una `main` compilada y con todas las pruebas correctas.
