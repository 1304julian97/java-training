package co.com.s4n.training.java.jdk;


//import com.sun.xml.internal.fastinfoset.algorithm.IntegerEncodingAlgorithm;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.IntBinaryOperator;
import java.util.function.Supplier;


import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(JUnitPlatform.class)
public class LambdaSuite {

    @FunctionalInterface
    interface InterfaceDeEjemplo{
        int metodoDeEjemplo(int x, int y);
    }

    @FunctionalInterface
    interface InterfaceCon4Supplier{
        Consumer<Integer> metodoAEjecutar(Supplier<Integer> s1,Supplier<Integer> s2, Supplier<Integer> s3);
    }

    @Test
    public void testEjercicio(){
        InterfaceCon4Supplier i = (x,y,z) ->{
            Integer parcial = x.get()+y.get()+z.get();
            Consumer<Integer> c = n-> {
                Integer suma = parcial.intValue() +n;
            };
            return c;
        };
        Supplier<Integer> s1  = () -> 1;
        Supplier<Integer> s2  = () -> 2;
        Supplier<Integer> s3  = () -> 3;

        Consumer <Integer> consumer = i.metodoAEjecutar(s1,s2,s3);

        consumer.accept(new Integer(9));


    }



    @Test
    public void testEjercicio2(){
        InterfaceCon4Supplier inteface = (s1, s2,s3) ->{
            Integer valorSuma = s1.get()*2 + s2.get()*3 + s3.get()*4;
            System.out.println("Esta es la suma de la ejecución sin el consumer:"+valorSuma);
            Consumer<Integer> consumer = n-> {
                Integer suma = valorSuma.intValue() + n;
                System.out.println("Esta es la suma de un consumer (accept)"+(suma));
            };
            return consumer;
        };

        Supplier<Integer> s1 = () -> {return 1;};
        Supplier<Integer> s2 = () -> {return 2;};
        Supplier<Integer> s3 = () -> {return 3;};

        Consumer<Integer> con = inteface.metodoAEjecutar(s1,s2,s3);
        con.accept(10);


    }

    @FunctionalInterface
    interface Interface3Consummer{
        Supplier<String> concatenarTextoDeConsumer(Consumer<String> c1,Consumer<String> c2, Consumer<String> c3);
    }

    @Test
    public void testInterface3Consumer(){

        //String nombre  = "";
        //String apellido1 ="";
        //String apellido2 = "";
        String[] nombre = new String[1];
        String[] apellido1 = new String[1];
        String[] apellido2 = new String[1];
        Consumer<String> con1 = s -> {
            nombre[0] = s;
        };

        Consumer<String> con2 = s2 -> {
            apellido1[0] = s2;
        };

        Consumer<String> con3 = s3 -> {
            apellido2[0] = s3;
        };


        Interface3Consummer i3c = (c1,c2,c3) -> {
            c1.accept("Julian");
            c2.accept("Carvajal");
            c3.accept("Montoya");
            Supplier<String> s1 = () ->{
                String miNombre = nombre[0]+apellido1[0]+apellido2[0];
                return miNombre;
            };
            return s1;
        };



        Supplier<String> s1 = i3c.concatenarTextoDeConsumer(con1,con2,con3);

        String resultado =  s1.get();
        System.out.println("mi nombre es: "+resultado);


    }

    class ClaseDeEjemplo{
        public int metodoDeEjemplo1(int z, InterfaceDeEjemplo i){
            return z + i.metodoDeEjemplo(1,2);
        }

        public int metodoDeEjemplo2(int z, BiFunction<Integer, Integer, Integer> fn){
            return z + fn.apply(1,2);
        }
    }

    @Test
    public void smokeTest() {
        assertTrue(true);
    }

    @Test
    public void usarUnaInterfaceFuncional1(){

        InterfaceDeEjemplo i = (x,y)->x+y;

        ClaseDeEjemplo instancia = new ClaseDeEjemplo();

        int resultado = instancia.metodoDeEjemplo1(1,i);

        assertTrue(resultado==4);
    }

    @Test
    public void usarUnaInterfaceFuncional3(){

        InterfaceDeEjemplo i = (x,y)->x+y+1;

        ClaseDeEjemplo instancia = new ClaseDeEjemplo();

        int resultado = instancia.metodoDeEjemplo1(1,i);

        assertTrue(resultado==5);
    }

    @Test
    public void usarUnaInterfaceFuncional2(){

        BiFunction<Integer, Integer, Integer> f = (x, y) -> new Integer(x.intValue()+y.intValue());

        ClaseDeEjemplo instancia = new ClaseDeEjemplo();

        int resultado = instancia.metodoDeEjemplo2(1,f);

        assertTrue(resultado==4);
    }


    @Test
    public void usarUnaInterfaceFuncional4(){

        BiFunction<Integer, Integer, Integer> funcion = (x, y) -> new Integer((x.intValue()+y.intValue())*2);

        ClaseDeEjemplo instancia = new ClaseDeEjemplo();

        int resultado = instancia.metodoDeEjemplo2(1,funcion);

        assertTrue(resultado==7);
    }


    class ClaseDeEjemplo2{

        public int metodoDeEjemplo2(int x, int y, IntBinaryOperator fn){
            return fn.applyAsInt(x,y);
        }
    }

    @Test
    public void usarUnaFuncionConTiposPrimitivos2(){
        IntBinaryOperator f = (x, y) -> x + y;

        ClaseDeEjemplo2 instancia = new ClaseDeEjemplo2();

        int resultado = instancia.metodoDeEjemplo2(1,2,f);

        assertEquals(3,resultado);
    }

    @Test
    public void usarUnaFuncionConTiposPrimitivos(){
        IntBinaryOperator f = (x, y) -> x + y;

        ClaseDeEjemplo2 instancia = new ClaseDeEjemplo2();

        int resultado = instancia.metodoDeEjemplo2(1,2,f);

        assertEquals(3,resultado);
    }



    class ClaseDeEjemplo3{

        public String operarConSupplier(Supplier<Integer> s){
            return "El int que me han entregado es: " + s.get();
        }
    }

    @Test
    public void usarUnaFuncionConSupplier(){
        Supplier s1 = () -> {
            System.out.println("Cuándo se evalúa esto? (1)");
            return 4;
        };

        Supplier s2 = () -> {
            System.out.println("Cuándo se evalúa esto? (2)");
            return 4;
        };

        ClaseDeEjemplo3 instancia = new ClaseDeEjemplo3();

        String resultado = instancia.operarConSupplier(s2);

        assertEquals("El int que me han entregado es: 4",resultado);
    }

    class ClaseDeEjemplo4{

        private int i = 0;

        public void operarConConsumer(Consumer<Integer> c){
            c.accept(i);
        }
    }

    @Test
    public void usarUnaFuncionConConsumer(){
        Consumer<Integer> c1 = x -> {
            System.out.println("Me han entregado este valor: "+x);
        };

        ClaseDeEjemplo4 instancia = new ClaseDeEjemplo4();

        instancia.operarConConsumer(c1);


    }

    class ClaseDeEjemplo5{
        public void operarConConsumer2(Consumer<Integer> consu, int i){
            consu.accept(i);
        }
    }

    @Test
    public void usarUnaFuncionConConsumer2(){
        Consumer<Integer> consumer1 = x -> {
          System.out.println("El consumer tiene un :" + x);
        };

        ClaseDeEjemplo5 claseDeEjemplo5 = new ClaseDeEjemplo5();
        claseDeEjemplo5.operarConConsumer2(consumer1,500);
    }

}
