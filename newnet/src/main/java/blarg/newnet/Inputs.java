package blarg.newnet;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cymrucoder
 */
public class Inputs {

    List<Double> list;

    public Inputs() {
        list = new ArrayList<>();
    }

    public double get(int position) {
        if (list.size() < position) {
            return 0.0;
        } else {
            return list.get(position);
        }
    }

    public void add(int position, double input) {
        list.add(position, input);
    }
}
