import java.util.ArrayList;
import java.util.List;

public class Domain<T>  {
    private final List<T> values;

    public Domain(){
        values = new ArrayList<>();
    }

    public List<T> getValues(){
        return values;
    }
}
