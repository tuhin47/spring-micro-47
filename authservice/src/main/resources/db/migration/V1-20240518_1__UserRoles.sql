INSERT INTO role (role_id, name)
VALUES (1, 'ROLE_USER');
INSERT INTO role (role_id, name)
VALUES (2, 'ROLE_ADMIN');
INSERT INTO role (role_id, name)
VALUES (3, 'ROLE_MODERATOR');

INSERT INTO user (id, created_at, updated_at, provider_user_id, email, enabled, display_name, password, provider,
                  using_2fa, secret, deleted_at, is_deleted)
VALUES ('61595b2e-2b25-4262-ab01-150860b0a875', '2024-05-18 01:37:49', '2024-05-18 01:37:49', null, 'admin@tuhin47.com',
        true, 'admin@tuhin47.com',
        0x24326124313024465A48436C504F4C5446646E567542416446782F4675627252786264783934364D67355636467235345A3850667977447A6D626643,
        'local', false, null, null, false);
INSERT INTO user (id, created_at, updated_at, provider_user_id, email, enabled, display_name, password, provider,
                  using_2fa, secret, deleted_at, is_deleted)
VALUES ('dbe424ee-3f02-4446-b26c-d9735d06651a', '2024-05-18 01:42:56', '2024-05-18 01:42:56', null, 'sample@gmail.com',
        true, 'Towhidul',
        0x243261243130246B3447307538734B4F352E3049366D4E316939756165376B4E7231704A327A496B30356E6537376C684E507347684875614D316675,
        'local', false, null, null, false);

INSERT INTO user_role (role_id, user_id)
VALUES (1, '61595b2e-2b25-4262-ab01-150860b0a875');
INSERT INTO user_role (role_id, user_id)
VALUES (2, '61595b2e-2b25-4262-ab01-150860b0a875');
INSERT INTO user_role (role_id, user_id)
VALUES (3, '61595b2e-2b25-4262-ab01-150860b0a875');
INSERT INTO user_role (role_id, user_id)
VALUES (1, 'dbe424ee-3f02-4446-b26c-d9735d06651a');
