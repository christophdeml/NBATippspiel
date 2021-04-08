package deml.nbatippspiel.Repository;

import deml.nbatippspiel.Model.UserPassword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPasswordRepository extends JpaRepository<UserPassword, Integer> {
}