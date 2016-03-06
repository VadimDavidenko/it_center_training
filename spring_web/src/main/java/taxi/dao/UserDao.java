package taxi.dao;

import taxi.domain.User;

import java.util.List;

/**
 * Created by Вадим on 28.02.2016.
 */
public interface UserDao {

    Long create(User user);

    User read(Long id);

    boolean update(User user);

    boolean delete(User user);

    List findAll();

    User findByLogin(String login);

    User findByLoginAndPassword(String login, String pass);
}
