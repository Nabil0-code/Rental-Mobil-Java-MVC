package main.java.com.goRentalApps.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import main.java.com.goRentalApps.database.KoneksiDB;
import main.java.com.goRentalApps.entity.Penyewaan;
import main.java.com.goRentalApps.exception.PenyewaanException;
import main.java.com.goRentalApps.service.PenyewaanDao;

public class PenyewaanDaoImpl implements PenyewaanDao {
    private final Connection connection;

    public PenyewaanDaoImpl() throws SQLException {
        this.connection = KoneksiDB.getConnection();
    }

    @Override
    public Integer insertPenyewaan(Penyewaan penyewaan) throws PenyewaanException {
        String sql = "INSERT INTO penyewaan (nama_pelanggan, nik, no_hp, jenis_kendaraan, merk_kendaraan, lama_sewa, total_biaya) VALUES (?, ?, ?, ?, ?, ?, ?)";

        // menambahkan parameter kedua untuk mengambil ID
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false);

            statement.setString(1, penyewaan.getNamaPelanggan());
            statement.setString(2, penyewaan.getNik());
            statement.setString(3, penyewaan.getNoHp());
            statement.setString(4, penyewaan.getJenisKendaraan());
            statement.setString(5, penyewaan.getMerkKendaraan());
            statement.setInt(6, penyewaan.getLamaSewa());
            statement.setInt(7, penyewaan.getTotalBiaya());

            statement.executeUpdate();

            // BARU: Kode untuk mengambil ID yang baru saja dibuat oleh MySQL
            try (java.sql.ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    // Set ID ke objek penyewaan
                    penyewaan.setIdSewa(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Gagal membuat data penyewaan, tidak ada ID yang didapat.");
                }
            }

            connection.commit();

            // MODIFIKASI: Kembalikan ID yang baru didapat
            return penyewaan.getIdSewa();

        } catch (SQLException e) {
            throw new PenyewaanException("Gagal menyimpan data: " + e.getMessage());
        }
    }
    @Override
    public void deletePenyewaan(Integer idSewa) throws PenyewaanException {
        String sql = "DELETE FROM penyewaan WHERE id_sewa = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);
            statement.setInt(1, idSewa);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            try { connection.rollback(); } catch (SQLException ex) {}
            throw new PenyewaanException("Gagal menghapus data dari database: " + e.getMessage());
        } finally {
            try { connection.setAutoCommit(true); } catch (SQLException ex) {}
        }
    }
    //Implementasi untuk mengambil data berdasarkan ID
    @Override
    public Penyewaan getPenyewaanById(Integer idSewa) throws PenyewaanException {
        String sql = "SELECT * FROM penyewaan WHERE id_sewa = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idSewa);
            
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    Penyewaan penyewaan = new Penyewaan();
                    penyewaan.setIdSewa(result.getInt("id_sewa"));
                    penyewaan.setNamaPelanggan(result.getString("nama_pelanggan"));
                    penyewaan.setNik(result.getString("nik"));
                    penyewaan.setNoHp(result.getString("no_hp"));
                    penyewaan.setJenisKendaraan(result.getString("jenis_kendaraan"));
                    penyewaan.setMerkKendaraan(result.getString("merk_kendaraan"));
                    penyewaan.setLamaSewa(result.getInt("lama_sewa"));
                    penyewaan.setTotalBiaya(result.getInt("total_biaya"));
                    return penyewaan;
                }
            }
            throw new PenyewaanException("Data penyewaan dengan ID " + idSewa + " tidak ditemukan");
        } catch (SQLException e) {
            throw new PenyewaanException(e.getMessage());
        }
    }

    //Implementasi untuk mengubah data
    @Override
    public void updatePenyewaan(Penyewaan penyewaan) throws PenyewaanException {
        String sql = "UPDATE penyewaan SET nama_pelanggan=?, nik=?, no_hp=?, jenis_kendaraan=?, merk_kendaraan=?, lama_sewa=?, total_biaya=? WHERE id_sewa=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);
            statement.setString(1, penyewaan.getNamaPelanggan());
            statement.setString(2, penyewaan.getNik());
            statement.setString(3, penyewaan.getNoHp());
            statement.setString(4, penyewaan.getJenisKendaraan());
            statement.setString(5, penyewaan.getMerkKendaraan());
            statement.setInt(6, penyewaan.getLamaSewa());
            statement.setInt(7, penyewaan.getTotalBiaya());
            statement.setInt(8, penyewaan.getIdSewa()); 
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            try { connection.rollback(); } catch (SQLException ex) {}
            throw new PenyewaanException("Gagal mengubah data: " + e.getMessage());
        } finally {
            try { connection.setAutoCommit(true); } catch (SQLException ex) {}
        }
    }
}