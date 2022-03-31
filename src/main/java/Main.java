import CSP.Binary.ColumnRowConstraint;
import CSP.Binary.SameRowConstraint;
import CSP.Binary.TheSameNeighboursConstraint;
import CSP.Binary.Utils;
import CSP.*;

import java.awt.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static int size;
    private static final List<Point> variables = new ArrayList<>();
    private static final Map<Point, List<Boolean>> domains = new HashMap<>();
    private static final Map<Point, Boolean> assignment = new HashMap<>();
    private static final List<Constraint<Point, Boolean>> constraints = new ArrayList<>();

    public static void main(String[] args){
        var readeData = Utils.readData(Paths.get("binary-futoshiki_dane_v1.0/binary_10x10"));
        size = readeData.getValue1();
        assignment.putAll(readeData.getValue0());
        generateVariablesAndDomains(size);
        generateConstrains();
        CSP<Point, Boolean> csp = new CSP<>(variables, domains);
        constraints.forEach(csp::addConstraint);
        var results = csp.backtrackingSearch(assignment);
        for(var res : results) {
            Utils.printArray(Utils.mapAsArray(res, new Dimension(size, size)));
        }
    }

    static void generateVariablesAndDomains(int size){
        for(int i = 0; i < size ; i++){
            for(int j = 0; j < size; j++){
                var newVar = new Point(i,j);
                variables.add(newVar);
                domains.put(newVar, List.of(true, false));
            }
        }
    }

    static void generateConstrains(){
        for(int i = 0 ; i < size ; i ++){
            for(int j = 0 ; j < size - 2; j++){
                constraints.add(new TheSameNeighboursConstraint(List.of(new Point(i ,j), new Point(i , j + 1), new Point(i, j + 2))));
            }
        }

        for(int i = 0 ; i < size ; i ++){
            for(int j = 0 ; j < size - 2; j++){
                constraints.add(new TheSameNeighboursConstraint(List.of(new Point(j ,i), new Point(j + 1 ,i ), new Point(j + 2, i))));
            }
        }

        for(int i = 0 ; i < size ; i ++){
            List<Point> points = new ArrayList<>();
            for(int j = 0 ; j < size; j++){
                points.add(new Point(i,j));
            }
            constraints.add(new ColumnRowConstraint(points, size/2));
            for(int i2 = i ; i2 < size ; i2 ++) {
                List<Point> nextPoints = new ArrayList<>();
                for (int j2 = 0; j2 < size; j2++) {
                    nextPoints.add(new Point(i2, j2));
                }
                constraints.add(new SameRowConstraint(points, nextPoints));
            }
        }

        for(int i = 0 ; i < size ; i ++){
            List<Point> points = new ArrayList<>();
            for(int j = 0 ; j < size; j++){
                points.add(new Point(j,i));
            }
            constraints.add(new ColumnRowConstraint(points, size/2));
            for(int i2 = i ; i2 < size ; i2 ++) {
                List<Point> nextPoints = new ArrayList<>();
                for (int j2 = 0; j2 < size; j2++) {
                    nextPoints.add(new Point(j2, i2));
                }
                constraints.add(new SameRowConstraint(points, nextPoints));
            }
        }
    }




}
