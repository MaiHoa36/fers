package fpt.edu.eresourcessystem.service;


import fpt.edu.eresourcessystem.model.Lecturer;
import fpt.edu.eresourcessystem.model.LecturerCourse;
import fpt.edu.eresourcessystem.model.LecturerCourseId;

import java.util.List;

public interface LecturerCourseService {
    LecturerCourse findById(LecturerCourseId lecturerCourseId);

    LecturerCourse add(LecturerCourse lecturerCourse);

    void delete(LecturerCourse lecturerCourse);

    List<LecturerCourse> findLecturerCoursesById(Lecturer lecturer);

    List<LecturerCourse> findCourseManageHistory(String courseId);

    LecturerCourse findCurrentLecturer(String courseId);
}
