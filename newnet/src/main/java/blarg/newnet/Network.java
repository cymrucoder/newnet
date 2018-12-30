package blarg.newnet;

import com.opencsv.CSVReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cymrucoder
 */
public class Network {

    List<Layer> layers;
    NetworkView view;
    
    public Network() {
        layers = new ArrayList<>();
    }
    
    public Network(Reader reader) throws IOException {
        CSVReader csvReader = new CSVReader(reader);
        List<String[]> readAll = csvReader.readAll();
        layers = new ArrayList<>();
        
        for (int i = 0; i < readAll.get(0).length - 1; i++) {
            layers.add(new Layer());
        }
        
        //Set up input nodes
        for (int i = 1; i < readAll.size(); i++) {// This gets a bit annoying because of the first line with headers
            if (readAll.get(i)[0] != null && !readAll.get(i)[0].isEmpty()) {
                Neuron neuron = new Neuron();
                neuron.addConnection(i - 1);
                neuron.setUseActivationFunction(true);
                layers.get(0).addNeuron(neuron);
            }
        }
        
        for (int i = 1; i < readAll.size(); i++) {
            if (readAll.get(i)[1] != null && !readAll.get(i)[1].isEmpty()) {
                Neuron neuron = new Neuron();                
                String connections = readAll.get(i)[1];
                
                if ("all".equals(connections)) {
                    for (int j = 0; j < layers.get(0).getNumberOfNeurons(); j++) {
                        neuron.addConnection(j);
                    }
                }                
                neuron.setUseActivationFunction(true);
                layers.get(1).addNeuron(neuron);
            }
        }
        
        for (int i = 1; i < readAll.size(); i++) {
            if (readAll.get(i)[2] != null && !readAll.get(i)[2].isEmpty()) {
                Neuron neuron = new Neuron();                
                String connections = readAll.get(i)[2];
                
                if ("all".equals(connections)) {
                    for (int j = 0; j < layers.get(1).getNumberOfNeurons(); j++) {
                        neuron.addConnection(j);
                    }
                }                
                neuron.setUseActivationFunction(true);
                layers.get(2).addNeuron(neuron);
            }
        }
    }
    
    public void enableView() {
        view = new NetworkView(this);
    }
    
    public void updateView() {
        if (view != null) {
            view.drawNetwork();
        }
    }
    
    public void addLayer(Layer layer) {
        layers.add(layer);
    }
    
    public ValueTracker process(ValueTracker inputs) {
        ValueTracker tracker = inputs;

        for (Layer layer : layers) {
            tracker = layer.process(tracker);
        }        
        return tracker;
    }
    
    public double calculateError(List<Double[]> testPoints) {
        if (testPoints.isEmpty()) { return 0.0; }// Don't want to divide by 0
        
        double totalError = 0.0;
        
        for (Double[] testPoint : testPoints) {
            int noTestPoints = testPoint.length - 1;
            ValueTracker vt = new ValueTracker();
            
            for (int i = 0; i < noTestPoints; i++) {
                vt.add(i, testPoint[i]);
            }            
            //vt.add(0, testPoint[0]);
            //double prediction = process(testPoint[0]);
            double prediction = process(vt).get(0);
            totalError += ((testPoint[noTestPoints] - prediction) * (testPoint[noTestPoints] - prediction));
        }                
        return totalError / (double) testPoints.size();
    }
    
    public void adjustForError(double error) {// TODO doesn't work atm
        //neurons.get(0).adjustForError(error);
        for (Layer layer : layers) {
            layer.adjustForError();
        }
    }
    
    public void undoAdjust() {// TODO doesn't work atm
        //neurons.get(0).undoAdjust();
        for (Layer layer : layers) {
            layer.undoAdjust();
        }
    }
    
    public static void main(String[] args) {        
        // Based on example from http://jalammar.github.io/visual-interactive-guide-basics-neural-networks/
        Network network = new Network();
        Layer layer = new Layer();
        layer.addNeuron(new Neuron());
        network.addLayer(layer);
        
        List<Double[]> dataPoints = new ArrayList<>();
        Double[] dataPointA = {2104.0, 3.0, 399.9};// All this array stuff is horrible
        Double[] dataPointB = {1600.0, 3.0, 329.9};
        Double[] dataPointC = {2400.0, 3.0, 369.0};
        Double[] dataPointD = {1416.0, 2.0, 232.0};
        Double[] dataPointE = {3000.0, 4.0, 539.0};
        Double[] dataPointF = {1985.0, 4.0, 299.9};
        dataPoints.add(dataPointA);
        dataPoints.add(dataPointB);
        dataPoints.add(dataPointC);
        dataPoints.add(dataPointD);
        dataPoints.add(dataPointE);
        dataPoints.add(dataPointF);
        
        double lowestError = network.calculateError(dataPoints);
        
        System.out.println("Start error: " + lowestError);
        
        for (int i = 0; i < 1; i++) {
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

    @Override
    public String toString() {
        String output = "";
        
        for (Layer layer : layers) {
            output += layer.toString();
        }        
        return output;        
    }
}
