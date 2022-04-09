package CSP.ValueHeuristics;

import CSP.CSP;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MPV<V,D> extends ValueHeuristic<V,D> {
    public MPV(CSP<V, D> csp) {
        super(csp);
    }

    @Override
    public void selection(V variable, List<V> unasignedVariables) {
        if(csp.getDomains().get(variable).size() > 1) {
            Map<D, Integer> countedDomains = new HashMap<>();
            for (var domainList : csp.getDomains().values()) {
                for (var domain : domainList) {
                    if (countedDomains.containsKey(domain)) {
                        countedDomains.put(domain, countedDomains.get(domain) + 1);
                    } else {
                        countedDomains.put(domain, 1);
                    }
                }
            }
            csp.getDomains().get(variable).sort((o1, o2) -> {
                var val1 = countedDomains.get(o1);
                var val2 = countedDomains.get(o2);
                return Integer.compare(val2, val1);
            });
        }
    }
}
