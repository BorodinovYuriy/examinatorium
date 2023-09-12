package ru.bor.examinatorium.entities;

import lombok.*;
import org.hibernate.annotations.Type;
import ru.bor.examinatorium.entities.answermode.AnswerModeEnum;

import javax.persistence.*;
@Getter
@Setter
@Builder
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
    private Long themeId;

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
    private String rightAnswer;

    @Enumerated(EnumType.STRING)
    @Column(name = "answer_mode",unique = true)
    private AnswerModeEnum answerMode;

    @Column(name = "file_name")
    private String fileName;

    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    private byte[] bytes;

    @Column(name = "content_type")
    private String contentType;

    @Override
    public String toString() {
        return  "Q_id = " + id +"\n\n"+
                question     +"\n\n\n"+
                answerOne    +"\n\n"+
                answerTwo    +"\n\n"+
                answerThree  +"\n\n"+
                answerFour;

    }
}
