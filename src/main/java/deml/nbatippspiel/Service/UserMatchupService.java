package deml.nbatippspiel.Service;

import deml.nbatippspiel.Model.UserMatchup;
import deml.nbatippspiel.Repository.MatchupRepository;
import deml.nbatippspiel.Repository.UserMatchupRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserMatchupService {
    private final UserMatchupRepository userMatchupRepository;
    private final MatchupService matchupService;

    public UserMatchupService(final UserMatchupRepository userMatchupRepository,
                              final MatchupService matchupService) {
        this.userMatchupRepository = userMatchupRepository;
        this.matchupService = matchupService;
    }

    public List<UserMatchup> findAll() {
        return userMatchupRepository.findAll();
    }

    public List<UserMatchup> getAllForMatchupForOtherUsers(final Integer userId, final String matchupKey) {
        return findAll().stream().filter(um -> !um.getUserId().equals(userId) && matchupService.getByMatchupKey(matchupKey).getId().equals(um.getMatchupId())).collect(Collectors.toList());
    }

    public void save(final Integer userId, final Integer matchupId, final Integer guessedDifference) {
        userMatchupRepository.save(new UserMatchup(getNextId(), userId, matchupId, guessedDifference));
    }

    private Integer getNextId() {
        List<UserMatchup> userMatchups = userMatchupRepository.findAll();
        Integer next = 0;
        for(UserMatchup um : userMatchups) {
            next = um.getId() >= next ? um.getId() + 1 : next;
        }
        return next;
    }

    public List<UserMatchup> getAllUserMatchupsForUser(final Integer userId) {
        return userMatchupRepository.findAll().stream().filter(um -> um.getUserId().equals(userId)).collect(Collectors.toList());
    }

    public Optional<UserMatchup> getUserMatchupByUserIdAndMatchupId(final Integer userId, final Integer matchupId) {
        return userMatchupRepository.findAll().stream().filter(um -> um.getMatchupId().equals(matchupId) && um.getUserId().equals(userId)).findFirst();
    }

    public void deleteUserMatchupForUser(final Integer userId, final String matchupKey) {
        matchupService.getFromMatchupKey(matchupKey).flatMap(m -> getUserMatchupByUserIdAndMatchupId(userId, m.getId())).ifPresent(userMatchupRepository::delete);
    }
}
