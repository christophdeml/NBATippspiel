package deml.nbatippspiel.Model;

import java.util.ArrayList;
import java.util.List;

public class MatchupRow {
    private Team team1;
    private Team team2;
    private String matchupKey;
    public Integer guessedTeam1wins;
    public Integer guessedTeam2wins;
    public Integer currentTeam1wins;
    public Integer currentTeam2wins;
    public String matchupDescription;
    public boolean started;
    public List<String[]> guessesOfOtherUsers = new ArrayList<>();

    public MatchupRow() {
    }

    public MatchupRow(
            final Team team1,
            final Team team2,
            final Integer guessedTeam1wins,
            final Integer guessedTeam2wins,
            final Integer currentTeam1wins,
            final Integer currentTeam2wins,
            final String matchupDescription,
            final boolean hasStarted) {
        this.team1 = team1;
        this.team2 = team2;
        this.matchupKey = team1.getShortName() + team2.getShortName();
        this.guessedTeam1wins = guessedTeam1wins;
        this.guessedTeam2wins = guessedTeam2wins;
        this.currentTeam1wins = currentTeam1wins;
        this.currentTeam2wins = currentTeam2wins;
        this.matchupDescription = matchupDescription;
        this.started = hasStarted;
    }

    public Team getTeam1() {
        return team1;
    }

    public void setTeam1(final Team team1) {
        this.team1 = team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public void setTeam2(final Team team2) {
        this.team2 = team2;
    }

    public String getMatchupKey() {
        return matchupKey;
    }

    public void setMatchupKey(final String matchupKey) {
        this.matchupKey = matchupKey;
    }

    public Integer getGuessedTeam1wins() {
        return guessedTeam1wins;
    }

    public void setGuessedTeam1wins(final Integer guessedTeam1wins) {
        this.guessedTeam1wins = guessedTeam1wins;
    }

    public Integer getGuessedTeam2wins() {
        return guessedTeam2wins;
    }

    public void setGuessedTeam2wins(final Integer guessedTeam2wins) {
        this.guessedTeam2wins = guessedTeam2wins;
    }

    public Integer getCurrentTeam1wins() {
        return currentTeam1wins;
    }

    public void setCurrentTeam1wins(final Integer currentTeam1wins) {
        this.currentTeam1wins = currentTeam1wins;
    }

    public Integer getCurrentTeam2wins() {
        return currentTeam2wins;
    }

    public void setCurrentTeam2wins(final Integer currentTeam2wins) {
        this.currentTeam2wins = currentTeam2wins;
    }

    public String getMatchupDescription() {
        return matchupDescription;
    }

    public void setMatchupDescription(final String matchupDescription) {
        this.matchupDescription = matchupDescription;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(final boolean started) {
        this.started = started;
    }

    public List<String[]> getGuessesOfOtherUsers() {
        return guessesOfOtherUsers;
    }

    public void setGuessesOfOtherUsers(final List<String[]> guessesOfOtherUsers) {
        this.guessesOfOtherUsers = guessesOfOtherUsers;
    }
}
