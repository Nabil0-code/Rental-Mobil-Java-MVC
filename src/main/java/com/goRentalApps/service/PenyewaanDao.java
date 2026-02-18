package main.java.com.goRentalApps.service;


import main.java.com.goRentalApps.entity.Penyewaan;
import main.java.com.goRentalApps.exception.PenyewaanException;

public interface PenyewaanDao {
    //menyimpan data penyewaan
    public Integer insertPenyewaan(Penyewaan penyewaan) throws PenyewaanException;
    public void deletePenyewaan(Integer idSewa) throws PenyewaanException;
    //Method untuk mengambil satu data berdasarkan ID
    Penyewaan getPenyewaanById(Integer idSewa) throws PenyewaanException;
    //Method untuk mengubah data
    void updatePenyewaan(Penyewaan penyewaan) throws PenyewaanException;
}
