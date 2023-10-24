package ru.karyeragame.paymentsystem.user.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.karyeragame.paymentsystem.user.dto.UserDtoRegistration;

public interface UserService {
    String createUser(UserDtoRegistration userDtoRegistration);

    String confirmationRegistration(String key, String email);
}
