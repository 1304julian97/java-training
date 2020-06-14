package co.com.s4n.training.java.vavr;

import com.sun.javaws.exceptions.ErrorCodeResponseException;
import io.vavr.API;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.collection.Seq;
import io.vavr.concurrent.Future;
import io.vavr.control.Either;
import io.vavr.control.Option;
import io.vavr.control.Try;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;

public class meetup {

    @Test
    public void tuplas() {
        Tuple2<String, Integer> stringYEntero = Tuple.of("Julian", 28);
        stringYEntero.toSeq().forEach(System.out::println);
    }

    @Test
    public void listas() {
        List<String> listaVavr = List.of("1", "2", "3");
        java.util.List<String> listaJava = Arrays.asList("1", "2", "3");

        java.util.List<Integer> collect = listaJava.
                stream().
                map(Integer::parseInt).
                collect(Collectors.toList());
        List<Integer> map = listaVavr.
                map(Integer::parseInt);

    }

    @Test
    public void option() {
        Option<String> option1 = Option.of("Julián").filter(x -> x.equals("Julián"));
        Optional<String> optional = Optional.of("Julian").filter(x -> x.equals("Julian"));
        Optional<String> optional2 = Optional.ofNullable("").filter(x -> x.equals("Julián"));
        System.out.println(optional.orElse("Johana"));
        System.out.println(option1.getOrElse("Johana"));
    }


    @Test
    public void tryTest() {
        String valor;
        try {
            metodoConError();
        } catch (Exception e) {
            valor = "Valor recuperado";
            System.out.println("Me recuperé");
        }

        valor = Try.of(this::metodoConError).getOrElse(() -> {
            System.out.println("Me recuperé");
            return "Valor recuperado";
        });


    }

    public String metodoConError() {
        String valor = "julian";
        throw new Error("Se dañó");
    }


    @Test
    public void either() throws Exception {
        int valor = 5;

        Either<Exception, Integer> either = Either.right(valor);
        Either<Exception, Integer> either2 = procesmaiento(either);
        Either<Exception, Integer> eitherFinal = procesmaiento2(either2);
        /*return*/
        eitherFinal.getOrElseThrow(eitherFinal::getLeft);


    }


    private Either<Exception, Integer> procesmaiento2(Either<Exception, Integer> either) {
        if (true) {
            return Either.left(new RuntimeException("Se daño"));
        } else {
            return Either.right(5);
        }
    }

    private Either<Exception, Integer> procesmaiento(Either<Exception, Integer> either) {
        return either.map(x -> x + 5);
    }


    @Test
    public void testFuture() {
        ExecutorService service = Executors.newFixedThreadPool(4);
        Future<String> future = Future.of(() -> "Julián");
        Future<String> future1 = Future.of(service, () -> "Julián");
        gettiempo();
        gettiempo();
        List.range(0,1000000);
        gettiempo();
    }


    private void gettiempo() {
        Try.run(()->{

        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String dateInString = sdf.format(Calendar.getInstance().getTime());
        System.out.println(dateInString);


        });

    }


    @Test
    public void englishClass()
    {
        String number = JOptionPane.showInputDialog("Input a number");
        String returnn = Match(number).of(
                Case($("1"),"Red"),
                Case($("2"),"Yellow"),
                Case($("3"),"Blue"),
                Case($("4"),"White"),
                Case($("5"),"Black"),
                Case($(),"Nothing :/")
        );

        JOptionPane.showMessageDialog(null,returnn);
    }


}
