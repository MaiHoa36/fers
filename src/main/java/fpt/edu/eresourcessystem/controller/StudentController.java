package fpt.edu.eresourcessystem.controller;

import fpt.edu.eresourcessystem.model.Account;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class StudentController {
    @GetMapping("/student")
    public String getLibraryManageDashboard(@ModelAttribute Account account) {
        return "manager_dashboard";
    }
}