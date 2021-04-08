package deml.nbatippspiel.Service;

import deml.nbatippspiel.Model.Game;
import deml.nbatippspiel.Model.GameRow;
import deml.nbatippspiel.Model.Team;
import deml.nbatippspiel.Repository.GameRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameService {

    private final TeamService teamService;
    private final GameRepository gameRepository;

    public GameService(final TeamService teamService, final GameRepository gameRepository) {
        this.teamService = teamService;
        this.gameRepository = gameRepository;
    }

    public Game getById(final Long id) {
        return gameRepository.findById(id).orElse(null);
    }

    public List<Game> findAll() {
        return gameRepository.findAll();
    }

    public void save(final Game game) {
        gameRepository.save(game);
    }

    public void delete(final Game game) {
        gameRepository.delete(game);
    }

    public List<Game> getGamesForMatchup(final String tricode1, final String tricode2) {
        List<Game> allGames = findAll();
        return allGames.stream().filter(
                g -> (g.getMatchupKey().equals(tricode1 + tricode2) || g.getMatchupKey().equals(tricode2 + tricode1)) && g.getDate().isAfter(LocalDate.of(2021, 5, 20)))
                .collect(Collectors.toList());
    }

    public List<Game> getGamesForMatchup(final String matchupKey) {
        return findAll().stream().filter(g -> g.getMatchupKey().equals(matchupKey)).collect(Collectors.toList());
    }

    public List<GameRow> getGameRowsForDate(final LocalDate date) {
        final List<Game> games = gameRepository.findGamesForDate(date);
        List<GameRow> gameRows = new ArrayList<>();
        games.forEach(g -> {
            Team homeTeam = teamService.getTeamFromId(g.getHomeTeamId());
            Team awayTeam = teamService.getTeamFromId(g.getAwayTeamId());
                    gameRows.add(
                            new GameRow(
                                    awayTeam,
                                    g.getAwayTeamPoints(),
                                    homeTeam,
                                    g.getHomeTeamPoints(),
                                    g
                            )
                    );
                }
        );
        return gameRows;
    }
}
