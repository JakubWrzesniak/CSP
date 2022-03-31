package CSP.Binary;

import CSP.Constraint;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TheSameNeighboursConstraint extends Constraint<Point, Boolean> {
    public TheSameNeighboursConstraint(List<Point> variables) {
        super(variables);
    }

    @Override
    public boolean satisfied(Map<Point, Boolean> assignment) {
        var filtered = assignment.entrySet().stream().filter(entry -> variables.contains(entry.getKey())).toList();
        if(filtered.size() < variables.size()) return true;
        var selectedVariables = filtered.stream().filter(entry -> this.variables.contains(entry.getKey()) && entry.getValue() != null).map(
                Map.Entry::getValue);
        return selectedVariables.distinct().count() != 1;
    }
}
