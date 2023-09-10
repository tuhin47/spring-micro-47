-- Insert into company table (5 records)
SET FOREIGN_KEY_CHECKS = 0;

truncate table company;
INSERT INTO company (id, created_at, updated_at, created_by, updated_by, address, name)
VALUES
    ('1', '2023-09-11 08:00:00', '2023-09-11 08:00:00', 'John Doe', 'John Doe', '123 Main St', 'Company 1'),
    ('2', '2023-09-11 08:30:00', '2023-09-11 08:30:00', 'Jane Smith', 'Jane Smith', '456 Elm St', 'Company 2'),
    ('3', '2023-09-11 09:00:00', '2023-09-11 09:00:00', 'Alice Johnson', 'Alice Johnson', '789 Oak St', 'Company 3'),
    ('4', '2023-09-11 09:30:00', '2023-09-11 09:30:00', 'Bob Wilson', 'Bob Wilson', '101 Pine St', 'Company 4'),
    ('5', '2023-09-11 10:00:00', '2023-09-11 10:00:00', 'Eve Brown', 'Eve Brown', '202 Cedar St', 'Company 5');

-- Insert into product table (10 records)
truncate table product;
INSERT INTO product (id, created_at, updated_at, created_by, updated_by, description, price, product_name, product_type, quantity, company_id)
VALUES
    ('1', '2023-09-11 08:00:00', '2023-09-11 08:00:00', 'John Doe', 'John Doe', 'Product 1 description', 19.99, 'Product 1', 'ELECTRONIC', 100, '1'),
    ('2', '2023-09-11 08:30:00', '2023-09-11 08:30:00', 'Jane Smith', 'Jane Smith', 'Product 2 description', 29.99, 'Product 2', 'FOOD', 150, '2'),
    ('3', '2023-09-11 09:00:00', '2023-09-11 09:00:00', 'Alice Johnson', 'Alice Johnson', 'Product 3 description', 9.99, 'Product 3', 'DRESS', 75, '1'),
    ('4', '2023-09-11 09:30:00', '2023-09-11 09:30:00', 'Bob Wilson', 'Bob Wilson', 'Product 4 description', 49.99, 'Product 4', 'FURNITURE', 200, '3'),
    ('5', '2023-09-11 10:00:00', '2023-09-11 10:00:00', 'Eve Brown', 'Eve Brown', 'Product 5 description', 14.99, 'Product 5', 'ELECTRONIC', 120, '2'),
    ('6', '2023-09-11 10:30:00', '2023-09-11 10:30:00', 'Grace Wilson', 'Grace Wilson', 'Product 6 description', 39.99, 'Product 6', 'FOOD', 80, '4'),
    ('7', '2023-09-11 11:00:00', '2023-09-11 11:00:00', 'Sam Brown', 'Sam Brown', 'Product 7 description', 14.99, 'Product 7', 'DRESS', 200, '1'),
    ('8', '2023-09-11 11:30:00', '2023-09-11 11:30:00', 'Olivia Taylor', 'Olivia Taylor', 'Product 8 description', 24.99, 'Product 8', 'FURNITURE', 125, '2'),
    ('9', '2023-09-11 12:00:00', '2023-09-11 12:00:00', 'Henry Johnson', 'Henry Johnson', 'Product 9 description', 19.99, 'Product 9', 'ELECTRONIC', 90, '5'),
    ('10', '2023-09-11 12:30:00', '2023-09-11 12:30:00', 'Lily Smith', 'Lily Smith', 'Product 10 description', 34.99, 'Product 10', 'FOOD', 150, '3');

SET FOREIGN_KEY_CHECKS = 1;
