package fpt.edu.eresourcessystem.service;

import fpt.edu.eresourcessystem.enums.CommonEnum;
import fpt.edu.eresourcessystem.model.*;
import fpt.edu.eresourcessystem.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("answerService")
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepository answerRepository;
    private final MongoTemplate mongoTemplate;

    @Override
    public Answer findById(String answerId) {
        Optional<Answer> answer = answerRepository.findById(answerId);
        return answer.orElse(null);
    }

    @Override
    public List<Answer> findByDocId(Document document) {
        return answerRepository
                .findByDocumentIdAndDeleteFlg(document, CommonEnum.DeleteFlg.PRESERVED);
    }

    @Override
    public List<Answer> findByDocIdAndQuestionId(Document document, Question question) {
        return answerRepository
                .findByDocumentIdAndQuestionIdAndDeleteFlg(document,
                        question, CommonEnum.DeleteFlg.PRESERVED);
    }

    @Override
    public Answer addAnswer(Answer answer) {
        if (null == answer.getId()) {
            return answerRepository.save(answer);
        } else if (answerRepository.findById(answer.getId().trim()).isEmpty()) {
            return answerRepository.save(answer);
        }
        return null;
    }

    @Override
    public Answer updateAnswer(Answer answer) {
        Answer savedAnswer = answerRepository
                .findByIdAndDeleteFlg(answer.getId(), CommonEnum.DeleteFlg.PRESERVED);
        if (null != savedAnswer) {
            return answerRepository.save(answer);
        }
        return null;
    }

    @Override
    public boolean deleteAnswer(Answer answer) {
        Answer check = answerRepository
                .findByIdAndDeleteFlg(answer.getId(), CommonEnum.DeleteFlg.PRESERVED);
        if (null != check) {
            check.setDeleteFlg(CommonEnum.DeleteFlg.DELETED);
            answerRepository.save(check);
            return true;
        }
        return false;
    }

    @Override
    public void deleteAnswersByDocId(ObjectId docId) {
        Query query = new Query(Criteria.where("deleteFlg").is(CommonEnum.DeleteFlg.PRESERVED)
                .and("documentId.id").is(docId));
        mongoTemplate.findAllAndRemove(query, Answer.class);
    }

    @Override
    public List<Answer> findByQuestion(Question question) {
        return answerRepository
                .findByQuestionAndDeleteFlg(question, CommonEnum.DeleteFlg.PRESERVED);
    }

    @Override
    public List<Answer> findByStudentAnsQuestion(Student student, Question question) {
        return answerRepository
                .findByStudentAndQuestionAndDeleteFlg(student, question, CommonEnum.DeleteFlg.PRESERVED);
    }
}
