package dao;

import java.sql.*;

import model.Pelicula;

public class PeliculaDAO implements IPeliculaDAO {

    @Override
public int create(Pelicula p) throws SQLException {
    final String sql =
        "INSERT INTO `Cine_DB`.`Cartelera` (`titulo`,`director`,`anio`,`duracion`,`genero`) VALUES (?,?,?,?,?)";

    try (Connection cn = DatabaseConnection.get();
         // Opción A: pedir las keys por nombre de columna (más fiable en MariaDB)
         PreparedStatement ps = cn.prepareStatement(sql, new String[] {"id"})) {

        ps.setString(1, p.getTitulo());
        ps.setString(2, p.getDirector());
        ps.setInt(3, p.getAnio());
        ps.setInt(4, p.getDuracion());
        ps.setString(5, p.getGenero().name());

        ps.executeUpdate();

        Integer newId = null;
        try (ResultSet keys = ps.getGeneratedKeys()) {
            if (keys.next()) newId = keys.getInt(1);
        }
        if (newId == null) { // Fallback compatible con MariaDB
            try (Statement st = cn.createStatement();
                 ResultSet rs = st.executeQuery("SELECT LAST_INSERT_ID()")) {
                if (rs.next()) newId = rs.getInt(1);
            }
        }
        if (newId == null) {
            throw new SQLException("No se pudo recuperar el ID generado.");
        }
        return newId;
    }
}
    // En próximas entregas se abordarán el resto de acciones para interactuar con la data
    
}
