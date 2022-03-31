package CSP.Futoshiki;

import CSP.Constraint;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class RowConstraint extends Constraint<Point, Integer> {

    public RowConstraint(List<Point> variables) {
        super(variables);
    }

    @Override
    public boolean satisfied(Map<Point, Integer> assignment) {
        var filtered = assignment.entrySet().stream().filter(entry -> variables.contains(entry.getKey())).map(
                Map.Entry::getValue).toList();
        return filtered.stream().distinct().count() == filtered.size();
    }
}
