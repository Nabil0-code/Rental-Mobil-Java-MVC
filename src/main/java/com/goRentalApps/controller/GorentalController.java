package main.java.com.goRentalApps.controller;


import javax.swing.JOptionPane;
import main.java.com.goRentalApps.entity.Penyewaan;
import main.java.com.goRentalApps.model.GorentalModel;
import main.java.com.goRentalApps.view.Pelanggan;
import main.java.com.goRentalApps.view.TambahPelangganView;

public class GorentalController {
    
    private final GorentalModel model;
    private final TambahPelangganView view;
    // Jika null, berarti mode tambah. Jika terisi, berarti mode ubah.
    private Integer idDiedit = null;
    

    public GorentalController(GorentalModel model, TambahPelangganView view) {
        this.model = model;
        this.view = view;
        initView();
        this.view.getjJenis().addActionListener(e -> updatejPilihComboBox());
        this.view.getjSubmit().addActionListener(e -> prosesSubmit());
    }
    
    private void initView(){
        String[] kategori = model.getjJenis();
        view.getjJenis().setModel(new javax.swing.DefaultComboBoxModel<>(kategori));
        updatejPilihComboBox();
    }
    
    private void updatejPilihComboBox(){
        String selectedKategori = (String) view.getjJenis().getSelectedItem();
        if (selectedKategori != null) {
            String[] merks = model.getjPilih(selectedKategori);
            if (merks != null) {
                view.updatejPilihComboBox(merks);
            }
        }
    }
    // Tambahkan method baru ini
    public void prosesHapus(Integer idSewa) {
        try {
            model.hapusPenyewaan(idSewa);
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus.");

            // Kembali ke form utama dengan data kosong
            view.resetForm(); // Kita akan buat method ini
            view.setVisible(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal menghapus data:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    //tambah method ubah
    public void prosesMulaiUbah(Integer idSewa) {
        try {
            // 1. Set penanda bahwa kita sedang dalam mode edit
            this.idDiedit = idSewa;

            // 2. Ambil data lama dari database melalui model
            Penyewaan dataLama = model.ambilDataPenyewaan(idSewa);

            // 3. Masukkan data lama ke dalam form input
            view.getjNama().setText(dataLama.getNamaPelanggan());
            view.getjNik().setText(dataLama.getNik());
            view.getjHp().setText(dataLama.getNoHp());
            view.getjJenis().setSelectedItem(dataLama.getJenisKendaraan());
            view.getjPilih().setSelectedItem(dataLama.getMerkKendaraan());
            view.getjLama().setSelectedItem(dataLama.getLamaSewa() + " Hari");

            // 4. Tampilkan kembali form input yang sudah terisi
            view.setVisible(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Gagal mengambil data untuk diubah:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void prosesSubmit() {
        // 1. Kumpulkan semua data dari View
        String nama = view.getjNama().getText();
        String nik = view.getjNik().getText();
        String noHp = view.getjHp().getText();
        String jenis = (String) view.getjJenis().getSelectedItem();
        String kendaraan = (String) view.getjPilih().getSelectedItem();
        String lamaSewa = (String) view.getjLama().getSelectedItem();

        if (nama.isEmpty() || nik.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Nama dan NIK wajib diisi!", "Input Tidak Lengkap", JOptionPane.WARNING_MESSAGE);
            return;
        }
        // 2. Lakukan perhitungan biaya
        int hargaPerHari = model.getHargaPerHari(jenis);
        int jumlahHari = 1; // Nilai default
        try {
            String hariStr = lamaSewa.split(" ")[0];
            jumlahHari = Integer.parseInt(hariStr);
        } catch (Exception e) {
            System.err.println("Gagal mem-parsing jumlah hari: " + e.getMessage());
        }
        int totalBiaya = hargaPerHari * jumlahHari;

        // 3. Buat objek Entity Penyewaan
        Penyewaan penyewaan = new Penyewaan();
        penyewaan.setNamaPelanggan(nama);
        penyewaan.setNik(nik);
        penyewaan.setNoHp(noHp);
        penyewaan.setJenisKendaraan(jenis);
        penyewaan.setMerkKendaraan(kendaraan);
        penyewaan.setLamaSewa(jumlahHari);
        penyewaan.setTotalBiaya(totalBiaya);

        try {
            // Cek apakah sedang dalam mode ubah atau mode tambah 
            if (idDiedit == null) {
                // JIKA MODE TAMBAH (karena idDiedit kosong)
                Integer idBaru = model.simpanPenyewaan(penyewaan);
                tampilkanStruk(penyewaan, idBaru);
                view.setVisible(false);
            } else {
                // JIKA MODE UBAH (karena idDiedit ada isinya)
                penyewaan.setIdSewa(idDiedit); // Set ID yang akan diupdate
                model.ubahPenyewaan(penyewaan);
                JOptionPane.showMessageDialog(view, "Data berhasil diubah.");
                tampilkanStruk(penyewaan, idDiedit); // Tampilkan struk hasil editan
                view.setVisible(false);
            }

            //Reset penanda setelah proses selesai
            idDiedit = null;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Terjadi error:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // METHOD YANG DIPERBAIKI
    private void tampilkanStruk(Penyewaan penyewaan, Integer id) {
        String nama = penyewaan.getNamaPelanggan();
        String nik = penyewaan.getNik();
        String noHp = penyewaan.getNoHp();
        String jenis = penyewaan.getJenisKendaraan();
        String kendaraan = penyewaan.getMerkKendaraan();
        int totalBiaya = penyewaan.getTotalBiaya();
        int lamaSewaInt = penyewaan.getLamaSewa();

        int hargaPerHari = 0;
        if (lamaSewaInt > 0) {
            hargaPerHari = totalBiaya / lamaSewaInt;
        }

        StringBuilder strukBuilder = new StringBuilder();
        strukBuilder.append("======= STRUK GORENTAL =======\n\n");
        strukBuilder.append("Nama Pelanggan  : ").append(nama).append("\n");
        strukBuilder.append("NIK             : ").append(nik).append("\n");
        strukBuilder.append("No. HP          : ").append(noHp).append("\n\n");
        strukBuilder.append("--------------------------------\n");
        strukBuilder.append("DETAIL SEWA\n");
        strukBuilder.append("--------------------------------\n");
        strukBuilder.append("Jenis Kendaraan : ").append(jenis).append("\n");
        strukBuilder.append("Kendaraan Dipilih: ").append(kendaraan).append("\n");
        strukBuilder.append("Harga per Hari  : Rp ").append(hargaPerHari).append("\n");
        strukBuilder.append("Lama Sewa       : ").append(lamaSewaInt).append(" Hari\n\n");
        strukBuilder.append("--------------------------------\n");
        strukBuilder.append("TOTAL BIAYA     : Rp ").append(totalBiaya).append("\n");
        strukBuilder.append("==============================\n\n");
        strukBuilder.append("Terima kasih telah memilih kami!\n");

        Pelanggan strukView = new Pelanggan(this, id);
        strukView.tampilkanStruk(strukBuilder.toString());
        strukView.setVisible(true);
    }
}