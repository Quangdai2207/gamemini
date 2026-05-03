package com.gamemini.api.dtos.requestes.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RequestLogin {

    @NotBlank(message = "Username is required")
    @Size(min = 5, max = 20, message = "Name must be between 5 and 20 characters")
    @Pattern(
            regexp = "^[a-zA-Z0-9 ]+$",
            message = "Name must contain only letters and spaces"
    )
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 32, message = "Password must be 6-32 characters")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$",
            message = "Password must contain at least 1 letter and 1 number"
    )
    private String password;
}
