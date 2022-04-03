package CSP.VariableHeuristics;

import CSP.CSP;
import CSP.VariableHeuristics.Comparators.MRV_MCVComparator;

import java.util.List;

public class MRV_MCV<V,D> extends VariableHeuristic<V, D> {

    public MRV_MCV(CSP<V,D> csp) {
        super(csp);
    }

    @Override
    public void selection(List<V> variables) {
        variables.sort(new MRV_MCVComparator<V,D>(csp));
    }
}
