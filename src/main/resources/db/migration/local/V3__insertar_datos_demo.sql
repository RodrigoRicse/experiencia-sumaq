INSERT INTO usuarios (rol_id, username, password_hash, nombres, apellidos, activo) VALUES
    ((SELECT id FROM roles WHERE nombre = 'ADMINISTRADOR'), 'admin', '$2a$10$DSOcOfdKrfKqNSkMJo5FUeYYg8PH.iq0IbgtZrKR4iNPJEpHCjNL.', 'Ana', 'Administradora', TRUE),
    ((SELECT id FROM roles WHERE nombre = 'COCINA'), 'cocina', '$2a$10$DSOcOfdKrfKqNSkMJo5FUeYYg8PH.iq0IbgtZrKR4iNPJEpHCjNL.', 'Carlos', 'Cocina', TRUE),
    ((SELECT id FROM roles WHERE nombre = 'CAJA'), 'caja', '$2a$10$DSOcOfdKrfKqNSkMJo5FUeYYg8PH.iq0IbgtZrKR4iNPJEpHCjNL.', 'Camila', 'Caja', TRUE);

INSERT INTO productos (categoria_id, nombre, descripcion, precio, ruta_imagen, disponible, calorias, proteinas_gramos, carbohidratos_gramos, grasas_gramos) VALUES
    ((SELECT id FROM categorias WHERE nombre = 'Entradas'), 'Ceviche de Hongos Silvestres', 'Hongos marinados en cítricos andinos con ají limo y cilantro fresco.', 28.00, '/img/productos/ceviche-hongos.webp', TRUE, 210, 8.00, 25.00, 4.00),
    ((SELECT id FROM categorias WHERE nombre = 'Entradas'), 'Hummus de Tarwi y Quinua', 'Crema de tarwi y quinua acompañada de tostadas de masa madre.', 22.00, '/img/productos/hummus-tarwi.webp', TRUE, 320, 12.00, 35.00, 15.00),
    ((SELECT id FROM categorias WHERE nombre = 'Fondos'), 'Quinotto de Setas Andinas', 'Quinua cremosa con setas, queso andino y hierbas aromáticas.', 38.00, '/img/productos/quinotto-setas.webp', TRUE, 480, 18.00, 62.00, 16.00),
    ((SELECT id FROM categorias WHERE nombre = 'Fondos'), 'Lomo Saltado Andino', 'Lomo salteado con vegetales, papas nativas y quinua graneada.', 44.00, '/img/productos/lomo-saltado.webp', TRUE, 610, 36.00, 58.00, 24.00),
    ((SELECT id FROM categorias WHERE nombre = 'Bebidas'), 'Chicha Morada de la Casa', 'Maíz morado, piña, canela y frutas de estación.', 12.00, '/img/productos/chicha-morada.webp', TRUE, 120, 1.00, 30.00, 0.00),
    ((SELECT id FROM categorias WHERE nombre = 'Bebidas'), 'Limonada de Muña', 'Limonada fresca aromatizada con muña andina.', 10.00, '/img/productos/limonada-muna.webp', TRUE, 95, 0.00, 24.00, 0.00),
    ((SELECT id FROM categorias WHERE nombre = 'Postres'), 'Mousse de Cacao y Aguaymanto', 'Cacao peruano con salsa de aguaymanto ligeramente ácida.', 18.00, '/img/productos/mousse-cacao.webp', TRUE, 340, 6.00, 42.00, 17.00),
    ((SELECT id FROM categorias WHERE nombre = 'Postres'), 'Cheesecake de Quinua', 'Cheesecake horneado con base crocante de quinua y frutos rojos.', 20.00, '/img/productos/cheesecake-quinua.webp', TRUE, 390, 8.00, 45.00, 20.00);
