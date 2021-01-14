package bg.mvr.the.kiss.rest.controllers;

import bg.mvr.the.kiss.rest.configuration.security.jwt.JwtUtils;
import bg.mvr.the.kiss.rest.entities.User;
import bg.mvr.the.kiss.rest.dto.UserSignIn;
import bg.mvr.the.kiss.rest.dto.UserSignUp;
import bg.mvr.the.kiss.rest.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

/**
 * Created by IntelliJ IDEA.
 * User: HDonev.
 * Date: 07.10.2020.
 * Time: 09:07.
 * Organization: DKIS MOIA.
 */
@RestController
@CrossOrigin(maxAge = 3600)
public class AuthController {

    private UserService userService;
    private AuthenticationManager authenticationManager;
    private ModelMapper modelMapper;
    private JwtUtils jwtUtils;
    public PasswordEncoder passwordEncoder;


    @Autowired
    public AuthController(UserService userService, AuthenticationManager authenticationManager,ModelMapper modelMapper, JwtUtils jwtUtils, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.modelMapper=modelMapper;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
    }


    @PostMapping(path = "/sign-up", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserSignUp> signUpUser(@RequestBody @Valid UserSignUp userSignUp) {
        User savedUser = userService.insertUser(modelMapper.map(userSignUp, User.class));
        return ResponseEntity.ok(modelMapper.map(savedUser, UserSignUp.class));
    }

    @PostMapping(value = "/sign-in",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> signIn(@Valid @RequestBody UserSignIn userSignIn) {
        User user = userService.getUserEntityByEmail(userSignIn.getEmail());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userSignIn.getEmail(), userSignIn.getPassword()));
        return ResponseEntity.ok(jwtUtils.generateToken(user));
    }
}
