package ru.karyeragame.paymentsystem.user.dao;

import ru.karyeragame.paymentsystem.user.model.RegisterKey;

public interface RegisterKeyDao {
    Boolean deleteRegisterKey(String key);

    RegisterKey findRegisterKeyByEmail(String email);

    boolean saveRegisterKey(RegisterKey registerKey);
}
