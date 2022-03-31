import CSP.CSP;
import CSP.Constraint;
import CSP.Futoshiki.RowConstraint;
import CSP.Futoshiki.Utils;

import java.awt.*;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FutoshikiMain {
    private static       int                             size;
    private static final java.util.List<Point>           variables   = new ArrayList<>();
    private static final Map<Point, List<Integer>>       domains     = new HashMap<>();
    private static final Map<Point, Integer>             assignment  = new HashMap<>();
    private static final Set<Constraint<Point, Integer>> constraints = new HashSet<>();

    public static void main(String[] args) {
        var readeData = Utils.readData(Paths.get("binary-futoshiki_dane_v1.0/futoshiki_6x6"));
        size = readeData.getValue2();
        assignment.putAll(readeData.getValue0());
        constraints.addAll(readeData.getValue1());
        generateVariablesAndDomains();
        generateConstraints();
        System.out.println(variables);
        CSP<Point, Integer> csp = new CSP<>(variables, domains);
        for(var constraint : constraints){
            csp.addConstraint(constraint);
        }
        Instant start   = Instant.now();
        var     results = csp.backtrackingSearch(assignment);
        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);
        for(var res : results) {
            Utils.printArray(Utils.mapAsArray(res, size));
            System.out.println("\n-----------\n");
        }
        System.out.println(Timestamp.from(Instant.now()) + " - Found " + results.size() + " results in " + timeElapsed.getSeconds() + " seconds");
    }

    private static void generateVariablesAndDomains(){
        for(int i = 0; i < size; i++){
            for (int j = 0; j < size; j ++){
                var variable = new Point(i,j);
                domains.put(variable, IntStream.range(1, size+1).boxed().toList());
                variables.add(variable);
            }
        }
    }

    private static void generateConstraints(){
        for(int i = 0 ; i < size; i++){
            var rowPoints = new ArrayList<Point>();
            var colPoints = new ArrayList<Point>();
            for(int j = 0; j < size; j++){
                rowPoints.add(new Point(i,j));
                colPoints.add(new Point(j,i));
            }
            constraints.add(new RowConstraint(rowPoints));
            constraints.add(new RowConstraint(colPoints));
        }
    }
}
