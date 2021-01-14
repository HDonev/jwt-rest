package bg.mvr.the.kiss.rest.services.serviceImplementation;

import bg.mvr.the.kiss.rest.entities.Role;
import bg.mvr.the.kiss.rest.repositories.RoleRepository;
import bg.mvr.the.kiss.rest.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: HDonev.
 * Date: 08.10.2020.
 * Time: 13:34.
 * Organization: DKIS MOIA.
 */
@Service
public class RoleServiceImplementation implements RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public RoleServiceImplementation(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public Role findById(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Role whit id: " + id + " doesn't exist"));
    }

    @Override
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role findRolesByAuthority(String authority) {
        return roleRepository.findRolesByAuthority(authority).orElseThrow(() -> new IllegalArgumentException("Role whit authority " + authority + " doesn't exist"));
    }

    @Override
    public Role insertRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role updateRole(Role Role) {
        return null;
    }

    @Override
    public Role deleteById(Long id) {
        return null;
    }
}
