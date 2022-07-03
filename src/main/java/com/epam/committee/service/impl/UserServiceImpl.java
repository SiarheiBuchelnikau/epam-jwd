package com.epam.committee.service.impl;

import com.epam.committee.command.ConstantName;
import com.epam.committee.dao.EntityTransaction;
import com.epam.committee.dao.impl.UserDaoImpl;
import com.epam.committee.entity.User;
import com.epam.committee.entity.UserRole;
import com.epam.committee.exception.DaoException;
import com.epam.committee.exception.ServiceException;
import com.epam.committee.service.UserService;
import com.epam.committee.util.PasswordHashGenerator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private final static Logger logger = LogManager.getLogger();
    private static UserServiceImpl instance = new UserServiceImpl();

    private UserServiceImpl() {
    }

    public static UserServiceImpl getInstance() {
        return instance;
    }

    @Override
    public Optional<User> registerEntrant(Map<String, String> parameters) throws ServiceException {
        Optional<User> optionalUser;
        EntityTransaction transaction = new EntityTransaction();
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        PasswordHashGenerator hashGenerator = new PasswordHashGenerator();
        int userId;
        transaction.begin(userDao);
        User user = new User();
        try {
            optionalUser = Optional.of(user);
            user.setUserRole(UserRole.ENTRANT);
            user.setLogin(parameters.get(ConstantName.PARAMETER_LOGIN));
            user.setPassword(hashGenerator.hash(parameters.get(ConstantName.PARAMETER_PASSWORD)));
            user.setFirstName(parameters.get(ConstantName.PARAMETER_FIRST_NAME));
            user.setLastName(parameters.get(ConstantName.PARAMETER_LAST_NAME));
            user.setPatronymic(parameters.get(ConstantName.PARAMETER_PATRONYMIC));
            user.setEmail(parameters.get(ConstantName.PARAMETER_EMAIL));
            user.setIsActive(true);
            userDao.create(user);
            transaction.commit();
        } catch (DaoException e) {
            transaction.rollback();
            logger.log(Level.ERROR, "Exception while committing transaction", e);
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
        return optionalUser;
    }

    @Override
    public boolean checkUserLogin(String login) throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        EntityTransaction transaction = new EntityTransaction();
        boolean exists;
        transaction.beginNoTransaction(userDao);
        try {
            exists = userDao.checkUserLogin(login);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Exception while executing service", e);
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
        return exists;
    }

    @Override
    public boolean findUserByLoginAndPassword(String login, String password) throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        EntityTransaction transaction = new EntityTransaction();
        PasswordHashGenerator hashGenerator = new PasswordHashGenerator();
        boolean flag;
        String hashPassword = hashGenerator.hash(password);
        transaction.beginNoTransaction(userDao);
        try {
            flag = userDao.findUserByLoginAndPassword(login, hashPassword);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Exception while executing service", e);
            throw new ServiceException(e);
        } finally {
            transaction.endNoTransaction();
        }
        return flag;
    }

    @Override
    public int getUserRoleId(String login) throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        EntityTransaction transaction = new EntityTransaction();
        int roleId;
        transaction.beginNoTransaction(userDao);
        try {
            roleId = userDao.getUserRoleId(login);
        } catch (DaoException | SQLException e) {
            logger.log(Level.ERROR, "Exception while executing service", e);
            throw new ServiceException(e);
        } finally {
            transaction.endNoTransaction();
        }
        return roleId;
    }

    @Override
    public Optional<User> findByLogin(String login) throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        EntityTransaction transaction = new EntityTransaction();
        Optional<User> userOptional;
        transaction.beginNoTransaction(userDao);
        try {
            userOptional = userDao.findUserByLogin(login);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Exception while executing service", e);
            throw new ServiceException(e);
        } finally {
            transaction.endNoTransaction();
        }
        return userOptional;
    }

    @Override
    public boolean changePassword(User user, String newPassword) throws ServiceException {
        boolean updated;
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        EntityTransaction transaction = new EntityTransaction();
        PasswordHashGenerator hashGenerator = new PasswordHashGenerator();
        transaction.beginNoTransaction(userDao);
        user.setPassword(hashGenerator.hash(newPassword));
        try {
            updated = userDao.update(user);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Exception while saving changes", e);
            throw new ServiceException(e);
        } finally {
            transaction.endNoTransaction();
        }
        return updated;
    }
}