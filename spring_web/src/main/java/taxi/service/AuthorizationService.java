package taxi.service;

import taxi.exception.AuthenticationException;

/**
 * Created by Вадим on 28.02.2016.
 *
 * Регистрация пользователя в системе (оператора)
 * Пользователь вводит:
 * - логин (должен быть не менее 4 символов, не должен содержать пробелы)
 * - идентификационный номер (10 цифр, без букв и других знаков)
 * - пароль (должен быть не менее 8 символов,
 * включать большие и маленькие буквы, цифры, должен совпадать с подтверждением)
 * - подтверждение пароля
 * - пользователь с таким логином должен быть уникальным
 *
 */
public interface AuthorizationService {

    boolean register(String login, String id, String pass) throws AuthenticationException;

    boolean isRegistered(String login, String pass);
}
