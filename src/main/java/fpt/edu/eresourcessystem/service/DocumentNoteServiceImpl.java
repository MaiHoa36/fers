package fpt.edu.eresourcessystem.service;

import fpt.edu.eresourcessystem.enums.CommonEnum;
import fpt.edu.eresourcessystem.model.Answer;
import fpt.edu.eresourcessystem.model.DocumentNote;
import fpt.edu.eresourcessystem.repository.DocumentNoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service("documentNoteService")
@RequiredArgsConstructor
public class DocumentNoteServiceImpl implements DocumentNoteService {
    private final DocumentNoteRepository documentNoteRepository;
    private final MongoTemplate mongoTemplate;

    @Override
    public DocumentNote findById(String studentNoteId) {
        return documentNoteRepository.findByIdAndDeleteFlg(studentNoteId, CommonEnum.DeleteFlg.PRESERVED);
    }

    @Override
    public DocumentNote findByDocIdAndStudentId(String docId, String studentId) {
        return documentNoteRepository
                .findByDocIdAndStudentIdAndDeleteFlg(docId, studentId, CommonEnum.DeleteFlg.PRESERVED);
    }

    @Override
    public DocumentNote addDocumentNote(DocumentNote documentNote) {
        if (null != documentNote && null == documentNote.getId()) {
            if (null != documentNoteRepository
                    .findByDocIdAndStudentIdAndDeleteFlg(documentNote.getDocId(), documentNote.getStudentId(),
                            CommonEnum.DeleteFlg.PRESERVED)) {
                return null;
            } else {
                documentNote.setDeleteFlg(CommonEnum.DeleteFlg.PRESERVED);
                return documentNoteRepository.save(documentNote);
            }
        }
        return null;
    }

    @Override
    public DocumentNote updateDocumentNote(DocumentNote documentNote) {
        if (null == documentNote || null == documentNote.getId()) {
            return null;
        }
        Optional<DocumentNote> savedStudentNote = documentNoteRepository.findById(documentNote.getId());
        if (savedStudentNote.isPresent()) {
            return documentNoteRepository.save(documentNote);
        }
        return null;
    }

    @Override
    public boolean deleteDocumentNote(DocumentNote documentNote) {
        DocumentNote check = documentNoteRepository.findByIdAndDeleteFlg(documentNote.getId(), CommonEnum.DeleteFlg.PRESERVED);
        if (null != check) {
            // SOFT DELETE
            documentNote.setDeleteFlg(CommonEnum.DeleteFlg.DELETED);
            documentNoteRepository.save(documentNote);
            return true;
        }
        return false;
    }

    @Override
    public void deleteDocumentNoteByDocId(String docId) {
        Query query = new Query(Criteria.where("deleteFlg").is(CommonEnum.DeleteFlg.PRESERVED)
                .and("docId").is(docId));
        Update update = new Update().set("deleteFlg", CommonEnum.DeleteFlg.DELETED);
        mongoTemplate.updateFirst(query, update, DocumentNote.class);
    }

    @Override
    public Page<DocumentNote> findByStudent(String search, String studentId, int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
        Criteria criteria = new Criteria();
        criteria.and("createdDate").exists(true);
//        criteria.and("createdBy").is(studentId);
        criteria.and("studentId").is(studentId);
        criteria.and("deleteFlg").is(CommonEnum.DeleteFlg.PRESERVED);
        // Add a condition to search in title, description, or editorContent
        Criteria textSearchCriteria = new Criteria().orOperator(
                Criteria.where("documentTitle").regex(Pattern.quote(search), "i"),  // "i" for case-insensitive
                Criteria.where("noteContent").regex(Pattern.quote(search), "i")
        );
        criteria.andOperator(textSearchCriteria);
        Query query = new Query(criteria).with(Sort.by(Sort.Order.desc("createdDate"))).with(pageable);
        List<DocumentNote> results = mongoTemplate.find(query, DocumentNote.class);
        return PageableExecutionUtils.getPage(results, pageable,
                () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), DocumentNote.class));

    }
}
