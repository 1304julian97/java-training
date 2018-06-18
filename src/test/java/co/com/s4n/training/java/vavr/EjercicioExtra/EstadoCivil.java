package co.com.s4n.training.java.vavr.EjercicioExtra;

enum TiposEstadoCivil {

    Soltero("Soltero"),Casado("Casado"),Divorciado("Divorciado");

    private String estado;

    private TiposEstadoCivil (String estadoCivil){
        this.estado = estadoCivil;
    }

    public String estadoCivilNombre() {
        return this.estado;
    }

}
