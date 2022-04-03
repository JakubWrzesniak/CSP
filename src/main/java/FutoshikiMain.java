import CSP.Futoshiki.FutoshikiCSP;
import CSP.SatisfactionAlgorithms.BacktrackingSearch;
import CSP.SatisfactionAlgorithms.ForwardChecking;
import CSP.SatisfactionAlgorithms.SatisfactionAlgorithm;
import CSP.ValueHeuristics.LCV;
import CSP.VariableHeuristics.MRV;
import CSP.VariableHeuristics.MRV_MCV;
import CSP.VariableHeuristics.MCV;

import java.awt.*;
import java.nio.file.Path;
import java.security.InvalidAlgorithmParameterException;
import java.util.List;

import static CSP.SatisfactionAlgorithms.SatisfactionAlgorithm.HeuristicMode.DYNAMIC;

public class FutoshikiMain {

    public static void main(String[] args) {
        try {
            List<SatisfactionAlgorithm<Point, Integer>> futoshikiCSPS = List.of(
                 //   new ForwardChecking<Point, Integer>(new FutoshikiCSP(Path.of("binary-futoshiki_dane_v1.0/futoshiki_4x4"), 4)),
                //    new ForwardChecking<Point, Integer>(new FutoshikiCSP(Path.of("binary-futoshiki_dane_v1.0/futoshiki_5x5"), 5)),
                 //   new BacktrackingSearch<Point, Integer>( new FutoshikiCSP(Path.of("binary-futoshiki_dane_v1.0/futoshiki_6x6"), 6)),
                 //   new BacktrackingSearch<Point, Integer>( new FutoshikiCSP(Path.of("binary-futoshiki_dane_v1.0/futoshiki_6x6"), 6)).withVariableSelection(
                 //           MRV.class, STATIC),
                //    new BacktrackingSearch<Point, Integer>( new FutoshikiCSP(Path.of("binary-futoshiki_dane_v1.0/futoshiki_6x6"), 6)).withVariableSelection(
                 //           MRV.class, DYNAMIC),
                //    new ForwardChecking<Point, Integer>( new FutoshikiCSP(Path.of("binary-futoshiki_dane_v1.0/futoshiki_6x6"), 6)),
               //     new ForwardChecking<Point, Integer>( new FutoshikiCSP(Path.of("binary-futoshiki_dane_v1.0/futoshiki_6x6"), 6)).withVariableSelection(
                //            MRV.class, DYNAMIC).withValueSelection(LCV.class, DYNAMIC),
                    new ForwardChecking<Point, Integer>( new FutoshikiCSP(Path.of("binary-futoshiki_dane_v1.0/futoshiki_6x6"), 6)).withVariableSelection(
                            MCV.class, DYNAMIC).withValueSelection(LCV.class, DYNAMIC),
                    new ForwardChecking<Point, Integer>( new FutoshikiCSP(Path.of("binary-futoshiki_dane_v1.0/futoshiki_6x6"), 6)).withVariableSelection(
                            MCV.class, DYNAMIC)//.withValueSelection(LCV.class, DYNAMIC)
                    );
            futoshikiCSPS.forEach(b -> {
                var results = b.satisfy();
//                for (var res : results) {
//                    Utils.printArray(Utils.mapAsArray(res, b.getSize()));
//                    System.out.println("\n------------------\n");
//                }
            });
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
    }
}
