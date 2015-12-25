package kz.pio.whereismystuff.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Category entity
 * @version 20140614
 * @author Rustem S
 */
@Entity
@Table(name = "category")
public class Category {
    private Long id;
    private String name;
    private Set<Question> questions = new HashSet<Question>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public Long getId() { return this.id; }

    public void setId(Long id) { this.id = id; }

    @Column(name = "NAME")
    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    @ManyToMany
    @JoinTable(name = "question_category",
            joinColumns = @JoinColumn(name = "CATEGORY_ID"),
            inverseJoinColumns = @JoinColumn(name = "QUESTION_ID"))
    public Set<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }
}
