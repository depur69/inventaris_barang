package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Koneksi {
    private static Connection koneksi;

    public static Connection getConnection() {
        if (koneksi == null) {
            try {
                String url = "jdbc:mysql://localhost:3306/inventaris_db";
                String user = "root";
                String pass = ""; 

                koneksi = DriverManager.getConnection(url, user, pass);
                System.out.println("Koneksi ke database berhasil");
            } catch (SQLException e) {
                System.out.println("Koneksi ke database gagal");
                e.printStackTrace();
            }
        }
        return koneksi;
    }
}
