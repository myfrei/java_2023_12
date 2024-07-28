package ru.otus.web.service;

import java.util.List;
import java.util.Optional;
import org.eclipse.jetty.security.AbstractLoginService;
import org.eclipse.jetty.security.RolePrincipal;
import org.eclipse.jetty.security.UserPrincipal;
import org.eclipse.jetty.util.security.Password;
import ru.otus.web.dao.UserDao;
import ru.otus.web.model.User;

public class InMemoryLoginServiceImpl extends AbstractLoginService {

    private final UserDao userDao;

    public InMemoryLoginServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    protected List<RolePrincipal> loadRoleInfo(UserPrincipal userPrincipal) {
        return List.of(new RolePrincipal("admin"));
    }

    @Override
    protected UserPrincipal loadUserInfo(String login) {
        User user = userDao.findByLogin(login).orElse(null);
        if (user != null) {
            return new UserPrincipal(user.login(), new Password(user.password()));
        }
        return null;
    }
}
