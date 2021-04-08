package deml.nbatippspiel.Service;

import deml.nbatippspiel.Model.Game;
import deml.nbatippspiel.Model.Matchup;
import deml.nbatippspiel.Repository.MatchupRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MatchupService {

    private final MatchupRepository matchupRepository;
    private final GameService gameService;
    private final TeamService teamService;

    public MatchupService(final MatchupRepository matchupRepository, final GameService gameService, final TeamService teamService) {
        this.matchupRepository = matchupRepository;
        this.gameService = gameService;
        this.teamService = teamService;
    }

    public Matchup getById(final Integer id) {
        return matchupRepository.getOne(id);
    }

    public Matchup getByMatchupKey(final String matchupKey) {
        Optional<Matchup> optionalMatchup = findAll().stream().filter(m -> m.getMatchupKey().equals(matchupKey)).findFirst();
        return optionalMatchup.orElse(null);
    }

    public List<Matchup> findAll() {
        return matchupRepository.findAll();
    }

    public List<Matchup> getAllClosedMatchups() {
        return findAll().stream().filter(Matchup::isClosed).collect(Collectors.toList());
    }

    public void save(final Matchup matchup) {
        matchupRepository.save(matchup);
    }

    public Optional<Matchup> getFromMatchupKey(final String matchupKey) {
        return this.findAll().stream().filter(m -> Objects.equals(m.getMatchupKey(), matchupKey)).findFirst();
    }
}
