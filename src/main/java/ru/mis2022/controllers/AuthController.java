package ru.mis2022.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mis2022.config.security.jwt.JwtResponse;
import ru.mis2022.config.security.jwt.JwtUtils;
import ru.mis2022.config.security.jwt.LoginRequest;
import ru.mis2022.models.dto.user.UserDto;
import ru.mis2022.models.dto.user.converter.UserDtoConverter;
import ru.mis2022.models.entity.Invite;
import ru.mis2022.models.entity.User;
import ru.mis2022.service.MutableHttpServletRequest;
import ru.mis2022.service.entity.AdministratorService;
import ru.mis2022.service.entity.InviteService;
import ru.mis2022.service.entity.UserService;
import ru.mis2022.utils.validation.ApiValidationUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
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
    private AdministratorService adminService;
    @Autowired
    UserDtoConverter userDtoConverter;

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

    @ApiOperation("Проверяем код активации и устанавливаем пароль пользователю")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пароль установлен"),
            @ApiResponse(code = 401, message = "Пароль не указан"),
            @ApiResponse(code = 410, message = "Пароль менее 10 символов"),
            @ApiResponse(code = 415, message = "Ссылка устарела"),
            @ApiResponse(code = 420, message = "Пользователь не найден")

    })
    @GetMapping("/confirm/emailpassword")
    public ResponseEntity<UserDto> confirmEmailPassword(HttpServletRequest request,
                                                        @RequestParam("token") String token, @RequestParam String pwd) {
        ApiValidationUtils.expectedFalse(pwd.trim().isEmpty(), 401, "Пароль не указан");
        ApiValidationUtils.expectedFalse(pwd.length() < 10, 410, "Пароль менее 10 символов");

        Invite invite = inviteService.findByToken(token);

        ApiValidationUtils.expectedFalse(invite == null || invite.getExpirationDate().isBefore(LocalDateTime.now()),
                415, "Ссылка устарела");

        User user = userService.findById(invite.getUserId()).orElse(null);
//        Administrator admin = adminService.findAdministratorById(invite.getUserId());

        ApiValidationUtils.expectedFalse(user == null, 420, "Пользователь не найден");
//        ApiValidationUtils.expectedFalse(admin == null, 420, "Пользователь не найден");

        user.setPassword(pwd);
//        admin.setPassword(pwd);
        user = userService.save(user);
//        adminService.merge(admin);
//        inviteService.delete(invite);

        UsernamePasswordAuthenticationToken userAuth =
            new UsernamePasswordAuthenticationToken(user, "USER", user.getAuthorities());
        String jwt = jwtUtils.generateJwtToken(userAuth); // SecurityContextHolder.getContext().getAuthentication());
        jwt = "Bearer " + jwt;
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Authorization", jwt);

        MutableHttpServletRequest muteRequest = new MutableHttpServletRequest(request);


        return ResponseEntity.ok().headers(responseHeaders).body(userDtoConverter.toDto(user));
//        return ResponseEntity.ok(userDtoConverter.toDto(user));
//        return ResponseEntity.ok(admin);
    }

}
