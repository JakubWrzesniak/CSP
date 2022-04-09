package CSP;
import java.util.*;
import java.util.List;

public abstract class CSP <V, D>{
    private final List<V>                        variables;
    private       Map<V, List<D>>                domains;
    private final Map<V, List<Constraint<V, D>>> constraints;

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

    public void addConstraint(Constraint<V, D> constraint){
        for(V variable: constraint.variables){
            if(!variables.contains(variable)){
                throw new IllegalArgumentException("Variable in constraint not in CSP");
            } else {
                constraints.get(variable).add(constraint);
            }
        }
    }

    public boolean consistent(V variable, Map<V, D> assignment){
        return constraints.get(variable).stream().allMatch(v -> v.satisfied(assignment));
    }

    public List<V> getVariables(){
        return variables;
    }

    public Map<V, List<D>> getDomains() {
        return domains;
    }

    public abstract Map<V, D> getAssignments();

    public abstract int getSize();

    public void setDomains(Map<V, List<D>> domains) {
        this.domains = domains;
    }

    public List<Constraint<V,D>> getConstraints(V variable){
        return constraints.get(variable);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " - " + getSize() + "x" + getSize();
    }
}
