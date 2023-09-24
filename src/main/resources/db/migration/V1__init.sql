create table theme
(
    id                  bigserial primary key,
    theme_name          text not null,
    countdown_seconds   int not null,
    number_of_questions int not null,
    number_of_mistakes  int not null
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
    bytes               bytea,
    flag                boolean
);

create table intern
(
    id                  bigserial primary key,
    info                text not null
);
