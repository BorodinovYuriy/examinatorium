create table theme
(
    id                  bigserial primary key,
    theme_name          text

);

create table question
(
    id                  bigserial primary key,
    theme_id            bigint not null,
    question            text,
    answer_one          text,
    answer_two          text,
    answer_three        text,
    answer_four         text,
    right_answer        int,
    answer_mode         varchar(15)
);
insert into theme (theme_name) values
(
'Простейшая математика'
);

insert into question
(
    theme_id,
    question,
    answer_one,
    answer_two,
    answer_three,
    answer_four,
    right_answer,
    answer_mode
) values
(
     1,
     'Чему равно выражение 2+2 ?',
    '1) 1',
    '2) 3',
    '3) 4',
    '4) 5',
    3,
    'SINGLE_ANSWER'
);

