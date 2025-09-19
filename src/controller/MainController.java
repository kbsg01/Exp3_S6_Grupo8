package controller;

import java.util.concurrent.atomic.AtomicBoolean;
import model.*;
import service.*;
import view.*;

import javax.swing.*;

public class MainController {

    private final MainFrame view;
    private final PeliculaService service;
    private final AtomicBoolean saving = new AtomicBoolean(false);

    public MainController(MainFrame view, PeliculaService service) {
        this.view = view;
        this.service = service;
        bind();
    }

    private void bind() {
        // “Agregar” solo muestra el formulario (ya visible) y guarda
        PeliculaFormPanel form = view.getFormPanel();
        view.getBtnAgregar().addActionListener(e -> form.requestFocusInWindow());
        form.getBtnGuardar().addActionListener(e -> onSave());
    }

    private void onSave() {
        PeliculaFormPanel form = view.getFormPanel();
        try {
            Genero genero = Genero.valueOf(form.getGenero());
            Pelicula p = new Pelicula(
                    form.getTitulo(),
                    form.getDirector(),
                    form.getAnio(),
                    form.getDuracion(),
                    genero
            );
            int id = service.add(p);
            JOptionPane.showMessageDialog(view, "Película guardada (ID: " + id + ")", "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            form.clear();

        } catch (IllegalArgumentException ex) { // valueOf falló
            JOptionPane.showMessageDialog(view, "Género inválido.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            saving.set(false);
        }
    }
}
