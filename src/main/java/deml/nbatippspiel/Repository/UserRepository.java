package deml.nbatippspiel.Repository;

import deml.nbatippspiel.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT username from User where id = :id")
    String getUsernameById(@Param("id") final Integer id);

    @Query("SELECT username from User")
    List<String> getAllUsernames();

    @Query("select max(id) + 1 from User")
    Integer qrynxtid();


    default Integer getNextId() {
        final Integer nextId = qrynxtid();
        return nextId == null ? 0 : nextId;
    }
}
