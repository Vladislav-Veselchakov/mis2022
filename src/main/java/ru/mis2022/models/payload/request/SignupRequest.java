package ru.mis2022.models.payload.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
public class SignupRequest {

    private String email;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    private String firstName;

    private String lastName;

    @Nullable
    private String surname;

    private LocalDate birthday;

    private String role;



}
