
CREATE TABLE company (
  id VARCHAR(255) NOT NULL,
   created_by VARCHAR(255) NULL,
   updated_by VARCHAR(255) NULL,
   created_at datetime NOT NULL,
   updated_at datetime NOT NULL,
   name VARCHAR(255) NULL,
   address VARCHAR(255) NULL,
   CONSTRAINT pk_company PRIMARY KEY (id)
);
CREATE TABLE product (
  id VARCHAR(36) NOT NULL,
   created_by VARCHAR(255) NULL,
   updated_by VARCHAR(255) NULL,
   created_at datetime NOT NULL,
   updated_at datetime NOT NULL,
   product_name VARCHAR(50) NOT NULL,
   price DOUBLE NOT NULL,
   quantity BIGINT NOT NULL,
   company_id VARCHAR(255) NULL,
   CONSTRAINT pk_product PRIMARY KEY (id)
);

ALTER TABLE product ADD CONSTRAINT FK_PRODUCT_ON_COMPANY FOREIGN KEY (company_id) REFERENCES company (id);

insert into product (id, created_at, updated_at, created_by, updated_by, price, product_name, quantity, company_id) values ('05d6158b-0acc-4440-8d3c-f4549497348c', '2023-09-04 18:48:06', '2023-09-04 18:48:06', '99eadd96-f133-43c3-928c-4edfcc9c2888', '99eadd96-f133-43c3-928c-4edfcc9c2888', 19.33, 'Towels', 245, null);
insert into product (id, created_at, updated_at, created_by, updated_by, price, product_name, quantity, company_id) values ('16fc7797-6302-48b3-ab6a-e6deff6669e2', '2023-09-04 18:48:05', '2023-09-04 18:48:05', '99eadd96-f133-43c3-928c-4edfcc9c2888', '99eadd96-f133-43c3-928c-4edfcc9c2888', 458.57, 'Table', 827, null);
insert into product (id, created_at, updated_at, created_by, updated_by, price, product_name, quantity, company_id) values ('32b052e6-87b0-46d8-8fe5-60417e99b2e7', '2023-09-04 18:48:07', '2023-09-04 18:48:07', '99eadd96-f133-43c3-928c-4edfcc9c2888', '99eadd96-f133-43c3-928c-4edfcc9c2888', 907.27, 'Car', 619, null);
insert into product (id, created_at, updated_at, created_by, updated_by, price, product_name, quantity, company_id) values ('3d9da85f-04b0-4347-b770-2a30759c8bb6', '2023-09-04 18:48:06', '2023-09-04 18:48:06', '99eadd96-f133-43c3-928c-4edfcc9c2888', '99eadd96-f133-43c3-928c-4edfcc9c2888', 6.0, 'Towels', 383, null);
insert into product (id, created_at, updated_at, created_by, updated_by, price, product_name, quantity, company_id) values ('54a23aa0-2ae4-4b61-a876-e2e987aed34f', '2023-09-04 18:48:06', '2023-09-04 18:48:06', '99eadd96-f133-43c3-928c-4edfcc9c2888', '99eadd96-f133-43c3-928c-4edfcc9c2888', 63.63, 'Salad', 310, null);
insert into product (id, created_at, updated_at, created_by, updated_by, price, product_name, quantity, company_id) values ('643f083d-1f6c-4c38-a337-e3550436c25a', '2023-09-04 18:48:06', '2023-09-04 18:48:06', '99eadd96-f133-43c3-928c-4edfcc9c2888', '99eadd96-f133-43c3-928c-4edfcc9c2888', 306.06, 'Pants', 685, null);
insert into product (id, created_at, updated_at, created_by, updated_by, price, product_name, quantity, company_id) values ('911db8a8-26ce-434c-9d36-df1c02542e9d', '2023-09-04 18:48:06', '2023-09-04 18:48:06', '99eadd96-f133-43c3-928c-4edfcc9c2888', '99eadd96-f133-43c3-928c-4edfcc9c2888', 864.88, 'Cheese', 116, null);
insert into product (id, created_at, updated_at, created_by, updated_by, price, product_name, quantity, company_id) values ('c210c45c-3ee4-45ae-b783-4fd7521b28be', '2023-09-04 18:48:06', '2023-09-04 18:48:06', '99eadd96-f133-43c3-928c-4edfcc9c2888', '99eadd96-f133-43c3-928c-4edfcc9c2888', 817.11, 'Salad', 257, null);
insert into product (id, created_at, updated_at, created_by, updated_by, price, product_name, quantity, company_id) values ('c50b94a2-33e7-414b-a39d-4e37643a5309', '2023-09-04 18:48:07', '2023-09-04 18:48:07', '99eadd96-f133-43c3-928c-4edfcc9c2888', '99eadd96-f133-43c3-928c-4edfcc9c2888', 763.14, 'Shoes', 78, null);
insert into product (id, created_at, updated_at, created_by, updated_by, price, product_name, quantity, company_id) values ('d8235cc7-15b3-4503-b666-be33426e993c', '2023-09-04 18:48:05', '2023-09-04 18:48:05', '99eadd96-f133-43c3-928c-4edfcc9c2888', '99eadd96-f133-43c3-928c-4edfcc9c2888', 330.27, 'Gloves', 8, null);



