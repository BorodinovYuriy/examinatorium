package ru.bor.examinatorium.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "question")
public class Question {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "theme_id")
    private Long theme_id;

    @Column(name = "question")
    private String question;

    @Column(name = "answer_one")
    private String answerOne;

    @Column(name = "answer_two")
    private String answerTwo;

    @Column(name = "answer_three")
    private String answerThree;

    @Column(name = "answer_four")
    private String answerFour;

    @Column(name = "right_answer")
    private int rightAnswer;

    @Column(name = "answer_mode")
    private String answerMode;
    // TODO: 04.09.2023 поддержать загрузку картинки

    @Override
    public String toString() {
        return  "id = " + id +"\n\n"+
                question     +"\n\n\n"+
                answerOne    +"\n\n"+
                answerTwo    +"\n\n"+
                answerThree  +"\n\n"+
                answerFour;

    }
}
