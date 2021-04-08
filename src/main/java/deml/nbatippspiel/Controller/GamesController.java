package deml.nbatippspiel.Controller;

import deml.nbatippspiel.Service.GameService;
import deml.nbatippspiel.Service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.Month;

@Controller
public class GamesController {

    private final UserService userService;
    private final GameService gameService;

    public GamesController(final UserService userService, final GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
    }

    @GetMapping("/games")
    public String getGames(final Model model, @RequestParam(value = "date", required = false) final String inputDate) throws Exception {
        LocalDate date = LocalDate.now();
        if (inputDate != null && !inputDate.isBlank()) {
            date = LocalDate.parse(inputDate);
        }
        model.addAttribute("user", userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
        model.addAttribute("gameRows", gameService.getGameRowsForDate(date));
        model.addAttribute("date", date);
        model.addAttribute("dateString", getDateString(date, true));
        model.addAttribute("dateStringPrevious", getDateString(date.minusDays(1), false));
        model.addAttribute("dateStringNext", getDateString(date.plusDays(1), false));
        return "games";
    }

    private String getDateString(final LocalDate date, boolean withYear) throws Exception {
        String dateString;
        final LocalDate today = LocalDate.now();

        if (today.equals(date)) {
            dateString = "Heute";
        } else if (today.minusDays(1).equals(date)) {
            dateString = "Gestern";
        } else if (today.minusDays(2).equals(date)) {
            dateString = "Vorgestern";
        } else if (today.plusDays(1).equals(date)) {
            dateString = "Morgen";
        } else if (today.plusDays(2).equals(date)) {
            dateString = "Übermorgen";
        } else {
            dateString = date.getDayOfMonth() + ". " + getMonthAsGermanString(date.getMonth());
            if (withYear) {
                dateString += " " + date.getYear();
            }
        }

        return dateString;
    }

    private String getMonthAsGermanString(final Month month) throws Exception {
        switch (month.getValue()) {
            case 1:
                return "Januar";
            case 2:
                return "Februar";
            case 3:
                return "März";
            case 4:
                return "April";
            case 5:
                return "Mai";
            case 6:
                return "Juni";
            case 7:
                return "Juli";
            case 8:
                return "August";
            case 9:
                return "September";
            case 10:
                return "Oktober";
            case 11:
                return "November";
            case 12:
                return "Dezember";
            default:
                throw new Exception("Value for month is invalid: month.getValue(): " + month.getValue());
        }
    }
}
