package blarg.newnet;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cymrucoder
 */
public class Layer {

    List<Neuron> neurons;
    
    public Layer() {
        neurons = new ArrayList<>();
    }
    
    public void addNeuron(Neuron neuron) {
        neurons.add(neuron);
    }
    
    public ValueTracker process(ValueTracker inputs) {
        ValueTracker outputs = new ValueTracker();
        
        for (int i = 0; i < neurons.size(); i++) {
            outputs.add(i, neurons.get(i).process(inputs));
        }        
        return outputs;
    }

    void adjustForError() {
        for (Neuron neuron : neurons) {
            neuron.adjustForError(0);
        }
    }

    void undoAdjust() {
        for (Neuron neuron : neurons) {
            neuron.undoAdjust();
        }
    }
    
    @Override
    public String toString() {
        String output = "";
        for (Neuron neuron : neurons) {
            output += neuron.toString() + " \n";
        }        
        
        return output;
    }

    public int getNumberOfNeurons() {
        return neurons.size();
    }
}
