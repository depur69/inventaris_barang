package ui;

import dao.BarangDAO;
import dao.KategoriDAO;
import dao.LokasiDAO;
import model.Barang;
import model.Kategori;
import model.Lokasi;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class FormBarang extends JFrame {

    JTextField txtKode, txtNama, txtJumlah;
    JComboBox<Kategori> cbKategori;
    JComboBox<String> cbKondisi;
    JComboBox<Lokasi> cbLokasi;
    JTable table;
    DefaultTableModel model;

    BarangDAO barangDAO = new BarangDAO();
    KategoriDAO kategoriDAO = new KategoriDAO();
    LokasiDAO lokasiDAO = new LokasiDAO();

    List<Barang> listBarang;
    int idBarangTerpilih = 0;

    Color bgMain = new Color(245, 247, 250);
    Color bgPanel = Color.WHITE;
    Color primary = new Color(52, 152, 219);
    Color success = new Color(46, 204, 113);
    Color danger = new Color(231, 76, 60);
    Color warning = new Color(241, 196, 15);

    public FormBarang() {
        setTitle("Sistem Inventaris Barang");
        setSize(850, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(bgMain);
        setLayout(null);

        initComponent();
        loadKategori();
        loadLokasi();
        loadTable();
        actionTable();
    }

    void initComponent() {

        JLabel lblTitle = new JLabel("SISTEM INVENTARIS BARANG", JLabel.CENTER);
        lblTitle.setBounds(0, 10, 850, 35);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(primary);
        add(lblTitle);

        JPanel panelForm = new JPanel(null);
        panelForm.setBackground(bgPanel);
        panelForm.setBounds(20, 60, 300, 330);
        add(panelForm);

        panelForm.add(label("Kode Barang", 20, 20));
        txtKode = field(130, 20);
        panelForm.add(txtKode);

        panelForm.add(label("Nama Barang", 20, 60));
        txtNama = field(130, 60);
        panelForm.add(txtNama);

        panelForm.add(label("Kategori", 20, 100));
        cbKategori = new JComboBox<>();
        cbKategori.setBounds(130, 100, 150, 25);
        panelForm.add(cbKategori);

        panelForm.add(label("Jumlah", 20, 140));
        txtJumlah = field(130, 140);
        panelForm.add(txtJumlah);

        panelForm.add(label("Kondisi", 20, 180));
        cbKondisi = new JComboBox<>(new String[]{"Baik", "Rusak", "Perlu Perbaikan"});
        cbKondisi.setBounds(130, 180, 150, 25);
        panelForm.add(cbKondisi);

        panelForm.add(label("Lokasi", 20, 220));
        cbLokasi = new JComboBox<>();
        cbLokasi.setBounds(130, 220, 150, 25);
        panelForm.add(cbLokasi);

        model = new DefaultTableModel(
                new String[]{"ID", "Kode", "Nama", "Kategori", "Jumlah", "Kondisi", "Lokasi"}, 0
        );
        table = new JTable(model);
        table.setRowHeight(24);
        table.getTableHeader().setBackground(primary);
        table.getTableHeader().setForeground(Color.WHITE);

        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(340, 60, 480, 260);
        add(sp);

        JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelButton.setBackground(bgMain);
        panelButton.setBounds(340, 330, 480, 50);
        add(panelButton);

        JButton btnSimpan = button("Simpan", success);
        JButton btnUpdate = button("Update", primary);
        JButton btnHapus = button("Hapus", danger);
        JButton btnReset = button("Reset", warning);

        panelButton.add(btnSimpan);
        panelButton.add(btnUpdate);
        panelButton.add(btnHapus);
        panelButton.add(btnReset);

        btnSimpan.addActionListener(e -> simpanData());
        btnUpdate.addActionListener(e -> updateData());
        btnHapus.addActionListener(e -> hapusData());
        btnReset.addActionListener(e -> resetForm());
    }

    JLabel label(String text, int x, int y) {
        JLabel lbl = new JLabel(text);
        lbl.setBounds(x, y, 100, 25);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        return lbl;
    }

    JTextField field(int x, int y) {
        JTextField txt = new JTextField();
        txt.setBounds(x, y, 150, 25);
        return txt;
    }

    JButton button(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        return btn;
    }

    void loadKategori() {
        cbKategori.removeAllItems();
        for (Kategori k : kategoriDAO.getAllKategori()) cbKategori.addItem(k);
    }

    void loadLokasi() {
        cbLokasi.removeAllItems();
        for (Lokasi l : lokasiDAO.getAllLokasi()) cbLokasi.addItem(l);
    }

    void loadTable() {
        model.setRowCount(0);
        listBarang = barangDAO.getAllBarang();
        for (Barang b : listBarang) {
            model.addRow(new Object[]{
                    b.getIdBarang(),
                    b.getKodeBarang(),
                    b.getNamaBarang(),
                    b.getNamaKategori(),
                    b.getJumlah(),
                    b.getKondisi(),
                    b.getNamaLokasi()
            });
        }
    }

    void actionTable() {
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row < 0) return;

                Barang b = listBarang.get(row);
                idBarangTerpilih = b.getIdBarang();

                txtKode.setText(b.getKodeBarang());
                txtNama.setText(b.getNamaBarang());
                txtJumlah.setText(String.valueOf(b.getJumlah()));
                cbKondisi.setSelectedItem(b.getKondisi());
                setSelectedKategori(b.getIdKategori());
                setSelectedLokasi(b.getIdLokasi());
            }
        });
    }

    void simpanData() {
        if (txtKode.getText().isEmpty() || txtNama.getText().isEmpty() || txtJumlah.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Data tidak boleh kosong!");
            return;
        }

        int jumlah;
        try {
            jumlah = Integer.parseInt(txtJumlah.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Jumlah harus angka!");
            return;
        }

        Barang b = new Barang();
        b.setKodeBarang(txtKode.getText());
        b.setNamaBarang(txtNama.getText());
        b.setIdKategori(((Kategori) cbKategori.getSelectedItem()).getIdKategori());
        b.setJumlah(jumlah);
        b.setKondisi(cbKondisi.getSelectedItem().toString());
        b.setIdLokasi(((Lokasi) cbLokasi.getSelectedItem()).getIdLokasi());

        barangDAO.insert(b);
        loadTable();
        resetForm();
    }

    void updateData() {
        if (idBarangTerpilih == 0) return;

        int jumlah;
        try {
            jumlah = Integer.parseInt(txtJumlah.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Jumlah harus angka!");
            return;
        }

        Barang b = new Barang();
        b.setIdBarang(idBarangTerpilih);
        b.setKodeBarang(txtKode.getText());
        b.setNamaBarang(txtNama.getText());
        b.setIdKategori(((Kategori) cbKategori.getSelectedItem()).getIdKategori());
        b.setJumlah(jumlah);
        b.setKondisi(cbKondisi.getSelectedItem().toString());
        b.setIdLokasi(((Lokasi) cbLokasi.getSelectedItem()).getIdLokasi());

        barangDAO.update(b);
        loadTable();
        resetForm();
    }

    void hapusData() {
        if (idBarangTerpilih == 0) return;

        int confirm = JOptionPane.showConfirmDialog(this,
                "Yakin ingin menghapus data?",
                "Konfirmasi",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            barangDAO.delete(idBarangTerpilih);
            loadTable();
            resetForm();
        }
    }

    void resetForm() {
        txtKode.setText("");
        txtNama.setText("");
        txtJumlah.setText("");
        cbKondisi.setSelectedIndex(0);
        idBarangTerpilih = 0;
    }

    void setSelectedKategori(int idKategori) {
        for (int i = 0; i < cbKategori.getItemCount(); i++)
            if (cbKategori.getItemAt(i).getIdKategori() == idKategori)
                cbKategori.setSelectedIndex(i);
    }

    void setSelectedLokasi(int idLokasi) {
        for (int i = 0; i < cbLokasi.getItemCount(); i++)
            if (cbLokasi.getItemAt(i).getIdLokasi() == idLokasi)
                cbLokasi.setSelectedIndex(i);
    }

    public static void main(String[] args) {
        new FormBarang().setVisible(true);
    }
}
