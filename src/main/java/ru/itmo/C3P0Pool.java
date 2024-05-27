package ru.itmo;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class C3P0Pool {
    private static ComboPooledDataSource pool =
            new ComboPooledDataSource();

    public static Connection getConnection() throws SQLException {
        return pool.getConnection();
    }
}