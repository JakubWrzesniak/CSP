package CSP.VariableHeuristics;

import CSP.CSP;
import CSP.VariableHeuristics.Comparators.MCVComparator;

import java.util.List;

public class MCV<V, D> extends VariableHeuristic<V,D> {

    public MCV(CSP<V, D> csp) {
        super(csp);
    }

    @Override
    public void selection(List<V> variables) {
        variables.sort(new MCVComparator<V,D>(csp));
    }
}
