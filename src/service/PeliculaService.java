package service;

import dao.PeliculaDAO;
import java.time.Year;
import model.Pelicula;

/**
 * Servicio de aplicación para la gestión de películas
 * 
 * Responsabilidades:
 * - Implementar la lógica de negocio
 * - Validar datos antes de persistir
 * - Coordinar operaciones con el DAO
 * - Manejar excepciones de negocio
 * 
 */

public class PeliculaService {

    private final PeliculaDAO dao; // DAO para operaciones de persistencia

    /**
     * Constructor que inyecta el DAO
     * 
     * @param dao Implementación de PeliculaDAO
     */
    public PeliculaService(PeliculaDAO dao) {
        this.dao = dao; 
    }

    /**
     * Crea una nueva película en la base de datos con validaciones de negocio
     * 
     * Validaciones implementadas:
     * -Título no nulo y no vacío
     * - Director no nulo y no vacío
     * - Año dentro de rango válido (1900 - año actual +1)
     * - Duración dentro de rango válido (1-999 minutos)
     * - Prevención de duplicados (título + año)
     * 
     * @param p Película a crear
     * @return int ID generado por la base de datos
     * @throws Exception Si falla validación o persistencia
     */
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
            // Delegar la persistencia al DAO
            int id = dao.create(p);
            p.setId(id);
            return id;
        } catch (java.sql.SQLIntegrityConstraintViolationException dup) {
            // Manejar violación del constraint única (título + año)
            throw new IllegalArgumentException("Ya existe una película con el mismo TÍTULO y AÑO.");
        }
    }

}
