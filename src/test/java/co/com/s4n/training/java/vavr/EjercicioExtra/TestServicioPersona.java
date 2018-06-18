package co.com.s4n.training.java.vavr.EjercicioExtra;

import io.vavr.control.Option;
import org.junit.Test;


import static co.com.s4n.training.java.vavr.EjercicioExtra.ServicioPersona.*;
import static io.vavr.API.Some;
import static org.junit.Assert.*;


public class TestServicioPersona {

    public static final Persona MUJER = new Persona(29,"Juliana","33333",TiposEstadoCivil.Soltero,20000000,Sexo.FEMENINO);
    public static final Persona HOMBRE = new Persona(35,"Julian","333343",TiposEstadoCivil.Soltero,20000000,Sexo.MASCULINO);
    public static final Persona OTRO = new Persona(35,"Raul","333343",TiposEstadoCivil.Soltero,200000030,Sexo.OTRO);


    @Test
    public void testAdoptarNiniosParejaHetero(){
        Option<Persona> resultado = adoptarNinios(MUJER,HOMBRE);
        Persona ninio = new Persona(0,"Miguel","399393",TiposEstadoCivil.Soltero,0,Sexo.MASCULINO);
        assertEquals(resultado.getOrElse(()->null),ninio);
    }

}
