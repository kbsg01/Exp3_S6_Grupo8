package model;

public enum Genero {
    // Enum con generos
    Accion, Drama, Comedia, Terror, Aventura, Ciencia_Ficcion, Romance, Thriller;
    
    // Obtiene valores del enum
    public static String[] names(){
        Genero[] vals = values();
        String[] out = new String[vals.length];
        for (int i = 0; i < vals.length; i++) out[i] = vals[i].name();
        return out;
    }
    
}
