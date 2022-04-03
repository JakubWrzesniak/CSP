package CSP.SatisfactionAlgorithms;

import CSP.CSP;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static CSP.SatisfactionAlgorithms.SatisfactionAlgorithm.HeuristicMode.DYNAMIC;
import static CSP.SatisfactionAlgorithms.SatisfactionAlgorithm.HeuristicMode.STATIC;

public class ForwardChecking<V,D> extends SatisfactionAlgorithm<V, D> {

    public ForwardChecking(CSP<V,D> csp) {
        super(csp);
    }


    @Override
    public List<Map<V, D>> satisfy() {
        results = new ArrayList<>();
        var values = csp.getAssignments().keySet();
        var     filteredAssignment = csp.getVariables().stream().filter(v -> !values.contains(v)).toList();
        var variables = new LinkedList<>(filteredAssignment);
        Instant start              = Instant.now();
        if(getVariableHeuristicMode() == STATIC) variableSelection(variables);
        forwardChecking(csp.getAssignments(), variables, csp.getDomains());
        Instant  end         = Instant.now();
        Duration timeElapsed = Duration.between(start, end);
        System.out.println(Timestamp.from(Instant.now()) + " Finished Backtracking in " + timeElapsed.toMillis() + " milliseconds");
        return results;
    }

    private void forwardChecking(Map<V,D> assignment, LinkedList<V> unassignedVariables, Map<V, List<D>> domains){
        if(unassignedVariables.isEmpty()) {
            System.out.println(Timestamp.from(Instant.now()) + " - Found new result: " + (results.size() + 1));
            this.results.add(assignment);
            return;
        }
        var newDomain = domains.entrySet().stream().filter(entry -> unassignedVariables.contains(entry.getKey())).collect(Collectors.toMap(Map.Entry::getKey,
                entry -> entry.getValue().stream().filter(v -> {
                    Map<V, D> localAssignment = new HashMap<>(assignment);
                    localAssignment.put(entry.getKey(), v);
                    return csp.consistent(entry.getKey(), localAssignment);
                }).collect(Collectors.toList()))
        );
        if(newDomain.values().stream().anyMatch(List::isEmpty)) return;
        csp.setDomains(newDomain);
        if(getVariableHeuristicMode() == DYNAMIC) variableSelection(unassignedVariables);
        V unassigned = unassignedVariables.pop();
        if(getValueHeuristicMode() == DYNAMIC) valueSelection(unassigned, newDomain.get(unassigned), unassignedVariables);
        for(D value : newDomain.get(unassigned)){
            Map<V, D> localAssignment = new HashMap<>(assignment);
            localAssignment.put(unassigned, value);
            if(csp.consistent(unassigned, localAssignment))
                forwardChecking(localAssignment, unassignedVariables, newDomain);
        }
        unassignedVariables.push(unassigned);
    }
}
