package CSP.SatisfactionAlgorithms;

import CSP.CSP;

import java.util.*;

import static CSP.SatisfactionAlgorithms.SatisfactionAlgorithm.HeuristicMode.DYNAMIC;

public class BacktrackingSearch <V,D> extends SatisfactionAlgorithm<V,D> {

    public BacktrackingSearch(CSP<V,D> csp){
        super(csp);
    }

    @Override
    protected void search(Map<V,D> assignment, LinkedList<V> unassignedVariables) {
        if(unassignedVariables.isEmpty()) {
            addNewResult(assignment);
            return;
        }
        if(getVariableHeuristicMode() == DYNAMIC) variableSelection(unassignedVariables);
        V unassigned = unassignedVariables.pop();
        if(getValueHeuristicMode() == DYNAMIC && !unassignedVariables.isEmpty()) valueSelection(unassigned, unassignedVariables);
        for(D value : csp.getDomains().get(unassigned)){
            totalVisitedNodes++;
            Map<V, D> localAssignment = new HashMap<>(assignment);
            localAssignment.put(unassigned, value);
            if(csp.consistent(unassigned, localAssignment)) {
                search(localAssignment, unassignedVariables);
            } else{
                totalNumberOfBacks++;
            }
        }
        unassignedVariables.push(unassigned);
    }

}
