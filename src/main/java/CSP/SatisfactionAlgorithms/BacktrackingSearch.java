package CSP.SatisfactionAlgorithms;

import CSP.CSP;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

import static CSP.SatisfactionAlgorithms.SatisfactionAlgorithm.HeuristicMode.DYNAMIC;
import static CSP.SatisfactionAlgorithms.SatisfactionAlgorithm.HeuristicMode.STATIC;

public class BacktrackingSearch <V,D> extends SatisfactionAlgorithm<V,D> {

    public BacktrackingSearch(CSP<V,D> csp){
        super(csp);
    }

    @Override
    public List<Map<V, D>> satisfy() {
        results = new ArrayList<>();
        var values = csp.getAssignments().keySet();
        var     filteredAssignment = csp.getVariables().stream().filter(v -> !values.contains(v)).toList();
        var variables = new LinkedList<>(filteredAssignment);
        if(getVariableHeuristicMode() == STATIC) variableSelection(variables);
        Instant start              = Instant.now();
        backtrackingSearch(csp.getAssignments(), variables);
        Instant  end         = Instant.now();
        Duration timeElapsed = Duration.between(start, end);
        System.out.println(Timestamp.from(Instant.now()) + " Finished Backtracking in " + timeElapsed.toMillis() + " milliseconds");
        return results;
    }


    private void backtrackingSearch(Map<V,D> assignment, LinkedList<V> unassignedVariables) {
        if(unassignedVariables.isEmpty()) {
            System.out.println(Timestamp.from(Instant.now()) + " - Found new result: " + (results.size() + 1));
            this.results.add(assignment);
            return;
        }
        if(getVariableHeuristicMode() == DYNAMIC) variableSelection(unassignedVariables);
        V unassigned = unassignedVariables.pop();
        if(getValueHeuristicMode() == DYNAMIC) valueSelection(unassigned, csp.getDomains().get(unassigned), unassignedVariables);
        for(D value : csp.getDomains().get(unassigned)){
            Map<V, D> localAssignment = new HashMap<>(assignment);
            localAssignment.put(unassigned, value);
            if(csp.consistent(unassigned, localAssignment))
                backtrackingSearch(localAssignment, unassignedVariables);
        }
        unassignedVariables.push(unassigned);
    }

}
