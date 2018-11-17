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
    
    public double calculateError(List<Double[]> testPoints) {
        if (testPoints.isEmpty()) { return 0.0; }// Don't want to divide by 0
        
        double totalError = 0.0;
        
        for (Double[] testPoint : testPoints) {
            double prediction = process(testPoint[0]);
            totalError += ((testPoint[1] - prediction) * (testPoint[1] - prediction));
        }                
        return totalError / (double) testPoints.size();
    }
    
    public static void main(String[] args) {
        Network network = new Network();
        network.addNeuron(new Neuron(100.0, 150.0));// Based on example from http://jalammar.github.io/visual-interactive-guide-basics-neural-networks/
        
        System.out.println("Start " + network.process(2000.0));
    }
}
