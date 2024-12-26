CREATE TABLE menu
(
    id     BIGINT       NOT NULL,
    label  VARCHAR(255) NULL,
    parent BIGINT       NULL,
    icon   VARCHAR(20)  NULL,
    CONSTRAINT pk_menu PRIMARY KEY (id)
);

CREATE TABLE menu_privileges
(
    menu_id      BIGINT NOT NULL,
    privilege_id BIGINT NOT NULL,
    CONSTRAINT pk_menu_privileges PRIMARY KEY (menu_id, privilege_id)
);

CREATE TABLE privilege
(
    privilege_id  BIGINT AUTO_INCREMENT NOT NULL,
    name          VARCHAR(25)           NOT NULL,
    `description` VARCHAR(255)          NULL,
    CONSTRAINT pk_privilege PRIMARY KEY (privilege_id)
);

CREATE TABLE `role`
(
    role_id       BIGINT AUTO_INCREMENT NOT NULL,
    name          VARCHAR(20)           NOT NULL,
    `description` VARCHAR(50)           NULL,
    CONSTRAINT pk_role PRIMARY KEY (role_id)
);

CREATE TABLE role_privilege
(
    privilege_id BIGINT NOT NULL,
    role_id      BIGINT NOT NULL,
    CONSTRAINT pk_role_privilege PRIMARY KEY (privilege_id, role_id)
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
    password         VARCHAR(100) NOT NULL,
    provider         VARCHAR(255) NULL,
    using_2fa        BIT(1)       NULL,
    secret           VARCHAR(255) NULL,
    is_deleted       BIT(1)       NOT NULL,
    deleted_at       datetime     NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

CREATE TABLE user_privilege
(
    privilege_id BIGINT      NOT NULL,
    user_id      VARCHAR(36) NOT NULL,
    CONSTRAINT pk_user_privilege PRIMARY KEY (privilege_id, user_id)
);

CREATE TABLE user_role
(
    role_id BIGINT      NOT NULL,
    user_id VARCHAR(36) NOT NULL,
    CONSTRAINT pk_user_role PRIMARY KEY (role_id, user_id)
);

ALTER TABLE privilege
    ADD CONSTRAINT uc_privilege_name UNIQUE (name);

ALTER TABLE `role`
    ADD CONSTRAINT uc_role_name UNIQUE (name);

ALTER TABLE user
    ADD CONSTRAINT uc_user_email UNIQUE (email);
CREATE INDEX idx_user_email ON user (email);

ALTER TABLE menu
    ADD CONSTRAINT FK_MENU_ON_PARENT FOREIGN KEY (parent) REFERENCES menu (id);

ALTER TABLE menu_privileges
    ADD CONSTRAINT fk_menpri_on_menu FOREIGN KEY (menu_id) REFERENCES menu (id);

ALTER TABLE menu_privileges
    ADD CONSTRAINT fk_menpri_on_privilege FOREIGN KEY (privilege_id) REFERENCES privilege (privilege_id);

ALTER TABLE role_privilege
    ADD CONSTRAINT fk_rolpri_on_privilege FOREIGN KEY (privilege_id) REFERENCES privilege (privilege_id);

ALTER TABLE role_privilege
    ADD CONSTRAINT fk_rolpri_on_role FOREIGN KEY (role_id) REFERENCES `role` (role_id);

ALTER TABLE user_privilege
    ADD CONSTRAINT fk_usepri_on_privilege FOREIGN KEY (privilege_id) REFERENCES privilege (privilege_id);

ALTER TABLE user_privilege
    ADD CONSTRAINT fk_usepri_on_user FOREIGN KEY (user_id) REFERENCES user (id);

ALTER TABLE user_role
    ADD CONSTRAINT fk_user_role_on_role FOREIGN KEY (role_id) REFERENCES `role` (role_id);

ALTER TABLE user_role
    ADD CONSTRAINT fk_user_role_on_user FOREIGN KEY (user_id) REFERENCES user (id);