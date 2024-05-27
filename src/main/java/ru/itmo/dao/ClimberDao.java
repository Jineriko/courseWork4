package ru.itmo.dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import ru.itmo.C3P0Pool;
import ru.itmo.model.Climber;

public class ClimberDao {
    public boolean createTable() {
        String createSql = "CREATE TABLE IF NOT EXISTS tb_climber(" +
                "id SERIAL PRIMARY KEY, " +
                "name VARCHAR(120) NOT NULL, " +
                "lastname VARCHAR(120) NOT NULL, " +
                "phone VARCHAR(20) NOT NULL, " +
                "email VARCHAR(120) NOT NULL, " +
                "date_of_last_ascent TIMESTAMPTZ, " +
                "created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP NOT NULL, " +
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

    public int insert(Climber climber) {
        String insertSql = "INSERT INTO tb_climber (name, lastname, phone, email, date_of_last_ascent) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = C3P0Pool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(insertSql)) {
                ps.setString(1, climber.getName());
                ps.setString(2, climber.getLastname());
                ps.setString(3, climber.getPhone());
                ps.setString(4, climber.getEmail());
                ps.setObject(5, climber.getDateOfLastAscent());
                return ps.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Ошибка вставки объекта в таблицу");
            return 0;
        }
    }

    public int[] insert(List<Climber> climbers) {
        String insertSql = "INSERT INTO tb_climber (name, lastname, phone, email, date_of_last_ascent) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = C3P0Pool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(insertSql)) {
                for (Climber climber : climbers) {
                    ps.setString(1, climber.getName());
                    ps.setString(2, climber.getLastname());
                    ps.setString(3, climber.getPhone());
                    ps.setString(4, climber.getEmail());
                    ps.setObject(5, climber.getDateOfLastAscent());
                    ps.addBatch();
                }
                return ps.executeBatch();
            }
        } catch (SQLException e) {
            System.out.println("Ошибка вставки объекта в таблицу");
            throw new RuntimeException(e);
        }
    }

    public List<Climber> getLastnameAndEmail() {
        String selectSql = "SELECT lastname, email, date_of_last_ascent " +
                "FROM tb_climber " +
                "WHERE date_of_last_ascent < CURRENT_DATE - 365 " +
                "ORDER BY lastname";
        try (Connection connection = C3P0Pool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(selectSql)) {
                ResultSet resultSet = ps.executeQuery();
                List<Climber> climbers = new ArrayList<>();
                while (resultSet.next()) {
                    Climber climber = new Climber();
                    climber.setLastname(resultSet.getString("lastname"));
                    climber.setEmail(resultSet.getString("email"));
                    climber.setDateOfLastAscent(resultSet.getObject("date_of_last_ascent", OffsetDateTime.class));
                    climbers.add(climber);
                }
                return climbers;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Climber getClimberInfo() {
        String selectSql = "SELECT * " +
                "FROM tb_climber " +
                "WHERE tb_climber.id = 10";
        try (Connection connection = C3P0Pool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(selectSql)) {
                ResultSet resultSet = ps.executeQuery();
                Climber climber = new Climber();
                while (resultSet.next()) {
                    climber.setId(resultSet.getInt("id"));
                    climber.setName(resultSet.getString("name"));
                    climber.setLastname(resultSet.getString("lastname"));
                    climber.setPhone(resultSet.getString("phone"));
                    climber.setEmail(resultSet.getString("email"));
                    climber.setDateOfLastAscent(resultSet.getObject("date_of_last_ascent", OffsetDateTime.class));
                }
                return climber;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
