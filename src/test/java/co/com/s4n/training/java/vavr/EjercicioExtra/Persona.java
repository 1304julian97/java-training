package co.com.s4n.training.java.vavr.EjercicioExtra;

import lombok.AccessLevel;
import lombok.Getter;

public class Persona {


    @Getter
    public int edad;

    @Getter
    private String nombre;

    @Getter
    private String identificacion;

    @Getter
    private TiposEstadoCivil estadoCivil;

    @Getter
    private int patrimonio;

    @Getter
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
