package deml.nbatippspiel.Controller;

import deml.nbatippspiel.Model.User;
import deml.nbatippspiel.Model.UserPassword;
import deml.nbatippspiel.Repository.UserPasswordRepository;
import deml.nbatippspiel.Service.UserService;
import deml.nbatippspiel.Validation.UserValidation;
import deml.nbatippspiel.Validation.UserValidationResult;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static deml.nbatippspiel.Utility.UserUtility.getMergedUser;
import static deml.nbatippspiel.Utility.UserUtility.isNullOrEmpty;

@Controller
public class AccountController {

    private final UserService userService;
    private final UserPasswordRepository userPasswordRepository;

    public AccountController(final UserService userService, final UserPasswordRepository userPasswordRepository) {
        this.userService = userService;
        this.userPasswordRepository = userPasswordRepository;
    }

    @GetMapping("/account")
    public String getAccountPage(final Model model) {
        model.addAttribute("user", userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
        return "account";
    }

    @PostMapping("/account")
    public String updateAccountData(@RequestParam("usernameOld") final String usernameOld,
                                    @RequestParam("username") final String username,
                                    @RequestParam("firstname") final String firstname,
                                    @RequestParam("lastname") final String lastname,
                                    @RequestParam("passwordOld") final String passwordOld,
                                    @RequestParam("passwordNew") final String passwordNew,
                                    @RequestParam("passwordNewAgain") final String passwordNewAgain,
                                    final Model model) {

        final User oldUser = userService.getUserByUsername(usernameOld);
        final UserValidationResult<Boolean, List<String>> userValidationResult = new UserValidation().validateUserUpdate(oldUser, username, passwordOld, passwordNew, passwordNewAgain, userService, userPasswordRepository);
        model.addAttribute("isError", userValidationResult.getIsError());
        model.addAttribute("errorMessages", userValidationResult.getErrorMessages());

        if (!userValidationResult.getIsError()) {
            final User toBeSavedUser = getMergedUser(oldUser, username, firstname, lastname);
            final User savedUser = userService.save(toBeSavedUser);

            if (!savedUser.equals(toBeSavedUser)) {
                model.addAttribute("databaseError", true);
                model.addAttribute("databaseErrorMessage", "Could not save new user data");
            }

            if (!isNullOrEmpty(passwordNew)) {
                final UserPassword newUserPassword = new UserPassword(savedUser.getId(), new BCryptPasswordEncoder().encode(passwordNew));
                final UserPassword savedUserPassword = userPasswordRepository.save(newUserPassword);

                if (!savedUserPassword.equals(newUserPassword)) {

                    model.addAttribute("databaseError", true);
                    model.addAttribute("databaseErrorMessage", "Could not save new password data");
                }
            }
        }
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getPassword(), List.of(new SimpleGrantedAuthority("USER"))));
        model.addAttribute("user", userService.getUserByUsername(isNullOrEmpty(username) ? usernameOld : username));

        return "redirect:/logout";

    }
}
