package fpt.edu.eresourcessystem.service;

import fpt.edu.eresourcessystem.model.Lecturer;
import fpt.edu.eresourcessystem.model.LecturerCourse;
import fpt.edu.eresourcessystem.model.LecturerCourseId;
import fpt.edu.eresourcessystem.repository.LecturerCourseRepository;
import fpt.edu.eresourcessystem.repository.LecturerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("lecturerCourseService")
@RequiredArgsConstructor
public class LecturerCourseServiceImpl implements LecturerCourseService {
    private final LecturerCourseRepository lecturerCourseRepository;
    private final MongoTemplate mongoTemplate;

    @Override
    public LecturerCourse findById(LecturerCourseId lecturerCourseId) {
        Optional<LecturerCourse> lecturer = lecturerCourseRepository.findById(lecturerCourseId);
        return lecturer.orElse(null);
    }

    @Override
    public LecturerCourse add(LecturerCourse lecturerCourse) {
        if (null != lecturerCourse && null != lecturerCourse.getId()) {
            if (null != findById(lecturerCourse.getId())) {
                return null;
            } else {
                return lecturerCourseRepository.save(lecturerCourse);
            }
        }
        return null;
    }

    @Override
    public void delete(LecturerCourse lecturerCourse) {
        lecturerCourseRepository.delete(lecturerCourse);
    }

    @Override
    public List<LecturerCourse> findLecturerCoursesById(Lecturer lecturer) {

        return lecturerCourseRepository.findLecturerCoursesById(lecturer);
    }

    @Override
    public List<LecturerCourse> findCourseManageHistory(String courseId) {
        Query query = new Query();

        Criteria criteria = Criteria.where("id.courseId").is(courseId)
                .and("id.createdDate").exists(true)
                .and("id.lastModifiedDate").exists(true);
        query.addCriteria(criteria);
        query.with(Sort.by(Sort.Order.desc("id.lastModifiedDate")));
        return mongoTemplate.find(query, LecturerCourse.class);
    }

    @Override
    public LecturerCourse findCurrentLecturer(String courseId) {
        System.out.println(courseId);
        Query query = new Query();
        Criteria criteria = Criteria.where("id.courseId").is(courseId)
                .and("id.createdDate").exists(true)
                .and("id.lastModifiedDate").exists(false);
        query.addCriteria(criteria);
        return mongoTemplate.findOne(query, LecturerCourse.class);
    }


}
