package CSP.VariableHeuristics.Comparators;

import CSP.CSP;

import java.util.Comparator;

public class MRV_MCVComparator<V, D> implements Comparator<V> {
    private final MRVComparator<V,D> mrvComparator;
    private final MCVComparator<V,D> mcvComparator;

    public MRV_MCVComparator(CSP<V,D> csp ){
        this.mrvComparator = new MRVComparator<V,D>(csp);
        this.mcvComparator = new MCVComparator<V,D>(csp);
    }
    @Override
    public int compare(V o1, V o2) {
        int mrvCompare = mrvComparator.compare(o1, o2);
        return mrvCompare == 0 ? mcvComparator.compare(o1, o2) : mrvCompare;
    }
}
