package CSP.Futoshiki;

import CSP.CSP;

import java.awt.*;
import java.nio.file.Path;
import java.security.InvalidAlgorithmParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class FutoshikiCSP extends CSP<Point, Integer> {
    private final int                 size;
    private final Map<Point, Integer> assignments;

    public FutoshikiCSP(Path path, int size) throws InvalidAlgorithmParameterException {
        super(generateVariables(size), generateDomains(size));
        var data = Utils.readData(path);
        this.assignments = data.getValue0();
        this.size = data.getValue2();
        if(size != this.size) throw new InvalidAlgorithmParameterException("Invalid size in read file. Current: " + this.size + " Expected: " + size);
        var fileConstraints = data.getValue1();
        fileConstraints.forEach(this::addConstraint);
        generateConstraints();
    }

    private static List<Point> generateVariables(int size){
        List<Point> variables = new ArrayList<>();
        for(int i = 0; i < size; i++){
            for (int j = 0; j < size; j ++){
                var variable = new Point(i,j);
                variables.add(variable);
            }
        }
        return variables;
    }

    private static Map<Point, List<Integer>> generateDomains(int size){
        Map<Point, List<Integer>> domains = new HashMap<>();
        for(int i = 0; i < size; i++){
            for (int j = 0; j < size; j ++){
                var variable = new Point(i,j);
                domains.put(variable, new ArrayList<>(IntStream.range(1, size+1).boxed().toList()));
            }
        }
        return domains;
    }

    private void generateConstraints(){
        for(int i = 0 ; i < size; i++){
            var rowPoints = new ArrayList<Point>();
            var colPoints = new ArrayList<Point>();
            for(int j = 0; j < size; j++){
                rowPoints.add(new Point(i,j));
                colPoints.add(new Point(j,i));
            }
            addConstraint(new RowConstraint(rowPoints));
            addConstraint(new RowConstraint(colPoints));
        }
    }

    @Override
    public Map<Point, Integer> getAssignments() {
        return assignments;
    }

    public int getSize(){
        return size;
    }
}
