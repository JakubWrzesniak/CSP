package CSP.Futoshiki;

import CSP.Constraint;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class CompareConstraint extends Constraint<Point, Integer> {
    private final Point firstPoint;
    private final Point secondPoint;
    private final CompareMethod compareMethod;

    public CompareConstraint(Point firstPoint, Point secondPoint, CompareMethod compareMethod) {
        super(List.of(firstPoint, secondPoint));
        this.firstPoint = firstPoint;
        this.secondPoint = secondPoint;
        this.compareMethod = compareMethod;
    }

    @Override
    public boolean satisfied(Map<Point, Integer> assignment) {
        var fv = assignment.get(firstPoint);
        var sv = assignment.get(secondPoint);
        if(fv == null || sv == null) return true;
        switch (compareMethod){
            case gt -> {
                return fv > sv;
            }
            case lt -> {
                return fv < sv;
            }
        }
        return true;
    }

    enum CompareMethod{
        gt, lt
    }
}
