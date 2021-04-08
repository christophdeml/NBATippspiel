package deml.nbatippspiel.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Random;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String getLoginpageWithError(
            final Model model,
            @RequestParam(value = "error", required = false) final String error,
            @RequestParam(value = "logout", required = false) final String logout
    ) {
        model.addAttribute("error", error);
        model.addAttribute("logout", logout);
        if(logout != null) {
            model.addAttribute("logoutMessage", getLogoutMessage());
        }
        return "login";
    }

    private String getLogoutMessage() {
        List<String> messages = List.of("allenthree", "letsgohome", "blockedbywade", "curryshot", "kyrietheshot");
        return messages.get(new Random().nextInt(5));
    }
}
