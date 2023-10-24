package ru.karyeragame.paymentsystem.user.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.karyeragame.paymentsystem.exceptions.ErrorPassword;
import ru.karyeragame.paymentsystem.exceptions.ExceptionNotFound;
import ru.karyeragame.paymentsystem.exceptions.SaveException;
import ru.karyeragame.paymentsystem.exceptions.UserAlreadyExistException;
import ru.karyeragame.paymentsystem.user.Profession;
import ru.karyeragame.paymentsystem.user.Role;
import ru.karyeragame.paymentsystem.user.Status;
import ru.karyeragame.paymentsystem.user.dao.RegisterKeyDao;
import ru.karyeragame.paymentsystem.user.dao.UserDao;
import ru.karyeragame.paymentsystem.user.dto.UserDtoRegistration;
import ru.karyeragame.paymentsystem.user.model.RegisterKey;
import ru.karyeragame.paymentsystem.user.model.User;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    //   private final EmailService emailService;
    private final UserDao userDao;
    private final RegisterKeyDao registerKeyDao;
    private final PasswordEncoder passwordEncoder;


    @Override
    public String createUser(UserDtoRegistration userDtoRegistration) {
        if (!userDtoRegistration.getPassword().equals(userDtoRegistration.getPasswordConfirm())) {
            throw new ErrorPassword("Пароли не совпадают.");
        }

        if (userDao.isExistUser(userDtoRegistration.getEmail())) {
            throw new UserAlreadyExistException("Пользователь с таким Email уже зарегистрирован: "
                    + userDtoRegistration.getEmail());
        }

        User user = User.builder().
                name(userDtoRegistration.getName()).
                email(userDtoRegistration.getEmail()).
                password(passwordEncoder.encode(userDtoRegistration.getPassword())).   //шифрование пароля пока не реализовано. encoder.encode(userDtoRegistration.getPassword()) passwordEncoder.passwordEncoder().encode(userDtoRegistration.getPassword())
                        created(LocalDateTime.now()).
                role(Role.UNAPPROVED).
                status(Status.UNAPPROVED).
                profession(Profession.UNAPPROVED).
                build();

        String key = UUID.randomUUID().toString();

//        Раскомментировать и проверить методы после слияния с emailService

//        emailService.sendMail("career@career.ru", new String[]{user.getEmail()}, "Регистрация в Карьера",
//                "Для завершения регистрации перейдите по ссылке " +
//                        "http://localhost:8081/registration/confirmation?key=" + key + "?email=" + user.getEmail());

        if (!userDao.saveUser(user)) {
            throw new SaveException("Не удалось сохранить user.");
        }

        User saveUser = userDao.findUserByEmail(userDtoRegistration.getEmail());

        RegisterKey registerKey = RegisterKey.builder().
                key(key).
                email(user.getEmail()).
                userId(saveUser.getId()).
                exp(LocalDateTime.now().plusDays(2)).
                build();


        if (!registerKeyDao.saveRegisterKey(registerKey)) {
            throw new SaveException("Не удалось сохранить registerKey.");
        }


        // для проверки функциональности подтверждения регистрации вызываем метод
        confirmationRegistration(key, user.getEmail());

        return "Проверьте электронную почту, перейдите по ссылке чтобы завершить регистрацию.";
    }

    @Override
    public String confirmationRegistration(String key, String email) {
        RegisterKey registerKey = registerKeyDao.findRegisterKeyByEmail(email);

        if (registerKey.getKey() == null) {
            throw new ExceptionNotFound("Подтверждающий ключ не найден.");
        }


        if (!registerKey.getKey().equals(key) && registerKey.getEmail().equals(email)) {
            throw new ExceptionNotFound("Несовпадение ключа или почты.");
        }
        if (registerKey.getExp().isBefore(LocalDateTime.now())) {
            registerKeyDao.deleteRegisterKey(key);
            userDao.deleteUserByEmail(email);
            throw new ExceptionNotFound("Время подтверждения вышло.");
        }

        User user = userDao.findById(registerKey.getUserId());

        user.setRole(Role.USER);
        user.setStatus(Status.READY);
        user.setProfession(Profession.WORKER);

//        registerKeyDao.deleteRegisterKey(key);
        userDao.updateUser(user);

        return "Успех, регистрация подтверждена.";
    }

}
