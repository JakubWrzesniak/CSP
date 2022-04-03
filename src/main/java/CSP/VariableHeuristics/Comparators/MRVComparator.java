package CSP.VariableHeuristics.Comparators;

import CSP.CSP;

import java.util.Comparator;

public class MRVComparator<V, D> implements Comparator<V> {
    private final CSP<V, D> csp;

    public MRVComparator(CSP<V,D> csp){
        this.csp = csp;
    }

    @Override
    public int compare(V o1, V o2) {
        return Integer.compare(csp.getDomains().get(o1).size(), csp.getDomains().get(o2).size());
    }
}