CREATE TABLE IF NOT EXISTS file_info
(
    id                     INT AUTO_INCREMENT PRIMARY KEY,
    file_name              varchar(64)  NOT NULL,
    file_url               varchar(328) NOT NULL,
    is_upload_success_full boolean
);
