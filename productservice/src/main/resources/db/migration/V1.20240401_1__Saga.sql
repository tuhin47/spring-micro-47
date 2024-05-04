create table association_value_entry
(
    id                bigint       not null,
    association_key   varchar(255) not null,
    association_value varchar(255),
    saga_id           varchar(255) not null,
    saga_type         varchar(255),
    primary key (id)
) engine = InnoDB;

create index IDXk45eqnxkgd8hpdn6xixn8sgft on association_value_entry (saga_type, association_key, association_value);
create index IDXgv5k1v2mh6frxuy5c0hgbau94 on association_value_entry (saga_id, saga_type);

create table dead_letter_entry
(
    dead_letter_id       varchar(255) not null,
    cause_message        varchar(1023),
    cause_type           varchar(255),
    diagnostics          longblob,
    enqueued_at          datetime     not null,
    last_touched         datetime,
    aggregate_identifier varchar(255),
    event_identifier     varchar(255) not null,
    message_type         varchar(255) not null,
    meta_data            longblob,
    payload              longblob     not null,
    payload_revision     varchar(255),
    payload_type         varchar(255) not null,
    sequence_number      bigint,
    time_stamp           varchar(255) not null,
    token                longblob,
    token_type           varchar(255),
    type                 varchar(255),
    processing_group     varchar(255) not null,
    processing_started   datetime,
    sequence_identifier  varchar(255) not null,
    sequence_index       bigint       not null,
    primary key (dead_letter_id)
) engine = InnoDB;

create index IDXe67wcx5fiq9hl4y4qkhlcj9cg on dead_letter_entry (processing_group);
create index IDXrwucpgs6sn93ldgoeh2q9k6bn on dead_letter_entry (processing_group, sequence_identifier);
alter table dead_letter_entry
    add constraint UKhlr8io86j74qy298xf720n16v unique (processing_group, sequence_identifier, sequence_index);

create table domain_event_entry
(
    global_index         bigint       not null,
    event_identifier     varchar(255) not null,
    meta_data            longblob,
    payload              longblob     not null,
    payload_revision     varchar(255),
    payload_type         varchar(255) not null,
    time_stamp           varchar(255) not null,
    aggregate_identifier varchar(255) not null,
    sequence_number      bigint       not null,
    type                 varchar(255),
    primary key (global_index)
) engine = InnoDB;
alter table domain_event_entry
    add constraint UK8s1f994p4la2ipb13me2xqm1w unique (aggregate_identifier, sequence_number);
alter table domain_event_entry
    add constraint UK_fwe6lsa8bfo6hyas6ud3m8c7x unique (event_identifier);

create table hibernate_sequence
(
    next_val bigint
) engine = InnoDB;

insert into hibernate_sequence
values (1);
create table saga_entry
(
    saga_id         varchar(255) not null,
    revision        varchar(255),
    saga_type       varchar(255),
    serialized_saga longblob,
    primary key (saga_id)
) engine = InnoDB;

create table snapshot_event_entry
(
    aggregate_identifier varchar(255) not null,
    sequence_number      bigint       not null,
    type                 varchar(255) not null,
    event_identifier     varchar(255) not null,
    meta_data            longblob,
    payload              longblob     not null,
    payload_revision     varchar(255),
    payload_type         varchar(255) not null,
    time_stamp           varchar(255) not null,
    primary key (aggregate_identifier, sequence_number, type)
) engine = InnoDB;


alter table snapshot_event_entry
    add constraint UK_e1uucjseo68gopmnd0vgdl44h unique (event_identifier);

create table token_entry
(
    processor_name varchar(255) not null,
    segment        integer      not null,
    owner          varchar(255),
    timestamp      varchar(255) not null,
    token          longblob,
    token_type     varchar(255),
    primary key (processor_name, segment)
) engine = InnoDB;
