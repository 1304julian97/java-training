package co.com.s4n.training.java.vavr;

import co.com.s4n.training.java.FisicaMUA;



import io.vavr.PartialFunction;
import io.vavr.control.Option;

import static io.vavr.API.None;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import java.util.ArrayList;

import static io.vavr.API.*;
import static io.vavr.Patterns.$None;
import static io.vavr.Patterns.$Some;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static io.vavr.API.Some;


import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(JUnitPlatform.class)
public class OptionSuite {



    @Test
    public void testConstruction1(){
        Option<Integer> o = Option(1);
        assertTrue(o.isDefined());
        assertEquals(o,Some(1));
    }

    @Test
    public void testConstruccion2(){
        Option<Integer> o = Option(null);
        assertEquals(o,Option.none());
    }

    private Boolean esParPosibleNull(int i)
    {
        if (i%2 == 0) return true;
        else return null;
    }

    @Test
    public void testConstruccion3(){
        Option<Boolean> b = Option(esParPosibleNull(1));
        assertEquals(b,Option.none());
    }

    private Integer identidadPosibleNull(int i)
    {
        if (i%2 == 0) return new Integer(i);
        else return null;
    }

    @Test
    public void testFilter(){
        Option<Integer> b = Option(identidadPosibleNull(1));
        Option<Integer> r = b.filter(x-> x.intValue()<4);
        assertEquals(r,Option.none());
    }

    @Test
    public void mapOption(){
        Option<Integer> b = Option(identidadPosibleNull(8));
        Option<Integer> r = b.map(x-> x-8);
        assertEquals(r,Some(0));
    }

    @Test
    public void mapOptionNull(){
        Option<Integer> b = Option(identidadPosibleNull(3));
        Option<Integer> r = b.map(x-> x-8);
        assertEquals(r,Option.none());
    }

    @Test
    public void mapOptionGetNull(){
        Option<Integer> b = Option(identidadPosibleNull(3));
        Option<Integer> r = b.map(x-> x-8);
        assertThrows(NoSuchElementException.class,()-> r.get());
    }
    /**
     * Un option se puede filtar, y mostrar un some() o un none si no encuentra el resultado
     */
    @Test
    public void testOptionWithFilter() {
        Option<Integer> o = Option(3);

        assertEquals(
                Some(3),
                o.filter(it -> it >= 3));

        assertEquals(
                None(),
                o.filter(it -> it > 3));
    }

    /**
     * Se puede hacer pattern matching a un option y comparar entre Some y None.
     */
    private String patternMatchSimple(Option<Integer> number) {
        String result = Match(number).of(
                Case($Some($()),"Existe"),
                Case($None(),"Imaginario")
        );
        return result;
    }

    @Test
    public void testOptionWithPatternMatching() {
        Option<Integer> o1 = Option(1);
        Option<Integer> o2 = None();

        //Comparacion de Some o None()
        assertEquals("Existe", patternMatchSimple(o1));
        assertEquals("Imaginario", patternMatchSimple(o2));
    }
    /**
     *
     * el metodo peek aplica una funcion lambda o un metodo con el valor de Option cuando esta definido
     * este metodo se usa para efectos colaterales y retorna el mismo Option que lo llamó
     */
    @Test
    public void testPeekMethod(){
        Option<String> defined_option = Option.of("Hello!");
        /* Se debe utilizar una variable mutable para reflejar los efectos colaterales*/
        final List<String> list = new ArrayList<>();
        Option<String> peek = defined_option.peek(list::add);// the same as defined_option.peek(s -> list.add(s))

        System.out.println("peek: "+ peek);

        assertEquals(
                Option.of("Hello!"),
                defined_option);

        assertEquals(
                "Hello!",
                list.get(0));
    }

    /**
     * Un option se puede transformar dada una función
     */
    @Test
    public void testOptionTransform() {
        String textToCount = "Count this text";
        Option<String> text = Option.of(textToCount);
        Option<Integer> count = text.transform(s -> Option.of(s.getOrElse("DEFAULT").length()));

        assertEquals(
                Option.of(textToCount.length()),
                count);

        Option<String> hello = Option.of("Hello");
        Tuple2<String, String> result = hello.transform(s -> Tuple.of("OK", s.getOrElse("DEFAULT")));

        assertEquals(
                Tuple.of("OK", "Hello"),
                result);

    }

