package com.epam.committee.pool;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The {@code ConnectionPool} class has private BlockingQueue in which
 * ProxyConnections are stored.
 * The max amount of created connections is set by POOL_SIZE int value.
 * The connection can be taken from the BlockingQueue and
 * released to it.
 * Thread safe.
 *
 * @see ProxyConnection
 */
public class ConnectionPool {
    private final static Logger logger = LogManager.getLogger();
    private static final String DB_PROPERTIES = "db";
    private static final String DB_URL = "url";
    private static final String DB_USER = "user";
    private static final String DB_PASSWORD = "password";
    private static final String DB_DRIVER = "driver";
    private static final int POOL_SIZE = 8;
    private static Lock lock = new ReentrantLock();
    private static AtomicBoolean isCreated = new AtomicBoolean(false);
    private static ConnectionPool instance;
    private BlockingQueue<ProxyConnection> free = new LinkedBlockingQueue<>(POOL_SIZE);
    private BlockingQueue<ProxyConnection> used = new LinkedBlockingQueue<>(POOL_SIZE);

    static {
        try {
            ResourceBundle resource = ResourceBundle.getBundle(DB_PROPERTIES);
            String driverName = resource.getString(DB_DRIVER);
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); // fatal exception
        }
    }

    private ConnectionPool() throws IOException {
        ResourceBundle resource = ResourceBundle.getBundle(DB_PROPERTIES);
        String url = resource.getString(DB_URL);
        String user = resource.getString(DB_USER);
        String pass = resource.getString(DB_PASSWORD);

        for (int i = 0; i < POOL_SIZE; i++) {
            Connection connection = null;
            try {
                connection = DriverManager.getConnection(url, user, pass);
            } catch (SQLException e) {
                throw new ExceptionInInitializerError(e);
            }
            free.add(new ProxyConnection(connection));
        }
        if (free.size() == 0) {
            logger.log(Level.FATAL, "Pool is not created.");
            throw new RuntimeException();
        }
    }

    private int getPoolSize() {
        return free.size() + used.size();
    }

    /**
     * Returns the ConnectionPool object.
     *
     * @return the {@code ConnectionPool} object.
     */
    public static ConnectionPool getInstance() {
        if (!isCreated.get()) {
            try {
                lock.lock();
                if (instance == null) {
                    instance = new ConnectionPool();
                    isCreated.set(true);
                    logger.log(Level.INFO, "Pool Created successfully.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    /**
     * Returns the Connection object.
     *
     * @return the {@code Connection} object.
     */
    public Connection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = free.take();
            used.put(connection);
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, "Connection is lost");
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    /**
     * Returns the Connection object to the {@code ConnectionPool} object .
     */
    public void releaseConnection(Connection connection) {
        try {
            used.remove(connection);
            free.put((ProxyConnection) connection);
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, "Connection is not realise");
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Destroy the {@code ConnectionPool} object.
     */
    public void destroyPool() {
        for (int i = 0; i < POOL_SIZE; i++) {
            try {
                free.take().realClose();
            } catch (SQLException | InterruptedException e) {
                logger.log(Level.ERROR, "ConnectionPool is not destroy", e);
            }
        }
        deregisterDriver();
    }

    /**
     * Removes the specified driver from the {@code DriverManager}'s list of
     * registered drivers.
     */
    private void deregisterDriver() {
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                logger.log(Level.ERROR, " DriverManager close error", e);
            }
        });
    }
}