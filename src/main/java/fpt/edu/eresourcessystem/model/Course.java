package fpt.edu.eresourcessystem.model;

import fpt.edu.eresourcessystem.dto.CourseDto;
import fpt.edu.eresourcessystem.enums.CommonEnum;
import fpt.edu.eresourcessystem.enums.CourseEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Document("courses")
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @Id
    private String id;

    @NotNull
    @DocumentReference(lazy = true)
    private Librarian librarian;

    @DocumentReference(lazy = true)
    private Lecturer lecturer;

    @Indexed(unique = true)
    @NotEmpty(message = "course.validation.courseCode.required")
    private String courseCode;

    @NotEmpty(message = "course.validation.courseName.required")
    private String courseName;
    private String description;

    @DocumentReference //(lazy = true)
    private TrainingType trainingType;

    @DocumentReference(lazy = true)
    private List<Topic> topics;
    @DocumentReference(lazy = true)
    private List<ResourceType> resourceTypes;
    private List<String> students;

    @NotNull
    private CourseEnum.Status status;
//    private List<LecturerCourseId> lecturerCourseIds;

    // Delete flag
    private CommonEnum.DeleteFlg deleteFlg;
    private LocalDate deletedDate;
    private String deletedBy;

    //Audit Log
    @CreatedBy
    private String createdBy;
    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedBy
    private String lastModifiedBy;
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    // Constructor DTO
    public Course(CourseDto courseDTO, CourseEnum.@NotNull Status status) {
        this.id = courseDTO.getId();
        this.librarian = courseDTO.getLibrarian();
        this.lecturer = courseDTO.getLecturer();
        this.courseCode = courseDTO.getCourseCode();
        this.courseName = courseDTO.getCourseName();
        this.description = courseDTO.getDescription();
        this.trainingType = courseDTO.getTrainingType();
        this.status = status;
        this.deleteFlg = CommonEnum.DeleteFlg.PRESERVED;
    }

    public Course publishCourse(CourseDto courseDTO) {
        return new Course(courseDTO, CourseEnum.Status.PUBLISH);
    }

    public Course privateCourse(CourseDto courseDTO) {
        return new Course(courseDTO, CourseEnum.Status.PRIVATE);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        return lecturer != null ? lecturer.getId().equals(course.lecturer.getId()) : course.lecturer == null;
    }

    @Override
    public int hashCode() {
        // Implement a hashCode method if you override equals
        return lecturer != null ? lecturer.getId().hashCode() : 0;
    }
}