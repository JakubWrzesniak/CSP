package CSP.ValueHeuristics;

import CSP.CSP;

import java.util.*;

public class LCV<V,D> extends ValueHeuristic<V,D>{
    public LCV(CSP<V, D> csp) {
        super(csp);
    }

    @Override
    public void selection(V variable, List<D> values, List<V> unassginedVariables) {
        Map<D, Integer> res = new HashMap<>();
        for (D val : values) {
            int       counter         = 0;
            Map<V, D> localAssignment = new HashMap<>(csp.getAssignments());
            localAssignment.put(variable, val);
            for (var unassginedVariable : unassginedVariables) {
                Map<V, D> moreLocalAssignment = new HashMap<>(localAssignment);
                for (var domain : csp.getDomains().get(unassginedVariable)) {
                    moreLocalAssignment.put(unassginedVariable, domain);
                    if (!csp.consistent(unassginedVariable, moreLocalAssignment))
                        counter++;
                }
                res.put(val, counter);
            }
        }
        values.sort((o1, o2) -> {
            var val1 = res.get(o1);
            var val2 = res.get(o2);
            return Integer.compare(val1, val2);
        });
    }
}
