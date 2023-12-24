package fpt.edu.eresourcessystem.controller.restcontrollers;

import fpt.edu.eresourcessystem.dto.AccountDto;
import fpt.edu.eresourcessystem.dto.Response.AccountResponseDto;
import fpt.edu.eresourcessystem.dto.Response.DataTablesResponse;
import fpt.edu.eresourcessystem.model.Account;
import fpt.edu.eresourcessystem.model.CourseLog;
import fpt.edu.eresourcessystem.model.Feedback;
import fpt.edu.eresourcessystem.model.UserLog;
import fpt.edu.eresourcessystem.service.AccountService;
import fpt.edu.eresourcessystem.service.FeedbackService;
import fpt.edu.eresourcessystem.service.UserLogService;
import fpt.edu.eresourcessystem.utils.ExportFileExcelUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminRestController {

    private final AccountService accountService;
    private final FeedbackService feedbackService;
    private final UserLogService userLogService;
    private final ExportFileExcelUtil excelExporter;

    @GetMapping("/feedbacks")
    public ResponseEntity<DataTablesResponse<Feedback>> getAllFeedbacks(
            @RequestParam(value = "start", defaultValue = "0") Integer start,
            @RequestParam(value = "length", defaultValue = "2") Integer length,
            @RequestParam(value = "draw", defaultValue = "1") Integer draw,
            @RequestParam(value = "minDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date minDate,
            @RequestParam(value = "maxDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date maxDate) {

        Page<Feedback> feedbacksPage = feedbackService.findAllByDateRange(minDate, maxDate, PageRequest.of(start / length, length));
        DataTablesResponse<Feedback> response = new DataTablesResponse<>();

        response.setData(feedbacksPage.getContent());
        response.setDraw(draw);
        response.setRecordsTotal(feedbacksPage.getTotalElements());
        response.setRecordsFiltered(feedbacksPage.getTotalElements());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/user_log")
    public ResponseEntity<DataTablesResponse<UserLog>> listUserLogs(
            @RequestParam("draw") int draw,
            @RequestParam("start") int start,
            @RequestParam("length") int length,
            @RequestParam(value = "search[value]", defaultValue = "") String search,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @RequestParam(value = "role", required = false) String role) {

        int pageIndex = start / length; // Convert start to page index
        Page<UserLog> userLogsPage = userLogService.getUserLogsBySearchAndDate(search, startDate, endDate, role, pageIndex, length);

        DataTablesResponse<UserLog> response = new DataTablesResponse<>();
        response.setData(userLogsPage.getContent());
        response.setDraw(draw);
        response.setRecordsTotal(userLogsPage.getTotalElements());
        response.setRecordsFiltered(userLogsPage.getTotalElements());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/export-user-logs")
    public void exportCourseLogs(HttpServletResponse response,
                                 @RequestParam(value = "search[value]", defaultValue = "") String search,
                                 @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                 @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                                 @RequestParam(value = "role", required = false) String role) throws IOException {
        List<UserLog> userLogsList = userLogService.getUserLogsBySearchAndDate(search, startDate, endDate, role);
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=user_logs.xlsx";
        response.setHeader(headerKey, headerValue);
        excelExporter.exportUserLog(response.getOutputStream(), userLogsList);
    }


    @GetMapping(value = "/account", produces = {MimeTypeUtils.APPLICATION_JSON_VALUE})
    @Transactional
    public ResponseEntity<AccountResponseDto> getAccountByEmail(@ModelAttribute AccountDto accountDto,
                                                                @RequestParam String email) {
        Account account = accountService.findByEmail(email.trim());
        if (null == account) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        AccountResponseDto accountResponseDto = new AccountResponseDto(account);
        return new ResponseEntity<>(accountResponseDto, HttpStatus.OK);
    }

    @GetMapping("/feedback/respond")
    public ResponseEntity<?> updateFeedbackStatus(@RequestParam("id") String feedbackId,
                                                  @RequestParam("status") String status) {
        try {
            feedbackService.updateFeedbackStatus(feedbackId, status);
            return ResponseEntity.ok("Feedback status updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating feedback status.");
        }
    }
}
