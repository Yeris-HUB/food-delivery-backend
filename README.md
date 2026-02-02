# Food Delivery Backend API

Backend REST API para una aplicación de pedidos de comida a domicilio, construido con Spring Boot 3.5.10, Java 21, PostgreSQL y JWT Authentication.

## 🚀 Características

- ✅ Autenticación JWT con Spring Security
- ✅ 16 Endpoints REST API
- ✅ 5 Modelos de datos (User, Restaurant, Product, Order, OrderItem)
- ✅ Repositorios JPA con consultas personalizadas
- ✅ Servicios de negocio completos
- ✅ CORS configurado para frontend React
- ✅ BCrypt para encriptación de contraseñas
- ✅ PostgreSQL como base de datos
- ✅ Datos de prueba incluidos

## 📋 Requisitos

- Java 21+
- PostgreSQL 12+
- Gradle 8.14.4 (incluido con wrapper)

## 🔧 Instalación

### 1. Clonar el repositorio

```bash
git clone https://github.com/Yeris-HUB/food-delivery-backend.git
cd food-delivery-backend
```

### 2. Configurar PostgreSQL

Crear la base de datos:

```bash
# En Linux/Mac
sudo -u postgres psql -c "CREATE DATABASE food_delivery;"
sudo -u postgres psql -c "ALTER USER postgres PASSWORD 'postgres';"

# En Windows (PowerShell como administrador)
# Asumiendo que PostgreSQL está en el PATH
psql -U postgres -c "CREATE DATABASE food_delivery;"
```

### 3. Configurar application.properties (opcional)

El archivo `src/main/resources/application.properties` ya está configurado con valores por defecto:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/food_delivery
spring.datasource.username=postgres
spring.datasource.password=postgres
jwt.secret=mySecretKeyForJwtTokenGeneration12345678901234567890
jwt.expiration=86400000
server.port=8080
```

Si tu configuración de PostgreSQL es diferente, actualiza estos valores.

### 4. Compilar el proyecto

```bash
./gradlew build
```

En Windows:
```bash
gradlew.bat build
```

### 5. Ejecutar la aplicación

```bash
./gradlew bootRun
```

En Windows:
```bash
gradlew.bat bootRun
```

La aplicación estará disponible en: `http://localhost:8080`

### 6. Cargar datos de prueba (opcional)

```bash
psql -U postgres -d food_delivery -f datos_prueba.sql
```

Esto insertará:
- 1 usuario de prueba (test2@test.com / 123456)
- 5 restaurantes
- 18 productos

## 📚 API Endpoints

### Autenticación (Públicos)

#### POST `/api/auth/register`
Registrar un nuevo usuario.

**Request:**
```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "password123",
  "phone": "+1234567890",
  "address": "123 Main St"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "user": {
    "id": 1,
    "name": "John Doe",
    "email": "john@example.com",
    "phone": "+1234567890",
    "address": "123 Main St"
  }
}
```

#### POST `/api/auth/login`
Iniciar sesión.

**Request:**
```json
{
  "email": "john@example.com",
  "password": "password123"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "user": { ... }
}
```

### Restaurantes (Públicos)

#### GET `/api/restaurants`
Obtener todos los restaurantes.

**Response:**
```json
[
  {
    "id": 1,
    "name": "Burger King",
    "category": "Hamburguesas",
    "rating": 4.5,
    "deliveryTime": "25-35 min",
    "deliveryFee": 2.99,
    "minOrder": 10.0,
    "image": "https://...",
    "description": "Las mejores hamburguesas",
    "createdAt": "2026-02-02T..."
  }
]
```

#### GET `/api/restaurants/{id}`
Obtener un restaurante por ID.

#### GET `/api/restaurants/category/{category}`
Obtener restaurantes por categoría.

**Ejemplo:** `/api/restaurants/category/Hamburguesas`

#### GET `/api/restaurants/search?name={name}`
Buscar restaurantes por nombre (case-insensitive).

**Ejemplo:** `/api/restaurants/search?name=sushi`

### Productos (Públicos)

#### GET `/api/products`
Obtener todos los productos.

#### GET `/api/products/{id}`
Obtener un producto por ID.

#### GET `/api/products/restaurant/{restaurantId}`
Obtener todos los productos de un restaurante.

#### GET `/api/products/restaurant/{restaurantId}/available`
Obtener solo productos disponibles de un restaurante.

### Usuarios (Requiere autenticación)

#### GET `/api/users/profile`
Obtener perfil del usuario autenticado.

**Headers:**
```
Authorization: Bearer {token}
```

**Response:**
```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john@example.com",
  "phone": "+1234567890",
  "address": "123 Main St"
}
```

#### PUT `/api/users/profile`
Actualizar perfil del usuario.

**Headers:**
```
Authorization: Bearer {token}
```

**Request:**
```json
{
  "name": "John Smith",
  "phone": "+9876543210",
  "address": "456 New St"
}
```

### Órdenes (Requiere autenticación)

#### GET `/api/orders`
Obtener todas las órdenes del usuario autenticado.

**Headers:**
```
Authorization: Bearer {token}
```

#### GET `/api/orders/{id}`
Obtener una orden específica del usuario.

**Headers:**
```
Authorization: Bearer {token}
```

#### POST `/api/orders`
Crear una nueva orden.

