package ru.karyeragame.paymentsystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class GameOutputDto {
    private long id;
    private String name;
    private String description;
    private boolean isAlive;
    private String adminEmail;
    private LocalDateTime creationDate;
}
