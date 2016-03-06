package taxi.service;

import taxi.dao.UserDao;
import taxi.domain.User;
import taxi.exception.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

/**
 * Created by Вадим on 28.02.2016.
 */

@Service
@Scope("singleton")
@Transactional
public class AuthorizationServiceImpl implements AuthorizationService {

    @Autowired
    private UserDao userDao;

    public AuthorizationServiceImpl() {
        Locale.setDefault(Locale.ENGLISH);
    }

    public boolean register(String login, String id, String pass)
            throws AuthenticationException {
        if (userDao.findByLogin(login) != null) {
            throw new AuthenticationException("User with such login already exists");
        } else {
            User newUser = new User(login, id, pass);
            if (userDao.create(newUser) != null) return true;
        }
        return false;
    }

    public boolean isRegistered(String login, String pass) {
        return (userDao.findByLoginAndPassword(login, pass) != null);
    }
}
