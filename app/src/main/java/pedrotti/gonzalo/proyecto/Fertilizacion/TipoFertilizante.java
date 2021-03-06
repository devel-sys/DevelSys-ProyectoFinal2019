package pedrotti.gonzalo.proyecto.Fertilizacion;

class TipoFertilizante {

    private String nombre;
    private String descripcion;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TipoFertilizante(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public TipoFertilizante() {
    }

    @Override
    public String toString() {
        return nombre;
    }
}
