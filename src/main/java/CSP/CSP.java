package CSP;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class CSP <V, D>{
    private final List<V>                        variables;
    private final Map<V, List<D>>                domains;
    private final Map<V, List<Constraint<V, D>>> constraints;
    private List<Map<V,D>>                 results;

    public CSP(List<V> variables, Map<V, List<D>> domains) {
        this.variables = variables;
        this.domains = domains;
        constraints = new HashMap<>();
        for (V variable : variables) {
            constraints.put(variable, new ArrayList<>());
            if (!domains.containsKey(variable)) {
                throw new IllegalArgumentException("Every variable should have a domain assigned to it.");
            }
        }
    }

    /**
     * Add new constraint for existing values
     * @param constraint new constraint
     */
    public void addConstraint(Constraint<V, D> constraint){
        for(V variable: constraint.variables){
            if(!variables.contains(variable)){
                throw new IllegalArgumentException("Variable in constraint not in CSP.CSP");
            } else {
                constraints.get(variable).add(constraint);
            }
        }
    }

    /**
     * Check if set of values could be assigned into
     * @param variable - specific node
     * @param assignment - map off values with assignments
     * @return is the value assigment is consistent by checking all constraints
     */
    public boolean consistent(V variable, Map<V, D> assignment){
        return constraints.get(variable).stream().allMatch(v -> v.satisfied(assignment));
    }

    public List<Map<V,D>> backtrackingSearch(Map<V,D> assignment){
        results = new ArrayList<>();
        var values = assignment.keySet();
        var filteredAssignment = variables.stream().filter(v -> !values.contains(v)).toList();
        backtrackingSearch(assignment, new LinkedList<>(filteredAssignment));
        return results;
    }

    private Map<V,D> backtrackingSearch(Map<V,D> assignment, LinkedList<V> unassignedVariables) {
        if(unassignedVariables.isEmpty()) {
            return assignment;
        }
        V unassigned = unassignedVariables.pop();
        unassignedVariables.remove(unassigned);
        for(D value : domains.get(unassigned)){
            Map<V, D> localAssignment = new HashMap<>(assignment);
            localAssignment.put(unassigned, value);
            if(consistent(unassigned, localAssignment)){
               Map<V,D> result = backtrackingSearch(localAssignment, unassignedVariables);
                if(result != null){
                    System.out.println(Timestamp.from(Instant.now()) + " - Found new result: " + (results.size() + 1));
                    this.results.add(Map.copyOf(result));
                }
            }
        }
        unassignedVariables.push(unassigned);
        return null;
    }
}
