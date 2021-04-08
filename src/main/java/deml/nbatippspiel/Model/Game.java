package deml.nbatippspiel.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Game {

    @Id
    private Long id;
    private String matchupKey;
    private Integer awayTeamId;
    private Integer awayTeamPoints;
    private String awayTeamRecord;
    private Integer homeTeamId;
    private Integer homeTeamPoints;
    private String homeTeamRecord;
    private LocalDate date;
    private LocalDateTime time;

    public static Game fromGame(final Game game) {
        if(game == null) return null;
        return new Game(game.getId(),
                game.getMatchupKey(),
                game.getAwayTeamId(),
                game.getAwayTeamPoints(),
                game.getAwayTeamRecord(),
                game.getHomeTeamId(),
                game.getHomeTeamPoints(),
                game.getHomeTeamRecord(),
                game.getDate(),
                game.getTime());
    }

    public Game() {}

    public Game(
            final Long id,
            final String matchupKey,
            final Integer awayTeamId,
            final Integer awayTeamPoints,
            final String awayTeamRecord,
            final Integer homeTeamId,
            final Integer homeTeamPoints,
            final String homeTeamRecord,
            final LocalDate date,
            final LocalDateTime time
    ) {
        this.id = id;
        this.matchupKey = matchupKey;
        this.awayTeamId = awayTeamId;
        this.awayTeamPoints = awayTeamPoints;
        this.awayTeamRecord = awayTeamRecord;
        this.homeTeamId = homeTeamId;
        this.homeTeamPoints = homeTeamPoints;
        this.homeTeamRecord = homeTeamRecord;
        this.date = date;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getMatchupKey() {
        return matchupKey;
    }

    public void setMatchupKey(final String matchupKey) {
        this.matchupKey = matchupKey;
    }

    public Integer getAwayTeamId() {
        return awayTeamId;
    }

    public void setAwayTeamId(final Integer awayTeamId) {
        this.awayTeamId = awayTeamId;
    }

    public Integer getAwayTeamPoints() {
        return awayTeamPoints;
    }

    public void setAwayTeamPoints(final Integer awayTeamPoints) {
        this.awayTeamPoints = awayTeamPoints;
    }

    public String getAwayTeamRecord() {
        return awayTeamRecord;
    }

    public void setAwayTeamRecord(final String awayTeamRecord) {
        this.awayTeamRecord = awayTeamRecord;
    }

    public Integer getHomeTeamId() {
        return homeTeamId;
    }

    public void setHomeTeamId(final Integer homeTeamId) {
        this.homeTeamId = homeTeamId;
    }

    public Integer getHomeTeamPoints() {
        return homeTeamPoints;
    }

    public void setHomeTeamPoints(final Integer homeTeamPoints) {
        this.homeTeamPoints = homeTeamPoints;
    }

    public String getHomeTeamRecord() {
        return homeTeamRecord;
    }

    public void setHomeTeamRecord(final String homeTeamRecord) {
        this.homeTeamRecord = homeTeamRecord;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(final LocalDate date) {
        this.date = date;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(final LocalDateTime time) {
        this.time = time;
    }

    public boolean isValid() {
        return id != 0
                && homeTeamId != null && homeTeamId > -1 && homeTeamId < 30
                && awayTeamId != null && awayTeamId > -1 && awayTeamId < 30
                && date != null
                && time != null;
    }

    public boolean isEqual(final Game game) {
        if(game == null) return false;
        return Objects.equals(this.id, game.id) &&
                Objects.equals(this.homeTeamId, game.homeTeamId) &&
                Objects.equals(this.homeTeamPoints, game.homeTeamPoints) &&
                Objects.equals(this.homeTeamRecord, game.homeTeamRecord) &&
                Objects.equals(this.awayTeamId, game.awayTeamId) &&
                Objects.equals(this.awayTeamPoints, game.awayTeamPoints) &&
                Objects.equals(this.awayTeamRecord, game.awayTeamRecord) &&
                Objects.equals(this.matchupKey, game.matchupKey) &&
                Objects.equals(this.time, game.time) &&
                Objects.equals(this.date, game.date);
    }

    @Override
    public String toString() {
        return "ID: " + this.id +
                "\n\tMatchup-Key: " + this.matchupKey +
                "\n\tDate: " + this.date +
                "\n\tHome team points: " + this.homeTeamPoints +
                "\n\tAway team points: " + this.awayTeamPoints;
    }
}
