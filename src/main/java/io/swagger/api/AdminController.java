package io.swagger.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/")
    public String admin() {
        return "redirect:/admin/login";
    }

    @GetMapping("/login")
    public String login() {
        return "admin_login";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "admin_dashboard";
    }

    @GetMapping("/users")
    public String users() {
        return "admin_users";
    }

    @GetMapping("/settings")
    public String settings() {
        return "admin_settings";
    }

    @GetMapping("/logs")
    public String logs() {
        return "admin_logs";
    }
}
