package fpt.edu.eresourcessystem.repository;

import fpt.edu.eresourcessystem.enums.CommonEnum;
import fpt.edu.eresourcessystem.model.Answer;
import fpt.edu.eresourcessystem.model.Document;
import fpt.edu.eresourcessystem.model.Question;
import fpt.edu.eresourcessystem.model.Student;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("answerRepository")
public interface AnswerRepository extends MongoRepository<Answer, String> {
    Optional<Answer> findById(@NotNull String id);

    Answer findByIdAndDeleteFlg(String id, CommonEnum.DeleteFlg delete);

    List<Answer> findByDocumentIdAndDeleteFlg(Document document, CommonEnum.DeleteFlg delete);

    List<Answer> findByDocumentIdAndQuestionIdAndDeleteFlg(Document document, Question question, CommonEnum.DeleteFlg delete);

    List<Answer> findByQuestionAndDeleteFlg(Question question, CommonEnum.DeleteFlg delete);

    List<Answer> findByStudentAndQuestionAndDeleteFlg(Student student, Question question, CommonEnum.DeleteFlg delete);
}
