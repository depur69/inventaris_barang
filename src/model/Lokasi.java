package model;

public class Lokasi {

    private int idLokasi;
    private String namaLokasi;

    public Lokasi() {}

    public Lokasi(int idLokasi, String namaLokasi) {
        this.idLokasi = idLokasi;
        this.namaLokasi = namaLokasi;
    }

    public int getIdLokasi() {
        return idLokasi;
    }

    public void setIdLokasi(int idLokasi) {
        this.idLokasi = idLokasi;
    }

    public String getNamaLokasi() {
        return namaLokasi;
    }

    public void setNamaLokasi(String namaLokasi) {
        this.namaLokasi = namaLokasi;
    }

    @Override
    public String toString() {
        return namaLokasi;
    }
}
