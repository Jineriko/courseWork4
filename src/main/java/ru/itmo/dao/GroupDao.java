package ru.itmo.dao;

import ru.itmo.C3P0Pool;
import ru.itmo.model.Climber;
import ru.itmo.model.Group;
import ru.itmo.model.Mountain;

import java.sql.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class GroupDao {
    public boolean createTable() {
        String createSql = "CREATE TABLE IF NOT EXISTS tb_group(" +
                "group_id SERIAL PRIMARY KEY, " +
                "mountain_id INTEGER NOT NULL, " +
                "FOREIGN KEY (mountain_id) REFERENCES tb_mountain(mountain_id), " +
                "managers_name VARCHAR(120) NOT NULL, " +
                "next_ascent_date TIMESTAMPTZ, " +
                "count_of_climbers INTEGER, " +
                "max_count_of_climbers INTEGER NOT NULL, " +
                "cost_of_participation INTEGER NOT NULL, " +
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

    public int insert(Group group, Mountain mountain) {
        String insertSql = "INSERT INTO tb_group (mountain_id, managers_name, next_ascent_date, count_of_climbers, max_count_of_climbers, cost_of_participation) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = C3P0Pool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(insertSql)) {
                ps.setInt(1, (int) mountain.getId());
                ps.setString(2, group.getManagersName());
                ps.setObject(3, group.getNextAscentDate());
                ps.setInt(4, group.getCountOfClimbers());
                ps.setInt(5, group.getMaxCountClimbers());
                ps.setInt(6, group.getCostOfParticipation());
                return ps.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Ошибка вставки объекта в таблицу");
            return 0;
        }
    }

    public List<Group> getGroupsOpenForEntries() {
        String selectSql = "SELECT group_id, managers_name, next_ascent_date " +
                "FROM tb_group " +
                "WHERE count_of_climbers < max_count_of_climbers ";
        try (Connection connection = C3P0Pool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(selectSql)) {
                ResultSet resultSet = ps.executeQuery();
                List<Group> groups = new ArrayList<>();
                while (resultSet.next()) {
                    Group group = new Group();
                    group.setId(resultSet.getInt("group_id"));
                    group.setManagersName(resultSet.getString("managers_name"));
                    group.setNextAscentDate(resultSet.getObject("next_ascent_date", OffsetDateTime.class));
                    groups.add(group);
                }
                return groups;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
