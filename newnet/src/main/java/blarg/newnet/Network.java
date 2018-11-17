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
    
    public void adjustForError(double error) {
        neurons.get(0).adjustForError(error);
    }
    
    public void undoAdjust() {
        neurons.get(0).undoAdjust();
    }
    
    public static void main(String[] args) {
        Network network = new Network();
        network.addNeuron(new Neuron(0.18, 0.0));// Based on example from http://jalammar.github.io/visual-interactive-guide-basics-neural-networks/
        
        List<Double[]> dataPoints = new ArrayList<>();
        Double[] dataPointA = {2104.0, 399.9};// All this array stuff is horrible
        Double[] dataPointB = {1600.0, 329.9};
        Double[] dataPointC = {2400.0, 369.0};
        dataPoints.add(dataPointA);
        dataPoints.add(dataPointB);
        dataPoints.add(dataPointC);
        
        double lowestError = network.calculateError(dataPoints);
        
        System.out.println("Start error: " + lowestError);
        
        for (int i = 0; i < 20000; i++) {
            network.adjustForError(0.0);
            double error = network.calculateError(dataPoints);
            //System.out.println("Itr " + i + " error: " + error);
            
            if (error < lowestError) {
                System.out.println("Itr " + i + " error " + error + ", lower than " + lowestError + ", replacing");
                lowestError = error;
            } else {
                network.undoAdjust();
            }
        }
    }
}
