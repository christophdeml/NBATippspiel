package deml.nbatippspiel.Repository;

import deml.nbatippspiel.Model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public interface GameRepository extends JpaRepository<Game, Long> {

    default List<Game> findGamesForDate(final LocalDate date) {
        return this.findAll().stream().filter(g -> g.getDate().isEqual(date)).collect(Collectors.toList());
    }
}
