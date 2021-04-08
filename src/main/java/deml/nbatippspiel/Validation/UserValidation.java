package deml.nbatippspiel.Validation;

import deml.nbatippspiel.Model.User;
import deml.nbatippspiel.Model.UserPassword;
import deml.nbatippspiel.Repository.UserPasswordRepository;
import deml.nbatippspiel.Repository.UserRepository;
import deml.nbatippspiel.Service.UserService;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static deml.nbatippspiel.Utility.UserUtility.isNullOrEmpty;

public class UserValidation {

    public UserValidation() {}

    public UserValidationResult<Boolean, List<String>> validateUserUpdate(
            final User oldUser,
            final String username,
            final String passwordOld,
            final String passwordNew,
            final String passwordNewAgain,
            final UserService userService,
            final UserPasswordRepository userPasswordRepository
    ) {

        boolean isError = false;
        List<String> errorMessages = new ArrayList<>();

        final boolean isNewPassword = !isNullOrEmpty(passwordNew);
        final boolean doNewPasswordsMatch = passwordNew.equals(passwordNewAgain);
        final boolean isOldPasswordOk = checkIfPasswordIsOk(passwordOld, userPasswordRepository.findById(oldUser.getId()).orElse(new UserPassword()));
        final boolean isNewUsername = !isNullOrEmpty(username);

        if (isNewPassword) {
            if (!doNewPasswordsMatch) {
                isError = true;
                errorMessages.add("New passwords do not match");
            }
            if (!isOldPasswordOk) {
                isError = true;
                errorMessages.add("Old passwors is wrong");
            }
        }

        if (isNewUsername && checkIfNewUsernameIsTaken(username, userService)) {
            isError = true;
            errorMessages.add("Username is already taken");
        }

        return new UserValidationResult<>(isError, errorMessages);
    }

    private boolean checkIfNewUsernameIsTaken(final String newUsername, final UserService userService) {
        return userService.getAllUsernames().contains(newUsername);
    }

    private boolean checkIfPasswordIsOk(final String passwordOld, final UserPassword userPassword) {
        return getPasswordEncoder().matches(passwordOld, userPassword.getPassword());
    }

    @Bean
    private PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
