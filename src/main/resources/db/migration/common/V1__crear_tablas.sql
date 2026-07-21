CREATE TABLE roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(30) NOT NULL,
    descripcion VARCHAR(150),
    CONSTRAINT uk_roles_nombre UNIQUE (nombre)
);

CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    rol_id BIGINT NOT NULL,
    username VARCHAR(60) NOT NULL,
    password_hash VARCHAR(60) NOT NULL,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_usuarios_username UNIQUE (username),
    CONSTRAINT fk_usuarios_rol FOREIGN KEY (rol_id) REFERENCES roles (id)
);

CREATE INDEX idx_usuarios_rol ON usuarios (rol_id);

CREATE TABLE clientes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    email VARCHAR(150),
    creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_clientes_telefono ON clientes (telefono);
CREATE INDEX idx_clientes_email ON clientes (email);

CREATE TABLE categorias (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(60) NOT NULL,
    descripcion VARCHAR(200),
    orden_visual INTEGER NOT NULL DEFAULT 0,
    activa BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT uk_categorias_nombre UNIQUE (nombre),
    CONSTRAINT ck_categorias_orden CHECK (orden_visual >= 0)
);

CREATE TABLE productos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    categoria_id BIGINT NOT NULL,
    nombre VARCHAR(120) NOT NULL,
    descripcion VARCHAR(500) NOT NULL,
    precio DECIMAL(10, 2) NOT NULL,
    ruta_imagen VARCHAR(255) NOT NULL,
    disponible BOOLEAN NOT NULL DEFAULT TRUE,
    calorias INTEGER,
    proteinas_gramos DECIMAL(6, 2),
    carbohidratos_gramos DECIMAL(6, 2),
    grasas_gramos DECIMAL(6, 2),
    creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_productos_nombre UNIQUE (nombre),
    CONSTRAINT ck_productos_precio CHECK (precio > 0),
    CONSTRAINT ck_productos_calorias CHECK (calorias IS NULL OR calorias >= 0),
    CONSTRAINT ck_productos_proteinas CHECK (proteinas_gramos IS NULL OR proteinas_gramos >= 0),
    CONSTRAINT ck_productos_carbohidratos CHECK (carbohidratos_gramos IS NULL OR carbohidratos_gramos >= 0),
    CONSTRAINT ck_productos_grasas CHECK (grasas_gramos IS NULL OR grasas_gramos >= 0),
    CONSTRAINT fk_productos_categoria FOREIGN KEY (categoria_id) REFERENCES categorias (id)
);

CREATE INDEX idx_productos_categoria ON productos (categoria_id);
CREATE INDEX idx_productos_disponible ON productos (disponible);

CREATE TABLE estados_pedido (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(30) NOT NULL,
    nombre VARCHAR(60) NOT NULL,
    orden_flujo INTEGER NOT NULL,
    CONSTRAINT uk_estados_pedido_codigo UNIQUE (codigo),
    CONSTRAINT uk_estados_pedido_nombre UNIQUE (nombre),
    CONSTRAINT ck_estados_pedido_orden CHECK (orden_flujo >= 0)
);

CREATE TABLE pedidos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cliente_id BIGINT NOT NULL,
    estado_pedido_id BIGINT NOT NULL,
    codigo_recojo VARCHAR(20) NOT NULL,
    observaciones VARCHAR(500),
    subtotal DECIMAL(12, 2) NOT NULL,
    total DECIMAL(12, 2) NOT NULL,
    creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version BIGINT NOT NULL DEFAULT 0,
    CONSTRAINT uk_pedidos_codigo_recojo UNIQUE (codigo_recojo),
    CONSTRAINT ck_pedidos_subtotal CHECK (subtotal >= 0),
    CONSTRAINT ck_pedidos_total CHECK (total >= 0),
    CONSTRAINT fk_pedidos_cliente FOREIGN KEY (cliente_id) REFERENCES clientes (id),
    CONSTRAINT fk_pedidos_estado FOREIGN KEY (estado_pedido_id) REFERENCES estados_pedido (id)
);

CREATE INDEX idx_pedidos_cliente ON pedidos (cliente_id);
CREATE INDEX idx_pedidos_estado ON pedidos (estado_pedido_id);
CREATE INDEX idx_pedidos_creado_en ON pedidos (creado_en);

CREATE TABLE detalles_pedido (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    pedido_id BIGINT NOT NULL,
    producto_id BIGINT NOT NULL,
    nombre_producto VARCHAR(120) NOT NULL,
    precio_unitario DECIMAL(10, 2) NOT NULL,
    cantidad INTEGER NOT NULL,
    observaciones VARCHAR(300),
    subtotal DECIMAL(12, 2) NOT NULL,
    CONSTRAINT ck_detalles_precio CHECK (precio_unitario > 0),
    CONSTRAINT ck_detalles_cantidad CHECK (cantidad > 0),
    CONSTRAINT ck_detalles_subtotal CHECK (subtotal >= 0),
    CONSTRAINT fk_detalles_pedido FOREIGN KEY (pedido_id) REFERENCES pedidos (id) ON DELETE CASCADE,
    CONSTRAINT fk_detalles_producto FOREIGN KEY (producto_id) REFERENCES productos (id)
);

CREATE INDEX idx_detalles_pedido ON detalles_pedido (pedido_id);
CREATE INDEX idx_detalles_producto ON detalles_pedido (producto_id);

CREATE TABLE pagos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    pedido_id BIGINT NOT NULL,
    metodo VARCHAR(20) NOT NULL,
    estado VARCHAR(20) NOT NULL,
    monto DECIMAL(12, 2) NOT NULL,
    referencia VARCHAR(60),
    procesado_en TIMESTAMP,
    CONSTRAINT uk_pagos_pedido UNIQUE (pedido_id),
    CONSTRAINT uk_pagos_referencia UNIQUE (referencia),
    CONSTRAINT ck_pagos_metodo CHECK (metodo IN ('TARJETA', 'YAPE', 'EFECTIVO')),
    CONSTRAINT ck_pagos_estado CHECK (estado IN ('PENDIENTE', 'APROBADO', 'RECHAZADO')),
    CONSTRAINT ck_pagos_monto CHECK (monto >= 0),
    CONSTRAINT fk_pagos_pedido FOREIGN KEY (pedido_id) REFERENCES pedidos (id) ON DELETE CASCADE
);

CREATE INDEX idx_pagos_estado ON pagos (estado);
