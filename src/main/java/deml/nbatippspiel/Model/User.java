package deml.nbatippspiel.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    private Integer id;
    private String username;
    private String firstname;
    private String lastname;
    private Integer points;
    private Integer splashes; //matchup guess is correct
    private Integer freethrows; //matchup winner guess is correct
    private Integer airballs; //matchup winner is wrong
    private Integer championGuessId;

    public User(final Integer userid,
                final String username,
                final String firstname,
                final String lastname) {
        this.id = userid;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.points = 0;
        this.splashes = 0;
        this.freethrows = 0;
        this.airballs = 0;
    }

    public User() {

    }

    @Override
    public boolean equals(final Object obj) {
        return obj.getClass().equals(User.class) &&
                this.id.equals(((User) obj).getId()) &&
                this.username.equals(((User) obj).getUsername()) &&
                this.firstname.equals(((User) obj).getFirstname()) &&
                this.lastname.equals(((User) obj).getLastname());
    }

    @Id
    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(final String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(final String lastname) {
        this.lastname = lastname;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(final Integer points) {
        this.points = points;
    }

    public Integer getChampionGuessId() {
        return championGuessId;
    }

    public void setChampionGuessId(final Integer championGuessId) {
        this.championGuessId = championGuessId;
    }

    public Integer getSplashes() {
        return splashes;
    }

    public void setSplashes(final Integer splashes) {
        this.splashes = splashes;
    }

    public Integer getFreethrows() {
        return freethrows;
    }

    public void setFreethrows(final Integer freethrows) {
        this.freethrows = freethrows;
    }

    public Integer getAirballs() {
        return airballs;
    }

    public void setAirballs(final Integer airballs) {
        this.airballs = airballs;
    }
}
