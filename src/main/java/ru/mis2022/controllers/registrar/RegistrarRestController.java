package ru.mis2022.controllers.registrar;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mis2022.models.response.Response;

@RestController
@RequestMapping("/api/registrar")
public class RegistrarRestController {

    @GetMapping("/test")
    @PreAuthorize("hasRole('REGISTRAR')")
    public Response<String> testMethod() {
        return Response.ok("registrar rest controller");
    }

}
