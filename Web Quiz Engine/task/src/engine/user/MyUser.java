package engine.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import engine.CompletedQuestion;
import engine.Question;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class MyUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Email(regexp = ".{1,}@.{1,}\\..{1,}")
    private String email;

    @NotNull
    @Size(min = 5)
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Question> questions;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<CompletedQuestion> completedQuestions = new ArrayList<>();

    public MyUser() {

    }

    public MyUser(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<CompletedQuestion> getCompletedQuestions() {
        return completedQuestions;
    }

    public void setCompletedQuestions(List<CompletedQuestion> completedQuestions) {
        this.completedQuestions = completedQuestions;
    }
}
