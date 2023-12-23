package fpt.edu.eresourcessystem.controller.restcontrollers;

import fpt.edu.eresourcessystem.dto.CourseDto;
import fpt.edu.eresourcessystem.dto.LecturerDto;
import fpt.edu.eresourcessystem.dto.Response.DataTablesResponse;
import fpt.edu.eresourcessystem.model.Account;
import fpt.edu.eresourcessystem.model.Course;
import fpt.edu.eresourcessystem.model.Lecturer;
import fpt.edu.eresourcessystem.model.Librarian;
import fpt.edu.eresourcessystem.service.AccountService;
import fpt.edu.eresourcessystem.service.CourseService;
import fpt.edu.eresourcessystem.service.LecturerService;
import fpt.edu.eresourcessystem.service.LibrarianService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/librarian")
public class LibrarianRestController {
    private final LecturerService lecturerService;
    private final CourseService courseService;
    private final AccountService accountService;
    private final LibrarianService librarianService;

    private Librarian getLoggedInLibrarian() {
        String loggedInEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        if (null == loggedInEmail || "anonymousUser".equals(loggedInEmail)) {
            return null;
        }
        Account loggedInAccount = accountService.findByEmail(loggedInEmail);
        if (loggedInAccount != null) {
            return librarianService.findByAccountId(loggedInAccount.getId());
        } else return null;
    }

    @GetMapping("/lectures/list")
    @ResponseBody
    public ResponseEntity<DataTablesResponse<LecturerDto>> getLecturers(
            @RequestParam("start") int start,
            @RequestParam("length") int length,
            @RequestParam("draw") int draw,
            @RequestParam(name = "search", required = false) String searchValue) {

        // Xử lý yêu cầu từ DataTables và trả về dữ liệu tương ứng
        Page<Lecturer> page = lecturerService.findLecturers(start, length, searchValue);

        Page<LecturerDto> lecturers = page.map(LecturerDto::new);

        // Prepare the response for DataTables
        DataTablesResponse<LecturerDto> response = new DataTablesResponse<>();

        response.setDraw(draw);
        response.setRecordsTotal(lecturerService.getTotalLecturers()); // Total records
        response.setRecordsFiltered(lecturers.getTotalPages());
        response.setData(lecturers.getContent());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/courses/list")
    @ResponseBody
    public ResponseEntity<DataTablesResponse<CourseDto>> getCourses(
            @RequestParam("start") int start,
            @RequestParam("length") int length,
            @RequestParam("draw") int draw,
            @RequestParam(name = "search", required = false) String searchValue) {
        int page = start / length + 1; // Calculate the page number
//        Pageable pageable = PageRequest.of(page, length);
        if (page < 0) page = 0;
        Page<Course> coursesPage = courseService.findByCodeOrNameOrDescription(searchValue.trim(), searchValue.trim(), searchValue.trim(), getLoggedInLibrarian().getId(), page, length);

        // Chuyển đổi từ Page<Course> sang Page<CourseDto>
        Page<CourseDto> courseDtoPage = coursesPage.map(course -> {
            CourseDto courseDto = new CourseDto();
            courseDto.setId(course.getId());
            courseDto.setCourseCode(course.getCourseCode());
            courseDto.setCourseName(course.getCourseName());
            courseDto.setDescription(course.getDescription());
            return courseDto;
        });

        DataTablesResponse<CourseDto> response = new DataTablesResponse<>();
        response.setDraw(draw);
        response.setRecordsTotal(courseService.countTotalCourses());
        response.setRecordsFiltered(courseDtoPage.getTotalElements());
        response.setData(courseDtoPage.getContent());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/lecturers")
    public ResponseEntity<DataTablesResponse<LecturerDto>> getAllLecturers(
            @RequestParam(value = "draw", defaultValue = "0") int draw,
            @RequestParam(value = "start", defaultValue = "0") int start,
            @RequestParam(value = "length", defaultValue = "3") int length,
            @RequestParam(value = "search[value]", defaultValue = "") String searchValue) {

        PageRequest pageRequest = PageRequest.of(start / length, length);
        Page<LecturerDto> lecturerPage = lecturerService.findAllLecturersWithSearch(searchValue, pageRequest);

        DataTablesResponse<LecturerDto> response = new DataTablesResponse<>();
        response.setData(lecturerPage.getContent());
        response.setDraw(draw);
        response.setRecordsTotal(lecturerPage.getTotalElements());
        response.setRecordsFiltered(lecturerPage.getTotalElements());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
