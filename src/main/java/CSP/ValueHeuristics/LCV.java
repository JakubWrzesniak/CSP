package CSP.ValueHeuristics;

import CSP.CSP;

import java.util.*;

public class LCV<V,D> extends ValueHeuristic<V,D>{
    public LCV(CSP<V, D> csp) {
        super(csp);
    }

    @Override
    public void selection(V variable, List<V> unassignedValues) {
        var relatedValues = csp.getRelatedNodes(variable);
        var filteredNodes =   unassignedValues.stream().filter(relatedValues::contains).toList() ;
        Map<D, Integer> res = new HashMap<>();
        for (D val : csp.getDomains().get(variable)) {
            int       counter         = 0;
            Map<V, D> localAssignment = new HashMap<>(csp.getAssignments());
            localAssignment.put(variable, val);
            for (var unassginedVariable : filteredNodes) {
                Map<V, D> moreLocalAssignment = new HashMap<>(localAssignment);
                for (var domain : csp.getDomains().get(unassginedVariable)) {
                    moreLocalAssignment.put(unassginedVariable, domain);
                    if (csp.consistent(unassginedVariable, moreLocalAssignment))
                        counter++;
                }
                res.put(val, counter);
            }
        }
        csp.getDomains().get(variable).sort((o1, o2) -> {
            var val1 = res.getOrDefault(o1, -1);
            var val2 = res.getOrDefault(o2, -1);
            return Integer.compare(val1, val2);
        });
    }
}
