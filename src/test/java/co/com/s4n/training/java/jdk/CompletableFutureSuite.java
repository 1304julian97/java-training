package co.com.s4n.training.java.jdk;

import static org.junit.Assert.*;

import co.com.s4n.training.java.CollectablePerson;
import org.junit.Ignore;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.*;

public class CompletableFutureSuite {

    private void sleep(int milliseconds){
        try{
            Thread.sleep(milliseconds);
        }catch(Exception e){
            System.out.println("Problemas durmiendo hilo");
        }
    }


    public void imprimirMensajeConFechaActual(String mensaje){
        CompletableFuture<String> completableFuture = new CompletableFuture<>();

        String pattern = "HH:mm:ss:SSS";
        SimpleDateFormat formato = new SimpleDateFormat(pattern);
        Date fecha = new Date();
        String fechaString = formato.format(fecha);
        String retorno = fechaString+" "+mensaje;
        System.out.println(retorno);
    }

    @Test
    public void t1() {

        CompletableFuture<String> completableFuture
                = new CompletableFuture<>();


        ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.submit(() -> {
            Thread.sleep(300);

            completableFuture.complete("Hello");
            return null;
        });
            System.out.println(Thread.currentThread().getName());

        try {
            String s = completableFuture.get(500, TimeUnit.MILLISECONDS);
            assertEquals(s, "Hello");
        }catch(Exception e){
            assertTrue(false);
        }finally{
            executorService.shutdown();

        }

    }

    @Test
    public void t2(){
        CompletableFuture<String> completableFuture
                = new CompletableFuture<>();

        ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.submit(() -> {
            Thread.sleep(300);

            completableFuture.complete("Hello");
            return null;
        });

        try {
            String s = completableFuture.get(500, TimeUnit.MILLISECONDS);
            assertEquals(s, "Hello");
        }catch(Exception e){
            assertTrue(false);
        }finally{
            executorService.shutdown();
        }
    }

