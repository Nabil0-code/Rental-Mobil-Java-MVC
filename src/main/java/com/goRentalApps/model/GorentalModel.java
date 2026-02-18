package main.java.com.goRentalApps.model;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import main.java.com.goRentalApps.entity.Penyewaan;
import main.java.com.goRentalApps.exception.PenyewaanException;
import main.java.com.goRentalApps.impl.PenyewaanDaoImpl;
import main.java.com.goRentalApps.service.PenyewaanDao;

public class GorentalModel {
    //properties untuk pelanggan
    private String Nama;
    private String Nik;
    private String NoHp;
    private final Map<String, String[]> dataMap;
    private final Map<String, Integer> hargaMap;
    
    public GorentalModel() {
        // Inisialisasi data di sini
        dataMap = new HashMap<>();
        dataMap.put("Motor", new String[]{"Nmax", "XSR 155", "ZX 25R", "R6", });
        dataMap.put("Mobil", new String[]{"Pajero", "Fortuner", "Civic Turbo","Mazda 3",});
        // Data harga per hari
        hargaMap = new HashMap<>();
        hargaMap.put("Motor", 50000);
        hargaMap.put("Mobil", 250000);
    }
    public Integer simpanPenyewaan(Penyewaan penyewaan) throws SQLException, PenyewaanException {
        PenyewaanDao dao = new PenyewaanDaoImpl();
        return dao.insertPenyewaan(penyewaan); // Kembalikan ID yang didapat dari DAO
    }
    // Tambahkan method baru untuk memanggil DAO
    public void hapusPenyewaan(Integer idSewa) throws SQLException, PenyewaanException {
        PenyewaanDao dao = new PenyewaanDaoImpl();
        dao.deletePenyewaan(idSewa);
    }
    // Tambahkan dua method baru ini di dalam kelas GorentalModel
    public Penyewaan ambilDataPenyewaan(Integer idSewa) throws SQLException, PenyewaanException {
        PenyewaanDao dao = new PenyewaanDaoImpl();
        return dao.getPenyewaanById(idSewa);
    }

    public void ubahPenyewaan(Penyewaan penyewaan) throws SQLException, PenyewaanException {
        PenyewaanDao dao = new PenyewaanDaoImpl();
        dao.updatePenyewaan(penyewaan);
    }


    public int getHargaPerHari(String kategori) {
        // Mengembalikan harga berdasarkan kategori, atau 0 jika tidak ada
        return hargaMap.getOrDefault(kategori, 0);
    }

    // Fungsi untuk mendapatkan semua kategori
    public String[] getjJenis() {
        Set<String> keys = dataMap.keySet();
        return keys.toArray(new String[0]);
    }

    
    public String[] getjPilih(String merk) {
        return dataMap.get(merk);
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String Nama) {
        this.Nama = Nama;
    }

    public String getNik() {
        return Nik;
    }

    public void setNik(String Nik) {
        this.Nik = Nik;
    }

    public String getNoHp() {
        return NoHp;
    }

    public void setNoHp(String NoHp) {
        this.NoHp = NoHp;
    }
    
}
