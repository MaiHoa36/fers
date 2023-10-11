package fpt.edu.eresourcessystem.service;

import fpt.edu.eresourcessystem.model.Course;
import fpt.edu.eresourcessystem.model.Student;
import fpt.edu.eresourcessystem.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("studentService")
public class StudentServiceImpl implements StudentService{
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public void addStudent(Student student) {
        studentRepository.insert(student);
    }

    @Override
    public Student findByAccountId(String accountId) {
        Student student = studentRepository.findByAccountId(accountId);
        return student;
    }

    @Override
    public void updateStudentSavedCourse(Student student) {
        studentRepository.save(student);
    }

    @Override
    public boolean checkCourseSaved(String studentId, String courseId) {
        Optional<Student> student = studentRepository.findById(studentId);
        if(student.isPresent()){
            Student existedStudent = student.get();
            if(null!=existedStudent.getSavedCourses()){
                for (String cId: existedStudent.getSavedCourses() ) {
                    if(cId.equals(courseId)){
                        return true;
                    }
                }
            }
        }return false;
    }

    @Override
    public boolean saveACourse(String studentId, String courseId){
        Optional<Student> student = studentRepository.findById(studentId);
        if(!student.isPresent()){
            return false;
        }
        List<String> savedCourses = student.get().getSavedCourses();
        // check student have saved any course
        if(null == savedCourses ){
            savedCourses = new ArrayList<>();
        }
        // check course existed in saved course
        for (String cId: student.get().getSavedCourses()) {
            if(courseId.equals(cId)){
                return false;
            }
        }
        savedCourses.add(courseId);
        student.get().setSavedCourses(savedCourses);
        updateStudentSavedCourse(student.get());
        return true;
    }
}
