package CSP.Binary;

import CSP.Constraint;

import java.awt.*;
import java.util.*;
import java.util.List;

public class ColumnRowConstraint extends Constraint<Point, Boolean> {
    private final int maxNumber;

    public ColumnRowConstraint(List<Point> points, int maxNumber) {
        super(points);
        this.maxNumber = maxNumber;
    }

    @Override
    public boolean satisfied(Map<Point, Boolean> assignment) {
        var filtered = assignment.entrySet().stream().filter(entry -> variables.contains(entry.getKey())).map(
                Map.Entry::getValue).toList();
        var numOf0 = filtered.stream().filter(v -> v).count();
        var numOf1 = filtered.stream().filter(v -> !v).count();
        return numOf0 <= maxNumber && numOf1 <= maxNumber;
    }
}
