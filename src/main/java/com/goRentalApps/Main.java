package main.java.com.goRentalApps; 

import javax.swing.SwingUtilities;
import main.java.com.goRentalApps.model.GorentalModel;
import main.java.com.goRentalApps.view.TambahPelangganView;
import main.java.com.goRentalApps.controller.GorentalController;

public class Main {
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // 1. Buat object Model untuk menyimpan data
                GorentalModel model = new GorentalModel();
                
                // 2. Buat object View untuk menampilkan GUI
                TambahPelangganView view = new TambahPelangganView();
                
                // 3. Buat object Controller untuk menjadi jembatan
                //    Controller akan mengambil data dari model dan mengatur view
                GorentalController controller = new GorentalController(model, view);
                
                // 4. Tampilkan aplikasinya
                view.setVisible(true);
            }
        });
    }
}