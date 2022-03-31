import CSP.Binary.*;
import CSP.*;

import java.awt.*;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args){
        try {
            List<BinaryCSP> binaries= List.of(new BinaryCSP(Paths.get("binary-futoshiki_dane_v1.0/binary_6x6"), 6),
                    new BinaryCSP(Paths.get("binary-futoshiki_dane_v1.0/binary_8x8"), 8),
                    new BinaryCSP(Paths.get("binary-futoshiki_dane_v1.0/binary_10x10"), 10)
                    );
            binaries.forEach(b -> {
                var results = b.backtrackingSearch();
                for (var res : results) {
                    Utils.printArray(Utils.mapAsArray(res, new Dimension(b.getSize(), b.getSize())));
                    System.out.println("\n------------------\n");
                }
            });
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
//        var readeData = Utils.readData(Paths.get("binary-futoshiki_dane_v1.0/binary_10x10"));
//        size = readeData.getValue1();
//        assignment.putAll(readeData.getValue0());
//        generateVariablesAndDomains(size);
//        generateConstrains();
//        CSP<Point, Boolean> csp = new CSP<>(variables, domains);
//        constraints.forEach(csp::addConstraint);
//        var results = csp.backtrackingSearch(assignment);
//        for(var res : results) {
//            Utils.printArray(Utils.mapAsArray(res, new Dimension(size, size)));
//        }
    }
}
