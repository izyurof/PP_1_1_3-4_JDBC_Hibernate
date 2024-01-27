package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Valery", "ertgdfb", (byte) 32);
        userService.saveUser("sdfgdg", "dfgdg", (byte) 31);
        userService.saveUser("erthyj", "lkjhgf", (byte) 29);
        userService.saveUser("erthgf", "ewasdf", (byte) 34);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
