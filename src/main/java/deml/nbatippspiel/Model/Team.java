package deml.nbatippspiel.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Team {

    @Id
    private Integer id;
    private String cityName;
    private String teamName;
    private String shortName;
    private boolean isChampion;

    public Team(
            final Integer id,
            final String cityName,
            final String teamName,
            final String shortName,
            final boolean isChampion
    ) {
        this.id = id;
        this.cityName = cityName;
        this.teamName = teamName;
        this.shortName = shortName;
        this.isChampion = isChampion;
    }

    public Team() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(final String shortName) {
        this.shortName = shortName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(final String cityName) {
        this.cityName = cityName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(final String teamName) {
        this.teamName = teamName;
    }

    public boolean isChampion() {
        return isChampion;
    }

    public void setChampion(final boolean champion) {
        isChampion = champion;
    }
}
