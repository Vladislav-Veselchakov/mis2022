package ru.mis2022.controllers.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mis2022.models.entity.User;
import ru.mis2022.models.response.Response;
import ru.mis2022.service.abstr.UserService;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserRestController {

    private final UserService userService;

    @GetMapping("/current")
    public Response<User> getCurrentUser(Principal principal) {
        return Response.ok(userService.getCurrentUserByEmail(principal.getName()));
    }
}
