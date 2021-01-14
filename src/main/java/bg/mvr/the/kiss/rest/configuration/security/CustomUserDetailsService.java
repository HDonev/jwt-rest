package bg.mvr.the.kiss.rest.configuration.security;

import bg.mvr.the.kiss.rest.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


/**
 * Created by IntelliJ IDEA.
 * User: HDonev.
 * Date: 23.09.2020.
 * Time: 09:03.
 * Organization: DKIS MOIA.
 */
@Component
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.getUserEntityByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found", email)));
    }
}
