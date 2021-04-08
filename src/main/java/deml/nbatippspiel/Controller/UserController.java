package deml.nbatippspiel.Controller;

import deml.nbatippspiel.Model.User;
import deml.nbatippspiel.Service.GuessesService;
import deml.nbatippspiel.Service.TeamService;
import deml.nbatippspiel.Service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    private final UserService userService;
    private final GuessesService guessesService;
    private final TeamService teamService;

    public UserController(final UserService userService, final GuessesService guessesService, final TeamService teamService) {
        this.userService = userService;
        this.guessesService = guessesService;
        this.teamService = teamService;
    }

    @GetMapping("/user")
    public String getUser(final Model model, @RequestParam("username") final String username) {
        final User user = userService.getUserByUsername(username);
        model.addAttribute("user", userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
        model.addAttribute("showUser", user);
        model.addAttribute("closedMatchups", guessesService.getAllClosedMatchupsForUser(username));
        model.addAttribute("startedMatchups", guessesService.getAllStartedMatchupRowsForUsername(username));
        if (user.getChampionGuessId() != null) {
            model.addAttribute("selectedChampion", teamService.getOne(user.getChampionGuessId()));
        }
        return "user";
    }
}
