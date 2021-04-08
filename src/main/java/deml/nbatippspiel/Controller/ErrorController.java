package deml.nbatippspiel.Controller;

import deml.nbatippspiel.Service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    final UserService userService;

    public ErrorController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/error")
    public String handleError(final Model model) {
        model.addAttribute("user", userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
        return "error";
    }

    @PostMapping("/error/problem")
    public String postProblem() {
        return "redirect:/table";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