    /**
     * el metodo getOrElse permite obtener el valor de un Option o un sustituto en caso de ser None
     */
    @Test
    public void testGetOrElse(){
        Option<String> defined_option = Option.of("Hello!");
        Option<String> none = None();
        assertEquals( "Hello!", defined_option.getOrElse("Goodbye!"));
        assertEquals("Goodbye!", none.getOrElse("Goodbye!"));
    }

    /**
     * el metodo 'when' permite crear un Some(valor) o None utilizando condicionales booleanos
     */
    @Test
    public void testWhenMethod(){
        Option<String> valid = Option.when(true, "Good!");
        Option<String> invalid = Option.when(false, "Bad!");
        assertEquals(Some("Good!"), valid);
        assertEquals( None(), invalid);
    }

    @Test
    public void testOptionCollect() {
        final PartialFunction<Integer, String> pf = new PartialFunction<Integer, String>() {
            @Override
            public String apply(Integer i) {
                return String.valueOf(i);
            }

            @Override
            public boolean isDefinedAt(Integer i) {
                return i % 2 == 1;
            }
        };
        assertEquals( None(),Option.of(2).collect(pf));
        assertEquals( None(),Option.<Integer>none().collect(pf));
    }
    /**
     * En este test se prueba la funcionalidad para el manejo de Null en Option con FlatMap
     */
    @Test
    public void testMananagementNull(){
        Option<String> valor = Option.of("pepe");
        Option<String> someN = valor.map(v -> null);

        /* Se valida que devuelve un Some null lo cual podria ocasionar en una Excepcion de JavanullPointerExcepcion*/
        assertEquals(
                someN.get(),
                null);

        Option<String> buenUso = someN
                .flatMap(v -> {
                    System.out.println("testManagementNull - Esto se imprime? (flatMap)");
                    return Option.of(v);
                })
                .map(x -> {
                    System.out.println("testManagementNull - Esto se imprime? (map)");
                    return x.toUpperCase() +"Validacion";
                });

        assertEquals(
                None(),
                buenUso);
    }

    /**
     * En este test se prueba la funcionalidad para transformar un Option por medio de Map y flatMap
     */
    @Test
    public void testMapAndFlatMapToOption() {
        Option<String> myMap = Option.of("mi mapa");

        Option<String> myResultMapOne = myMap.map(s -> s + " es bonito");

        assertEquals(
                Option.of("mi mapa es bonito"),
                myResultMapOne);

        Option<String> myResultMapTwo = myMap
                .flatMap(s -> Option.of(s + " es bonito"))
                .map(v -> v + " con flat map");


        assertEquals(
                Option.of("mi mapa es bonito con flat map"),
                myResultMapTwo);
    }

    @Test
    public void optionFromNull(){
        Option<Object> of = Option.of(null);
        assertEquals(of, None());
    }

    @Test
    public void optionFromOptional(){
        Optional optional = Optional.of(1);
        Option option = Option.ofOptional(optional);
    }

    Option<Integer> esPar(int i){
        return (i%2==0)?Some(i):None();
    }

    @Test
    public void forCompEnOption1(){
        Option<Integer> integers = For(esPar(2), d -> Option(d)).toOption();
        assertEquals(integers,Some(2));
    }

    @Test
    public void forCompEnOption2(){
        Option<Integer> integers = For(esPar(2), d ->
                                   For(esPar(4), c -> Option(d+c))).toOption();
        assertEquals(integers,Some(6));
    }

    @Test
    public void flatMapInOption(){
        Option<Integer> o1 = Option.of(1);
        //Option<Option<Integer>> m = o1.map(i -> Option(i));
        Option<Integer> op = o1.flatMap(i -> Option.of(1));
    }

    private Option<Integer> sumar(int a, int b){
        return Option.of(a+b);
    }


    private Option<Integer> restar(int a, int b){
        return a-b>0 ? Option.of(a-b): None();
    }

    @Test
    public void flatMapInOptionCon4Flatmat(){
        Option<Integer> retorno;
        retorno = sumar(1,1).
                flatMap(r->sumar(r,1).
                    flatMap(x->sumar(x,1).
                        flatMap(y->sumar(y,1).
                                flatMap(z->sumar(z,1))
                        )));

        assertEquals(retorno.getOrElse(666).intValue(),6);
    }

