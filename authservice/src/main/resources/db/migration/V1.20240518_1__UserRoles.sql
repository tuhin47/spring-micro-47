INSERT INTO role (role_id, name)
VALUES (1, 'ROLE_USER');
INSERT INTO role (role_id, name)
VALUES (2, 'ROLE_ADMIN');
INSERT INTO role (role_id, name)
VALUES (3, 'ROLE_MODERATOR');

INSERT INTO user (id, created_at, updated_at, provider_user_id, email, enabled, display_name, password, provider, using_2fa, secret, is_deleted, deleted_at) VALUES ('61595b2e-2b25-4262-ab01-150860b0a875', '2024-05-18 01:37:49', '2024-05-18 01:37:49', null, 'admin@tuhin47.com', true, 'admin@tuhin47.com', '{bcrypt}$2a$10$FZHClPOLTFdnVuBAdFx/FubrRxbdx946Mg5V6Fr54Z8PfywDzmbfC', 'local', false, null, false, null);
INSERT INTO user (id, created_at, updated_at, provider_user_id, email, enabled, display_name, password, provider, using_2fa, secret, is_deleted, deleted_at) VALUES ('dbe424ee-3f02-4446-b26c-d9735d06651a', '2024-05-18 01:42:56', '2024-05-18 01:42:56', null, 'sample@gmail.com', true, 'Towhidul', '{bcrypt}$2a$10$k4G0u8sKO5.0I6mN1i9uae7kNr1pJ2zIk05ne77lhNPsGhHuaM1fu', 'local', false, null, false, null);


INSERT INTO user_role (role_id, user_id)
VALUES (1, '61595b2e-2b25-4262-ab01-150860b0a875');
INSERT INTO user_role (role_id, user_id)
VALUES (2, '61595b2e-2b25-4262-ab01-150860b0a875');
INSERT INTO user_role (role_id, user_id)
VALUES (3, '61595b2e-2b25-4262-ab01-150860b0a875');
INSERT INTO user_role (role_id, user_id)
VALUES (1, 'dbe424ee-3f02-4446-b26c-d9735d06651a');
