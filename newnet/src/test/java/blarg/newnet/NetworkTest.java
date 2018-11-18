package blarg.newnet;

import java.util.ArrayList;
import java.util.List;
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
    
    @Test
    public void testProcess_withTwoInputs() {
        Network networkTwoInputs = new Network();
        networkTwoInputs.addNeuron(new Neuron(0.186, 3.6, -10.0));
        Inputs inputs = new Inputs();
        inputs.add(0, 2104.0);
        inputs.add(1, 3.0);
        double output = networkTwoInputs.process(inputs);
        assertEquals(364.944, output, 0.1);
    }

    @Test
    public void testCalculateError() {
        List<Double[]> dataPoints = new ArrayList<>();
        Double[] dataPointA = {2104.0, 399.9};// All this array stuff is horrible
        Double[] dataPointB = {1600.0, 329.9};
        Double[] dataPointC = {2400.0, 369.0};
        dataPoints.add(dataPointA);
        dataPoints.add(dataPointB);
        dataPoints.add(dataPointC);
        
        Network errNetwork = new Network();
        errNetwork.addNeuron(new Neuron(0.18, 0));
        
        assertEquals(2058.0, errNetwork.calculateError(dataPoints), 1.0);
    }
//    @Test
//    public void testAdjust() {
//        Network adjNetwork = new Network();
//        adjNetwork.addNeuron(new Neuron(100.0, 150.0));// Based on example from http://jalammar.github.io/visual-interactive-guide-basics-neural-networks/
//        
//        
//        
//        double output = network.process()
//    }
}