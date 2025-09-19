package model;

public class Pelicula {
    //Atributos
    private Integer id; // autoincremental
    private String titulo;
    private String director;
    private int anio;
    private int duracion;
    private Genero genero;
    
    //Constructores
    public Pelicula(Integer id, String titulo, String director, int anio, int duracion, Genero genero){
        this.id = id;
        this.titulo = titulo;
        this.director = director;
        this.anio = anio;
        this.duracion = duracion;
        this.genero = genero;
    }
    
    public Pelicula(String titulo, String director, int anio, int duracion, Genero genero) {
        this(null, titulo, director, anio, duracion, genero);
    }

    //Getters
    public Integer getId() { return id; }

    public String getTitulo() { return titulo; }
    
    public String getDirector() { return director; }

    public int getAnio() { return anio; }
    
    public int getDuracion() { return duracion; }

    public Genero getGenero() { return genero; }

        
    //Setters
    public void setId(Integer id) { this.id = id; }

    public void setTitulo(String titulo) { this.titulo = titulo; }

    public void setDirector(String director) { this.director = director; }

    public void setAnio(int anio) { this.anio = anio; }

    public void setDuracion(int duracion) { this.duracion = duracion; }

    public void setGenero(Genero genero) { this.genero = genero; }
}
