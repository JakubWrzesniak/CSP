package CSP.SatisfactionAlgorithms;

import CSP.CSP;
import CSP.ValueHeuristics.ValueHeuristic;
import CSP.VariableHeuristics.VariableHeuristic;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.javatuples.Pair;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static CSP.SatisfactionAlgorithms.SatisfactionAlgorithm.HeuristicMode.NONE;
import static CSP.SatisfactionAlgorithms.SatisfactionAlgorithm.HeuristicMode.STATIC;

public  abstract class  SatisfactionAlgorithm <V,D> {
    public static final List<String> HEADERS = List.of("Date", "CSP", "Size", "Solver", "Variable Heuristic", "Value Heuristic", "Solutions", "Duration time", "Number of backs", "Number of checked values", "Time of First solution", "Number of backs to first solution", "Number of checked values to firs solution");
    private final       List<Map<V,D>> results = new ArrayList<>();
    protected final     CSP<V,D>       csp;
    protected           Pair<VariableHeuristic<V,D>, HeuristicMode> variableHeuristic;
    protected           Pair<ValueHeuristic<V,D>, HeuristicMode> valueHeuristic;
    protected           Instant startTime         = null;
    protected long      firstFoundTime    = 0;
    protected int       firstFoundNumberOfBacks = 0;
    protected int       firstFoundVisitedNodes  = 0;
    protected long      totalDurationTime = 0;
    protected int       totalNumberOfBacks = 0;
    protected int       totalVisitedNodes  = 0;

    public SatisfactionAlgorithm(CSP<V,D> csp) {
        this.csp = csp;
    }

    public  List<Map<V,D>> satisfy(){
        var values = csp.getAssignments().keySet();
        var     filteredAssignment = csp.getVariables().stream().filter(v -> !values.contains(v)).toList();
        var variables = new LinkedList<>(filteredAssignment);
        if(getVariableHeuristicMode() == STATIC) variableSelection(variables);
        System.out.println("------------" + this.getClass().getSimpleName() + "------------");
        System.out.println(Instant.now() + " - " + "Start searching...");
        startTime              = Instant.now();
        search(csp.getAssignments(), variables);
        Instant  end         = Instant.now();
        Duration timeElapsed = Duration.between(startTime, end);
        totalDurationTime = timeElapsed.toMillis();
        System.out.println(Instant.now() + " - " + "Finished in: " + totalDurationTime + " milliseconds");
        System.out.println(Instant.now() + " - " + "Found " + results.size() + " solutions");
        saveResults();
        return getResults();
    }

    protected abstract void search(Map<V,D> assignmetn, LinkedList<V> variables);

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

    public void valueSelection(V variable, List<V> unassignedVariables){
        valueHeuristic.getValue0().selection(variable, unassignedVariables);
    }

    protected void addNewResult(Map<V,D> result){
        if(results.isEmpty()){
            firstFoundTime = Duration.between(startTime, Instant.now()).toMillis();
            firstFoundNumberOfBacks = totalNumberOfBacks;
            firstFoundVisitedNodes = totalVisitedNodes;
        }
        results.add(result);
    }

    protected List<Map<V,D>> getResults(){
        return results;
    }

    public void printResults(){
            System.out.printf("""
                        Issue:                  %s
                        Type:                   %s
                        Variable Heuristic:     %s
                        Value Heuristic         %s
                        Total Duration time:    %d
                        FirstFound time:        %d
                        Number of Backs:        %d
                        Number of Nodes:        %d
                        %n""",
                csp.getClass().getSimpleName(),
                getClass().getSimpleName(),
                variableHeuristic != null ? variableHeuristic.getValue0().getClass().getSimpleName()  + " " + variableHeuristic.getValue1(): "brak",
                valueHeuristic != null ? valueHeuristic.getValue0().getClass().getSimpleName()  + " " + valueHeuristic.getValue1(): "brak",
                    totalDurationTime,
                firstFoundTime,
                    totalNumberOfBacks,
                    totalVisitedNodes
        );
    }

    public void saveResults(){
        File baseDirectory = Paths.get("Results").toAbsolutePath().toFile();
        File resultsFile   = new File(baseDirectory, "CSP_NEW" + ".csv");
        boolean newFile = false;
        if (!baseDirectory.exists()) {
            baseDirectory.mkdir();
        }
        if(!resultsFile.exists()){
            try {
                newFile = resultsFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try(CSVPrinter csvPrinter = new CSVPrinter(new FileWriter(resultsFile, true), CSVFormat.DEFAULT.withDelimiter(';'))) {
            if(newFile){
                csvPrinter.printRecord(SatisfactionAlgorithm.HEADERS);
            }
                csvPrinter.printRecord(
                        LocalDateTime.now(),
                        csp.getClass().getSimpleName(),
                        getSize(),
                        this.getClass().getSimpleName(),
                        variableHeuristic != null ? variableHeuristic.getValue0().getClass().getSimpleName()  + " " + variableHeuristic.getValue1(): "brak",
                        valueHeuristic != null ? valueHeuristic.getValue0().getClass().getSimpleName()  + " " + valueHeuristic.getValue1(): "brak",
                        results.size(),
                        totalDurationTime,
                        totalNumberOfBacks,
                        totalVisitedNodes,
                        firstFoundTime,
                        firstFoundNumberOfBacks,
                        firstFoundVisitedNodes
                );
            System.out.println("Results saved! path: " + resultsFile.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString(){
        return String.format(
                "%s%s;%s;%s;%s:%d;%d;%d;%d\n",
                LocalDateTime.now(),
                csp,
                getClass().getSimpleName(),
                variableHeuristic != null ? variableHeuristic.getValue0().getClass().getSimpleName()  + " " + variableHeuristic.getValue1(): "brak",
                valueHeuristic != null ? valueHeuristic.getValue0().getClass().getSimpleName()  + " " + valueHeuristic.getValue1(): "brak",
                totalDurationTime,
                firstFoundTime,
                totalNumberOfBacks,
                totalVisitedNodes
        );
    }
}
