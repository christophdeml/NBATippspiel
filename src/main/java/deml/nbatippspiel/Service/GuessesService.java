package deml.nbatippspiel.Service;

import deml.nbatippspiel.Model.MatchupRow;
import deml.nbatippspiel.Model.User;
import deml.nbatippspiel.Model.UserMatchup;
import deml.nbatippspiel.Repository.MatchupRepository;
import deml.nbatippspiel.Repository.TeamRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GuessesService {
    private final MatchupRepository matchupRepository;
    private final TeamRepository teamRepository;
    private final UserMatchupService userMatchupService;
    private final UserService userService;

    public GuessesService(
            final MatchupRepository matchupRepository,
            final TeamRepository teamRepository,
            final UserMatchupService userMatchupService,
            final UserService userService) {
        this.matchupRepository = matchupRepository;
        this.teamRepository = teamRepository;
        this.userMatchupService = userMatchupService;
        this.userService = userService;
    }

    public List<MatchupRow> getAllMatchupRows() {
        List<MatchupRow> matchupRows = new ArrayList<>();
        matchupRepository.findAll().forEach(m -> {
            matchupRows.add(
                    new MatchupRow(
                            teamRepository.getOne(m.getTeam1Id()),
                            teamRepository.getOne(m.getTeam2Id()),
                            m.getTeam1Wins(),
                            m.getTeam2Wins(),
                            0,
                            0,
                            m.getMatchupDescription(),
                            m.isStarted()
                    )
            );
        });
        return matchupRows;
    }

    public List<MatchupRow> getAllUnguessedMatchupRows() {
        final User user = userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        List<MatchupRow> matchupRows = new ArrayList<>();
        List<Integer> guessedMatchupIds = userMatchupService.getAllUserMatchupsForUser(user.getId()).stream().map(UserMatchup::getMatchupId).collect(Collectors.toList());
        matchupRepository.findAll().forEach(m -> {
            if (!guessedMatchupIds.contains(m.getId()) && !m.isStarted() && !m.isClosed()) {
                matchupRows.add(
                        new MatchupRow(
                                teamRepository.getOne(m.getTeam1Id()),
                                teamRepository.getOne(m.getTeam2Id()),
                                m.getTeam1Wins(),
                                m.getTeam2Wins(),
                                0,
                                0,
                                m.getMatchupDescription(),
                                m.isStarted()
                        )
                );
            }
        });
        return matchupRows;
    }

    public List<MatchupRow> getAllGuessedMatchupRows() {
        final User user = userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        List<MatchupRow> matchupRows = new ArrayList<>();
        List<UserMatchup> guessedUserMatchups = userMatchupService.getAllUserMatchupsForUser(user.getId());
        matchupRepository.findAll().forEach(m -> {
            guessedUserMatchups.forEach(um -> {
                if (m.getId().equals(um.getMatchupId()) && !m.isClosed()) {
                    List<Integer> guessedTeamWins = getTeamsWinsFromDifference(um.getGuessedDifference());
                    MatchupRow matchupRow = new MatchupRow(
                            teamRepository.getOne(m.getTeam1Id()),
                            teamRepository.getOne(m.getTeam2Id()),
                            guessedTeamWins.get(0),
                            guessedTeamWins.get(1),
                            m.getTeam1Wins(),
                            m.getTeam2Wins(),
                            m.getMatchupDescription(),
                            m.isStarted()
                    );
                    if (matchupRow.isStarted()) {
                        matchupRow.setGuessesOfOtherUsers(getGuessesOfMatchupForOtherUsers(user.getId(), matchupRow.getMatchupKey()));
                    }
                    matchupRows.add(matchupRow);
                }
            });
        });
        return matchupRows;
    }

    public List<MatchupRow> getAllStartedMatchupRowsForUsername(final String username) {
        final User user = userService.getUserByUsername(username);
        List<MatchupRow> matchupRows = new ArrayList<>();
        List<UserMatchup> guessedUserMatchups = userMatchupService.getAllUserMatchupsForUser(user.getId());
        matchupRepository.findAll().forEach(m -> {
            if (m.isStarted() && !m.isClosed()) {
                MatchupRow matchupRow = new MatchupRow(
                        teamRepository.getOne(m.getTeam1Id()),
                        teamRepository.getOne(m.getTeam2Id()),
                        null,
                        null,
                        m.getTeam1Wins(),
                        m.getTeam2Wins(),
                        m.getMatchupDescription(),
                        m.isStarted()
                );
                guessedUserMatchups.forEach(um -> {
                    if (m.getId().equals(um.getMatchupId())) {
                        List<Integer> guessedTeamWins = getTeamsWinsFromDifference(um.getGuessedDifference());
                        matchupRow.setGuessedTeam1wins(guessedTeamWins.get(0));
                        matchupRow.setGuessedTeam2wins(guessedTeamWins.get(1));
                    }
                });
                matchupRows.add(matchupRow);
            }
        });
        return matchupRows;
    }

    private List<String[]> getGuessesOfMatchupForOtherUsers(final Integer userId, final String matchupKey) {
        List<String[]> otherGuesses = new ArrayList<>();
        userMatchupService.getAllForMatchupForOtherUsers(userId, matchupKey).forEach(um -> {
            List<Integer> wins = getTeamsWinsFromDifference(um.getGuessedDifference());
            String name = userService.getOne(um.getUserId()).getFirstname();
            otherGuesses.add(new String[]{name, wins.get(0) + " : " + wins.get(1)});
        });
        return otherGuesses;
    }

    public List<MatchupRow> getAllUnguessedStartedMatchups() {
        final User user = userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        List<MatchupRow> matchupRows = new ArrayList<>();
        List<Integer> guessedMatchupIds = userMatchupService.getAllUserMatchupsForUser(user.getId()).stream().map(UserMatchup::getMatchupId).collect(Collectors.toList());
        matchupRepository.findAll().forEach(m -> {
            if (!guessedMatchupIds.contains(m.getId()) && m.isStarted() && !m.isClosed()) {
                MatchupRow matchupRow = new MatchupRow(
                        teamRepository.getOne(m.getTeam1Id()),
                        teamRepository.getOne(m.getTeam2Id()),
                        null,
                        null,
                        m.getTeam1Wins(),
                        m.getTeam2Wins(),
                        m.getMatchupDescription(),
                        m.isStarted()
                );
                matchupRow.setGuessesOfOtherUsers(getGuessesOfMatchupForOtherUsers(user.getId(), matchupRow.getMatchupKey()));
                matchupRows.add(matchupRow);
            }
        });
        return matchupRows;
    }

    public List<MatchupRow> getAllClosedMatchupsForUser(final String username) {
        final User user = userService.getUserByUsername(username);
        List<MatchupRow> matchupRows = new ArrayList<>();
        List<UserMatchup> guessedUserMatchups = userMatchupService.getAllUserMatchupsForUser(user.getId());
        matchupRepository.findAll().forEach(m -> {
            if (m.isClosed()) {
                MatchupRow matchupRow = new MatchupRow(
                        teamRepository.getOne(m.getTeam1Id()),
                        teamRepository.getOne(m.getTeam2Id()),
                        null,
                        null,
                        m.getTeam1Wins(),
                        m.getTeam2Wins(),
                        m.getMatchupDescription(),
                        m.isStarted()
                );
                guessedUserMatchups.forEach(um -> {
                    if (m.getId().equals(um.getMatchupId())) {
                        List<Integer> guessedTeamWins = getTeamsWinsFromDifference(um.getGuessedDifference());
                        matchupRow.setGuessedTeam1wins(guessedTeamWins.get(0));
                        matchupRow.setGuessedTeam2wins(guessedTeamWins.get(1));
                    }
                });

                matchupRows.add(matchupRow);
            }
        });
        return matchupRows;
    }

    public List<MatchupRow> getAllClosedMatchups() {
        final User user = userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        List<MatchupRow> matchupRows = new ArrayList<>();
        List<UserMatchup> guessedUserMatchups = userMatchupService.getAllUserMatchupsForUser(user.getId());
        matchupRepository.findAll().forEach(m -> {
            if (m.isClosed()) {
                MatchupRow matchupRow = new MatchupRow(
                        teamRepository.getOne(m.getTeam1Id()),
                        teamRepository.getOne(m.getTeam2Id()),
                        null,
                        null,
                        m.getTeam1Wins(),
                        m.getTeam2Wins(),
                        m.getMatchupDescription(),
                        m.isStarted()
                );

                matchupRow.setGuessesOfOtherUsers(getGuessesOfMatchupForOtherUsers(user.getId(), matchupRow.getMatchupKey()));

                guessedUserMatchups.forEach(um -> {
                    if (m.getId().equals(um.getMatchupId())) {
                        List<Integer> guessedTeamWins = getTeamsWinsFromDifference(um.getGuessedDifference());
                        matchupRow.setGuessedTeam1wins(guessedTeamWins.get(0));
                        matchupRow.setGuessedTeam2wins(guessedTeamWins.get(1));
                    }
                });

                matchupRows.add(matchupRow);
            }
        });
        return matchupRows;
    }

    private List<Integer> getTeamsWinsFromDifference(final Integer difference) {
        int team1wins, team2wins;
        if (difference < 0) {
            team2wins = 4;
            team1wins = 4 + difference;
        } else {
            team1wins = 4;
            team2wins = 4 - difference;
        }
        return List.of(team1wins, team2wins);
    }
}
