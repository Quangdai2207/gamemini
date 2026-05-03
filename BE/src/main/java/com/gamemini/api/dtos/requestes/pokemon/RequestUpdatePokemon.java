package com.gamemini.api.dtos.requestes.pokemon;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RequestUpdatePokemon {

    @NotBlank(message = "Pokemon type is required")
    @Size(min = 5, max = 20, message = "Type must be between 5 and 20 characters")
    @Pattern(
            regexp = "^[a-zA-Z]+$",
            message = "Type must contain only letters and spaces"
    )
    private String type;
}
