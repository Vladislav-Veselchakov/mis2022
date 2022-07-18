package ru.mis2022.controllers.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mis2022.config.security.jwt.JwtUtils;
import ru.mis2022.models.entity.User;
import ru.mis2022.models.payload.request.LoginRequest;
import ru.mis2022.models.payload.request.SignupRequest;
import ru.mis2022.models.payload.response.JwtResponse;
import ru.mis2022.models.payload.response.MessageResponse;
import ru.mis2022.repositories.UserRepository;
import ru.mis2022.service.RoleService;
import ru.mis2022.service.UserService;
import ru.mis2022.service.impl.UserDetailsImpl;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    AuthenticationManager authenticationManager;

    private final UserService userService;

    private final RoleService roleService;

    private final UserRepository userRepository;

    private final JwtUtils jwtUtils;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserService userService,
                          RoleService roleService, UserRepository userRepository,
                          JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.roleService = roleService;
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
            }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        String reqRole = signUpRequest.getRole();
        User newUser = new User();
        newUser.setEmail(signUpRequest.getEmail());
        newUser.setPassword(signUpRequest.getPassword());
        newUser.setBirthday(signUpRequest.getBirthday());
        newUser.setFirstName(signUpRequest.getFirstName());
        newUser.setLastName(signUpRequest.getLastName());
        newUser.setRole(roleService.findByName(reqRole));
        userService.persist(newUser);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
