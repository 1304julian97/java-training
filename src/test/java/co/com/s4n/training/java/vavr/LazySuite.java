package co.com.s4n.training.java.vavr;

import io.vavr.Lazy;
import io.vavr.concurrent.Future;
import org.junit.Test;

import java.util.function.Supplier;

import static java.lang.Thread.sleep;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class LazySuite {

    @Test
    public void testLenguajePerezoso(){
        float testInicio = System.nanoTime();


        Lazy<Future<Integer>> l1 = Lazy.of(()->Future.of(()->{
            sleep(500);
            return 1;
        }));

        Lazy<Future<Integer>> l2 = Lazy.of(()->Future.of(()->{
            sleep(500);
            return 1;
        }));

        Lazy<Future<Integer>> l3 = Lazy.of(()->Future.of(()->{
            sleep(500);
            return 1;
        }));

        Future<Integer> integers = l1.get().flatMap(x -> Future.of(() -> x + 1).
                flatMap(y -> l2.get().
                        flatMap(z -> Future.of(() ->
                                l3.get().getOrElse(5)))));
        integers.await().getOrElse(3);
        float testFinal = System.nanoTime();


        float tiempoDeDuracion = testFinal-testInicio;

        float testInicioSegundaPrueba = System.nanoTime();
        integers.await().get();
        float testFinalSegundaPrueba = System.nanoTime();
        float testFinalTiempoSegundaPrueba = testInicioSegundaPrueba-testFinalSegundaPrueba;

        assertEquals(5,testFinalTiempoSegundaPrueba,10);
        assertEquals(1500,(double)tiempoDeDuracion*(Math.pow(10,-6)),100);

    }


    @Test
    public void testLenguajePerezosoSupplier(){
        Supplier<Integer> sp = ()->{
            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        };

        float inicioTiempo1 = System.nanoTime();
        sp.get();
        float finTiempo1 = System.nanoTime();

        float diferencia = (finTiempo1-inicioTiempo1)*(float) Math.pow(10,-6);

        float inicioTiempo2 = System.nanoTime();
        sp.get();
        float finTiempo2 = System.nanoTime();
        float diferencia2 = (finTiempo2-inicioTiempo2)*(float) Math.pow(10,-6);

        assertEquals(diferencia,diferencia2,10);
    }

    @Test
    public void testLenguajePerezosoLazySupplier(){
        Lazy<Integer> lz = Lazy.of(()->{
            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        });


        float inicioTiempo1 = System.nanoTime();
        lz.get();
        float finTiempo1 = System.nanoTime();

        float diferencia = (finTiempo1-inicioTiempo1)*(float) Math.pow(10,-6);

        float inicioTiempo2 = System.nanoTime();
        lz.get();
        float finTiempo2 = System.nanoTime();
        float diferencia2 = (finTiempo2-inicioTiempo2)*(float) Math.pow(10,-6);

        assertNotEquals(diferencia,diferencia2,100);
    }



}
