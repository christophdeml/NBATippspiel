package deml.nbatippspiel.Repository;

import deml.nbatippspiel.Model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Integer> {
}
