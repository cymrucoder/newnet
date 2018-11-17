package blarg.newnet;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author cymrucoder
 */
public class NeuronTest {
    
    Neuron neuron;
    
    public NeuronTest() {
        neuron = new Neuron(1.5, 2.0);
    }
    
    @Test
    public void testSomeMethod() {
        double output = neuron.process(3.0);
        assertEquals(6.5, output, 0.01);
    }    
}
