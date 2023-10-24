package ru.karyeragame.paymentsystem.user.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterKey {
    private Long id;
    private String key;
    private String email;
    private Long userId;
    private LocalDateTime exp;
}
