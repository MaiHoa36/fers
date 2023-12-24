package fpt.edu.eresourcessystem.service;

import fpt.edu.eresourcessystem.model.Answer;
import fpt.edu.eresourcessystem.model.Document;
import fpt.edu.eresourcessystem.model.Question;
import fpt.edu.eresourcessystem.model.Student;
import org.bson.types.ObjectId;

import java.util.List;

public interface AnswerService {
    Answer findById(String answerId);

    List<Answer> findByDocId(Document document);

    List<Answer> findByDocIdAndQuestionId(Document document, Question question);

    Answer addAnswer(Answer answer);

    Answer updateAnswer(Answer answer);

    boolean deleteAnswer(Answer answer);

    void deleteAnswersByDocId(ObjectId docId);

    List<Answer> findByQuestion(Question question);

    List<Answer> findByStudentAnsQuestion(Student student, Question question);
}
