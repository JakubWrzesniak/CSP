package CSP.Futoshiki;

import CSP.Constraint;
import org.javatuples.Triplet;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class Utils {

    public static Triplet<Map<Point, Integer>, Set<Constraint<Point, Integer>>, Integer> readData(Path path){
        Map<Point, Integer>    results    = new HashMap<>();
        Set<Constraint<Point, Integer>> constraints = new HashSet<>();
        int lineNumber = -1;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(String.valueOf(path)))) {
            var lines  = bufferedReader.lines().toList();
            for(int i = 0; i < lines.size(); i++){
                if(i % 2 == 0) lineNumber++;
                char[] line =  lines.get(i).toCharArray();
                int columnNumber = -1;
                for(int j = 0 ; j < line.length; j ++){
                    if(i % 2 == 0) {
                        if (j % 2 == 0) columnNumber++;
                    } else {
                        columnNumber++;
                    }
                    if (Character.isDigit(line[j])) results.put(new Point(lineNumber,  columnNumber), Character.getNumericValue(line[j]));
                    if (line[j] == '<'){
                        if( i % 2 == 0){
                            constraints.add(new CompareConstraint(new Point(lineNumber, columnNumber), new Point(lineNumber, columnNumber + 1), CompareConstraint.CompareMethod.lt));
                        } else {
                            constraints.add(new CompareConstraint(new Point(lineNumber, columnNumber), new Point(lineNumber + 1, columnNumber), CompareConstraint.CompareMethod.lt));
                        }
                    } else if (line[j] == '>'){
                        if( i % 2 == 0){
                            constraints.add(new CompareConstraint(new Point(lineNumber, columnNumber), new Point(lineNumber, columnNumber + 1), CompareConstraint.CompareMethod.gt));
                        } else {
                            constraints.add(new CompareConstraint(new Point( lineNumber, columnNumber), new Point(lineNumber + 1, columnNumber), CompareConstraint.CompareMethod.gt));
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Triplet<>(results, constraints, lineNumber + 1);
    }

    public static  Integer[][] mapAsArray(Map<Point, Integer> assignment, int size){
        Integer[][] array = new Integer[size][size];
        if(assignment!= null) {
            for (var entry : assignment.entrySet()) {
                var k = entry.getKey();
                array[k.x][k.y] = entry.getValue();
            }
        }
        return array;
    }

    public  static void printArray(Integer[][] array){
        for (Integer[] ts : array) {
            for (Integer t : ts) {
                System.out.print("[ ");
                System.out.print(t);
                System.out.print(" ]");
            }
            System.out.println();
        }
    }
}
