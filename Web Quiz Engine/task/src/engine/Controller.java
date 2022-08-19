package engine;

import engine.user.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static java.util.stream.Collectors.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.lang.reflect.Array;
import java.util.*;

import static java.util.stream.Collectors.counting;

@RestController
@Validated
@RequestMapping("/api")
public class Controller {

    @Autowired
    private QuestionRepository questions;

    @Autowired
    private CompletedQuestionRepository completed;

    @GetMapping("/quiz")
    public Question getQues(){
        return new Question();
    }

    @PostMapping("/quiz")
    public Reply postAns(@RequestParam("answer") int answer){
        if(answer == 2){
            return Reply.WIN;
        }
        return Reply.LOSE;
    }

    @PostMapping("/quizzes")
    public Question postQuestion(@Valid @RequestBody Question question, @AuthenticationPrincipal MyUserDetails user){
        question.setUser(user.getUser());
        return questions.save(question);
    }

    @GetMapping("/quizzes/{id}")
    public Question getQuesById(@PathVariable @Min(1) long id){
        return questions.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, ""));
    }

    @GetMapping("/quizzes")
    public Page<Question> getAll(@RequestParam(defaultValue = "0") Integer page,
                                                 @RequestParam(defaultValue = "10") Integer pageSize,
                                                 @RequestParam(defaultValue = "id") String sortBy){
//        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
//        return questions.findAll(paging);
        return questions.findAll(PageRequest.of(page, pageSize, Sort.by(sortBy)));
    }

    @PostMapping("/quizzes/{id}/solve")
    public Reply answer(@PathVariable @Min(1) long id, @RequestBody Answer answer,
                        @AuthenticationPrincipal MyUserDetails auth){

            if(questions.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))
                    .getAnswer().stream().collect(groupingBy( x -> x, counting()))
                    .equals(answer.getAnswers().stream().collect(groupingBy( x -> x, counting())))) {
                completed.save(new CompletedQuestion(id, auth.getUser()));
                return Reply.WIN;
            }
        return Reply.LOSE;
    }

    @GetMapping("/quizzes/completed")
    public Page<CompletedQuestion> getCompleted(@RequestParam(defaultValue = "0") Integer page,
                                 @RequestParam(defaultValue = "10") Integer pageSize,
                                 @AuthenticationPrincipal MyUserDetails auth){
        List<CompletedQuestion> comp = new ArrayList<>();
        for (CompletedQuestion c:
                completed.findAll()) {
            if(c.getUser().getEmail().equals(auth.getUsername())){
                comp.add(c);
            }
        }
        comp.sort(Comparator.comparing(CompletedQuestion::getTime).reversed());
        return new PageImpl<>(comp.subList(page * pageSize, Math.min(page * pageSize + pageSize, comp.size())),
                PageRequest.of(page, pageSize), comp.size());
    }

    @DeleteMapping("/quizzes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delQuizze(@PathVariable @Min(1) int id, @AuthenticationPrincipal MyUserDetails auth){
        if(!auth.getUsername().equals(getQuesById(id).getUser().getEmail())){
            throw  new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        questions.delete(getQuesById(id));
    }

}
