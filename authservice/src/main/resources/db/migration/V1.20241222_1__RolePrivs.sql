CREATE TABLE privilege
(
    privilege_id  BIGINT AUTO_INCREMENT NOT NULL,
    name          VARCHAR(255)          NULL,
    `description` VARCHAR(255)          NULL,
    CONSTRAINT pk_privilege PRIMARY KEY (privilege_id)
);

CREATE TABLE role_privilege
(
    privilege_id BIGINT NOT NULL,
    role_id      BIGINT NOT NULL,
    CONSTRAINT pk_role_privilege PRIMARY KEY (privilege_id, role_id)
);

CREATE TABLE user_privilege
(
    privilege_id BIGINT      NOT NULL,
    user_id      VARCHAR(36) NOT NULL,
    CONSTRAINT pk_user_privilege PRIMARY KEY (privilege_id, user_id)
);

ALTER TABLE role_privilege
    ADD CONSTRAINT fk_rolpri_on_privilege FOREIGN KEY (privilege_id) REFERENCES privilege (privilege_id);

ALTER TABLE role_privilege
    ADD CONSTRAINT fk_rolpri_on_role FOREIGN KEY (role_id) REFERENCES `role` (role_id);

ALTER TABLE user_privilege
    ADD CONSTRAINT fk_usepri_on_privilege FOREIGN KEY (privilege_id) REFERENCES privilege (privilege_id);

ALTER TABLE user_privilege
    ADD CONSTRAINT fk_usepri_on_user FOREIGN KEY (user_id) REFERENCES user (id);