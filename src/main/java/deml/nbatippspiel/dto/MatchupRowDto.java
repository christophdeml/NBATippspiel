package deml.nbatippspiel.dto;

import deml.nbatippspiel.Model.MatchupRow;

import java.util.ArrayList;
import java.util.List;

public class MatchupRowDto {
    public List<MatchupRow> matchupRows;

    public MatchupRowDto() {
        this.matchupRows = new ArrayList<>();
    }

    public List<MatchupRow> getMatchupRows() {
        return matchupRows;
    }

    public void setMatchupRows(final List<MatchupRow> matchupRows) {
        this.matchupRows = matchupRows;
    }
}
