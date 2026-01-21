CREATE DATABASE inventaris_db;
USE inventaris_db;

CREATE TABLE kategori (
    id_kategori INT AUTO_INCREMENT PRIMARY KEY,
    nama_kategori VARCHAR(100) NOT NULL
);

CREATE TABLE lokasi (
    id_lokasi INT AUTO_INCREMENT PRIMARY KEY,
    nama_lokasi VARCHAR(100) NOT NULL
);

CREATE TABLE barang (
    id_barang INT AUTO_INCREMENT PRIMARY KEY,
    kode_barang VARCHAR(50) NOT NULL,
    nama_barang VARCHAR(100) NOT NULL,
    id_kategori INT,
    jumlah INT,
    kondisi VARCHAR(50),
    id_lokasi INT,
    FOREIGN KEY (id_kategori) REFERENCES kategori(id_kategori),
    FOREIGN KEY (id_lokasi) REFERENCES lokasi(id_lokasi)
);

INSERT INTO kategori (nama_kategori) VALUES
('Elektronik'),
('ATK'),
('Furniture'),
('Perangkat Komputer'),
('Peralatan Kantor');

INSERT INTO lokasi (nama_lokasi) VALUES
('Gudang'),
('Ruang Administrasi'),
('Ruang IT'),
('Ruang Meeting'),
('Ruang Arsip');