package deml.nbatippspiel.Repository;

import deml.nbatippspiel.Model.Matchup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface MatchupRepository extends JpaRepository<Matchup, Integer> {

}
