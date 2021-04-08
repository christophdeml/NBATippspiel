package deml.nbatippspiel.Controller;

import deml.nbatippspiel.Model.Matchup;
import deml.nbatippspiel.Model.User;
import deml.nbatippspiel.Repository.TeamRepository;
import deml.nbatippspiel.Repository.UserRepository;
import deml.nbatippspiel.Service.GuessesService;
import deml.nbatippspiel.Service.MatchupService;
import deml.nbatippspiel.Service.UserMatchupService;
import deml.nbatippspiel.Service.UserService;
import deml.nbatippspiel.dto.MatchupRowDto;
import org.springframework.lang.Nullable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class GuessesController {

    private final UserService userService;
    private final TeamRepository teamRepository;
    private final GuessesService guessesService;
    private final MatchupService matchupService;
    private final UserMatchupService userMatchupService;

    public GuessesController(
            final UserService userService,
            final TeamRepository teamRepository,
            final GuessesService guessesService,
            final MatchupService matchupService,
            final UserMatchupService userMatchupService) {
        this.userService = userService;
        this.teamRepository = teamRepository;
        this.guessesService = guessesService;
        this.matchupService = matchupService;
        this.userMatchupService = userMatchupService;
    }

    @GetMapping("/guesses")
    public String getGuesses(final Model model, @Nullable @RequestParam("type") String type) {
        final User user = userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if(type == null || type.isBlank()) {
            type = "open";
        }
        model.addAttribute("user", user);
        model.addAttribute("teams", teamRepository.findAll());
        model.addAttribute("championSelectable", false);
        model.addAttribute("type", type);

        if (user.getChampionGuessId() != null) {
            model.addAttribute("selectedChampion", teamRepository.getOne(user.getChampionGuessId()));
        }

        switch (type) {
            case "open":
                model.addAttribute("matchups", guessesService.getAllUnguessedMatchupRows());
                model.addAttribute("matchupRowDto", new MatchupRowDto());
                break;
            case "guessed":
                model.addAttribute("guessedMatchups", guessesService.getAllGuessedMatchupRows());
                model.addAttribute("conferenceOptions", List.of(new String[]{"Beide Conferences", "Beide"}, new String[]{"Eastern Conference", "East"}, new String[]{"Western Conference", "West"}));
                model.addAttribute("roundOptions", List.of(new String[]{"Alle Runden", "Alle"}, new String[]{"1. Runde", "1"}, new String[]{"Conference Semi Finals", "2"}, new String[]{"Conference Finals", "3"}, new String[]{"Finals", "4"}));

                break;
            case "closed":
                model.addAttribute("closedMatchups", guessesService.getAllClosedMatchups());
                break;
            case "unguessed":
                model.addAttribute("unguessableMatchups", guessesService.getAllUnguessedStartedMatchups());
                break;
            default:
                return "error";
        }
        return "guesses";
    }

    @PostMapping("/guesses/champion")
    public String postChampion(@RequestParam("champion") final Integer teamId) {
        User user = userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        user.setChampionGuessId(teamId);
        userService.save(user);
        return "redirect:/guesses";
    }

    @GetMapping("/guesses/champion/delete")
    public String deleteChampion() {
        User user = userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        user.setChampionGuessId(null);
        userService.save(user);
        return "redirect:/guesses";
    }

    @PostMapping("/guesses/matchups")
    public String postMatchups(@ModelAttribute MatchupRowDto matchupRowDto) {
        User user = userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        matchupRowDto.getMatchupRows().forEach(mr -> {
            if (mr.guessedTeam1wins != null && mr.guessedTeam2wins != null && mr.getMatchupKey() != null) {
                Optional<Matchup> matchup = matchupService.getFromMatchupKey(mr.getMatchupKey());
                matchup.ifPresent(m -> userMatchupService.save(user.getId(), m.getId(), mr.guessedTeam1wins - mr.guessedTeam2wins));
            }
        });
        return "redirect:/guesses";
    }

    @PostMapping("guesses/delete/{matchupKey}")
    public String deleteGuess(@PathVariable("matchupKey") final String matchupKey) {
        User user = userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        userMatchupService.deleteUserMatchupForUser(user.getId(), matchupKey);
        return "redirect:/guesses";
    }
}
