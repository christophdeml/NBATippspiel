package deml.nbatippspiel.Service;

import deml.nbatippspiel.Model.Team;
import deml.nbatippspiel.Repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    public TeamService(final TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Team getTeamFromId(final Integer teamId) {
        return teamRepository.getOne(teamId);
    }

    public Optional<Team> getChampion() {
        return teamRepository.findAll().stream().filter(Team::isChampion).findFirst();
    }

    public Team getOne(final Integer id) {
        return teamRepository.getOne(id);
    }

    public Integer getIdFromTricode(final String tricode) {
        Optional<Team> optionalTeam = teamRepository.findAll().stream().filter(t -> t.getShortName().equals(tricode)).findFirst();
        return optionalTeam.map(Team::getId).orElse(null);
    }

    public boolean isTricodeValid(final String tricode) {
        if(tricode == null) return false;
        return getIdFromTricode(tricode) != null;
    }
}
