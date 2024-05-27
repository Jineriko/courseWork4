package ru.itmo.dao;

import ru.itmo.C3P0Pool;
import ru.itmo.model.Mountain;
import ru.itmo.model.Reserve;

import java.sql.*;

public class ReserveDao {
    public boolean createTable() {
        String createSql = "CREATE TABLE IF NOT EXISTS tb_reserve(" +
                "reserve_id SERIAL PRIMARY KEY, " +
                "climber_id INTEGER NOT NULL, " +
                "FOREIGN KEY (climber_id) REFERENCES tb_climber(id), " +
                "group_id INTEGER NOT NULL, " +
                "FOREIGN KEY (group_id) REFERENCES tb_group(group_id), " +
                "next_ascent_date TIMESTAMPTZ, " +
                "created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP NOT NULL CHECK (created_at <= CURRENT_TIMESTAMP), " +
                "update_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP NOT NULL, " +
                "enable BOOLEAN DEFAULT TRUE NOT NULL)";

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try (Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/newBase",
                "user",
                "00009999"
        )) {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(createSql);
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int insert(Reserve reserve) {
        String insertSql = "INSERT INTO tb_reserve (climber_id, group_id, next_ascent_date) " +
                "VALUES (?, ?, ?)";
        try (Connection connection = C3P0Pool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(insertSql)) {
                ps.setInt(1, (int) reserve.getClimber().getId());
                ps.setInt(2, (int) reserve.getGroup().getId());
                ps.setObject(3, reserve.getNextAscentDate());
                return ps.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Ошибка вставки объекта в таблицу");
            return 0;
        }
    }
}
