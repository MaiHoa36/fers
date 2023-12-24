package fpt.edu.eresourcessystem.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class CustomErrorController implements ErrorController {
    @RequestMapping("/error")
    public String error() {
        // Forward to the custom error page (e.g., error.html)
        return "exception/404";
    }
}
