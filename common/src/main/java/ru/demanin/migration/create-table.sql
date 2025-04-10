create table carrier
(
    id           bigint generated always as identity
        constraint carrier_pk
            primary key,
    company_name varchar not null,
    phone_number varchar not null
);

comment on table carrier is 'Переврзчик';

comment on column carrier.id is 'Id перевозчика';

comment on column carrier.company_name is 'Название фирмы';

comment on column carrier.phone_number is 'Номер телефона';

alter table carrier
    owner to postgres;

create table person
(
    id        bigint generated always as identity
        constraint client_pk
            primary key,
    name      varchar not null,
    full_name varchar not null,
    surname   varchar,
    login     varchar not null
        constraint client_pk2
            unique,
    password  varchar not null,
    role      varchar
);

comment on column person.id is 'Id Клиента';

comment on column person.name is 'Имя клиента';

comment on column person.full_name is 'Фамилия клиента';

comment on column person.surname is 'Отчество клиента';

comment on column person.login is 'Логин клиента';

comment on column person.password is 'Пароль клиента';

alter table person
    owner to postgres;

create table route
(
    id                  bigint generated always as identity
        constraint route_pk
            primary key,
    departure_point     varchar not null,
    destination_point   varchar not null,
    duration_in_minutes integer not null
);

comment on table route is 'Маршрут';

comment on column route.departure_point is 'Точка отправления';

comment on column route.destination_point is 'Пункт назначения';

comment on column route.duration_in_minutes is 'Время в пути в минутах';

alter table route
    owner to postgres;

create table ticket
(
    id             bigint generated always as identity
        constraint ticket_pk
            primary key,
    route_id       bigint    not null
        constraint ticket_route_id_fk
            references route,
    data_time      timestamp not null,
    seat_number    integer   not null,
    price          numeric   not null,
    status         varchar   not null,
    departure_time timestamp not null,
    carrier_id     bigint    not null
        constraint ticket_carrier_id_fk
            references carrier,
    person_id      bigint
        constraint ticket_person_id_fk
            references person
);

comment on table ticket is 'Билет';

comment on column ticket.id is 'Id бидета';

comment on column ticket.route_id is 'Id пути';

comment on column ticket.data_time is 'Дата и время';

comment on column ticket.seat_number is 'Номер места';

comment on column ticket.price is 'Цена билета';

comment on column ticket.departure_time is 'Дата и время отправления';

alter table ticket
    owner to postgres;