    @Test
    public void t3(){
        // Se puede construir un CompletableFuture a partir de una lambda Supplier (que no recibe parámetros pero sí tiene retorno)
        // con supplyAsync
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            sleep(300);
            return "Hello";
        });

        try {
            String s = future.get(500, TimeUnit.MILLISECONDS);
            assertEquals(s, "Hello");
        }catch(Exception e){

            assertTrue(false);
        }
    }

    @Test
    public void t4(){

        int i = 0;
        // Se puede construir un CompletableFuture a partir de una lambda (Supplier)
        // con runAsync
        Runnable r = () -> {
            sleep(300);
            System.out.println("Soy impuro y no merezco existir");
        };

        // Note el tipo de retorno de runAsync. Siempre es un CompletableFuture<Void> asi que
        // no tenemos manera de determinar el retorno al completar el computo
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(r);

        try {
            voidCompletableFuture.get(500, TimeUnit.MILLISECONDS);
        }catch(Exception e){
            assertTrue(false);
        }
    }

    @Test
    public void t5(){

        String testName = "t5";

        System.out.println(testName + " - El test (hilo ppal) esta corriendo en: "+Thread.currentThread().getName());

        CompletableFuture<String> completableFuture
                = CompletableFuture.supplyAsync(() -> {
            System.out.println(testName + " - completbleFuture corriendo en el thread: "+Thread.currentThread().getName());
            return "Hello";
        });

        //thenApply acepta lambdas de aridad 1 con retorno
        CompletableFuture<String> future = completableFuture
                .thenApply(s -> {
                    imprimirMensajeConFechaActual("Este es el hilo de apply: Hola soy un mensaje 2 compose corriendo en el thread: " + Thread.currentThread().getName());
                    sleep(500);
                    return s + " World";
                })
                .thenApply(s -> {
                    imprimirMensajeConFechaActual("Este es el hilo de apply: Hola soy un mensaje 2 compose corriendo en el thread: " + Thread.currentThread().getName());

                    return s + "!";
                });

        try {
            assertEquals("Hello World!", future.get());
        }catch(Exception e){
            assertTrue(false);
        }
    }


    @Test
    public void t6(){

        String testName = "t6";

        CompletableFuture<String> completableFuture
                = CompletableFuture.supplyAsync(() -> {
            System.out.println(testName + " - completbleFuture corriendo en el thread: "+Thread.currentThread().getName());
            return "Hello";
        });

        // thenAccept solo acepta Consumer (lambdas de aridad 1 que no tienen retorno)
        // analice el segundo thenAccept ¿Tiene sentido?
        CompletableFuture<Void> future = completableFuture
                .thenAccept(s -> {
                    imprimirMensajeConFechaActual("Este es el hilo de accept: Hola soy un mensaje 2 compose corriendo en el thread: " + Thread.currentThread().getName());
                    sleep(500);                })
                .thenAccept(s -> {
                    imprimirMensajeConFechaActual("Este es el hilo de accept: Hola soy un mensaje 3 compose corriendo en el thread: " + Thread.currentThread().getName());
                });

    }

    @Test
    public void t7(){

        String testName = "t7";

        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            imprimirMensajeConFechaActual("Hola soy un mensaje 1");

            return "Hello";
        });

        //thenAccept solo acepta Consumer (lambdas de aridad 1 que no tienen retorno)
        CompletableFuture<Void> future = completableFuture
                .thenRun(() -> {
                    imprimirMensajeConFechaActual("Este es el hilo de run: Hola soy un mensaje 2 compose corriendo en el thread: " + Thread.currentThread().getName());
                    sleep(500);

                })
                .thenRun(() -> {
                    imprimirMensajeConFechaActual("Este es el hilo de run: Hola soy un mensaje 2 compose corriendo en el thread: " + Thread.currentThread().getName());
                });

    }

    @Test
    public void t8(){

        String testName = "t8";

        CompletableFuture<String> completableFuture = CompletableFuture
                .supplyAsync(() -> {
                    System.out.println(testName + " - completableFuture corriendo en el thread: " + Thread.currentThread().getName());
                    return "Hello";
                })
                .thenCompose(s -> {
                    System.out.println(testName + " - compose corriendo en el thread: " + Thread.currentThread().getName());
                    return CompletableFuture.supplyAsync(() ->{
                        System.out.println(testName + " - CompletableFuture interno corriendo en el thread: " + Thread.currentThread().getName());
                        return s + " World"  ;
                    } );
                });

        try {
            assertEquals("Hello World", completableFuture.get());
        }catch(Exception e){
            assertTrue(false);
        }
    }

    @Test
    public void t12(){

        String testName = "t12";

        CompletableFuture<CollectablePerson> completableFuture = CompletableFuture
                .supplyAsync(() -> {
                    System.out.println(testName + " - completableFuture corriendo en el thread: " + Thread.currentThread().getName());
                    return "Julian.21";
                })
                .thenCompose(s -> {
                    System.out.println(testName + " - compose corriendo en el thread: " + Thread.currentThread().getName());
                    String string = s;
                    String[] parts = string.split("\\.");
                    String nombre = parts[0];
                    int edad = Integer.parseInt(parts[1]);

                    return CompletableFuture.supplyAsync(() ->{
                        System.out.println(testName + " - CompletableFuture interno corriendo en el thread: " + Thread.currentThread().getName());

                        return new CollectablePerson(nombre,edad)  ;
                    } );
                });

        try {
            CollectablePerson collectablePerson = completableFuture.get();
            CollectablePerson collectablePersonAnswer = new CollectablePerson("Julian",21);
            assertEquals(collectablePersonAnswer.age,collectablePerson.age);
            assertEquals(collectablePersonAnswer.name,collectablePerson.name);
        }catch(Exception e){
            assertTrue(false);
        }
    }


    @Test
    public void t9(){

        String testName = "t9";


        // El segundo parametro de thenCombina es un BiFunction la cual sí tiene que tener retorno.
        CompletableFuture<String> completableFuture = CompletableFuture
                .supplyAsync(() ->{
                    System.out.println(testName+" "+Thread.currentThread().getName());
                    return "Hello";})
                .thenCombine(
                        CompletableFuture.supplyAsync(() -> {
                            System.out.println(testName+" "+Thread.currentThread().getName());
                            return " World";}),
                        (s1, s2) ->  {
                            System.out.println(testName+" "+Thread.currentThread().getName());
                            return s1+s2;
                        }
                );

        try {
            assertEquals("Hello World", completableFuture.get());
        }catch(Exception e){
            assertTrue(false);
        }
    }

    @Test
    public void t10(){

        String testName = "t10";

        // El segundo parametro de thenAcceptBoth debe ser un BiConsumer. No puede tener retorno.
        CompletableFuture future = CompletableFuture.supplyAsync(() -> "Hello")
                .thenAcceptBoth(
                        CompletableFuture.supplyAsync(() -> " World"),
                        (s1, s2) -> System.out.println(testName + " corriendo en thread: "+Thread.currentThread().getName()+ " : " +s1 + s2));

        try{
            Object o = future.get();
        }catch(Exception e){
            assertTrue(false);
        }
    }

    @Test
    public void testEnlaceConSupplyAsync(){
        ExecutorService es  = Executors.newFixedThreadPool(1);
        CompletableFuture f = CompletableFuture.supplyAsync(()->"Hello",es);

        CompletableFuture<String> f2 = f.supplyAsync(() -> {
            System.out.println("Test Extra:  Ejecutando a: ");
            sleep(500);
            System.out.println("Test Extra:  Ejecutando a2: ");
            return "a";
        }).supplyAsync(()-> {
            System.out.println("Test Extra: Ejecutando b: ");
            return "b";
        });

        try {
            assertEquals(f2.get(),"b");
        }catch (Exception e){
            fail();
        }
    }

    @Test
    public void testEnlaceConSupplyAsync2(){
        ExecutorService es  = Executors.newFixedThreadPool(1);
        CompletableFuture f = CompletableFuture.supplyAsync(()->"Hello",es);

        CompletableFuture<String> f2 = f.supplyAsync(() -> {
            imprimirMensajeConFechaActual("Test Extra2:  Ejecutando a: ");
            sleep(500);
            imprimirMensajeConFechaActual("Test Extra2:  Ejecutando a2: ");
            return "a";
        },es).supplyAsync(()-> {
            imprimirMensajeConFechaActual("Test Extra2: Ejecutando b: ");
            return "b";
        },es);

        try {
            assertEquals(f2.get(),"b");
        }catch (Exception e){
            fail();
        }
    }

    @Test
    public void testEnlaceConThenApplyAsync() {
        String pruebaNombre = "*******Enlace Con thenApplyAsync********";
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        CompletableFuture f = CompletableFuture.supplyAsync(()->{
            imprimirMensajeConFechaActual(pruebaNombre+" futuro "+Thread.currentThread().getName());
            return "Julian";
            },executorService);

        CompletableFuture <String> completableFuture2 = f.thenApplyAsync(x->{
            imprimirMensajeConFechaActual(pruebaNombre+" primer thenApply "+Thread.currentThread().getName()+" "+x);
            return x + " Carvajal";
        },executorService ).
            thenApplyAsync(y->{
                imprimirMensajeConFechaActual(pruebaNombre+" Segundo Apply "+Thread.currentThread().getName()+ " "+y);
                return y + " Montoya";
            },executorService);



        try {
            sleep(1000);
            imprimirMensajeConFechaActual(pruebaNombre+" MiNombre es:  "+(String)completableFuture2.get());
        }catch (Exception e) {
            fail();
        }
    }

    @Test
    public void t11(){

        String testName = "t11";

        ExecutorService es = Executors.newFixedThreadPool(1);
        CompletableFuture f = CompletableFuture.supplyAsync(()->"Hello",es);

        f.supplyAsync(() -> "Hello")
                .thenCombineAsync(
                    CompletableFuture.supplyAsync(() -> {
                        System.out.println(testName + " thenCombineAsync en Thread (1): " + Thread.currentThread().getName());
                        return " World";
                    }),
                    (s1, s2) -> {
                        System.out.println(testName + " thenCombineAsync en Thread (2): " + Thread.currentThread().getName());
                        return s1 + s2;
                    },
                    es
                );

    }

}
