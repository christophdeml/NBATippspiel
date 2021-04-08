package deml.nbatippspiel.Model;

import java.time.LocalDate;

public class GameRow {

    private Team awayTeam;
    private Integer awayTeamPoints;
    private Team homeTeam;
    private Integer homeTeamPoints;
    private Game game;

    public GameRow(
            final Team awayTeam,
            final Integer awayTeamPoints,
            final Team homeTeam,
            final Integer homeTeamPoints,
            final Game game
    ) {
        this.awayTeam = awayTeam;
        this.awayTeamPoints = awayTeamPoints;
        this.homeTeam = homeTeam;
        this.homeTeamPoints = homeTeamPoints;
        this.game = game;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(final Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    public Integer getAwayTeamPoints() {
        return awayTeamPoints;
    }

    public void setAwayTeamPoints(final Integer awayTeamPoints) {
        this.awayTeamPoints = awayTeamPoints;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(final Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Integer getHomeTeamPoints() {
        return homeTeamPoints;
    }

    public void setHomeTeamPoints(final Integer homeTeamPoints) {
        this.homeTeamPoints = homeTeamPoints;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(final Game game) {
        this.game = game;
    }
}
