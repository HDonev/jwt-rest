package bg.mvr.the.kiss.rest.controllers;

import bg.mvr.the.kiss.rest.dto.ChangeRole;
import bg.mvr.the.kiss.rest.dto.RoleForAdmin;
import bg.mvr.the.kiss.rest.dto.UserForAdmin;
import bg.mvr.the.kiss.rest.services.RoleService;
import bg.mvr.the.kiss.rest.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 * User: HDonev.
 * Date: 08.01.2021.
 * Time: 11:43.
 * Organization: DKIS MOIA.
 */
@RestController
@RequestMapping("/admin")
@CrossOrigin(maxAge = 3600)
public class AdminController {

    private UserService userService;
    private ModelMapper modelMapper;
    private RoleService roleService;

    @Autowired
    public AdminController(UserService userService, ModelMapper modelMapper, RoleService roleService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.roleService = roleService;
    }

    @GetMapping("/user")
    public ResponseEntity<List<UserForAdmin>> findAllUsers() {
        return ResponseEntity.ok().body(userService.findAllUsers().stream().map(user -> modelMapper.map(user, UserForAdmin.class)).collect(Collectors.toList()));
    }

    @GetMapping("/role")
    public ResponseEntity<List<RoleForAdmin>> findAllRoles() {
        return ResponseEntity.ok().body(roleService.findAllRoles().stream().map(role -> this.modelMapper.map(role, RoleForAdmin.class)).collect(Collectors.toList()));
    }

    @PostMapping("/user/change-role")
    public ResponseEntity changeRole(@RequestBody @Valid ChangeRole changeRole, Authentication authentication) {
        userService.changeRole(changeRole, authentication);
        return ResponseEntity.ok().build();
    }
}
