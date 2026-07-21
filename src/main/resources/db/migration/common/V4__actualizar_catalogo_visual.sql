UPDATE roles SET descripcion = 'Acceso a la administración de productos y reportes'
WHERE nombre = 'ADMINISTRADOR';

UPDATE roles SET descripcion = 'Gestión de preparación de pedidos'
WHERE nombre = 'COCINA';

UPDATE estados_pedido SET nombre = 'En preparación'
WHERE codigo = 'EN_PREPARACION';

UPDATE productos
SET descripcion = 'Hongos marinados en cítricos andinos con ají limo y cilantro fresco.',
    ruta_imagen = '/img/productos/entradas.svg'
WHERE nombre = 'Ceviche de Hongos Silvestres';

UPDATE productos
SET descripcion = 'Crema de tarwi y quinua acompañada de tostadas de masa madre.',
    ruta_imagen = '/img/productos/entradas.svg'
WHERE nombre = 'Hummus de Tarwi y Quinua';

UPDATE productos SET ruta_imagen = '/img/productos/fondos.svg'
WHERE nombre IN ('Quinotto de Setas Andinas', 'Lomo Saltado Andino');

UPDATE productos
SET descripcion = 'Maíz morado, piña, canela y frutas de estación.',
    ruta_imagen = '/img/productos/bebidas.svg'
WHERE nombre = 'Chicha Morada de la Casa';

UPDATE productos
SET descripcion = 'Limonada fresca aromatizada con muña andina.',
    ruta_imagen = '/img/productos/bebidas.svg'
WHERE nombre = 'Limonada de Muña';

UPDATE productos
SET descripcion = 'Cacao peruano con salsa de aguaymanto ligeramente ácida.',
    ruta_imagen = '/img/productos/postres.svg'
WHERE nombre = 'Mousse de Cacao y Aguaymanto';

UPDATE productos SET ruta_imagen = '/img/productos/postres.svg'
WHERE nombre = 'Cheesecake de Quinua';
