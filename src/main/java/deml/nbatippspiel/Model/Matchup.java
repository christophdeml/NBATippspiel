package deml.nbatippspiel.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Matchup {
    @Id
    private Integer id;
    private String matchupKey;
    private String matchupDescription;
    private Integer team1Id;
    private Integer team2Id;
    private Integer team1Wins;
    private Integer team2Wins;
    private boolean closed;
    private boolean started;

    public static Matchup fromMatchup(final Matchup matchup) {
        if(matchup == null) return null;
        return new Matchup(matchup.getId(),
                matchup.getMatchupKey(),
                matchup.getMatchupDescription(),
                matchup.getTeam1Id(),
                matchup.getTeam2Id(),
                matchup.getTeam1Wins(),
                matchup.getTeam2Wins(),
                matchup.isClosed(),
                matchup.isClosed());
    }

    public Matchup() {
    }

    public Matchup(
            final Integer id,
            final String matchupKey,
            final String matchupDescription,
            final Integer team1Id,
            final Integer team2Id,
            final Integer team1Wins,
            final Integer team2Wins,
            final boolean closed,
            final boolean started
    ) {
        this.id = id;
        this.matchupKey = matchupKey;
        this.matchupDescription = matchupDescription;
        this.team1Id = team1Id;
        this.team2Id = team2Id;
        this.team1Wins = team1Wins;
        this.team2Wins = team2Wins;
        this.closed = closed;
        this.started = started;
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getMatchupKey() {
        return matchupKey;
    }

    public void setMatchupKey(final String matchupKey) {
        this.matchupKey = matchupKey;
    }

    public String getMatchupDescription() {
        return matchupDescription;
    }

    public void setMatchupDescription(final String matchupDescription) {
        this.matchupDescription = matchupDescription;
    }

    public Integer getTeam1Id() {
        return team1Id;
    }

    public void setTeam1Id(final Integer team1Id) {
        this.team1Id = team1Id;
    }

    public Integer getTeam2Id() {
        return team2Id;
    }

    public void setTeam2Id(final Integer team2Id) {
        this.team2Id = team2Id;
    }

    public Integer getTeam1Wins() {
        return team1Wins;
    }

    public void setTeam1Wins(final Integer team1Wins) {
        this.team1Wins = team1Wins;
    }

    public Integer getTeam2Wins() {
        return team2Wins;
    }

    public void setTeam2Wins(final Integer team2Wins) {
        this.team2Wins = team2Wins;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(final boolean closed) {
        this.closed = closed;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(final boolean started) {
        this.started = started;
    }

    public Integer getDifference() {
        return team1Wins - team2Wins;
    }

    public boolean isValid() {
        return id != null && matchupKey.length() == 6 &&
                team1Id != null && team1Id >= 0 &&
                team2Id != null && team2Id >= 0;
    }

    public boolean isEqual(final Matchup matchup) {
        if(matchup == null) return false;
        return Objects.equals(this.id, matchup.id) &&
                Objects.equals(this.matchupKey, matchup.matchupKey) &&
                Objects.equals(this.matchupDescription, matchup.matchupDescription) &&
                Objects.equals(this.team1Id, matchup.team1Id) &&
                Objects.equals(this.team2Id, matchup.team2Id) &&
                Objects.equals(this.team1Wins, matchup.team1Wins) &&
                Objects.equals(this.team2Wins, matchup.team2Wins) &&
                this.closed == matchup.closed &&
                this.started == matchup.started;
    }

    @Override
    public String toString() {
        return "ID: " + this.id +
                "\n\tMatchup-Key: " + this.matchupKey +
                "\n\tTeam1: " + this.team1Id +
                "\n\tTeam2: " + this.team2Id +
                "\n\tTeam1 wins: " + this.team1Wins +
                "\n\tTeam2 wins" + this.team2Wins +
                "\n\tStarted: " + this.started +
                "\n\tClosed: " + this.closed;
    }
}
