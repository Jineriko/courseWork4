package ru.itmo.dao;

import ru.itmo.C3P0Pool;
import ru.itmo.model.Climber;
import ru.itmo.model.Group;
import ru.itmo.model.Info;
import ru.itmo.model.Mountain;

import java.sql.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class InfoDao {
    public boolean createTable() {
        String createSql = "CREATE TABLE IF NOT EXISTS tb_info(" +
                "info_id SERIAL PRIMARY KEY, " +
                "beginning_of_the_ascent TIMESTAMPTZ, " +
                "ending_of_the_ascent TIMESTAMPTZ, " +
                "count_of_conquered_the_mountain INTEGER, " +
                "group_id INTEGER NOT NULL, " +
                "FOREIGN KEY (group_id) REFERENCES tb_group(group_id), " +
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

    public int insert(Group group, Info info) {
        String insertSql = "INSERT INTO tb_info (beginning_of_the_ascent, ending_of_the_ascent, count_of_conquered_the_mountain, group_id) " +
                "VALUES (?, ?, ?, ?)";
        try (Connection connection = C3P0Pool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(insertSql)) {
                ps.setObject(1, info.getBeginningOfTheAscent());
                ps.setObject(2, info.getEndingOfTheAscent());
                ps.setInt(3, info.getCountOfConqueredTheMountain());
                ps.setInt(4, (int) group.getId());
                return ps.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Ошибка вставки объекта в таблицу");
            return 0;
        }
    }

    public List<Integer> getId(String managersName, int value) {
        String selectSql = "SELECT tb_group.group_id " +
                "FROM tb_group " +
                "JOIN tb_info " +
                "ON tb_group.group_id = tb_info.group_id " +
                "WHERE tb_group.managers_name = 'managersName AND tb_info.count_of_conquered_the_mountain > value";
        try (Connection connection = C3P0Pool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(selectSql)) {
                ResultSet resultSet = ps.executeQuery();
                List<Integer> groupId = new ArrayList<>();
                while (resultSet.next()) {
                    Group group = new Group();
                    group.setId(resultSet.getInt("group_id"));
                    groupId.add((int) group.getId());
                }
                return groupId;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Info> getInfoForPeriod(OffsetDateTime dateOfBeginning, OffsetDateTime dateOfEnding) {
        String selectSql = "SELECT info_id, beginning_of_the_ascent, ending_of_the_ascent, count_of_conquered_the_mountain " +
                "FROM tb_info " +
                "WHERE beginning_of_the_ascent >= ? AND ending_of_the_ascent <= ?";
        try (Connection connection = C3P0Pool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(selectSql)) {
                ResultSet resultSet = ps.executeQuery();
                List<Info> infoList = new ArrayList<>();
                while (resultSet.next()) {
                    Info info = new Info();
                    info.setId(resultSet.getInt("info_id"));
                    info.setBeginningOfTheAscent(resultSet.getObject("beginning_of_the_ascent", OffsetDateTime.class));
                    info.setBeginningOfTheAscent(resultSet.getObject("ending_of_the_ascent", OffsetDateTime.class));
                    info.setCountOfConqueredTheMountain(resultSet.getInt("count_of_conquered_the_mountain"));
                    infoList.add(info);
                }
                return infoList;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
