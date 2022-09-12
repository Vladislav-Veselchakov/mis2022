package ru.mis2022.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mis2022.config.security.jwt.JwtResponse;
import ru.mis2022.config.security.jwt.JwtUtils;
import ru.mis2022.config.security.jwt.LoginRequest;
import ru.mis2022.models.entity.Invite;
import ru.mis2022.models.entity.User;
import ru.mis2022.service.entity.InviteService;
import ru.mis2022.service.entity.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    @Autowired
    private InviteService inviteService;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        User userDetails = (User) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getEmail(),
                roles));
    }

    @GetMapping("/confirm/emailpassword")
    public ResponseEntity<?> confirmEmailPassword(@RequestParam("token") String token, @RequestParam String pwd) {
//        Doctor signedInDoc = (Doctor) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(pwd.trim().isEmpty())
            return ResponseEntity.ok("password is not specified");
        if(pwd.length() < 10)
            return ResponseEntity.ok("password is too small");

        Invite invite = inviteService.findByToken(token);
        if(invite == null || invite.getExpirationDate().isBefore(LocalDateTime.now()))
            return ResponseEntity.ok("link expired");

        User user = userService.findById(invite.getUserId()).orElse(null);
        if(user == null)
            return ResponseEntity.ok("User not found");

        user.setPassword(encoder.encode(pwd));
        inviteService.delete(invite);



        return ResponseEntity.ok("OK. Password've been set, invite've been deleted");
    }

}
