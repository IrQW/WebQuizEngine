package engine;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import engine.user.MyUser;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class CompletedQuestion {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonProperty("id")
    private Long question_id;

    @JsonProperty("completedAt")
    private LocalDateTime time;

    @JsonIgnore
    @ManyToOne
    private MyUser user;

    public CompletedQuestion() {
    }

    public CompletedQuestion(Long question_id, MyUser user) {
        this.question_id = question_id;
        this.time = LocalDateTime.now();
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(Long question_id) {
        this.question_id = question_id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public MyUser getUser() {
        return user;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }
}
