package bg.mvr.the.kiss.rest.services;

import bg.mvr.the.kiss.rest.entities.Role;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: HDonev.
 * Date: 08.01.2021.
 * Time: 13:34.
 * Organization: DKIS MOIA.
 */
public interface RoleService {
    Role findById(Long id);

    List<Role> findAllRoles();

    Role findRolesByAuthority(String authority);

    Role insertRole(Role role);

    Role updateRole(Role Role);

    Role deleteById(Long id);
}
