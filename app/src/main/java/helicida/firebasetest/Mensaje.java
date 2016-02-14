package helicida.firebasetest;

/**
 * Created by 46465442z on 08/02/16.
 */
public class Mensaje {

    private String titulo;
    private String descripcion;
    private String rutaImagen;
    private double latitud;
    private double longitud;

    public Mensaje() {

    }

    public Mensaje(String titulo, String descripcion, String rutaImagen, double latitud, double longitud) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.rutaImagen = rutaImagen;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    // Getters

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    // Setters

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
}
