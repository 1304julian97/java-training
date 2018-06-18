package co.com.s4n.training.java.vavr.EjercicioExtra;

public class Persona {

    private int edad;
    private String nombre;
    private String identificacion;
    private TiposEstadoCivil estadoCivil;
    private int patrimonio;
    private Sexo sexo;


    public Persona(int edad, String nombre, String identificacion, TiposEstadoCivil estadoCivil, int patrimonio, Sexo sexo){
        this.edad = edad;
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.estadoCivil = estadoCivil;
        this.patrimonio = patrimonio;
        this.sexo = sexo;
    }


    public Sexo sexo(){
        return this.sexo;
    }

    public int edad(){
        return this.edad;
    }

    public String nombre(){
        return this.nombre;
    }

    public String identificacion(){
        return this.identificacion;
    }

    public TiposEstadoCivil estadoCivil(){
        return this.estadoCivil;
    }

    public int patrimonio(){
        return this.patrimonio;
    }

}
