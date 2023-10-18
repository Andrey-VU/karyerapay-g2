package ru.karyeragame.paymentsystem.mock;

import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class User {
    private long id;
    private String email;
}
