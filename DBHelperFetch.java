package face_recognition_web.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBHelperFetch {

    private static final String URL = "jdbc:mysql://localhost:3306/face_db";
    private static final String USER = "root";
    private static final String PASSWORD = "noah";

    public static String getUserInfo(int id) {
        String sql = "SELECT * FROM users WHERE id=?";

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return "\n--- MATCH FOUND ---\n"
                            + "Name: " + rs.getString("name") + "\n"
                            + "Age: " + rs.getInt("age") + "\n"
                            + "Dept: " + rs.getString("department") + "\n"
                            + "Info: " + rs.getString("info");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "User ID " + id + " not in database.";
    }
}