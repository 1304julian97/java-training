package co.com.s4n.training.java;

import io.vavr.control.Option;

public class FisicaMUA {

    public static Option<Double> calcularVelocidadInicialAlCuadrado(Double velocidadInicial){
        return Option.of(velocidadInicial*velocidadInicial);
    }

    public static Option<Double> calcular2VecesAceleracionXDistancia(Double aceleracion,Double distancia)
    {
        return Option.of(2.0*aceleracion*distancia);
    }

    public static Option<Double> calcularRaizCuadradaDeLaSumaDeDosNumeros(Double numero1,Double numero2)
    {
        return Option.of(Math.sqrt(numero1+numero2));
    }
}
