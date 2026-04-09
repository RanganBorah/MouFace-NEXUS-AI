package face_recognition_web.service;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DBHelperSave {

    public static int getSuggestedId() {
        File folder = new File("dataset");
        if (!folder.exists()) folder.mkdir();

        File[] files = folder.listFiles();
        int maxId = 0;

        if (files != null) {
            for (File file : files) {
                if (file.getName().startsWith("user.")) {
                    try {
                        int id = Integer.parseInt(file.getName().split("\\.")[1]);
                        if (id > maxId) {
                            maxId = id;
                        }
                    } catch (Exception ignored) {
                    }
                }
            }
        }

        return maxId + 1;
    }

    public static int insertUser(String name, int age, String dept, String info) throws Exception {
        int id = getSuggestedId();

        String url = "jdbc:mysql://localhost:3306/face_db";
        try (Connection con = DriverManager.getConnection(url, "root", "noah");
             PreparedStatement ps = con.prepareStatement(
                     "INSERT INTO users (id, name, age, department, info) VALUES (?, ?, ?, ?, ?) " +
                     "ON DUPLICATE KEY UPDATE name=?, age=?, department=?, info=?")) {

            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setInt(3, age);
            ps.setString(4, dept);
            ps.setString(5, info);

            ps.setString(6, name);
            ps.setInt(7, age);
            ps.setString(8, dept);
            ps.setString(9, info);

            ps.executeUpdate();
        }

        return id;
    }
}