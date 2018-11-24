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

    @Test
    public void testProcess() {
        Network networkOneNeuron = new Network();
        Layer layer = new Layer();
        layer.addNeuron(new Neuron(1.5, 2.0));
        networkOneNeuron.addLayer(layer);
        ValueTracker vt = new ValueTracker();
        vt.add(0, 3.0);
        double output = networkOneNeuron.process(vt).get(0);
        assertEquals(6.5, output, 0.01);
    }
    
    @Test
    public void testProcess_withTwoInputs() {
        Network networkTwoInputs = new Network();
        Layer layer = new Layer();
        layer.addNeuron(new Neuron(0.186, 3.6, -10.0));
        networkTwoInputs.addLayer(layer);
        ValueTracker inputs = new ValueTracker();
        inputs.add(0, 2104.0);
        inputs.add(1, 3.0);
        ValueTracker outputs = networkTwoInputs.process(inputs);
        assertEquals(364.944, outputs.get(0), 0.1);
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
        Layer layer = new Layer();
        layer.addNeuron(new Neuron(0.18, 0));
        errNetwork.addLayer(layer);
        
        assertEquals(2058.0, errNetwork.calculateError(dataPoints), 1.0);
    }
    
    @Test
    public void testProcess_withTwoLayers() {
        Network networkTwoLayers = new Network();
        Layer layer1 = new Layer();
        layer1.addNeuron(new Neuron(0.7, -0.3));
        Layer layer2 = new Layer();
        layer2.addNeuron(new Neuron(1.4, 0.5));
        networkTwoLayers.addLayer(layer1);
        networkTwoLayers.addLayer(layer2);
        ValueTracker inputs = new ValueTracker();
        inputs.add(0, 3.0);
        ValueTracker outputs = networkTwoLayers.process(inputs);
        assertEquals(3.02, outputs.get(0), 0.01);
    }
}