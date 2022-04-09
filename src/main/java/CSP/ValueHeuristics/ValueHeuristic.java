package CSP.ValueHeuristics;

import CSP.CSP;

import java.util.List;

public abstract class ValueHeuristic<V, D> {
    protected final CSP<V, D> csp;

    protected ValueHeuristic(CSP<V, D> csp) {
        this.csp = csp;
    }

    public abstract void selection(V variable, List<V> unasignedVariables);
}
