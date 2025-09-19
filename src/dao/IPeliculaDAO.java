package dao;

import java.util.List;
import model.Pelicula;
import java.sql.SQLException;

public interface IPeliculaDAO {

    int create(Pelicula p) throws SQLException;
    
// Funcionalidades a extender en próximas entregas
    //Pelicula findById(int id) throws SQLException;
    //List<Pelicula> findAll() throws SQLException;
    //List<Pelicula> findByTitleLike(String query) throws SQLException;
    //void update(Pelicula p) throws SQLException;
    //void delete(int id) throws SQLException;
    
}
