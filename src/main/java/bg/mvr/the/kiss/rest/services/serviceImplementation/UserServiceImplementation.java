package bg.mvr.the.kiss.rest.services.serviceImplementation;

import bg.mvr.the.kiss.rest.entities.Role;
import bg.mvr.the.kiss.rest.entities.User;
import bg.mvr.the.kiss.rest.dto.ChangeRole;
import bg.mvr.the.kiss.rest.repositories.UserRepository;
import bg.mvr.the.kiss.rest.services.RoleService;
import bg.mvr.the.kiss.rest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 * User: HDonev.
 * Date: 08.10.2020.
 * Time: 10:07.
 * Organization: DKIS MOIA.
 */
@Service
public class UserServiceImplementation implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleService roleService;


    @Autowired
    public UserServiceImplementation(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User with id: " + id + " doesn't exist"));
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserEntityByEmail(String email) {
        return userRepository.getUserEntityByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User with email: " + email + " doesn't exist"));
    }

    @Override
    public User insertUser(User user) {
        userRepository.getUserEntityByEmail(user.getEmail()).ifPresent(u -> {
            throw new IllegalArgumentException("User with email " + u.getEmail() + " already exist.");
        });
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (userRepository.count() != 0) {
            user.setAuthorities(Collections.singleton(roleService.findRolesByAuthority("USER")));
        } else {
            user.setAuthorities(roleService.findAllRoles().stream().collect(Collectors.toSet()));
        }
        user.setCreatedDate(LocalDateTime.now());
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user, Long modifier) {
        User userFromDB = userRepository.getUserEntityByEmail(user.getEmail()).orElseThrow(() -> new IllegalArgumentException("User doesn't exist."));
        userFromDB.setPassword(passwordEncoder.encode(user.getPassword()));
        userFromDB.setUsername(user.getUsername());
        userFromDB.setLastModifiedBy(modifier);
        userFromDB.setLastModifiedDate(LocalDateTime.now());
        return userRepository.save(userFromDB);
    }

    @Override
    public User deleteById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User with id " + id + " doesn't exist."));
        userRepository.deleteById(id);
        return user;
    }

    @Override
    public void changeRole(ChangeRole changeRole, Long modifier) {
        User userFromDB = userRepository.getUserEntityByEmail(changeRole.getEmail()).orElseThrow(() -> new IllegalArgumentException("User with email: " + changeRole.getEmail() + " doesn't exist"));
        Set<Role> authorities = changeRole.getAuthorities().stream().map(role -> roleService.findRolesByAuthority(role.getAuthority())).collect(Collectors.toSet());
        userFromDB.setAuthorities(authorities);
        userFromDB.setLastModifiedBy(modifier);
        userFromDB.setLastModifiedDate(LocalDateTime.now());
        userRepository.save(userFromDB);
    }
}
