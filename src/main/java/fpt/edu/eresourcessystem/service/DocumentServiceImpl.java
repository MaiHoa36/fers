package fpt.edu.eresourcessystem.service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import fpt.edu.eresourcessystem.dto.DocumentDto;
import fpt.edu.eresourcessystem.dto.Response.DocumentResponseDto;
import fpt.edu.eresourcessystem.enums.CommonEnum;
import fpt.edu.eresourcessystem.enums.CourseEnum;
import fpt.edu.eresourcessystem.enums.DocumentEnum;
import fpt.edu.eresourcessystem.model.Course;
import fpt.edu.eresourcessystem.model.Document;
import fpt.edu.eresourcessystem.model.Lecturer;
import fpt.edu.eresourcessystem.model.elasticsearch.EsDocument;
import fpt.edu.eresourcessystem.repository.CourseRepository;
import fpt.edu.eresourcessystem.repository.DocumentRepository;
import fpt.edu.eresourcessystem.repository.ResourceTypeRepository;
import fpt.edu.eresourcessystem.repository.elasticsearch.EsDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service("documentService")
public class DocumentServiceImpl implements DocumentService {
    private final DocumentRepository documentRepository;
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final DocumentNoteService documentNoteService;
    private final NotificationService notificationService;
    private final EsDocumentRepository esDocumentRepository;
    private final MongoTemplate mongoTemplate;

    private final GridFsTemplate template;

    private final GridFsOperations operations;
    private final CourseRepository courseRepository;
    private final ResourceTypeRepository resourceTypeRepository;

