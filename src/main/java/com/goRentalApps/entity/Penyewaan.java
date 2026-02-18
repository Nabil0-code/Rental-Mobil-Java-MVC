package main.java.com.goRentalApps.entity;


public class Penyewaan {
    private Integer idSewa;
    private String namaPelanggan;
    private String nik;
    private String noHp;
    private String jenisKendaraan;
    private String merkKendaraan;
    private Integer lamaSewa;
    private Integer totalBiaya;

    public Integer getIdSewa() {
        return idSewa;
    }

    public void setIdSewa(Integer idSewa) {
        this.idSewa = idSewa;
    }

    public String getNamaPelanggan() {
        return namaPelanggan;
    }

    public void setNamaPelanggan(String namaPelanggan) {
        this.namaPelanggan = namaPelanggan;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public String getJenisKendaraan() {
        return jenisKendaraan;
    }

    public void setJenisKendaraan(String jenisKendaraan) {
        this.jenisKendaraan = jenisKendaraan;
    }

    public String getMerkKendaraan() {
        return merkKendaraan;
    }

    public void setMerkKendaraan(String merkKendaraan) {
        this.merkKendaraan = merkKendaraan;
    }

    public Integer getLamaSewa() {
        return lamaSewa;
    }

    public void setLamaSewa(Integer lamaSewa) {
        this.lamaSewa = lamaSewa;
    }

    public Integer getTotalBiaya() {
        return totalBiaya;
    }

    public void setTotalBiaya(Integer totalBiaya) {
        this.totalBiaya = totalBiaya;
    }
}
