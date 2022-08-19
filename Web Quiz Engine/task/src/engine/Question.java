package engine;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import engine.user.MyUser;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    @NotBlank
    private String title;

    public String getTitle() {
        return title;
    }

    @NotBlank
    private String text;

    public String getText() {
        return text;
    }

    @NotNull
    @Size(min = 2)
    @ElementCollection
    private List<String> options = new ArrayList<>();

    public List<String> getOptions() {
        return options;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ElementCollection
    private List<Integer> answer = new ArrayList<>();

    @JsonIgnore
    @ManyToOne
    private MyUser user;

    public List<Integer> getAnswer() {
        return answer;
    }

    public Question(String title, String text, List<String> options, List<Integer> answer) {
        this.title = title;
        this.text = text;
        this.options = options;
        this.answer = answer;
    }

    public Question() {
    }

    public MyUser getUser() {
        return user;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }
}
