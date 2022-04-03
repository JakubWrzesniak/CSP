package CSP.SatisfactionAlgorithms;

import CSP.CSP;
import CSP.ValueHeuristics.ValueHeuristic;
import CSP.VariableHeuristics.VariableHeuristic;
import org.javatuples.Pair;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import static CSP.SatisfactionAlgorithms.SatisfactionAlgorithm.HeuristicMode.NONE;

public  abstract class  SatisfactionAlgorithm <V,D> {
    protected    List<Map<V,D>> results;
    protected final CSP<V,D>                                    csp;
    protected       Pair<VariableHeuristic<V,D>, HeuristicMode> variableHeuristic;
    protected       Pair<ValueHeuristic<V,D>, HeuristicMode>    valueHeuristic;

    public SatisfactionAlgorithm(CSP<V,D> csp) {
        this.csp = csp;
    }

    public abstract List<Map<V,D>> satisfy();
    public int getSize(){
        return csp.getSize();
    }

    public enum HeuristicMode {
        STATIC, DYNAMIC, NONE
    }


    public SatisfactionAlgorithm<V,D> withVariableSelection(Class<? extends VariableHeuristic> valueSelection, HeuristicMode mode){
        try {
            this.variableHeuristic = new Pair<>(
                    ((Class<VariableHeuristic<V, D>>) valueSelection).getDeclaredConstructor(CSP.class).newInstance(csp), mode);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return this;
    }

    public SatisfactionAlgorithm<V,D> withValueSelection(Class<? extends ValueHeuristic> valueSelection, HeuristicMode mode){
        try {
            this.valueHeuristic = new Pair<>(
                    ((Class<ValueHeuristic<V, D>>) valueSelection).getDeclaredConstructor(CSP.class).newInstance(csp), mode);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return this;
    }

    public HeuristicMode getVariableHeuristicMode(){
        if (variableHeuristic == null){
            return NONE;
        }
        return variableHeuristic.getValue1();
    }

    public HeuristicMode getValueHeuristicMode(){
        if (valueHeuristic == null){
            return NONE;
        }
        return valueHeuristic.getValue1();
    }

    public void variableSelection(List<V> variables){
        variableHeuristic.getValue0().selection(variables);
    }

    public void valueSelection(V variable, List<D> values, List<V> unassignedVariables){
        valueHeuristic.getValue0().selection(variable, values,  unassignedVariables);
    }
}
