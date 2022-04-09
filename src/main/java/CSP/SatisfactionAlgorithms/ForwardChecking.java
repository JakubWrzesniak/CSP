package CSP.SatisfactionAlgorithms;

import CSP.CSP;

import java.util.*;
import java.util.stream.Collectors;

import static CSP.SatisfactionAlgorithms.SatisfactionAlgorithm.HeuristicMode.DYNAMIC;

public class ForwardChecking<V,D> extends SatisfactionAlgorithm<V, D> {

    public ForwardChecking(CSP<V,D> csp) {
        super(csp);
    }


    @Override
    protected void search(Map<V, D> assignmetn, LinkedList<V> variables) {
        search(assignmetn, variables, csp.getDomains());
    }

    private void search(Map<V,D> assignment, LinkedList<V> unassignedVariables, Map<V, List<D>> domains){
        if(unassignedVariables.isEmpty()) {
            addNewResult(assignment);
            return;
        }

       if(getVariableHeuristicMode() == DYNAMIC) variableSelection(unassignedVariables);
        var unassigned = unassignedVariables.pop();
        var relatedNodes = csp.getRelatedNodes(unassigned);
        var relatedDomains = domains.entrySet().stream().filter(entry -> relatedNodes.contains(entry.getKey())).collect(Collectors.toMap(Map.Entry::getKey,
                Map.Entry::getValue));
        var currentDomain = domains.get(unassigned);
        if(getValueHeuristicMode() == DYNAMIC && !unassignedVariables.isEmpty()) valueSelection(unassigned, unassignedVariables);
        domains.remove(unassigned);
        for(D currentValue : currentDomain){
            totalVisitedNodes++;
            Map<V, D> localAssignment = new HashMap<>(assignment);
            localAssignment.put(unassigned, currentValue);
            Map<V,List<D>> localDomain = new HashMap<>(domains);
            if(passed(relatedDomains.entrySet(), localAssignment, localDomain)) {
                csp.setDomains(localDomain);
                search(localAssignment, unassignedVariables, localDomain);
            }
        }
        domains.put(unassigned, currentDomain);
        unassignedVariables.push(unassigned);
    }

    private boolean passed(Set<Map.Entry<V,List<D>>> relatedDomains, Map<V, D> localAssignment, Map<V,List<D>> localDomain){
        for(var domainEntry : relatedDomains){
            var tempDomain = new ArrayList<D>();
            for(var value : domainEntry.getValue()) {
                var temAssignemt = new HashMap<>(localAssignment);
                temAssignemt.put(domainEntry.getKey(), value);
                if (csp.consistent(domainEntry.getKey(), temAssignemt)) {
                    tempDomain.add(value);
                }
            }
            if(tempDomain.isEmpty()){
                totalNumberOfBacks++;
                return false;
            }
            localDomain.put(domainEntry.getKey(), tempDomain);
        }
        return true;
    }
}