**Headers:**
```
Authorization: Bearer {token}
```

**Request:**
```json
{
  "items": [
    {
      "productId": 1,
      "restaurantId": 1,
      "quantity": 2,
      "price": 899,
      "productName": "Whopper",
      "productImage": "https://..."
    }
  ],
  "subtotal": 1798,
  "deliveryFee": 299,
  "total": 2097,
  "deliveryAddress": "123 Main St",
  "notes": "Extra ketchup"
}
```

**Nota:** Los precios se envían como enteros en centavos (899 = $8.99) desde el frontend. El backend los convierte automáticamente a BigDecimal.

#### PATCH `/api/orders/{id}/status`
Actualizar estado de una orden.

**Headers:**
```
Authorization: Bearer {token}
```

**Request:**
```json
{
  "status": "CONFIRMED"
}
```

**Estados válidos:** `PENDING`, `CONFIRMED`, `PREPARING`, `ON_THE_WAY`, `DELIVERED`, `CANCELLED`

## 🧪 Ejemplos de uso con cURL

### Registrar usuario
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Test User",
    "email": "test@example.com",
    "password": "password123",
    "phone": "+1234567890",
    "address": "123 Test St"
  }'
```

### Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "password123"
  }'
```

### Obtener restaurantes
```bash
curl http://localhost:8080/api/restaurants
```

### Obtener perfil (con JWT)
```bash
curl http://localhost:8080/api/users/profile \
  -H "Authorization: Bearer YOUR_TOKEN_HERE"
```

### Crear orden (con JWT)
```bash
curl -X POST http://localhost:8080/api/orders \
  -H "Authorization: Bearer YOUR_TOKEN_HERE" \
  -H "Content-Type: application/json" \
  -d '{
    "items": [
      {
        "productId": 1,
        "restaurantId": 1,
        "quantity": 2,
        "price": 899,
        "productName": "Whopper",
        "productImage": "https://via.placeholder.com/200"
      }
    ],
    "subtotal": 1798,
    "deliveryFee": 299,
    "total": 2097,
    "deliveryAddress": "123 Test St",
    "notes": "Extra ketchup"
  }'
```

## 🏗️ Arquitectura

```
src/main/java/com/proyectorestaurante/
├── controller/           # Controladores REST
│   ├── AuthController.java
│   ├── UserController.java
│   ├── RestaurantController.java
│   ├── ProductController.java
│   └── OrderController.java
├── dto/                 # Data Transfer Objects
│   ├── LoginRequest.java
│   ├── RegisterRequest.java
│   ├── AuthResponse.java
│   ├── UserDTO.java
│   ├── UpdateUserRequest.java
│   ├── CreateOrderRequest.java
│   └── OrderItemRequest.java
├── model/              # Entidades JPA
│   ├── User.java
│   ├── Restaurant.java
│   ├── Product.java
│   ├── Order.java
│   ├── OrderItem.java
│   └── OrderStatus.java
├── repository/         # Repositorios JPA
│   ├── UserRepository.java
│   ├── RestaurantRepository.java
│   ├── ProductRepository.java
│   ├── OrderRepository.java
│   └── OrderItemRepository.java
├── security/          # Configuración de seguridad
│   ├── JwtTokenProvider.java
│   ├── JwtAuthenticationFilter.java
│   └── SecurityConfig.java
├── service/          # Lógica de negocio
│   ├── AuthService.java
│   ├── UserService.java
│   ├── RestaurantService.java
│   ├── ProductService.java
│   └── OrderService.java
└── FoodDeliveryBackendApplication.java
```

## 🔒 Seguridad

- **JWT Authentication:** Tokens válidos por 24 horas
- **BCrypt Password Encryption:** Contraseñas hasheadas con BCrypt
- **CORS:** Configurado para `localhost:3000` (frontend React)
- **Endpoints públicos:** `/api/auth/**`, `/api/restaurants/**`, `/api/products/**`
- **Endpoints protegidos:** `/api/users/**`, `/api/orders/**`

## 🐛 Troubleshooting

### Error: Port 8080 already in use

**Windows:**
```powershell
# Ver procesos en puerto 8080
netstat -ano | findstr :8080

# Detener proceso (reemplazar PID)
taskkill /PID <PID> /F
```

**Linux/Mac:**
```bash
# Ver procesos en puerto 8080
lsof -i :8080

# Detener proceso
kill -9 <PID>
```

O cambiar el puerto en `application.properties`:
```properties
server.port=8081
```

### Error: Connection to database failed

Verificar que PostgreSQL esté ejecutándose:

**Windows:**
```powershell
# Verificar servicio
Get-Service postgresql*

# Iniciar servicio
Start-Service postgresql-x64-XX
```

**Linux:**
```bash
sudo service postgresql status
sudo service postgresql start
```

### Error: Invalid credentials al hacer login

Asegúrate de que la contraseña esté hasheada correctamente en la base de datos. Si insertaste datos manualmente, usa el endpoint de registro para crear usuarios con contraseñas encriptadas automáticamente.

## 📄 Licencia

Este proyecto es de código abierto y está disponible bajo la licencia MIT.

## 👥 Contribución

Las contribuciones son bienvenidas. Por favor, abre un issue o pull request para sugerencias o mejoras.

## 📞 Contacto

Para preguntas o soporte, por favor abre un issue en el repositorio de GitHub.
