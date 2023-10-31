package fpt.edu.eresourcessystem.service;

import fpt.edu.eresourcessystem.enums.CommonEnum;
import fpt.edu.eresourcessystem.model.Course;
import fpt.edu.eresourcessystem.model.Lecturer;
import fpt.edu.eresourcessystem.model.LecturerCourse;
import fpt.edu.eresourcessystem.repository.CourseRepository;
import fpt.edu.eresourcessystem.repository.LecturerCourseRepository;
import fpt.edu.eresourcessystem.repository.LecturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service("lecturerService")
public class LecturerServiceImpl implements LecturerService {
    private final LecturerRepository lecturerRepository;
    private final CourseService courseService;
    private final LecturerCourseRepository lecturerCourseRepository;

    @Autowired
    public LecturerServiceImpl(LecturerRepository lecturerRepository, CourseService courseService, LecturerCourseRepository lecturerCourseRepository) {
        this.lecturerRepository = lecturerRepository;
        this.courseService = courseService;
        this.lecturerCourseRepository = lecturerCourseRepository;
    }

    @Override
    public Lecturer findByCourseId(String courseId) {
        return lecturerRepository.findByCourseId(courseId);
    }

    @Override
    public Lecturer addLecturer(Lecturer lecturer) {
        lecturer.setDeleteFlg(CommonEnum.DeleteFlg.PRESERVED);
        return lecturerRepository.save(lecturer);
    }

    @Override
    public Lecturer updateLecturer(Lecturer lecturer) {
        Optional<Lecturer> foundLecturer = lecturerRepository.findById(lecturer.getId());
        if(foundLecturer.isPresent()){
            Lecturer result =  lecturerRepository.save(lecturer);
            return result;
        }
        return null;
    }

    @Override
    public List<Lecturer> findAll() {
        return lecturerRepository.findAll();
    }

    @Override
    public Lecturer findByAccountId(String accountId) {
        return lecturerRepository.findByAccountId(accountId);
    }

    @Override
    public List<Lecturer> findByListLecturerIds(List<String> ids) {
        List<Lecturer> lecturers = lecturerRepository.findByIds(ids);
        return lecturers;
    }

    @Override
    public Lecturer findCurrentCourseLecturer(String courseId) {
        LecturerCourse lecturerCourse = lecturerCourseRepository.findCurrentCourseLecturer(courseId);
        if (null != lecturerCourse) {
            if (null != lecturerCourse.getId().getLecturerId()) {
                Optional<Lecturer> lecturer = lecturerRepository.findById(
                        lecturerCourse.getId().getLecturerId());
                return lecturer.orElse(null);
            }
        }
        return null;
    }

    public List<Lecturer> findAllLecture() {
        return lecturerRepository.findAll();
    }


    public Page<Lecturer> findLecturerByLecturerIdLike(String lectureId, int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
        Page<Lecturer> page = lecturerRepository.findLecturerByIdLike(lectureId, pageable);
        return page;
    }



//    @Override
//    public Lecturer addLectureWithCourse(Lecturer lecturer) {
//        Optional<Lecturer> foundLecturer = lecturerRepository.findById(lecturer.getId());
//        if(foundLecturer.isPresent()){
//
//            Lecturer result =  lecturerRepository.save(lecturer);
//            return result;
//        }
//        return null;
//    }

    public Lecturer findLecturerByEmail(String email) {
        return lecturerRepository.findByAccount_Email(email);
    }

    public boolean removeCourse(String lectureId, Course course) {
        Optional<Lecturer> optionalLecture = lecturerRepository.findById(lectureId);

        if (optionalLecture.isPresent()) {
            Lecturer lecture = optionalLecture.get();

            // Check if the course is associated with the lecture
            if (lecture.getCourses().contains(course)) {
                lecture.getCourses().remove(course);
                lecturerRepository.save(lecture);

                // Optionally, update the course to remove the lecture's association
//                course.setLecturer(null);
//                courseService.updateCourse(course);

                return true;
            }
        }
        return false;
    }
}
