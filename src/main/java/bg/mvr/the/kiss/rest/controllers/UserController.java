package bg.mvr.the.kiss.rest.controllers;

import bg.mvr.the.kiss.rest.entities.User;
import bg.mvr.the.kiss.rest.dto.UserSignUp;
import bg.mvr.the.kiss.rest.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * Created by IntelliJ IDEA.
 * User: HDonev.
 * Date: 21.09.2020.
 * Time: 15:05.
 * Organization: DKIS MOIA.
 */
@RestController
@CrossOrigin(maxAge = 3600)
public class UserController {

    private UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(path = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserSignUp> getUserById(@PathVariable Long id) {
        User userById = userService.findById(id);
        return ResponseEntity.ok(modelMapper.map(userById, UserSignUp.class));
    }

    @PutMapping(path = "/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserSignUp> updateUser(@RequestBody UserSignUp userSignUp, Authentication authentication) throws IllegalAccessException {
        User authenticatedUser = (User) authentication.getPrincipal();
        if (!authenticatedUser.getEmail().equals(userSignUp.getEmail())) {
            throw new IllegalAccessException("You must not change other user !");
        }
        User updatedUser = userService.updateUser(modelMapper.map(userSignUp,User.class), authenticatedUser.getId());
        return ResponseEntity.ok(modelMapper.map(updatedUser, UserSignUp.class));
    }

    @DeleteMapping(path = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserSignUp> deleteUser(@PathVariable Long id) {
        User deletedUser = userService.deleteById(id);
        return ResponseEntity.ok(modelMapper.map(deletedUser, UserSignUp.class));
    }
}
