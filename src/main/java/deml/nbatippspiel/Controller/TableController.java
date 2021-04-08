package deml.nbatippspiel.Controller;

import deml.nbatippspiel.Repository.UserRepository;
import deml.nbatippspiel.Service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TableController {

    private final UserService userService;

    public TableController(
            final UserService userService
    ) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String getTable() {
        return "redirect:/table";
    }

    @GetMapping("/table")
    public String getTable(final Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("user", userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));

        return "table";
    }

}
