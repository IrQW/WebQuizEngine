package engine;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompletedQuestionRepository extends JpaRepository<CompletedQuestion, Long> {
}
