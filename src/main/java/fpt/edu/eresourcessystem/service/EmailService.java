package fpt.edu.eresourcessystem.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendCourseAssignmentEmail(String to, String courseName) throws MessagingException {
        MimeMessage message = createCourseAssignmentEmail(to, courseName);
        javaMailSender.send(message);
    }

    private MimeMessage createCourseAssignmentEmail(String to, String courseName) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom("noreply@example.com");
        helper.setTo(to);

        // Setting the subject of the email in Vietnamese
        String subject = "Thông Báo: Phân Công Quản Lý Khóa Học " + courseName;
        helper.setSubject(subject);

        // Composing a detailed HTML body of the email in Vietnamese
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
        String htmlBody = String.format(
                "<html>" +
                        "<body>" +
                        "<h2>Kính gửi Giảng viên,</h2>" +
                        "<p>Chúng tôi xin thông báo bạn đã được phân công quản lý khóa học <strong>'%s'</strong> từ thời điểm <strong>%s</strong>.</p>" +
                        "<p>Đây là một cơ hội tuyệt vời để bạn thể hiện khả năng và đóng góp vào sự phát triển của khóa học.</p>" +
                        "<p>Vui lòng đăng nhập vào hệ thống <a href='https://www.eresource.site/guest'>tại đây</a> quản lý khóa học để cập nhật nội dung, tài liệu và thông tin liên quan đến khóa học. " +
                        "Nếu bạn có bất kỳ thắc mắc hoặc cần sự hỗ trợ, hãy liên hệ với chúng tôi.</p>" +
                        "<p>Chúc bạn một ngày làm việc hiệu quả!</p>" +
                        "<p>Trân trọng,<br>Thư viện đại học FPT.</p>" +
                        "</body>" +
                        "</html>",
                courseName, dateTime);
        helper.setText(htmlBody, true);

        return message;
    }
    public void sendCourseRemovalEmail(String to, String courseName) throws MessagingException {
        MimeMessage message = createCourseRemovalEmail(to, courseName);
        javaMailSender.send(message);
    }

    private MimeMessage createCourseRemovalEmail(String to, String courseName) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom("noreply@example.com");
        helper.setTo(to);

        // Setting the subject of the email in Vietnamese
        String subject = "Thông Báo: Khóa Học '" + courseName + "' Đã Bị Loại Bỏ";
        helper.setSubject(subject);

        // Composing a detailed HTML body of the email in Vietnamese
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
        String htmlBody = String.format(
                "<html>" +
                        "<body>" +
                        "<h2>Kính gửi Giảng viên,</h2>" +
                        "<p>Chúng tôi xin thông báo rằng khóa học <strong>'%s'</strong> đã được loại bỏ khỏi hệ thống của chúng tôi từ thời điểm <strong>%s</strong>.</p>" +
                        "<p>Chúng tôi hiểu rằng đây có thể là tin không vui đối với bạn, nhưng quyết định này đã được cân nhắc kỹ lưỡng dựa trên nhiều yếu tố khác nhau.</p>" +
                        "<p>Nếu bạn có bất kỳ câu hỏi hoặc cần sự hỗ trợ, vui lòng không ngần ngại liên hệ với chúng tôi.</p>" +
                        "<p>Chúng tôi mong muốn được hợp tác với bạn trong các dự án và khóa học khác trong tương lai.</p>" +
                        "<p>Trân trọng,<br>Thư viện đại học FPT.</p>" +
                        "</body>" +
                        "</html>",
                courseName, dateTime);
        helper.setText(htmlBody, true);

        return message;
    }

}
