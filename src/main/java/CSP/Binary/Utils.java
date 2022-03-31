package CSP.Binary;

import org.javatuples.Pair;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class Utils {
    public static  Boolean[][] mapAsArray(Map<Point, Boolean> assignment, Dimension dimension){
        Boolean[][] array = new Boolean[dimension.width][dimension.height];
        if(assignment!= null) {
            for (var entry : assignment.entrySet()) {
                var k = entry.getKey();
                array[k.x][k.y] = entry.getValue();
            }
        }
        return array;
    }

    public  static void printArray(Boolean[][] array){
        for (Boolean[] ts : array) {
            for (Boolean t : ts) {
                System.out.print("[ ");
                System.out.print(t != null ? (t ? "1" : "0" ): " ");
                System.out.print(" ]");
            }
            System.out.println();
        }
    }

    public static Pair<Map<Point, Boolean>, Integer> readData(Path path){
        Map<Point, Boolean> results = new HashMap<>();
        AtomicReference<Integer> lineNumber = new AtomicReference<>(0);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(String.valueOf(path)))){
            var lines  = bufferedReader.lines();
            lines.forEach(line -> {
              String[] splitedLine = line.split("");
              for(int i = 0 ; i < splitedLine.length; i++){
                  if(splitedLine[i].equals("0")){
                      results.put(new Point(lineNumber.get(), i), false);
                  } else if (splitedLine[i].equals("1")){
                      results.put(new Point(lineNumber.get(), i), true);
                  }
              }
              lineNumber.updateAndGet(v -> v+1);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Pair<>(results, lineNumber.get());
    }
}
