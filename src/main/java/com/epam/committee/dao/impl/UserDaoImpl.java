package com.epam.committee.dao.impl;

import com.epam.committee.dao.TableColumn;
import com.epam.committee.dao.UserDao;
import com.epam.committee.entity.UserRole;
import com.epam.committee.exception.DaoException;
import com.epam.committee.dao.BaseDao;
import com.epam.committee.entity.User;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl extends BaseDao<Long, User> implements UserDao {
    private final static Logger logger = LogManager.getLogger();
    private final static String SQL_SELECT_ALL_USERS = "SELECT user_id, fk_user_role_id, login, password, first_name, last_name, patronymic, email, isActive" +
            "   FROM committee_db.user";
    private final static String SQL_CREATE_USER = "INSERT INTO committee_db.user (fk_user_role_id, login, password, first_name, last_name, patronymic, email, isActive) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private final static String SQL_CHECK_LOGIN_PASSWORD = "SELECT user_id, fk_user_role_id, login, password, first_name, last_name, patronymic, email, isActive" +
            "   FROM committee_db.user WHERE login= ? AND password= ?";
    private final static String SQL_SELECT_USER_BY_LOGIN = "SELECT user_id, fk_user_role_id, login, password, first_name, last_name, patronymic, email, isActive" +
            " FROM committee_db.user WHERE login= ?";
    private final static String SQL_SELECT_USER_BY_ID = "SELECT fk_user_role_id, login, password, first_name, last_name, patronymic, email, isActive" +
            " FROM committee_db.user WHERE user_id= ?";
    private static final String SQL_SELECT_USER_ROLE_BY_LOGIN = "SELECT fk_user_role_id FROM committee_db.user WHERE login = ?";
    private final static String SQL_UPDATE_USER_PASSWORD = "UPDATE committee_db.user SET password= ? WHERE login= ?";
    private static UserDaoImpl instance = new UserDaoImpl();

    private UserDaoImpl() {
    }

    public static UserDaoImpl getInstance() {
        return instance;
    }

    @Override
    public boolean create(User user) throws DaoException {
        boolean created = false;
        PreparedStatement statement = null;
        ResultSet generatedKeys;
        try {
            statement = connection.prepareStatement(SQL_CREATE_USER, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, user.getUserRole().getId());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getFirstName());
            statement.setString(5, user.getLastName());
            statement.setString(6, user.getPatronymic());
            statement.setString(7, user.getEmail());
            statement.setBoolean(8, user.getIsActive());
            statement.executeUpdate();
            generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setUserId(generatedKeys.getInt(1));
                created = true;
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "User creation failed! ", e);
            throw new DaoException(e);
        } finally {
            close(statement);
        }
        return created;
    }

    @Override
    public boolean update(User user) throws DaoException {
        boolean updated;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_UPDATE_USER_PASSWORD);
            statement.setString(1, user.getPassword());
            statement.setString(2, user.getLogin());
            updated = statement.executeUpdate() != 0;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "User password updating failed!: ", e);
            throw new DaoException(e);
        } finally {
            close(statement);
        }
        return updated;
    }

    @Override
    public List<User> findAll() throws DaoException {
        List<User> userList = new ArrayList<>();
        User user;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_ALL_USERS);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                user = extractUser(resultSet);
                userList.add(user);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "SQL exception (request or table failed): ", e);
            throw new DaoException(e);
        } finally {
            close(statement);
        }
        return userList;
    }

    @Override
    public Optional<User> findById(Long id) throws DaoException {
        User user = null;
        PreparedStatement statement = null;
        Optional<User> userOptional;
        try {
            statement = connection.prepareStatement(SQL_SELECT_USER_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                user = extractUser(resultSet);
            }
            userOptional = Optional.ofNullable(user);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "SQL exception (request or table failed): ", e);
            throw new DaoException(e);
        } finally {
            close(statement);
        }
        return userOptional;
    }

    @Override
    public boolean checkUserLogin(String login) throws DaoException {
        boolean exist;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_USER_BY_LOGIN);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            exist = resultSet.next();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "SQL exception (request or table failed): ", e);
            throw new DaoException(e);
        } finally {
            close(statement);
        }
        return exist;
    }

    @Override
    public boolean findUserByLoginAndPassword(String login, String password) throws DaoException {
        boolean verified = false;
        logger.log(Level.DEBUG, login + " " + password);
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_CHECK_LOGIN_PASSWORD);
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            verified = resultSet.next();
            logger.log(Level.DEBUG, verified + " FROM logInUser USERDAO ");
        } catch (SQLException e) {
            logger.log(Level.ERROR, "SQL exception (request or table failed): ", e);
            throw new DaoException(e);
        } finally {
            close(statement);
        }
        return verified;
    }

    @Override
    public Optional<User> findUserByLogin(String login) throws DaoException {
        User user = new User();
        PreparedStatement statement = null;
        Optional<User> optionalUser;
        try {
            statement = connection.prepareStatement(SQL_SELECT_USER_BY_LOGIN);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                user = extractUser(resultSet);
            }
            optionalUser = Optional.of(user);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "SQL exception (request or table failed): ", e);
            throw new DaoException(e);
        } finally {
            close(statement);
        }
        return optionalUser;
    }

    /**
     * Creates a new {@code User} object and
     * sets its values using {@code ResultSet}
     *
     * @param resultSet a {@code ResultSet} to build an object
     * @return a {@code User}
     */
    private User extractUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setUserId(resultSet.getLong(TableColumn.USER_ID));
        int roleCode = resultSet.getInt(TableColumn.USER_ROLE);
        switch (roleCode) {
            case 1:
                user.setUserRole(UserRole.ADMIN);
                break;
            case 2:
                user.setUserRole(UserRole.ENTRANT);
                break;
            default:
                logger.log(Level.ERROR, "Unknown role of user");
                throw new IllegalArgumentException();
        }
        user.setLogin(resultSet.getString(TableColumn.USER_LOGIN));
        user.setPassword(resultSet.getString(TableColumn.USER_PASSWORD));
        user.setFirstName(resultSet.getString(TableColumn.USER_FIRST_NAME));
        user.setLastName(resultSet.getString(TableColumn.USER_LAST_NAME));
        user.setPatronymic(resultSet.getString(TableColumn.USER_PATRONYMIC));
        user.setEmail(resultSet.getString(TableColumn.USER_EMAIL));
        user.setIsActive(resultSet.getBoolean(TableColumn.USER_STATUS));
        return user;
    }

//    @Override
//    public boolean authenticate(String login, String password) throws DaoException {
//        boolean match = false;
//        try (Connection connection = ConnectionPool.getInstance().getConnection();
//             PreparedStatement statement = connection.prepareStatement(SELECT_LOGIN_PASSWORD)) {
//            statement.setString(1, login);
//            ResultSet resultSet = statement.executeQuery();
//            String passFromDb;
//            if (resultSet.next()) {
//                passFromDb = resultSet.getString(1);
//                match = password.equals(passFromDb);
//            }
//
//        } catch (SQLException e) {
//            logger.log(Level.ERROR, "SQL exception (request or table failed): ", e);
//            throw new DaoException(e);
//        }
//        return match;
//    }

    @Override
    public int getUserRoleId(String login) throws DaoException, SQLException {
        int roleId = 0;
        try (
                PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USER_ROLE_BY_LOGIN)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                roleId = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "SQL exception (request or table failed): ", e);
            throw new DaoException(e);
        }
        return roleId;
    }
}