package ru.karyeragame.paymentsystem.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@NoArgsConstructor
public class GameCreateDto {
    @NotNull
    @Size(min = 5, max = 100)
    private String name;
    @PositiveOrZero
    private long adminId;
    @Nullable
    private String description;
}
