oms
└── src/main/java/com/ecommerce/oms
├── controller
├── service
├── service/impl
├── entity
├── repo
└── OmsApplication.java

CREATE DATABASE oms;
USE oms;

Product Table
CREATE TABLE product (
id BIGINT PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(255),
price DECIMAL(10,2),
sku VARCHAR(50)
);

Inventory

CREATE TABLE inventory (
product_id BIGINT PRIMARY KEY,
available_qty INT NOT NULL,
reserved_qty INT NOT NULL DEFAULT 0,
updated_at DATETIME(6) NOT NULL,
version BIGINT DEFAULT 0
);

Customer
CREATE TABLE customer (
id BIGINT PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(255),
email VARCHAR(255)
);

Orders
CREATE TABLE orders (
id BIGINT PRIMARY KEY AUTO_INCREMENT,
customer_id BIGINT,
status VARCHAR(50),
created_at TIMESTAMP,
idempotency_key VARCHAR(255)
);

Order_Items
CREATE TABLE order_items (
id BIGINT PRIMARY KEY AUTO_INCREMENT,
order_id BIGINT,
product_id BIGINT,
qty INT,
price DECIMAL(10,2)
);

 4. Run the Application (Windows PowerShell)
Step 1 — Go to project folder
cd "C:\Users\DELL\Downloads\oms\oms"

Step 2 — Build the project
.\mvnw.cmd clean package -DskipTests

Step 3 — Run the service
java -jar target\oms.jar

 5. BASE URL
http://localhost:8080/api/v1

6. API Endpoints (Fully Working URLs)
 PRODUCT APIs
➤ Add Bulk Products

POST http://localhost:8080/api/v1/products/bulk

[
{ "id": 1, "name": "Laptop", "price": 50000, "sku": "LP001" },
{ "id": 2, "name": "Mobile", "price": 20000, "sku": "MB001" }
]

➤ Get All Products

GET http://localhost:8080/api/v1/products

INVENTORY APIs
➤ Add/Update Inventory

POST http://localhost:8080/api/v1/inventory

{
"productId": 1,
"availableQty": 100
}

➤ Get Inventory By Product

GET http://localhost:8080/api/v1/inventory/1

 CUSTOMER APIs
➤ Create Customer

POST http://localhost:8080/api/v1/customers

{
"name": "Pavan",
"email": "pavan@gmail.com"
}

➤ Get Customer

GET http://localhost:8080/api/v1/customers/1

 ORDER APIs
➤ Place Order

POST http://localhost:8080/api/v1/orders

{
"customerId": 1,
"idempotencyKey": "XYZ-123",
"items": [
{ "productId": 1, "qty": 1, "price": 50000 }
]
}

➤ Get Order by ID

GET http://localhost:8080/api/v1/orders/1

➤ Get All Orders

GET http://localhost:8080/api/v1/orders

➤ Get Orders by Customer

GET http://localhost:8080/api/v1/orders/customer/1

➤ Update Order Status

PATCH http://localhost:8080/api/v1/orders/1/status?status=SHIPPED

➤ Delete Order

DELETE http://localhost:8080/api/v1/orders/1

7. Postman Collection
   https://web.postman.co/workspace/My-Workspace~1e845867-722c-422a-8b1e-6ee98ea606cc/request/46471487-4db7b4ae-f849-4213-bf13-72c9d2246bda?action=share&source=copy-link&creator=46471487&ctx=documentation
