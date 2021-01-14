package bg.mvr.the.kiss.rest.repositories;

import bg.mvr.the.kiss.rest.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * User: HDonev.
 * Date: 23.09.2020.
 * Time: 09:04.
 * Organization: DKIS MOIA.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getUserEntityByEmail(String email);
}
