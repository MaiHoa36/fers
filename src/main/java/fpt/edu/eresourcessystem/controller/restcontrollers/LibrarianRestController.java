package fpt.edu.eresourcessystem.controller.restcontrollers;

import fpt.edu.eresourcessystem.dto.Response.DataTablesResponse;
import fpt.edu.eresourcessystem.dto.Response.LecturerDto;
import fpt.edu.eresourcessystem.model.Lecturer;
import fpt.edu.eresourcessystem.service.LecturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/librarian")
public class LibrarianRestController {

    @Autowired
    private LecturerService lecturerService;

    @GetMapping("/lectures/list")
    @ResponseBody
    public ResponseEntity<DataTablesResponse<LecturerDto>> getLecturers(
            @RequestParam("start") int start,
            @RequestParam("length") int length,
            @RequestParam("draw") int draw,
            @RequestParam(value = "search[value]", defaultValue = "") String searchValue) {

        // Xử lý yêu cầu từ DataTables và trả về dữ liệu tương ứng
        List<Lecturer> lecturer = lecturerService.findLecturers(start, length, searchValue);
        List<LecturerDto> lecturers = lecturer.stream()
                .map(LecturerDto::new)
                .collect(Collectors.toList());

        int totalLecturers = lecturerService.getTotalLecturers(); // Tổng số hàng trong tập dữ liệu

        int filteredCount = lecturerService.getFilteredCount(searchValue); // Số hàng sau khi áp dụng bộ lọc

        DataTablesResponse<LecturerDto> response = new DataTablesResponse<>();
        response.setData(lecturers);
        response.setDraw(draw);
        response.setRecordsTotal(totalLecturers);
        response.setRecordsFiltered(filteredCount);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/lecturers")
    public ResponseEntity<DataTablesResponse<LecturerDto>> getAllLecturers(
            @RequestParam(value = "draw", defaultValue = "0") int draw,
            @RequestParam(value = "start", defaultValue = "0") int start,
            @RequestParam(value = "length", defaultValue = "10") int length,
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
