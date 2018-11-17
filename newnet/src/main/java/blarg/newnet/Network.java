package blarg.newnet;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cymrucoder
 */
public class Network {

    List<Neuron> neurons;
    
    public Network() {
      neurons = new ArrayList<>();  
    }
    
    public void addNeuron(Neuron neuron) {
        neurons.add(neuron);
    }
    
    public double process(double input) {
        double tracker = input;
        
        for (Neuron neuron : neurons) {
            tracker = neuron.process(input);
        }        
        return tracker;
    }
}
