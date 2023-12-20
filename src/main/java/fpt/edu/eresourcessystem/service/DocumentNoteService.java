package fpt.edu.eresourcessystem.service;

import fpt.edu.eresourcessystem.model.DocumentNote;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DocumentNoteService {
    DocumentNote findById(String studentNoteId);

    DocumentNote findByDocIdAndStudentId(String docId, String studentId);

    DocumentNote addDocumentNote(DocumentNote documentNote);

    DocumentNote updateDocumentNote(DocumentNote documentNote);

    boolean deleteDocumentNote(DocumentNote documentNote);

    Page<DocumentNote> findByStudent(String search, String studentId, int pageIndex, int pageSize);
}
