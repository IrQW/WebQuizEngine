package engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Answer {
    public List<Integer> answer = new ArrayList<>(){};

    public List<Integer> getAnswers() {
        return answer;
    }

    public Answer(List<Integer> answers) {
        this.answer = answers;
    }

    public Answer() {
        Collections.sort(answer);
    }
}
