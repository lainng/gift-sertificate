CREATE TABLE IF NOT EXISTS tag (
    id          IDENTITY NOT NULL PRIMARY KEY,
    tag_name    VARCHAR(40) NOT NULL
);

create table gift_certificate
(
    id                  identity           not null primary key,
    name                varchar(30)        not null,
    description         varchar(100)       not null,
    duration            integer            not null,
    price               numeric(8, 2)      not null,
    create_date         varchar(30)        not null,
    last_update_date    varchar(30)        not null
);

create table gift_certificate_with_tags
(
    gift_certificate_id bigint
        references gift_certificate
            on update cascade on delete cascade,
    tag_id              bigint
        references tag
            on update cascade on delete cascade
);