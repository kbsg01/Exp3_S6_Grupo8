package service;

import dao.PeliculaDAO;
import java.time.Year;
import model.Pelicula;

public class PeliculaService {

    private final PeliculaDAO dao;

    public PeliculaService(PeliculaDAO dao) {
        this.dao = dao;
    }

    //Crear pelicula en BD
    public int add(Pelicula p) throws Exception {
        // Validaciones de negocio (no en la vista)
        if (p.getTitulo() == null || p.getTitulo().isBlank()) {
            throw new IllegalArgumentException("El título es obligatorio.");
        }
        if (p.getDirector() == null || p.getDirector().isBlank()) {
            throw new IllegalArgumentException("El director es obligatorio.");
        }
        int currentMax = Year.now().getValue() + 1;
        if (p.getAnio() < 1900 || p.getAnio() > currentMax) {
            throw new IllegalArgumentException("El año debe estar entre 1900 y " + currentMax + ".");
        }
        if (p.getDuracion() < 1 || p.getDuracion() > 999) {
            throw new IllegalArgumentException("La duración debe estar entre 1 y 999.");
        }

        try {
            int id = dao.create(p);
            p.setId(id);
            return id;
        } catch (java.sql.SQLIntegrityConstraintViolationException dup) {
            throw new IllegalArgumentException("Ya existe una película con el mismo TÍTULO y AÑO.");
        }
    }

}
