package blarg.newnet;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cymrucoder
 */
public class ValueTracker {

    List<Double> values;

    public ValueTracker() {
        values = new ArrayList<>();
    }

    public double get(int position) {
        if (values.size() < position) {
            return 0.0;
        } else {
            return values.get(position);
        }
    }

    public void add(int position, double value) {
        values.add(position, value);
    }
    
    public int size() {
        return values.size();
    }
}
