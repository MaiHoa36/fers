package fpt.edu.eresourcessystem.repository;

import com.mongodb.lang.NonNull;
import fpt.edu.eresourcessystem.enums.CommonEnum;
import fpt.edu.eresourcessystem.model.ResourceType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("resourceTypeRepository")
public interface ResourceTypeRepository extends MongoRepository<ResourceType, String> {

    @Override
    List<ResourceType> findAll();

    Optional<ResourceType> findById(String id);

    @Query("{$and: [{'deleteFlg' : 'PRESERVED'}, {'_id': ?0}]}")
    @NonNull
    Optional<ResourceType> findByIdAndDeleteFlg(String id);

}
