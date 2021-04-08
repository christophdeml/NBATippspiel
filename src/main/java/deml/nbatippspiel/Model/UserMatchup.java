package deml.nbatippspiel.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserMatchup {

    @Id
    private Integer id;
    private Integer userId;
    private Integer matchupId;
    private Integer guessedDifference;

    public UserMatchup(
            final Integer id,
            final Integer userId,
            final Integer matchupId,
            final Integer guessedDifference) {
        this.id = id;
        this.userId = userId;
        this.matchupId = matchupId;
        this.guessedDifference = guessedDifference;
    }

    public UserMatchup() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(final Integer userId) {
        this.userId = userId;
    }

    public Integer getMatchupId() {
        return matchupId;
    }

    public void setMatchupId(final Integer matchupId) {
        this.matchupId = matchupId;
    }

    public Integer getGuessedDifference() {
        return guessedDifference;
    }

    public void setGuessedDifference(final Integer guessedDifference) {
        this.guessedDifference = guessedDifference;
    }
}
