-- Sample data for Food Delivery Backend
-- Run this script after the application has created the tables

-- Clear existing data (optional)
DELETE FROM order_items;
DELETE FROM orders;
DELETE FROM products;
DELETE FROM restaurants;
DELETE FROM users;

-- Reset sequences
ALTER SEQUENCE restaurants_id_seq RESTART WITH 1;
ALTER SEQUENCE products_id_seq RESTART WITH 1;
ALTER SEQUENCE users_id_seq RESTART WITH 1;
ALTER SEQUENCE orders_id_seq RESTART WITH 1;
ALTER SEQUENCE order_items_id_seq RESTART WITH 1;

-- Insert test user (password: 123456 with BCrypt)
-- You can generate BCrypt passwords at: https://bcrypt-generator.com/
INSERT INTO users (name, email, password, phone, address, created_at, updated_at)
VALUES ('Test User', 'test@test.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', '+1234567890', '123 Main St, City', NOW(), NOW());

-- Insert restaurants
INSERT INTO restaurants (name, category, rating, delivery_time, delivery_fee, min_order, image, description, created_at)
VALUES 
('Burger King', 'Hamburguesas', 4.5, '25-35 min', 2.99, 10.00, 'https://via.placeholder.com/300x200?text=Burger+King', 'Las mejores hamburguesas a la parrilla', NOW()),
('Pizza Hut', 'Pizza', 4.2, '30-40 min', 3.50, 15.00, 'https://via.placeholder.com/300x200?text=Pizza+Hut', 'Pizzas artesanales con los mejores ingredientes', NOW()),
('Sushi Place', 'Japonesa', 4.8, '35-45 min', 4.00, 20.00, 'https://via.placeholder.com/300x200?text=Sushi+Place', 'Sushi fresco y auténtico', NOW()),
('Taco Bell', 'Mexicana', 4.0, '20-30 min', 2.50, 8.00, 'https://via.placeholder.com/300x200?text=Taco+Bell', 'Auténtica comida mexicana', NOW()),
('KFC', 'Pollo', 4.3, '25-35 min', 3.00, 12.00, 'https://via.placeholder.com/300x200?text=KFC', 'Pollo crujiente y delicioso', NOW());

-- Insert products for Burger King (id=1)
INSERT INTO products (restaurant_id, name, description, price, category, image, available, created_at)
VALUES 
(1, 'Whopper', 'Nuestra hamburguesa insignia con carne a la parrilla', 8.99, 'Hamburguesas', 'https://via.placeholder.com/200?text=Whopper', true, NOW()),
(1, 'Chicken Royale', 'Crujiente pollo empanizado con lechuga y mayonesa', 7.99, 'Hamburguesas', 'https://via.placeholder.com/200?text=Chicken+Royale', true, NOW()),
(1, 'Papas Fritas', 'Papas fritas crujientes', 3.50, 'Acompañamientos', 'https://via.placeholder.com/200?text=Papas', true, NOW()),
(1, 'Coca Cola', 'Bebida refrescante', 2.50, 'Bebidas', 'https://via.placeholder.com/200?text=Coca+Cola', true, NOW());

-- Insert products for Pizza Hut (id=2)
INSERT INTO products (restaurant_id, name, description, price, category, image, available, created_at)
VALUES 
(2, 'Pizza Pepperoni', 'Pizza clásica con pepperoni y queso mozzarella', 12.99, 'Pizzas', 'https://via.placeholder.com/200?text=Pepperoni', true, NOW()),
(2, 'Pizza Vegetariana', 'Pizza con vegetales frescos', 11.99, 'Pizzas', 'https://via.placeholder.com/200?text=Vegetariana', true, NOW()),
(2, 'Pizza Hawaiana', 'Pizza con jamón y piña', 12.50, 'Pizzas', 'https://via.placeholder.com/200?text=Hawaiana', true, NOW()),
(2, 'Pan de Ajo', 'Delicioso pan con mantequilla de ajo', 4.99, 'Acompañamientos', 'https://via.placeholder.com/200?text=Pan+de+Ajo', true, NOW());

-- Insert products for Sushi Place (id=3)
INSERT INTO products (restaurant_id, name, description, price, category, image, available, created_at)
VALUES 
(3, 'California Roll', 'Roll clásico con cangrejo y aguacate', 9.99, 'Rolls', 'https://via.placeholder.com/200?text=California+Roll', true, NOW()),
(3, 'Spicy Tuna Roll', 'Roll picante de atún', 11.99, 'Rolls', 'https://via.placeholder.com/200?text=Spicy+Tuna', true, NOW()),
(3, 'Sashimi Mix', 'Variedad de pescado fresco', 15.99, 'Sashimi', 'https://via.placeholder.com/200?text=Sashimi', true, NOW()),
(3, 'Edamame', 'Frijoles de soya cocidos al vapor', 4.50, 'Acompañamientos', 'https://via.placeholder.com/200?text=Edamame', true, NOW());

-- Insert products for Taco Bell (id=4)
INSERT INTO products (restaurant_id, name, description, price, category, image, available, created_at)
VALUES 
(4, 'Taco Carne Asada', 'Taco con carne asada y guacamole', 3.99, 'Tacos', 'https://via.placeholder.com/200?text=Taco+Carne', true, NOW()),
(4, 'Burrito Supreme', 'Burrito con carne, frijoles y queso', 7.99, 'Burritos', 'https://via.placeholder.com/200?text=Burrito', true, NOW()),
(4, 'Quesadilla', 'Tortilla con queso derretido', 5.99, 'Quesadillas', 'https://via.placeholder.com/200?text=Quesadilla', true, NOW());

-- Insert products for KFC (id=5)
INSERT INTO products (restaurant_id, name, description, price, category, image, available, created_at)
VALUES 
(5, 'Pollo Frito 4 Piezas', 'Cuatro piezas de pollo crujiente', 9.99, 'Pollo', 'https://via.placeholder.com/200?text=Pollo+Frito', true, NOW()),
(5, 'Alitas Picantes', 'Alitas de pollo picantes', 8.99, 'Pollo', 'https://via.placeholder.com/200?text=Alitas', true, NOW()),
(5, 'Ensalada Coleslaw', 'Ensalada de col fresca', 3.50, 'Acompañamientos', 'https://via.placeholder.com/200?text=Coleslaw', true, NOW());

-- Verification queries
SELECT 'Users:' as table_name, COUNT(*) as count FROM users
UNION ALL
SELECT 'Restaurants:', COUNT(*) FROM restaurants
UNION ALL
SELECT 'Products:', COUNT(*) FROM products;

-- Show all restaurants
SELECT id, name, category, rating, delivery_time FROM restaurants;

-- Show products by restaurant
SELECT r.name as restaurant, p.name as product, p.price 
FROM products p 
JOIN restaurants r ON p.restaurant_id = r.id 
ORDER BY r.id, p.id;
