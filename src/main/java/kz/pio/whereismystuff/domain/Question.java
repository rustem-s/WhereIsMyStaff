package kz.pio.whereismystuff.domain;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Question entity
 * @version 20140614
 * @author Rustem S
 */
@Entity
@Table(name = "question")
public class Question implements Comparable<Question>{
    private Long id;
    private User user;
    private String title;
    private String body;
    private Date createDate;
    private Set<Category> categories = new HashSet<Category>();
    private Set<Answer> answers = new HashSet<Answer>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) { this.id = id; }

    @ManyToOne
    @JoinColumn(name = "USERS_ID")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "TITLE")
    @Size(min=1)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "BODY")
    @Size(min=1)
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATE_DATE")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "question_category",
            joinColumns = @JoinColumn(name = "QUESTION_ID"),
            inverseJoinColumns = @JoinColumn(name = "CATEGORY_ID"))
    public Set<Category> getCategories() {
        return this.categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval=true, fetch = FetchType.EAGER)
    public Set<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<Answer> answers) {
        this.answers = answers;
    }

    @Override
    public int compareTo(Question o) {
        return ((this.createDate.after(o.createDate)) ? -1 : 1);
    }
}
