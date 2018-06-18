package co.com.s4n.training.java.vavr.EjercicioExtra;


import io.vavr.control.Option;
import io.vavr.control.Try;

public final class ServicioPersona {

    @FunctionalInterface
    interface  IparejaDePersona {boolean parejaValida(Persona persona, Persona persona1);}


    public static Option<Persona> adoptarNinios(Persona persona, Persona persona2){
        IparejaDePersona iparejaDePersona = (p1,p2) -> {
            boolean respuestaLambda = (p1.getSexo().sexo().equals(Sexo.FEMENINO.sexo()) && p2.getSexo().sexo().equals(Sexo.MASCULINO.sexo())) ||
                    (p2.getSexo().sexo().equals(Sexo.FEMENINO.sexo()) && p1.getSexo().sexo().equals(Sexo.MASCULINO.sexo()));
            return respuestaLambda;
        };
        if(iparejaDePersona.parejaValida(persona,persona2)){
            Persona ninio = new Persona(0,"Miguel","399393",TiposEstadoCivil.Soltero,0,Sexo.MASCULINO);
            return Option.of(ninio);
        }
        else{
            return Option.none();
        }
    }

    public static Option<Integer> realizarMatrimonio(Persona persona, Persona persona2){
        IparejaDePersona iparejaDePersona = (p1,p2) -> !p1.getSexo().sexo().equals(Sexo.OTRO.sexo()) || !p2.getSexo().sexo().equals(Sexo.OTRO.sexo());
        if(iparejaDePersona.parejaValida(persona,persona2))
        {
            return Option.of(persona.getPatrimonio()+persona2.getPatrimonio());
        }
        return Option.none();

    }

    public static Try<Double> repartirHerenciaEntreHijos(Persona persona, Integer numeroHijos){
        return Try.of(()->persona.getPatrimonio()/numeroHijos.doubleValue());
    }
}
