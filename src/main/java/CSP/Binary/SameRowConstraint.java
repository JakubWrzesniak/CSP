package CSP.Binary;

import CSP.Constraint;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class SameRowConstraint extends Constraint<Point, Boolean> {
    private final List<Point> firstRow, secondRow;
    public SameRowConstraint(List<Point> firstRow, List<Point> secondRow) {
        super(Stream.concat(firstRow.stream(), secondRow.stream()).toList());
        this.firstRow = firstRow;
        this.secondRow = secondRow;
    }

    @Override
    public boolean satisfied(Map<Point, Boolean> assignment) {
        for(int i = 0 ; i < firstRow.size(); i ++){
            if(firstRow.get(i) != secondRow.get(i) && firstRow.get(i) != null) return true;
        }
        return false;
    }
}
