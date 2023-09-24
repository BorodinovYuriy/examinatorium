package ru.bor.examinatorium.entities;

import lombok.*;

import javax.persistence.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "theme")
public class Theme {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "theme_name")
    private String themeName;

    @Column(name = "countdown_seconds")
    private int countdownSeconds;

    @Column(name = "number_of_questions")
    private int numberOfQuestions;

    @Column(name = "number_of_mistakes")
    private int numberOfMistakes;
}
