package kz.pio.whereismystuff.domain;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Answer entity
 * @version 20140614
 * @author Rustem S
 */
@Entity
@Table(name = "answer")
public class Answer {
    private Long id;
    private Question question;
    private User user;
    private String body;
    private Date createDate;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "QUESTION_ID")
    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) { this.question = question; }

    @ManyToOne
    @JoinColumn(name = "USERS_ID")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "body")
    @Size(min=1)
    public String getBody() { return body; }

    public void setBody(String body) { this.body = body; }

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATE_DATE")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
