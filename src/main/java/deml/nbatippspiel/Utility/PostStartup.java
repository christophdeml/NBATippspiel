package deml.nbatippspiel.Utility;

import deml.nbatippspiel.Model.Team;
import deml.nbatippspiel.Repository.TeamRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PostStartup implements InitializingBean {

    private final TeamRepository teamRepository;

    public PostStartup(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if(teamRepository.findAll().size() == 30) return;
        List<Team> teams = new ArrayList<>();
        teams.add(new Team(0, "Atlanta", "Hawks", "ATL", false));
        teams.add(new Team(1, "Boston", "Celtics", "BOS", false));
        teams.add(new Team(2, "Brooklyn", "Nets", "BKN", false));
        teams.add(new Team(3, "Charlotte", "Hornets", "CHA", false));
        teams.add(new Team(4, "Chicago", "Bulls", "CHI", false));
        teams.add(new Team(5, "Cleveland", "Cavaliers", "CLE", false));
        teams.add(new Team(6, "Dallas", "Mavericks", "DAL", false));
        teams.add(new Team(7, "Denver", "Nuggets", "DEN", false));
        teams.add(new Team(8, "Detroit", "Pistons", "DET", false));
        teams.add(new Team(9, "Golden State", "Warriors", "GSW", false));
        teams.add(new Team(10, "Houston", "Rockets", "HOU", false));
        teams.add(new Team(11, "Indiana", "Pacers", "IND", false));
        teams.add(new Team(12, "Los Angeles", "Clippers", "LAC", false));
        teams.add(new Team(13, "Los Angeles", "Lakers", "LAL", false));
        teams.add(new Team(14, "Memphis", "Grizzlies", "MEM", false));
        teams.add(new Team(15, "Miami", "Heat", "MIA", false));
        teams.add(new Team(16, "Milwaukee", "Bucks", "MIL", false));
        teams.add(new Team(17, "Minnesota", "Timberwolves", "MIN", false));
        teams.add(new Team(18, "New Orleans", "Pelicans", "NOP", false));
        teams.add(new Team(19, "New York", "Knicks", "NYK", false));
        teams.add(new Team(20, "Oklahoma City", "Thunder", "OKC", false));
        teams.add(new Team(21, "Orlando", "Magic", "ORL", false));
        teams.add(new Team(22, "Philadelphia", "76ers", "PHI", false));
        teams.add(new Team(23, "Phoenix", "Suns", "PHX", false));
        teams.add(new Team(24, "Portland", "Trail Blazers", "POR", false));
        teams.add(new Team(25, "Sacramento", "Kings", "SAC", false));
        teams.add(new Team(26, "San Antonio", "Spurs", "SAS", false));
        teams.add(new Team(27, "Toronto", "Raptors", "TOR", false));
        teams.add(new Team(28, "Utah", "Jazz", "UTA", false));
        teams.add(new Team(29, "Washington", "Wizards", "WAS", false));

        teams.forEach(teamRepository::save);
    }
}
