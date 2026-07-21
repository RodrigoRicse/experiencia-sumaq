INSERT INTO roles (nombre, descripcion) VALUES
    ('ADMINISTRADOR', 'Acceso a la administración de productos y reportes'),
    ('COCINA', 'Gestión de preparación de pedidos'),
    ('CAJA', 'Validación de pagos y entrega de pedidos');

INSERT INTO estados_pedido (codigo, nombre, orden_flujo) VALUES
    ('PENDIENTE', 'Pendiente', 10),
    ('EN_PREPARACION', 'En preparación', 20),
    ('LISTO', 'Listo para recojo', 30),
    ('ENTREGADO', 'Entregado', 40),
    ('CANCELADO', 'Cancelado', 50);

INSERT INTO categorias (nombre, descripcion, orden_visual, activa) VALUES
    ('Entradas', 'Platos ligeros para comenzar', 10, TRUE),
    ('Fondos', 'Platos principales con ingredientes andinos', 20, TRUE),
    ('Bebidas', 'Bebidas naturales y tradicionales', 30, TRUE),
    ('Postres', 'Postres inspirados en sabores peruanos', 40, TRUE);
