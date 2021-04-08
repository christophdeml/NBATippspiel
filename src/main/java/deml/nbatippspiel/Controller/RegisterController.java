package deml.nbatippspiel.Controller;

import deml.nbatippspiel.Model.User;
import deml.nbatippspiel.Model.UserPassword;
import deml.nbatippspiel.Repository.UserPasswordRepository;
import deml.nbatippspiel.Repository.UserRepository;
import deml.nbatippspiel.Security.SecurityConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.query.Param;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

//Registrierung ist abgeschlossen
//@Controller
public class RegisterController {

    private final UserRepository userRepository;
    private final UserPasswordRepository userPasswordRepository;

    public RegisterController(final UserRepository userRepository, final UserPasswordRepository userPasswordRepository) {
        this.userRepository = userRepository;
        this.userPasswordRepository = userPasswordRepository;
    }

    @GetMapping("/register")
    public String getRegister() {
        return "register";
    }

    @PostMapping("/perform_registration")
    public String register(@RequestParam("username") final String username,
                           @RequestParam("firstname") final String firstname,
                           @RequestParam("lastname") final String lastname,
                           @RequestParam("password") final String password,
                           @RequestParam("password_again") final String passwordAgain,
                           final Model model) throws Exception {

        boolean errors = false;
        List<String> allUsernames = userRepository.getAllUsernames();

        if (allUsernames.contains(username)) {
            errors = true;
            model.addAttribute("usernameIsTaken", true);
        }

        if (password == null || password.isEmpty()) {
            errors = true;
            model.addAttribute("noPasswordGiven", true);
        }

        if (password != null && !password.equals(passwordAgain)) {
            errors = true;
            model.addAttribute("passwordsDontMatch", true);
        }

        if (password != null && password.length() < 8) {
            errors = true;
            model.addAttribute("passwordTooShort", true);
        }

        if(password != null && (password.contains(" ") || password.contains("\t"))) {
            errors = true;
            model.addAttribute("passwordContainsWhitespaces", true);
        }

        User toBeSavedUser = null, savedUser = null;
        UserPassword toBeSavedUserPassword = null, savedUserPassword = null;

        try {

            toBeSavedUser = new User(userRepository.getNextId(), username, firstname, lastname);
            savedUser = userRepository.save(toBeSavedUser);

            toBeSavedUserPassword = new UserPassword(savedUser.getId(), getPasswordEncoder().encode(password));
            savedUserPassword = userPasswordRepository.save(toBeSavedUserPassword);

        } catch (Exception e) {
            errors = true;
            model.addAttribute("couldNotInsert");
            e.printStackTrace();
        }

        if (!errors && toBeSavedUser.equals(savedUser) && toBeSavedUserPassword.equals(savedUserPassword)) {
            return "redirect:/login";
        } else {
            try {
                assert savedUser != null;
                userRepository.delete(savedUser);
                assert savedUserPassword != null;
                userPasswordRepository.delete(savedUserPassword);
            } catch (Exception e) {
                e.printStackTrace();
            }
            model.addAttribute("errors", errors);
            return "register";
        }

    }

    @Bean
    private PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
