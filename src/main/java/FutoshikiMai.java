import CSP.Binary.BinaryCSP;
import CSP.Futoshiki.FutoshikiCSP;
import CSP.Futoshiki.Utils;
import CSP.SatisfactionAlgorithms.BacktrackingSearch;
import CSP.SatisfactionAlgorithms.ForwardChecking;
import CSP.SatisfactionAlgorithms.SatisfactionAlgorithm;
import CSP.ValueHeuristics.LCV;
import CSP.ValueHeuristics.MPV;
import CSP.VariableHeuristics.MRV;
import CSP.VariableHeuristics.MRV_MCV;
import CSP.VariableHeuristics.MCV;

import java.awt.*;
import java.nio.file.Path;
import java.security.InvalidAlgorithmParameterException;
import java.util.List;
import java.util.Map;

import static CSP.SatisfactionAlgorithms.SatisfactionAlgorithm.HeuristicMode.DYNAMIC;
import static CSP.SatisfactionAlgorithms.SatisfactionAlgorithm.HeuristicMode.STATIC;

public class FutoshikiMai {

    public static void main(String[] args) throws InvalidAlgorithmParameterException {
        try {
            List<SatisfactionAlgorithm> futoshikiCSPS = List.of(
//                    new BacktrackingSearch<Point, Boolean>( new BinaryCSP(Path.of("binary-futoshiki_dane_v1.0/binary_6x6"), 6)),
//                    new BacktrackingSearch<Point, Integer>( new FutoshikiCSP(Path.of("binary-futoshiki_dane_v1.0/futoshiki_6x6"), 6)),
//                    new BacktrackingSearch<Point, Integer>( new FutoshikiCSP(Path.of("binary-futoshiki_dane_v1.0/futoshiki_6x6"), 6)).withVariableSelection(
//                            MCV.class, STATIC),
//                    new BacktrackingSearch<Point, Integer>( new FutoshikiCSP(Path.of("binary-futoshiki_dane_v1.0/futoshiki_6x6"), 6)).withVariableSelection(
//                            MCV.class, DYNAMIC),
//                    new BacktrackingSearch<Point, Integer>( new FutoshikiCSP(Path.of("binary-futoshiki_dane_v1.0/futoshiki_6x6"), 6)).withVariableSelection(
//                            MCV.class, STATIC).withValueSelection(LCV.class, DYNAMIC),
//                    new ForwardChecking<Point, Integer>( new FutoshikiCSP(Path.of("binary-futoshiki_dane_v1.0/futoshiki_6x6"), 6)),
//                    new ForwardChecking<Point, Integer>( new FutoshikiCSP(Path.of("binary-futoshiki_dane_v1.0/futoshiki_6x6"), 6)).withVariableSelection(
//                            MRV.class, DYNAMIC),
//                    new ForwardChecking<Point, Integer>( new FutoshikiCSP(Path.of("binary-futoshiki_dane_v1.0/futoshiki_6x6"), 6)).withVariableSelection(
//                            MRV.class, DYNAMIC).withValueSelection(LCV.class, DYNAMIC),
//                    new ForwardChecking<Point, Integer>( new FutoshikiCSP(Path.of("binary-futoshiki_dane_v1.0/futoshiki_6x6"), 6)).withVariableSelection(
//                            MCV.class, DYNAMIC),
//                    new ForwardChecking<Point, Integer>( new FutoshikiCSP(Path.of("binary-futoshiki_dane_v1.0/futoshiki_6x6"), 6)).withVariableSelection(
//                            MCV.class, DYNAMIC).withValueSelection(LCV.class, DYNAMIC),
  //                  new ForwardChecking<Point, Integer>( new FutoshikiCSP(Path.of("binary-futoshiki_dane_v1.0/futoshiki_6x6"), 6)),
                    new ForwardChecking<Point, Integer>( new FutoshikiCSP(Path.of("binary-futoshiki_dane_v1.0/futoshiki_6x6"), 6)).withVariableSelection(
                            MRV_MCV.class, DYNAMIC).withValueSelection(LCV.class, DYNAMIC),
                    new ForwardChecking<Point, Integer>( new FutoshikiCSP(Path.of("binary-futoshiki_dane_v1.0/futoshiki_6x6"), 6)).withVariableSelection(
                            MRV_MCV.class, DYNAMIC),
                    new ForwardChecking<Point, Integer>( new FutoshikiCSP(Path.of("binary-futoshiki_dane_v1.0/futoshiki_6x6"), 6)).withVariableSelection(
                            MRV_MCV.class, DYNAMIC).withValueSelection(LCV.class, DYNAMIC),
                    new ForwardChecking<Point, Integer>( new FutoshikiCSP(Path.of("binary-futoshiki_dane_v1.0/futoshiki_6x6"), 6)).withVariableSelection(
                            MRV_MCV.class, DYNAMIC),
                    new ForwardChecking<Point, Integer>( new FutoshikiCSP(Path.of("binary-futoshiki_dane_v1.0/futoshiki_6x6"), 6)).withVariableSelection(
                            MRV_MCV.class, DYNAMIC).withValueSelection(LCV.class, DYNAMIC),
                    new ForwardChecking<Point, Integer>( new FutoshikiCSP(Path.of("binary-futoshiki_dane_v1.0/futoshiki_6x6"), 6))
                    );
            futoshikiCSPS.forEach(f ->
            {
                List<Map<Point, Integer>> res = f.satisfy();
            });
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
    }
}
