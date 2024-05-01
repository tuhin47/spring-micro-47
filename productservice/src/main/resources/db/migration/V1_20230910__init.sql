create table
    company
(
    id         varchar(36) not null,
    created_at datetime    not null,
    updated_at datetime    not null,
    created_by varchar(36),
    updated_by varchar(36),
    address    varchar(100),
    name       varchar(50),
    primary key (id)
) engine = InnoDB;

create table
    product
(
    id           varchar(36)      not null,
    created_at   datetime         not null,
    updated_at   datetime         not null,
    created_by   varchar(36),
    updated_by   varchar(36),
    description  varchar(250),
    price        double precision not null,
    product_name varchar(50)      not null,
    product_type varchar(50),
    quantity     bigint           not null,
    company_id   varchar(36),
    primary key (id)
) engine = InnoDB;

alter table product
    add
        constraint FK_company_product foreign key (company_id) references company (id);