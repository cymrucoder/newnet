package blarg.newnet;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author cymrucoder
 */
public class NeuronTest {
    
    Neuron neuron;
    
    public NeuronTest() {
        neuron = new Neuron();
        List<Double> weights = new ArrayList<>();
        weights.add(1.5);
        neuron.setWeights(weights);
        neuron.setBias(2.0);
    }

    @Test
    public void testProcess() {
        double output = neuron.process(3.0);
        assertEquals(6.5, output, 0.01);
    }
}
