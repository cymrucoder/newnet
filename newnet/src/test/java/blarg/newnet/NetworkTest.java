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
        Neuron neuron = new Neuron();
        List<Double> weights = new ArrayList<>();
        weights.add(1.5);
        neuron.setWeights(weights);
        neuron.setBias(2.0);
        layer.addNeuron(neuron);
        networkOneNeuron.addLayer(layer);
        ValueTracker vt = new ValueTracker();
        vt.add(0, 3.0);
        double output = networkOneNeuron.process(vt).get(0);
        assertEquals(6.5, output, 0.01);
    }
    
    @Test
    public void testProcess_withTwoInputs() {
        Network networkTwoInputs = new Network();
        Neuron neuron = new Neuron();
        List<Double> weights = new ArrayList<>();
        weights.add(0.186);
        weights.add(-10.0);
        neuron.setWeights(weights);
        neuron.setBias(3.6);
        Layer layer = new Layer();        
        layer.addNeuron(neuron);
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
        Neuron neuron = new Neuron();
        List<Double> weights = new ArrayList<>();
        weights.add(0.18);
        neuron.setWeights(weights);
        neuron.setBias(0.0);
        Layer layer = new Layer();        
        layer.addNeuron(neuron);
        errNetwork.addLayer(layer);
        
        assertEquals(2058.0, errNetwork.calculateError(dataPoints), 1.0);
    }
    
    @Test
    public void testProcess_withTwoLayers() {
        Network networkTwoLayers = new Network();
        
        Neuron neuronA = new Neuron();
        List<Double> weightsA = new ArrayList<>();
        weightsA.add(0.7);
        neuronA.setWeights(weightsA);
        neuronA.setBias(-0.3);
        
        Layer layer1 = new Layer();
        layer1.addNeuron(neuronA);
        
        Neuron neuronB = new Neuron();
        List<Double> weightsB = new ArrayList<>();
        weightsB.add(1.4);
        neuronB.setWeights(weightsB);
        neuronB.setBias(0.5);
        
        Layer layer2 = new Layer();
        layer2.addNeuron(neuronB);
        
        networkTwoLayers.addLayer(layer1);
        networkTwoLayers.addLayer(layer2);
        
        ValueTracker inputs = new ValueTracker();
        inputs.add(0, 3.0);
        ValueTracker outputs = networkTwoLayers.process(inputs);
        assertEquals(3.02, outputs.get(0), 0.01);
    }

    @Test
    public void testProcess_withTwoInputsOnlyOneConnected() {
        Network networkOneConnectedInput = new Network();
        Neuron neuron = new Neuron();
        neuron.addConnection(0);
        Layer layer = new Layer();        
        layer.addNeuron(neuron);
        networkOneConnectedInput.addLayer(layer);
        ValueTracker inputs = new ValueTracker();
        inputs.add(0, 3.0);
        inputs.add(1, 7.0);
        ValueTracker outputs = networkOneConnectedInput.process(inputs);
        assertEquals(4.0, outputs.get(0), 0.1);
    }
    
    @Test
    public void testProcess_withSigmoidFunction_shouldReturnOne() {// Neuron works out 4.5, which is more than 0, so output 1
        Network networkOneNeuron = new Network();
        Layer layer = new Layer();
        Neuron neuron = new Neuron();
        List<Double> weights = new ArrayList<>();
        weights.add(1.5);
        neuron.setWeights(weights);
        neuron.setBias(-2.0);
        neuron.setUseActivationFunction(true);
        layer.addNeuron(neuron);
        networkOneNeuron.addLayer(layer);
        ValueTracker vt = new ValueTracker();
        vt.add(0, 3.0);
        double output = networkOneNeuron.process(vt).get(0);
        assertEquals(1.0, output, 0.01);
    }
    
    @Test
    public void testProcess_withSigmoidFunction_shouldReturnZero() {// Neuron works out -0.5, which is more than 0, so output 0
        Network networkOneNeuron = new Network();
        Layer layer = new Layer();
        Neuron neuron = new Neuron();
        List<Double> weights = new ArrayList<>();
        weights.add(1.5);
        neuron.setWeights(weights);
        neuron.setBias(-5.0);
        neuron.setUseActivationFunction(true);
        layer.addNeuron(neuron);
        networkOneNeuron.addLayer(layer);
        ValueTracker vt = new ValueTracker();
        vt.add(0, 3.0);
        double output = networkOneNeuron.process(vt).get(0);
        assertEquals(0.0, output, 0.01);
    }
}