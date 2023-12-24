package fpt.edu.eresourcessystem.service;

import fpt.edu.eresourcessystem.enums.CommonEnum;
import fpt.edu.eresourcessystem.model.Course;
import fpt.edu.eresourcessystem.model.TrainingType;
import fpt.edu.eresourcessystem.repository.TrainingTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("trainingtypeService")
public class TrainingTypeServiceImpl implements TrainingTypeService {

    private final TrainingTypeRepository trainingTypeRepository;

    //    @Autowired
    public TrainingTypeServiceImpl(TrainingTypeRepository trainingTypeRepository) {
        this.trainingTypeRepository = trainingTypeRepository;
    }

    public TrainingType save(TrainingType trainingType) {
        if (trainingType == null) {
            throw new IllegalArgumentException("TrainingType cannot be null");
        }
        return trainingTypeRepository.save(trainingType);
    }

    public List<TrainingType> findAll() {
        return trainingTypeRepository.findAll();
    }

    public Optional<TrainingType> findById(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }
        return trainingTypeRepository.findById(id);
    }

    @Override
    public void deleteById(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }
        if (!trainingTypeRepository.existsById(id)) {
            throw new RuntimeException("TrainingType with ID " + id + " does not exist");
        }
        trainingTypeRepository.deleteById(id);
    }

    @Override
    public TrainingType updateTrainingType(TrainingType trainingType) {
        if (trainingType == null) {
            throw new IllegalArgumentException("TrainingType cannot be null");
        }

        TrainingType existingTrainingType = trainingTypeRepository.findById(trainingType.getId())
                .orElseThrow(() -> new RuntimeException("Training type not found"));

        existingTrainingType.setTrainingTypeName(trainingType.getTrainingTypeName());
        existingTrainingType.setTrainingTypeDescription(trainingType.getTrainingTypeDescription());

        return trainingTypeRepository.save(existingTrainingType);
    }


    @Override
    public TrainingType addCourseToTrainingType(String trainingTypeId, Course course) {
        Optional<TrainingType> trainingTypeOpt = trainingTypeRepository.findById(trainingTypeId);

        if (trainingTypeOpt.isPresent()) {
            TrainingType trainingType = trainingTypeOpt.get();
            course.setTrainingType(trainingType);
            trainingType.getCourses().add(course);
            return trainingTypeRepository.save(trainingType);
        }
        return null;
    }

    @Override
    public void removeCourseFromTrainingType(String trainingTypeId, String courseId) {
        // Retrieve the TrainingType by its ID
        Optional<TrainingType> trainingTypeOptional = trainingTypeRepository.findById(trainingTypeId);

        if (trainingTypeOptional.isPresent()) {
            TrainingType trainingType = trainingTypeOptional.get();

            // Remove the course with the given ID from the list of courses
            trainingType.setCourses(trainingType.getCourses().stream()
                    .filter(course -> !course.getId().equals(courseId))
                    .collect(Collectors.toList()));

            // Save the updated TrainingType back to the database
            trainingTypeRepository.save(trainingType);
        } else {
            // Handle the case where the TrainingType is not found
            throw new NoSuchElementException("TrainingType with ID " + trainingTypeId + " not found.");
        }
    }

//    @Override
//    public TrainingType getTrainingTypeByCourseId(String courseId) {
//        return trainingTypeRepository.findById(courseId)
//                .orElseThrow(() -> new NoSuchElementException("TrainingType with Course ID " + courseId + " not found."));
//    }

    @Override
    public boolean softDelete(TrainingType trainingType) {
        Optional<TrainingType> existingTrainingType = trainingTypeRepository.findById(trainingType.getId());
        if (existingTrainingType.isPresent()) {
            TrainingType toDelete = existingTrainingType.get();
            toDelete.setDeleteFlg(CommonEnum.DeleteFlg.DELETED); // Mark as deleted
            trainingTypeRepository.save(toDelete);
            return true;
        }
        return false;
    }


}