    @Override
    public List<DocumentResponseDto> findRelevantDocument(String topicId, String docId) {
        Query query = new Query(Criteria.where("deleteFlg").is(CommonEnum.DeleteFlg.PRESERVED)
                .and("id").ne(docId)
                .and("topic.id").is(topicId))
                .skip(0)
                .limit(9)
                .with(Sort.by(Sort.Order.desc("createdDate")));
        List<Document> documents = mongoTemplate.find(query, Document.class);
        return documents.stream()
                .filter(entity -> CommonEnum.DeleteFlg.PRESERVED.equals(entity.getDeleteFlg()))
                .map(DocumentResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public Document findById(String documentId) {
        Optional<Document> document = documentRepository.findById(documentId);
        return document.orElse(null);
    }

    @Override
    public Document findByIdStudentSavedCourse(String mail, String documentId) {
        Optional<Document> check = documentRepository.findByIdWithoutDeleteFlg(documentId);
        Document document = null;
        if(check.isPresent()){
            Course course = check.get().getTopic().getCourse();
            if(course != null){
                if(course.getStudents() != null) {
                    if(course.getStudents().contains(mail)){
                        document = check.orElse(null);
                    }
                }
            } else {
                document = documentRepository.findById(documentId).orElse(null);
            }
        }
        return document;
    }

//    @Override
//    public void changeDocumentCourseStatus(String docId, CourseEnum.Status courseStatus) {
//        Query query = new Query(Criteria.where("id").is(docId));
//        Update update = new Update().set("courseStatus", courseStatus);
//        mongoTemplate.updateMulti(query, update, Document.class);
//    }

    @Override
    public void addStudentSaveToDocument(String docId, String studentMail) {
        Query query = new Query(Criteria.where("id").is(docId));
        Update update = new Update().push("students", studentMail);
        mongoTemplate.updateFirst(query, update, Document.class);
    }

    @Override
    public void removeStudentUnSaveFromDocument(String docId, String studentMail) {
        Query query = new Query(Criteria.where("id").is(docId));
        Update update = new Update().pull("students", studentMail);
        mongoTemplate.updateFirst(query, update, Document.class);
    }

    @Override
    public List<Document> findByListId(List<String> documentIds) {
        Query query = new Query(Criteria.where("id").in(documentIds));
        return mongoTemplate.find(query, Document.class);
    }

    @Override
    public List<Document> findAll() {
        return documentRepository.findAll();
    }

    @Override
    public List<Document> findByLecturer(Lecturer lecturer) {
        return documentRepository.findByCreatedBy(lecturer.getAccount().getEmail());
    }

    @Override
    public Page<Document> filterAndSearchDocument(String course, String topic, String title, String description, int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
        return documentRepository.filterAndSearchDocument(course, topic, title, description,
                pageable);
    }

    public String addFile(MultipartFile upload) throws IOException {

        //define additional metadata
        DBObject metadata = new BasicDBObject();
        metadata.put("fileSize", upload.getSize());

        //store in database which returns the objectID
        Object fileID = template.store(upload.getInputStream(),
                upload.getOriginalFilename(),
                upload.getContentType(),
                metadata);

        //return as a string
        return fileID.toString();
    }


    @Override
    public GridFSFile getGridFSFile(ObjectId id) {
        return template.findOne(new Query(Criteria.where("_id").is(id)));
    }

    @Override
    public byte[] getGridFSFileContent(ObjectId id) throws IOException {
        GridFSFile file = template.findOne(new Query(Criteria.where("_id").is(id)));
        if (file != null) {
            return IOUtils.toByteArray(operations.getResource(file).getInputStream());
        }
        return null;
    }


    @Override
    public Document addDocument(DocumentDto documentDTO, String id) {
        //search file
        if (null == documentDTO.getId()) {
            if (id.equalsIgnoreCase("fileNotFound")) {
                documentDTO.setSuffix("unknown");
                Document result = documentRepository.save(new Document(documentDTO));
                esDocumentRepository.save(new EsDocument(result));
                return result;
            } else if (id.equalsIgnoreCase("uploadToCloud")) {
                Document result = documentRepository.save(new Document(documentDTO));
                esDocumentRepository.save(new EsDocument(result));
                return result;
            } else {
                GridFSFile file = getGridFSFile(new ObjectId(id));
                documentDTO.setContentId(file.getObjectId());
                String filename = StringUtils.cleanPath(file.getFilename());
                String fileExtension = StringUtils.getFilenameExtension(filename);
                documentDTO.setFileName(filename);
                documentDTO.setSuffix(fileExtension);
                Document result = documentRepository.save(new Document(documentDTO));
                esDocumentRepository.save(new EsDocument(result));
                return result;
            }
        } else {
            Optional<Document> checkExist = documentRepository.findById(documentDTO.getId());
            if (checkExist.isEmpty()) {
                Document result = documentRepository.save(new Document(documentDTO));
                esDocumentRepository.save(new EsDocument(result));
                return result;
            }
            return null;
        }
    }

    @Override
    public Document updateDocument(Document document, String currentFileId, String id) {
        //search file
        if (null != currentFileId) {
            template.delete(new Query(Criteria.where("_id").is(currentFileId)));
        }
        if (null != document.getId()) {
            if (id.equalsIgnoreCase("fileNotFound")) {
                document.setSuffix("unknown");
                Document result = documentRepository.save(document);
                esDocumentRepository.save(new EsDocument(result));
                return result;
            } else if (id.equalsIgnoreCase("uploadToCloud")) {
                Document result = documentRepository.save(document);
                esDocumentRepository.save(new EsDocument(result));
                return result;
            } else {
                GridFSFile file = getGridFSFile(new ObjectId(id));
                document.setContentId(file.getObjectId());
                String filename = StringUtils.cleanPath(file.getFilename());
                String fileExtension = StringUtils.getFilenameExtension(filename);
                document.setFileName(filename);
                document.setSuffix(fileExtension);
                if (fileExtension != null) {
                    document.setDocType(DocumentEnum.DocumentFormat.getDocType(fileExtension));
                }
                Document result = documentRepository.save(document);
                esDocumentRepository.save(new EsDocument(result));
                return result;
            }
        } else {
            Document result = documentRepository.save(document);
            esDocumentRepository.save(new EsDocument(result));
            return result;
        }
    }

    @Override
    public Document updateDoc(Document document) {
        Document result = documentRepository.save(document);
        esDocumentRepository.save(new EsDocument(result));
        return result;
    }

    @Override
    public boolean softDelete(Document document) {
        Optional<Document> check = documentRepository.findById(document.getId());
        if (check.isPresent()) {
            // Here: delete note, question & answer
            questionService.deleteQuestionsByDocId(new ObjectId(document.getId()));
            answerService.deleteAnswersByDocId(new ObjectId(document.getId()));
            documentNoteService.deleteDocumentNoteByDocId(document.getId());
            notificationService.deleteNotificationByDocId(new ObjectId(document.getId()));


            // Soft delete
            document.setDeleteFlg(CommonEnum.DeleteFlg.DELETED);
            documentRepository.save(document);
            esDocumentRepository.delete(new EsDocument(document));
            return true;
        }
        return false;
    }

    @Override
    public boolean setToDefaultResourceType(Course course, Document document) {
        Optional<Document> check = documentRepository.findById(document.getId());
        if (check.isPresent()) {
            // Here: Soft delete note, question & answer

            // Soft delete
            document.setResourceType(course.getResourceTypes().get(4));
            documentRepository.save(document);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(String documentId) {
        Optional<Document> check = documentRepository.findById(documentId);
        if (check.isPresent()) {
            documentRepository.deleteById(documentId);
            return true;
        }
        return false;
    }

    @Override
    public HashMap<String, List<DocumentResponseDto>> findAllDocumentsByCourseAndResourceType(String courseId, String resourceTypeId) {
        Course course = courseRepository.findByIdAndDeleteFlg(courseId, CommonEnum.DeleteFlg.PRESERVED);
        List<ObjectId> topics = course.getTopics().stream().map(o -> new ObjectId(o.getId())).toList();
        Criteria criteria = Criteria.where("topic.id").in(topics)
                .and("resourceType.id").is(new ObjectId(resourceTypeId))
                .and("deleteFlg").is(CommonEnum.DeleteFlg.PRESERVED.toString());
        Query query = new Query(criteria);
        query.fields().include("id")
                .include("title")
                .include("topic")
                .include("description")
                .include("createdBy")
                .include("createdDate")
                .include("lastModifiedBy")
                .include("lastModifiedDate");
        HashMap<String, List<DocumentResponseDto>> topicResponseDtos = new HashMap<>();
        mongoTemplate.find(query, Document.class, "documents").stream().forEach(o -> {
            String topicKey = o.getTopic().getId();
            if (topicResponseDtos.containsKey(topicKey)) {
                if (topicResponseDtos.get(topicKey) != null) {
                    topicResponseDtos.get(topicKey).add(new DocumentResponseDto(o));
                }
            } else {
                topicResponseDtos.put(topicKey, new ArrayList<>());
                topicResponseDtos.get(topicKey).add(new DocumentResponseDto(o));
            }
        });

        return topicResponseDtos;
    }

    @Override
    public HashMap<String, List<DocumentResponseDto>> findAllDocumentsByCourseAndResourceType(String mail, String courseId, String resourceTypeId) {
        Course check = courseRepository.findByIdWithoutDeleteFlg(courseId).orElse(null);
        Criteria criteria;
        if(!check.getStudents().contains(mail)) {
            Course course = courseRepository.findByIdAndDeleteFlg(courseId, CommonEnum.DeleteFlg.PRESERVED);
            List<ObjectId> topics = course.getTopics().stream().map(o -> new ObjectId(o.getId())).toList();
            criteria = Criteria.where("topic.id").in(topics)
                    .and("resourceType.id").is(new ObjectId(resourceTypeId))
                    .and("deleteFlg").is(CommonEnum.DeleteFlg.PRESERVED.toString());
        } else {
            List<ObjectId> topics = check.getTopics().stream().map(o -> new ObjectId(o.getId())).toList();
            criteria = Criteria.where("topic.id").in(topics)
                    .and("resourceType.id").is(new ObjectId(resourceTypeId));
        }
        Query query = new Query(criteria);
        query.fields().include("id")
                .include("title")
                .include("topic")
                .include("description")
                .include("createdBy")
                .include("createdDate")
                .include("lastModifiedBy")
                .include("lastModifiedDate");
        HashMap<String, List<DocumentResponseDto>> topicResponseDtos = new HashMap<>();
        mongoTemplate.find(query, Document.class, "documents").stream().forEach(o -> {
            String topicKey = o.getTopic().getId();
            if (topicResponseDtos.containsKey(topicKey)) {
                if (topicResponseDtos.get(topicKey) != null) {
                    topicResponseDtos.get(topicKey).add(new DocumentResponseDto(o));
                }
            } else {
                topicResponseDtos.put(topicKey, new ArrayList<>());
                topicResponseDtos.get(topicKey).add(new DocumentResponseDto(o));
            }
        });

        return topicResponseDtos;
    }

    @Override
    public void removeMultiFile(String docId, ObjectId multiFileId) {
        Query query = new Query(Criteria.where("id").is(docId));
        Update update = new Update().pull("multipleFiles", multiFileId);
        mongoTemplate.updateFirst(query, update, Document.class);
    }

    @Override
    public Page<Document> findByListDocumentIdAndSearch(String search, List<String> documentIds, int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
        Criteria criteria = new Criteria();
        criteria.and("id").in(documentIds);
        if (search != null && !search.isEmpty()) {
            Criteria regexCriteria = new Criteria().orOperator(
                    Criteria.where("title").regex(Pattern.quote(search), "i"),
                    Criteria.where("description").regex(Pattern.quote(search), "i")
            );
            criteria.andOperator(regexCriteria);
        }
        Query query = new Query(criteria);
        query.fields().include("id", "title", "description", "createdDate", "resourceTypes");
        long total = mongoTemplate.count(query, Document.class);
        List<Document> documents = mongoTemplate.find(query.with(pageable), Document.class);
        return new PageImpl<>(documents, pageable, total);
    }
}
