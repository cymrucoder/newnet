package blarg.newnet;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author cymrucoder
 */
public class NeuronTest {
    
    Neuron neuron;
    
    public NeuronTest() {
        neuron = new Neuron(1.5, 2.0);
    }

    @Before
    public void before() {
        neuron.setWeight(1.5);
        neuron.setBias(2.0);
    }
    
    @Test
    public void testProcess() {
        double output = neuron.process(3.0);
        assertEquals(6.5, output, 0.01);
    }
    
    @Test
    public void testSetWeightAndBias() {
        assertEquals(6.5, neuron.process(3.0), 0.01);
        
        neuron.setWeight(5.0);
        neuron.setBias(6.0);
        
        assertEquals(21.0, neuron.process(3.0), 0.01);
    }
}
