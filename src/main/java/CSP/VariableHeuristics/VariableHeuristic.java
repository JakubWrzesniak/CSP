package CSP.VariableHeuristics;

import CSP.CSP;

import java.util.List;

public abstract class VariableHeuristic<V, D> {
    protected final CSP<V, D> csp;

    protected VariableHeuristic(CSP<V, D> csp) {
        this.csp = csp;
    }

    abstract public void selection(List<V> variables);
}
