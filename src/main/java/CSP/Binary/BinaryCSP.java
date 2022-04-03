package CSP.Binary;

import CSP.CSP;

import java.awt.*;
import java.nio.file.Path;
import java.security.InvalidAlgorithmParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BinaryCSP extends CSP<Point, Boolean> {
    private final Integer             size;
    private final Map<Point, Boolean> assignments;

    public BinaryCSP(Path path, int size) throws InvalidAlgorithmParameterException {
        super(generateVariables(size), generateDomains(size));
        var data = Utils.readData(path);
        this.assignments = data.getValue0();
        this.size = data.getValue1();
        if(size != this.size) throw new InvalidAlgorithmParameterException("Invalid size in read file. Current: " + this.size + " Expected: " + size);
        generateConstrains();
    }

    private static Map<Point, List<Boolean>> generateDomains(int size){
        Map<Point, List<Boolean>> domains = new HashMap<>();
        for(int i = 0; i < size ; i++){
            for(int j = 0; j < size; j++){
                var newVar = new Point(i,j);
                domains.put(newVar, new ArrayList<>(List.of(true, false)));
            }
        }
        return domains;
    }

    private static List<Point> generateVariables(int size){
        List<Point> variables = new ArrayList<>();
        for(int i = 0; i < size ; i++){
            for(int j = 0; j < size; j++){
                var newVar = new Point(i,j);
                variables.add(newVar);
            }
        }
        return variables;
    }

     private void generateConstrains(){
             for(int i = 0 ; i < size ; i ++){
                 List<Point> column = generateColumn(i);
                 List<Point> row = generateRow(i);
                 addConstraint(new ColumnRowConstraint(column, size/2));
                 addConstraint(new ColumnRowConstraint(row, size/2));
                 for(int i2 = i ; i2 < size ; i2 ++) {
                     List<Point> nextColumn = generateColumn(i2);
                     List<Point> nextRow = generateRow(i2);
                     addConstraint(new SameRowConstraint(column, nextColumn));
                     addConstraint(new SameRowConstraint(row, nextRow));
                 }
                 for(int j = 0 ; j < size - 2; j++){
                     addConstraint(new TheSameNeighboursConstraint(List.of(new Point(i ,j), new Point(i , j + 1), new Point(i, j + 2))));
                     addConstraint(new TheSameNeighboursConstraint(List.of(new Point(j ,i), new Point(j + 1 ,i ), new Point(j + 2, i))));
                 }
             }
     }

    private List<Point> generateRow(int column){
        List<Point> points = new ArrayList<>();
        for(int j = 0 ; j < size; j++){
            points.add(new Point(j, column));
        }
        return points;
    }

    private List<Point> generateColumn(int row){
        List<Point> points = new ArrayList<>();
        for(int j = 0 ; j < size; j++){
            points.add(new Point(row, j));
        }
        return points;
    }

    public int getSize(){
        return size;
    }

    @Override
    public Map<Point, Boolean> getAssignments() {
        return assignments;
    }
}
