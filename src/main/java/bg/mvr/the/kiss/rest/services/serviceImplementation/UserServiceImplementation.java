package bg.mvr.the.kiss.rest.services.serviceImplementation;

import bg.mvr.the.kiss.rest.configuration.security.jwt.JwtUtils;
import bg.mvr.the.kiss.rest.entities.Role;
import bg.mvr.the.kiss.rest.entities.User;
import bg.mvr.the.kiss.rest.dto.ChangeRole;
import bg.mvr.the.kiss.rest.repositories.UserRepository;
import bg.mvr.the.kiss.rest.services.RoleService;
import bg.mvr.the.kiss.rest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
 * Date: 08.01.2021.
 * Time: 10:07.
 * Organization: DKIS MOIA.
 */
@Service
public class UserServiceImplementation implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtUtils jwtUtils;
    private RoleService roleService;

    @Autowired
    public UserServiceImplementation(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtils jwtUtils, RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
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
    public User updateUser(User user, Authentication authentication) throws IllegalAccessException {
        User authenticatedUser = (User) authentication.getPrincipal();
        if (!authenticatedUser.getEmail().equals(user.getEmail())) {
            throw new IllegalAccessException("You may not modify other users !");
        }
        User userFromDB = userRepository.getUserEntityByEmail(user.getEmail()).orElseThrow(() -> new IllegalArgumentException("User doesn't exist."));
        userFromDB.setPassword(passwordEncoder.encode(user.getPassword()));
        userFromDB.setUsername(user.getUsername());
        userFromDB.setLastModifiedBy(authenticatedUser.getId());
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
    public void changeRole(ChangeRole changeRole, Authentication authentication) {
        User admin = (User) authentication.getPrincipal();
        User userFromDB = userRepository.getUserEntityByEmail(changeRole.getEmail()).orElseThrow(() -> new IllegalArgumentException("User with email: " + changeRole.getEmail() + " doesn't exist"));
        Set<Role> authorities = changeRole.getAuthorities().stream().map(role -> roleService.findRolesByAuthority(role.getRole())).collect(Collectors.toSet());
        userFromDB.setAuthorities(authorities);
        userFromDB.setLastModifiedBy(admin.getId());
        userFromDB.setLastModifiedDate(LocalDateTime.now());
        userRepository.save(userFromDB);
    }

    @Override
    public String signIn(User user) {
        Authentication authenticate = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        return this.jwtUtils.generateToken((User) authenticate.getPrincipal());
    }
}
