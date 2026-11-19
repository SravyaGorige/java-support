package com.example.utils;

import com.example.pojo.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCManager {

    private static final String URL =
            "jdbc:oracle:thin:@//localhost:1521/xe";
    private static final String USERNAME = "system";
    private static final String PASSWORD = "system";

    // INSERT
    public static User saveUser(User user) {

        String query = "INSERT INTO student(Name, RollNo) VALUES(?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, user.getName());
            ps.setString(2, String.valueOf(user.getRollNo()));

            if(ps.executeUpdate(query)==1)
                return user;


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    // READ
    public static List<User> getAllUsers() {

        List<User> users = new ArrayList<>();

        String query = "SELECT Name, RollNo FROM student";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {

                User user = new User();

                user.setName(rs.getString("Name"));
                user.setRollNo(Integer.parseInt(rs.getString("RollNo")));

                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    // UPDATE
    public static void updateStudent(String rollNo, String newName) {

        String query = "UPDATE student SET Name=? WHERE RollNo=?";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, newName);
            ps.setString(2, rollNo);

            int rows = ps.executeUpdate();

            System.out.println(rows + " row updated successfully");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE
    public static void deleteStudent(String rollNo) {

        String query = "DELETE FROM student WHERE RollNo=?";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, rollNo);

            int rows = ps.executeUpdate();

            System.out.println(rows + " row deleted successfully");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}