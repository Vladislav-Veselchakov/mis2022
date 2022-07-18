package ru.mis2022.controllers.doctor;

import org.springframework.web.bind.annotation.*;
import ru.mis2022.models.response.Response;

@RestController
@RequestMapping("/api/doctor")
public class DoctorRestController {

    @GetMapping("/test")
    public Response<String> test() {
        return Response.ok("doctor rest controller");
    }

}
