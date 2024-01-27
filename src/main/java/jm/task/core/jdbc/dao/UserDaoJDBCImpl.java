package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String createTable = """
                create table if not exists users
                (
                id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
                name VARCHAR(64) NOT NULL,
                last_name VARCHAR(64) NOT NULL,
                age INT NOT NULL
                );
                """;

        try (Connection connection = Util.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(createTable)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void dropUsersTable() {
        String dropTable = """
                drop table if exists users;
                """;
        try (Connection connection = Util.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(dropTable)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String addUser = """
                insert into users (name, last_name, age)
                values (?, ?, ?);
                """;

        try (Connection connection = Util.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(addUser)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                System.out.printf("User с именем - %s добавлен в базу данных", name);
                System.out.println();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String removeUser = """
                delete from users
                where id=?;
                """;

        try (Connection connection = Util.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(removeUser)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String getUsers = """
                select
                id,
                name,
                last_name,
                age
                from users;
                """;

        try (Connection connection = Util.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getUsers)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    public void cleanUsersTable() {
        String cleanTable = """
                delete from users;
                """;

        try (Connection connection = Util.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(cleanTable)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
