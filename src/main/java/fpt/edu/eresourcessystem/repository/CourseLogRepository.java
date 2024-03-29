package fpt.edu.eresourcessystem.repository;

import fpt.edu.eresourcessystem.model.CourseLog;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository("courseLogRepository")
public interface CourseLogRepository extends MongoRepository<CourseLog, String> {
    List<CourseLog> findByEmail(String courseId);
    @NotNull List<CourseLog> findAll(@NotNull Sort sort);

    Page<CourseLog>
    findByEmailLikeAndCourseNameLikeAndCourseCodeLikeAndCreatedDateBetween(
            String email, String courseName, String courseCode, Date startDate, Date endDate, Pageable pageable);

}
