package dao;

import model.Barang;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BarangDAO {
    private Connection conn;

    public BarangDAO() {
        try {
            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/inventaris_db", "root", ""); // sesuaikan DB
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // GET ALL
    public List<Barang> getAllBarang() {
        List<Barang> list = new ArrayList<>();
        String sql = "SELECT b.id_barang, b.kode_barang, b.nama_barang, b.id_kategori, k.nama_kategori, " +
                     "b.jumlah, b.kondisi, b.id_lokasi, l.nama_lokasi " +
                     "FROM barang b " +
                     "JOIN kategori k ON b.id_kategori = k.id_kategori " +
                     "JOIN lokasi l ON b.id_lokasi = l.id_lokasi";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Barang b = new Barang();
                b.setIdBarang(rs.getInt("id_barang"));
                b.setKodeBarang(rs.getString("kode_barang"));
                b.setNamaBarang(rs.getString("nama_barang"));
                b.setIdKategori(rs.getInt("id_kategori"));
                b.setJumlah(rs.getInt("jumlah"));
                b.setKondisi(rs.getString("kondisi"));
                b.setIdLokasi(rs.getInt("id_lokasi"));
                b.setNamaKategori(rs.getString("nama_kategori")); // penting
                b.setNamaLokasi(rs.getString("nama_lokasi"));     // penting
                list.add(b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // CREATE
    public void insert(Barang b) {
        String sql = "INSERT INTO barang (kode_barang, nama_barang, id_kategori, jumlah, kondisi, id_lokasi) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, b.getKodeBarang());
            ps.setString(2, b.getNamaBarang());
            ps.setInt(3, b.getIdKategori());
            ps.setInt(4, b.getJumlah());
            ps.setString(5, b.getKondisi());
            ps.setInt(6, b.getIdLokasi());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // UPDATE
    public void update(Barang b) {
        String sql = "UPDATE barang SET kode_barang=?, nama_barang=?, id_kategori=?, jumlah=?, kondisi=?, id_lokasi=? WHERE id_barang=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, b.getKodeBarang());
            ps.setString(2, b.getNamaBarang());
            ps.setInt(3, b.getIdKategori());
            ps.setInt(4, b.getJumlah());
            ps.setString(5, b.getKondisi());
            ps.setInt(6, b.getIdLokasi());
            ps.setInt(7, b.getIdBarang());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE
    public void delete(int idBarang) {
        String sql = "DELETE FROM barang WHERE id_barang=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idBarang);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
