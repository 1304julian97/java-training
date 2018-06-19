package co.com.s4n.training.java.vavr.EjercicioExtra;

import lombok.AccessLevel;
import lombok.Getter;

public class Persona {



    public int edad;


    private String nombre;


    private String identificacion;


    private TiposEstadoCivil estadoCivil;


    private int patrimonio;

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public TiposEstadoCivil getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(TiposEstadoCivil estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public int getPatrimonio() {
        return patrimonio;
    }

    public void setPatrimonio(int patrimonio) {
        this.patrimonio = patrimonio;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    private Sexo sexo;


    public Persona(int edad, String nombre, String identificacion, TiposEstadoCivil estadoCivil, int patrimonio, Sexo sexo){
        this.edad = edad;
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.estadoCivil = estadoCivil;
        this.patrimonio = patrimonio;
        this.sexo = sexo;
    }



}
