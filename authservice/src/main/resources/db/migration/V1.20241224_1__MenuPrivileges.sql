CREATE TABLE menu_privileges
(
    privilege_id BIGINT NOT NULL,
    menu_id      BIGINT NOT NULL,
    CONSTRAINT pk_menu_privileges PRIMARY KEY (privilege_id, menu_id)
);

ALTER TABLE menu_privileges
    ADD CONSTRAINT fk_menpri_on_menu FOREIGN KEY (menu_id) REFERENCES menu (id);

ALTER TABLE menu_privileges
    ADD CONSTRAINT fk_menpri_on_privilege FOREIGN KEY (privilege_id) REFERENCES privilege (privilege_id);
