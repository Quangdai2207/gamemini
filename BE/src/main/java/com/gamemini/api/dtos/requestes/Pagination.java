package com.gamemini.api.dtos.requestes;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class Pagination {
    @Min(0)
    private int page = 0;

    @Min(0)
    @Max(100)
    private int size = 10;
}
