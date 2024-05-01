CREATE TABLE menu
(
    id     BIGINT       NOT NULL,
    label  VARCHAR(255) NULL,
    parent BIGINT       NULL,
    icon   VARCHAR(20)  NULL,
    CONSTRAINT pk_menu PRIMARY KEY (id)
);

CREATE TABLE role
(
    role_id BIGINT AUTO_INCREMENT NOT NULL,
    name    VARCHAR(255)          NULL,
    CONSTRAINT pk_role PRIMARY KEY (role_id)
);

CREATE TABLE user
(
    id               VARCHAR(36)  NOT NULL,
    created_at       datetime     NOT NULL,
    updated_at       datetime     NOT NULL,
    provider_user_id VARCHAR(255) NULL,
    email            VARCHAR(255) NOT NULL,
    enabled          BIT          NULL,
    display_name     VARCHAR(255) NULL,
    password         BLOB         NOT NULL,
    provider         VARCHAR(255) NULL,
    using_2fa        BIT(1)       NULL,
    secret           VARCHAR(255) NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

CREATE TABLE user_role
(
    role_id BIGINT      NOT NULL,
    user_id VARCHAR(36) NOT NULL,
    CONSTRAINT pk_user_role PRIMARY KEY (role_id, user_id)
);

ALTER TABLE user
    ADD CONSTRAINT uc_user_email UNIQUE (email);

ALTER TABLE menu
    ADD CONSTRAINT FK_MENU_ON_PARENT FOREIGN KEY (parent) REFERENCES menu (id);

ALTER TABLE user_role
    ADD CONSTRAINT fk_user_role_on_role FOREIGN KEY (role_id) REFERENCES role (role_id);

ALTER TABLE user_role
    ADD CONSTRAINT fk_user_role_on_user FOREIGN KEY (user_id) REFERENCES user (id);