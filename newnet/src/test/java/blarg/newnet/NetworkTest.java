package blarg.newnet;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author cymrucoder
 */
public class NetworkTest {

    Network network;    
    
    public NetworkTest() {
        network = new Network();
        network.addNeuron(new Neuron(1.5, 2.0));
    }

    @Test
    public void testProcess() {
        double output = network.process(3.0);
        assertEquals(6.5, output, 0.01);
    }

}