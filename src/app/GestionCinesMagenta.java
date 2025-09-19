package app;

import dao.DatabaseConnection;
import dao.PeliculaDAO;
import controller.MainController;
import service.PeliculaService;
import view.MainFrame;

import javax.swing.*;

public class GestionCinesMagenta {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Prueba la conexión
        boolean ok = DatabaseConnection.databaseTest();
        String msg = ok ? "Conexión a Cine_DB exitosa."
                        : "ERROR conectando a Cine_DB. Revisa credenciales/servicio.";
        System.out.println(msg); //Muestra el mensaje en consola
        
        
        JOptionPane.showMessageDialog(null, msg, "Estado de Conexión",
                ok ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);

        if (!ok) return;

        SwingUtilities.invokeLater(() -> {
            MainFrame view = new MainFrame();
            PeliculaService service = new PeliculaService(new PeliculaDAO());
            new MainController(view, service);
            view.setVisible(true);
        });
    }
}
