package com.epam.committee.dao;

import com.epam.committee.command.RequestContent;
import com.epam.committee.command.Router;
import com.epam.committee.entity.User;
import com.epam.committee.exception.DaoException;
import com.google.protobuf.ServiceException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public interface UserDao {
    /**
     * Checks if the table 'users' contains a row with login
     *
     * @param login a user login that should be checked
     * @return a {@code true} if the table has such row, {@code false} otherwise
     * @throws DaoException if a database access error occurs
     */
    boolean checkUserLogin(String login) throws DaoException;
    /**
     * Checks if the table 'users' contains a row with these login and password
     *
     * @param login    a user login that should be checked
     * @param password a password
     * @return a {@code true} if the table has such row, {@code false} otherwise
     * @throws DaoException if a database access error occurs
     */
    boolean findUserByLoginAndPassword(String login, String password) throws DaoException;

    /**
     * Gets a row from the table using login,
     * builds and returns  optional {@code User} object that represents this login.
     *
     * @param login a login of the user object
     * @return a Optional {@code User}, or Optional.empty() if login is not founded.
     * @throws DaoException if a database access error occurs
     */
    Optional<User> findUserByLogin(String login) throws DaoException;

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
    //
    int getUserRoleId(String login) throws DaoException, SQLException;
}