    @Test
    public void flatMapInOptionCon4FlatmatConNone(){
        Option<Integer> retorno;
        retorno = sumar(1,1).
                flatMap(r->sumar(r,1).
                        flatMap(x->restar(x,5).
                                flatMap(y->sumar(y,1).
                                        flatMap(z->sumar(z,1))
                                )));

        assertEquals(retorno,Option.none());
        assertEquals(retorno.getOrElse(666).intValue(),666);
    }

    @Test
    public void flatMapInOptionCon4FlatmatConFor() {
        Option<Integer> retorno;
        retorno =
                For(sumar(1, 1), x ->
                        For(sumar(x, 5), y ->
                                For(sumar(y, 1), z ->
                                        For(sumar(z, 1), a -> sumar(a, y))))).toOption();

        //assertEquals(retorno,Option.none());
        assertEquals(retorno.getOrElse(666).intValue(), 16);
    }

    public void testVelocidadFinalMUA()
    {
        Double velocidadInicial = new Double(10.0);
        Double aceleracion = new Double(20.0);
        Double distancia = new Double(100);
        //Option<Double> optionVelocidadInicialAlCuadrado = FisicaMUA.calcularVelocidadInicialAlCuadrado(velocidadInicial);
        //Option<Double> option2VecesAceleracionXDistancia = FisicaMUA.calcular2VecesAceleracionXDistancia(aceleracion,distancia);
        Option<Double> resultado = FisicaMUA.calcularVelocidadInicialAlCuadrado(velocidadInicial).
                                    flatMap(velocidadAlCuadrado -> FisicaMUA.calcular2VecesAceleracionXDistancia(aceleracion,distancia).
                                    flatMap(_2VecesAcelaracionXTiempo -> FisicaMUA.calcularRaizCuadradaDeLaSumaDeDosNumeros(velocidadAlCuadrado,_2VecesAcelaracionXTiempo))
                                    );
                    //flatMap(velocidadAlCuadrado -> FisicaMUA.calcularRaizCuadradaDeLaSumaDeDosNumeros(velocidadAlCuadrado,
                                //FisicaMUA.calcular2VecesAceleracionXDistancia(aceleracion,distancia).getOrElse(new Double(-100))));

        assertEquals(resultado.getOrElse(-100.0),64.0,1.0);
    }

    @Test
    public void testVelocidadFinalMUAFor(){
        Double velocidadInicial = new Double(10.0);
        Double aceleracion = new Double(20.0);
        Double distancia = new Double(100);
        //Option<Double> optionVelocidadInicialAlCuadrado = FisicaMUA.calcularVelocidadInicialAlCuadrado(velocidadInicial);
        //Option<Double> option2VecesAceleracionXDistancia = FisicaMUA.calcular2VecesAceleracionXDistancia(aceleracion,distancia);
        Option<Double> resultado =
                For(FisicaMUA.calcular2VecesAceleracionXDistancia(aceleracion,distancia),
                        x->For(FisicaMUA.calcularVelocidadInicialAlCuadrado(velocidadInicial),
                                y->FisicaMUA.calcularRaizCuadradaDeLaSumaDeDosNumeros(x,y))).toOption();

        assertEquals(resultado.getOrElse(-100.0),64.0,1.0);
    }

    @Test
    public void testVelocidadFinalMUAForNone(){
        Double velocidadInicial = new Double(1.0);
        Double aceleracion = new Double(-20.0);
        Double distancia = new Double(1);
        //Option<Double> optionVelocidadInicialAlCuadrado = FisicaMUA.calcularVelocidadInicialAlCuadrado(velocidadInicial);
        //Option<Double> option2VecesAceleracionXDistancia = FisicaMUA.calcular2VecesAceleracionXDistancia(aceleracion,distancia);
        Option<Double> resultado =
                For(FisicaMUA.calcular2VecesAceleracionXDistancia(aceleracion,distancia),
                        x->For(FisicaMUA.calcularVelocidadInicialAlCuadrado(velocidadInicial),
                                y->FisicaMUA.calcularRaizCuadradaDeLaSumaDeDosNumeros(x,y))).toOption();

        assertEquals(resultado,None());
        assertEquals(resultado.getOrElse(-100.0),-100.0,1.0);
    }


}
