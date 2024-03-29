package fpt.edu.eresourcessystem.service;

import fpt.edu.eresourcessystem.enums.CommonEnum;
import fpt.edu.eresourcessystem.model.Document;
import fpt.edu.eresourcessystem.model.ResourceType;
import fpt.edu.eresourcessystem.model.Topic;
import fpt.edu.eresourcessystem.repository.ResourceTypeRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("ResourceTypeService")
public class ResourceTypeServiceImpl implements ResourceTypeService {
    private final ResourceTypeRepository resourceTypeRepository;
    private final DocumentService documentService;
    @Autowired
    MongoTemplate mongoTemplate;

    public ResourceTypeServiceImpl(ResourceTypeRepository ResourceTypeRepository, DocumentService documentService) {
        this.resourceTypeRepository = ResourceTypeRepository;
        this.documentService = documentService;
    }

    @Override
    public List<ResourceType> findAll() {
        return resourceTypeRepository.findAll();
    }

    @Override
    public List<ResourceType> findByCourseId(String courseId) {
        Query query = new Query(Criteria.where("courseId").is(courseId));
        return mongoTemplate.find(query, ResourceType.class);
    }

    @Override
    public ResourceType addResourceType(ResourceType ResourceType) {
        ResourceType resourceType = new ResourceType(ResourceType);
        if (null == resourceType.getId()) {
            return resourceTypeRepository.insert(resourceType);
        } else {
            Optional<ResourceType> checkExist = resourceTypeRepository.findByIdAndDeleteFlg(resourceType.getId());
            if (checkExist.isEmpty()) {
                return resourceTypeRepository.save(resourceType);
            }
            return null;
        }
    }

    @Override
    public ResourceType findById(String ResourceTypeId) {
        Optional<ResourceType> ResourceType = resourceTypeRepository.findByIdAndDeleteFlg(ResourceTypeId);
        return ResourceType.orElse(null);
    }

    @Override
    public ResourceType updateResourceType(ResourceType ResourceType) {
        Optional<ResourceType> checkExist = resourceTypeRepository.findByIdAndDeleteFlg(ResourceType.getId());
        if (checkExist.isPresent()) {
            return resourceTypeRepository.save(ResourceType);
        }
        return null;
    }

    @Override
    public boolean delete(String ResourceTypeId) {
        Optional<ResourceType> check = resourceTypeRepository.findByIdAndDeleteFlg(ResourceTypeId);
        if (check.isPresent()) {
            resourceTypeRepository.deleteById(ResourceTypeId);
            return true;
        }
        return false;
    }

    @Override
    public void addDocumentToResourceType(String resourceTypeId, ObjectId documentId) {
        Query query = new Query(Criteria.where("id").is(resourceTypeId));
        Update update = new Update().push("documents", documentId);
        mongoTemplate.updateFirst(query, update, ResourceType.class);
    }

    @Override
    public void removeDocumentFromResourceType(String id, ObjectId objectId) {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update().pull("documents", objectId);
        mongoTemplate.updateFirst(query, update, ResourceType.class);
    }

    @Override
    public boolean softDelete(ResourceType resourceType) {
        Optional<ResourceType> check = resourceTypeRepository.findById(resourceType.getId());
        if (check.isPresent()) {
            // Soft delete document first
            for (Document document : resourceType.getDocuments()) {
                documentService.setToDefaultResourceType(resourceType.getCourse(), document);
            }
            // Soft delete topic
            resourceType.setDeleteFlg(CommonEnum.DeleteFlg.DELETED);
            resourceTypeRepository.save(resourceType);
            return true;
        }
        return false;
    }
}
