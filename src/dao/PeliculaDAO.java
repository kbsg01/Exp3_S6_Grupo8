package dao;

import java.sql.*;
import model.Pelicula;

/**
 * Implementación concreta del DAO para la entidad Película
 * 
 * Responsabilidades:
 * - Implementar las operaciones CRUD para películas
 * - Manejar la persistencia en base de datos MySQL
 * - Gestionar transacciones y recursos JDBC
 * - Recuperar IDs auto-generados
 * 
 * Implementa: IPeliculaDAO
 * Utiliza: DatabaseConnection para obtener conexiones
 * 
 */

public class PeliculaDAO implements IPeliculaDAO {
    
    /**
     * Crea una nueva película en la base de datos
     * 
     * Características:
     * - Usa PreparedStatement para prevenir SQL injection
     * - Recupera el ID auto-generado
     * - Incluye fallback para compatibilidad con MariaDB
     * - Usa try-with-resources para manejo automático de recursos
     * 
     * @param p Película a persistir
     * @return int ID generado por la base de datos
     * @throws SQLException Si ocurre error en la operación de base de datos
     */

    @Override
    public int create(Pelicula p) throws SQLException {
    final String sql =
        "INSERT INTO `Cine_DB`.`Cartelera` (`titulo`,`director`,`anio`,`duracion`,`genero`) VALUES (?,?,?,?,?)";

    try (Connection cn = DatabaseConnection.get();
         // Opción A: pedir las keys por nombre de columna (más fiable en MariaDB)
         PreparedStatement ps = cn.prepareStatement(sql, new String[] {"id"})) {

        // Establece parámetros del PreparedStatement
        ps.setString(1, p.getTitulo());
        ps.setString(2, p.getDirector());
        ps.setInt(3, p.getAnio());
        ps.setInt(4, p.getDuracion());
        ps.setString(5, p.getGenero().name());
        
        // Ejecuta la inserción
        ps.executeUpdate();

        // Intenta recuperar el ID auto-generado
        Integer newId = null;
        try (ResultSet keys = ps.getGeneratedKeys()) {
            if (keys.next()) newId = keys.getInt(1);
        }
        
        // Fallback compatible con MariaDB
        if (newId == null) { // Fallback compatible con MariaDB
            try (Statement st = cn.createStatement();
                 ResultSet rs = st.executeQuery("SELECT LAST_INSERT_ID()")) {
                if (rs.next()) newId = rs.getInt(1);
            }
        }
        
        // Verifica que se obtuvo un ID válido
        if (newId == null) {
            throw new SQLException("No se pudo recuperar el ID generado.");
        }
        return newId;
    }
}
    // En próximas entregas se abordarán el resto de acciones para interactuar con la data
    
}
