package CSP.VariableHeuristics.Comparators;

import CSP.CSP;

import java.util.Comparator;

public class MCVComparator<V, D> implements Comparator<V> {
    private final CSP<V, D> csp;

    public MCVComparator(CSP<V,D> csp){
        this.csp = csp;
    }

    @Override
    public int compare(V o1, V o2) {
        return Integer.compare(csp.getConstraints(o2).size(), csp.getConstraints(o1).size());
    }
}