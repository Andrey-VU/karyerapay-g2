package ru.karyeragame.paymentsystem.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;
import ru.karyeragame.paymentsystem.mock.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "games")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @Size(min = 5, max = 100)
    private String name;
    @Nullable
    private String description;
    private boolean isAlive;
    @NotNull
    private User admin;
    @Column(name = "create_date")
    @NotNull
    private LocalDateTime creationDate;
}
