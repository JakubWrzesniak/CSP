package CSP.VariableHeuristics;

import CSP.CSP;
import CSP.VariableHeuristics.Comparators.MRVComparator;

import java.util.Comparator;
import java.util.List;

public class MRV<V, D> extends VariableHeuristic<V,D> {

    public MRV(CSP<V, D> csp) {
        super(csp);
    }

    @Override
    public void selection(List<V> variables) {
        variables.sort(new MRVComparator<V,D>(csp));
    }
}
