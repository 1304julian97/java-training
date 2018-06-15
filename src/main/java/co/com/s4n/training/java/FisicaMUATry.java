package co.com.s4n.training.java;

import io.vavr.control.Option;
import io.vavr.control.Try;

import static io.vavr.API.Failure;

public class FisicaMUATry {


    public static Try<Double> calcularVelocidadInicialAlCuadrado(Double velocidadInicial){
        return Try.of(()->velocidadInicial*velocidadInicial);
    }

    public static Try<Double> calcular2VecesAceleracionXDistancia(Double aceleracion,Double distancia)
    {
        return Try.of(()->2.0*aceleracion*distancia);
    }

    public static Try<Double> calcularRaizCuadradaDeLaSumaDeDosNumeros(Double numero1, Double numero2) {
        if (numero1+numero2 > 0){
            return Try.of(()->Math.sqrt(numero1+numero2));
        }
        else
        {
            return Failure(new Exception("/ Raiz Cuadrada de número negativo"));
        }
    }
}
