package bg.mvr.the.kiss.rest.repositories;

import bg.mvr.the.kiss.rest.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * User: HDonev.
 * Date: 06.10.2020.
 * Time: 08:56.
 * Organization: DKIS MOIA.
 */
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findRolesByAuthority(String authority);
}
