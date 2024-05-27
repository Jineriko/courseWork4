package ru.itmo.dao;

import ru.itmo.C3P0Pool;
import ru.itmo.model.Climber;
import ru.itmo.model.Info;
import ru.itmo.model.Mountain;

import java.sql.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MountainDao {
    public boolean createTable() {
        String createSql = "CREATE TABLE IF NOT EXISTS tb_mountain(" +
                "mountain_id SERIAL PRIMARY KEY, " +
                "name VARCHAR(120) UNIQUE NOT NULL, " +
                "countries TEXT NOT NULL, " +
                "height INTEGER NOT NULL, " +
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

    public int insert(Mountain mountain) {
        String insertSql = "INSERT INTO tb_mountain (name, countries, height) " +
                "VALUES (?, ?, ?)";
        try (Connection connection = C3P0Pool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(insertSql)) {
                ps.setString(1, mountain.getName());
                ps.setString(2, mountain.getCountries().toString());
                ps.setInt(3, mountain.getHeight());
                return ps.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Ошибка вставки объекта в таблицу");
            return 0;
        }
    }

    public int[] insert(List<Mountain> mountains) {
        String insertSql = "INSERT INTO tb_mountain (name, countries, height) " +
                "VALUES (?, ?, ?)";
        try (Connection connection = C3P0Pool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(insertSql)) {
                for (Mountain mountain : mountains) {
                    ps.setString(1, mountain.getName());
                    ps.setString(2, mountain.getCountries().toString());
                    ps.setInt(3, mountain.getHeight());
                    ps.addBatch();
                }
                return ps.executeBatch();
            }
        } catch (SQLException e) {
            System.out.println("Ошибка вставки объекта в таблицу");
            throw new RuntimeException(e);
        }
    }

    public List<String> getNameOfMountain(Integer value) {
        String selectSql = "SELECT tb_mountain.name" +
                "FROM tb_info" +
                "LEFT JOIN tb_group ON tb_info.group_id = tb_group.group_id " +
                "LEFT JOIN tb_mountain ON tb_mountain.mountain_id = tb_group.group_id" +
                "WHERE tb_info.count_of_conquered_the_mountain > ?";
        try (Connection connection = C3P0Pool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(selectSql)) {
                ResultSet resultSet = ps.executeQuery();
                List<String> nameOfMountains = new ArrayList<>();
                while (resultSet.next()) {
                    String name = resultSet.getString("tb_mountain.name");
                    nameOfMountains.add(name);
                }
                return nameOfMountains;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
