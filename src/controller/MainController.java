package controller;

import java.util.concurrent.atomic.AtomicBoolean;
import model.*;
import service.*;
import view.*;
import javax.swing.*;

/**
 * Controlador principal que coordina las interacciones entre la vista y los servicios
 * 
 * Responsabilidades:
 * - Gestionar eventos de la interfaz de usuario
 * - Validar datos antes de enviarlos al servicio
 * - Coordinar el flujo entre la vista y la capa de negocio
 * - Mostrar mensajes de feedback al usuario
 * 
 * Patrón: Controller en arquitectura MVC
 */

public class MainController {

    private final MainFrame view;           // Referencia a la vista principal
    private final PeliculaService service;  // Referencia al servicio de negocio
    private final AtomicBoolean saving = new AtomicBoolean(false);  // Control de concurrencia

    /**
     * Constructor del controlador principal
     * 
     * @param view Vista principal de la aplicación
     * @param service Servicio de gestión de películas
     */
    public MainController(MainFrame view, PeliculaService service) {
        this.view = view;
        this.service = service;
        bind(); // Configura los listeners de eventos
    }
    
    /**
     * Configura los listeners para los eventos de la interfaz
     * Vincula los componentes de UI con sus respectivos manejadores
     */
    private void bind() {
        // “Agregar” solo muestra el formulario (ya visible) y guarda
        PeliculaFormPanel form = view.getFormPanel();
        view.getBtnAgregar().addActionListener(e -> form.requestFocusInWindow());
        
        // El botón "Guardar" ejecuta la acción de guardado
        form.getBtnGuardar().addActionListener(e -> onSave());
    }
    
    /**
     * Maneja el evento de guardado de una película
     * 
     * Flujo:
     * 1. Obtiene datos del formulario
     * 2. Convierte y valida los datos
     * 3. Invoca el servicio para persistir
     * 4. Muestra feedback al usuario
     * 5. Limpia el formulario en caso de éxito
     */

    private void onSave() {
        PeliculaFormPanel form = view.getFormPanel();
        try {
            // Convierte el género de String a Enum
            Genero genero = Genero.valueOf(form.getGenero());
            
            // Crea el objeto Pelicula con los datos del formulario
            Pelicula p = new Pelicula(
                    form.getTitulo(),
                    form.getDirector(),
                    form.getAnio(),
                    form.getDuracion(),
                    genero
            );
            
            // Persiste la película mediante el servicio
            int id = service.add(p);
            
            // Muestra mensaje de éxito con el ID generado
            JOptionPane.showMessageDialog(view, "Película guardada (ID: " + id + ")", "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            
            // Limpia el formulario para nueva entrada
            form.clear();

        } catch (IllegalArgumentException ex) { // valueOf falló
            // Error de conversión de género
            JOptionPane.showMessageDialog(view, "Género inválido.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            // Libera el flag de guardado
            saving.set(false);
        }
    }
}
