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

-- insert into question
-- (
--     theme_id,
--     question,
--     answer_one,
--     answer_two,
--     answer_three,
--     answer_four,
--     right_answer,
--     answer_mode,
--     file_name,
--     bytes,
--     content_type
-- ) values
-- (1,'Чему равно выражение 2+2 ?','1. Один','2. Три','3. Четыре','4. Пять',3,'SINGLE_ANSWER'),
-- (1,'Чему равно выражение 2+3 ?','1. Один','2. Три','3. Четыре','4. Пять',4,'SINGLE_ANSWER'),
-- (1,'Выберите правельные утверждения:','1. 2+2=5','2. 2-2=0','3. 3+3=7','4. 3+3=6',4,'MULTI_ANSWER')
-- ;


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