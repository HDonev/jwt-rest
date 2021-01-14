package bg.mvr.the.kiss.rest.services;

import bg.mvr.the.kiss.rest.entities.User;
import bg.mvr.the.kiss.rest.dto.ChangeRole;
import org.springframework.security.core.Authentication;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: HDonev.
 * Date: 08.01.2021.
 * Time: 10:02.
 * Organization: DKIS MOIA.
 */
public interface UserService {

    User findById(Long id);

    List<User> findAllUsers();

    User getUserEntityByEmail(String email);

    User insertUser(User user);

    User updateUser(User user, Authentication authentication) throws IllegalAccessException;

    User deleteById(Long id);

    void changeRole(ChangeRole changeRole, Authentication authentication);

    String signIn(User user);
}
