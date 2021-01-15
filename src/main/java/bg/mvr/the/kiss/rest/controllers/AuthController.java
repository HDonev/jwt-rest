package bg.mvr.the.kiss.rest.controllers;

import bg.mvr.the.kiss.rest.entities.User;
import bg.mvr.the.kiss.rest.dto.UserSignIn;
import bg.mvr.the.kiss.rest.dto.UserSignUp;
import bg.mvr.the.kiss.rest.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

/**
 * Created by IntelliJ IDEA.
 * User: HDonev.
 * Date: 07.01.2021.
 * Time: 09:07.
 * Organization: DKIS MOIA.
 */
@RestController
@CrossOrigin(maxAge = 3600)
public class AuthController {

    private UserService userService;
    private ModelMapper modelMapper;
    public PasswordEncoder passwordEncoder;


    @Autowired
    public AuthController(UserService userService, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }


    @PostMapping(path = "/sign-up", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserSignUp> signUpUser(@RequestBody @Valid UserSignUp userSignUp) {
        User savedUser = userService.insertUser(modelMapper.map(userSignUp, User.class));
        return ResponseEntity.ok(modelMapper.map(savedUser, UserSignUp.class));
    }

    @PostMapping(value = "/sign-in", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> signIn(@Valid @RequestBody UserSignIn userSignIn) {
        return ResponseEntity.ok(userService.signIn(modelMapper.map(userSignIn, User.class)));
    }
}
