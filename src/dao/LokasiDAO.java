package dao;

import config.Koneksi;
import model.Lokasi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LokasiDAO {

    private Connection conn;

    public LokasiDAO() {
        conn = Koneksi.getConnection();
    }

    public List<Lokasi> getAllLokasi() {
        List<Lokasi> list = new ArrayList<>();
        String sql = "SELECT * FROM lokasi";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Lokasi l = new Lokasi();
                l.setIdLokasi(rs.getInt("id_lokasi"));
                l.setNamaLokasi(rs.getString("nama_lokasi"));
                list.add(l);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
