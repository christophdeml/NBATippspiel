package deml.nbatippspiel.Service;

import deml.nbatippspiel.Model.Matchup;
import deml.nbatippspiel.Model.Team;
import deml.nbatippspiel.Model.User;
import deml.nbatippspiel.Model.UserMatchup;
import deml.nbatippspiel.Repository.MatchupRepository;
import deml.nbatippspiel.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final MatchupRepository matchupRepository;
    private final UserMatchupService userMatchupService;
    private final TeamService teamService;

    public UserService(
            final UserRepository userRepository,
            final MatchupRepository matchupRepository,
            final UserMatchupService userMatchupService,
            final TeamService teamService) {
        this.userRepository = userRepository;
        this.matchupRepository = matchupRepository;
        this.userMatchupService = userMatchupService;
        this.teamService = teamService;
    }

    public String getUsernameById(final Integer id) {
        return userRepository.getUsernameById(id);
    }

    public List<String> getAllUsernames() {
        return userRepository.getAllUsernames();
    }

    public User getOne(final Integer id) {
        User user = userRepository.getOne(id);
        calculatePointsForUser(user);
        return user;
    }

    public User save(final User user) {
        return userRepository.save(user);
    }

    public User getUserByUsername(final String username) {
        User user = this.findAll().stream().filter(u -> u.getUsername().equals(username)).findFirst().orElse(null);
        if(user != null) {
            calculatePointsForUser(user);
        }
        return user;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<User> getAllUsers() {
        List<User> users = findAll().stream().map(this::calculatePointsForUser).sorted(Comparator.comparing(User::getPoints)).collect(Collectors.toList());
        Collections.reverse(users);
        return users;
    }

    private User calculatePointsForUser(final User user) {
        int points = 0;
        user.setSplashes(0);
        user.setFreethrows(0);
        user.setAirballs(0);

        for(Matchup m : matchupRepository.findAll()) {
            for(UserMatchup um : userMatchupService.getAllUserMatchupsForUser(user.getId())) {
                if(m.isClosed() && m.getId().equals(um.getMatchupId())) {
                    if(m.getDifference().equals(um.getGuessedDifference())) {
                        points += 3;
                        user.setSplashes(user.getSplashes() + 1);
                    } else if (areSameSignum(m.getDifference(), um.getGuessedDifference())) {
                        points += 1;
                        user.setFreethrows(user.getFreethrows() + 1);
                    } else {
                        user.setAirballs(user.getAirballs() + 1);
                    }
                }
            }
        }

        Optional<Team> champion = teamService.getChampion();
        if(champion.isPresent() && champion.get().getId().equals(user.getChampionGuessId())) points += 10;

        user.setPoints(points);
        userRepository.save(user);
        return user;
    }

    private boolean areSameSignum(final Integer i1, final Integer i2) {
        return (i1 > 0) == (i2 > 0);
    }
}
