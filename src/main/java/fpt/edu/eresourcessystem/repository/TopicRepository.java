package fpt.edu.eresourcessystem.repository;

import com.mongodb.lang.NonNull;
import fpt.edu.eresourcessystem.model.Topic;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("topicRepository")
public interface TopicRepository extends MongoRepository<Topic, String> {

    @Override
    List<Topic> findAll();

    @Query("{$and: [{'deleteFlg' : 'PRESERVED'}, {'_id': ?0}]}")
    @NonNull
    Optional<Topic> findById(String id);

}
