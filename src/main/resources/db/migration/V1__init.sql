create table theme
(
    id                  bigserial primary key,
    theme_name          text not null,
    countdown_seconds   int not null,
    number_of_questions int not null,
    number_of_mistakes  int not null
);

insert into theme (theme_name,
                   countdown_seconds,
                   number_of_questions,
                   number_of_mistakes)
values
(
     'Простейшая математика',
        90,
        10,
        2
);

create table question
(
    id                  bigserial primary key,
    theme_id            bigint not null,
    question            text not null,
    answer_one          text not null,
    answer_two          text not null,
    answer_three        text not null,
    answer_four         text not null,
    right_answer        varchar(4) not null,
    answer_mode         varchar(15) not null,
    file_name           varchar(100),
    bytes               bytea,
    content_type        varchar(50)

);

create table intern
(
    id                  bigserial primary key,
    info                text not null
);

insert into intern (info)
values
    (
     'заипало - работай!'
    );