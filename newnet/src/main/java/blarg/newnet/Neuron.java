package blarg.newnet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author cymrucoder
 */
public class Neuron {

    private class Weight {
        private double weight;
        private double oldWeight;
        
        public Weight(double weight) {
            this.weight = weight;
            this.oldWeight = weight;
        }
        
        public double get() {
            return weight;
        }
        
        public void set(double weight) {
            oldWeight = this.weight;
            this.weight = weight;
        }
        
        public void reset() {
            weight = oldWeight;
        }
    }
    
    //List<Weight> weights;
    Map<Integer, Weight> weights;
    double bias;
    double oldBias;
    Random r;
    boolean useActivationFunction;
        
//    public Neuron(double weight, double bias) {
//        this.bias = bias;
//        weights = new ArrayList<>();
//        weights.add(new Weight(weight));
//        r = new Random();
//    }
//    
//    public Neuron(double weight, double bias, double weight2) {
//        this.bias = bias;
//        weights = new ArrayList<>();
//        weights.add(new Weight(weight));
//        weights.add(new Weight(weight2));
//        r = new Random();
//    }
    
    public Neuron() {
        r = new Random();
        weights = new HashMap<>();
        bias = 1.0;
        useActivationFunction = false;
    }
    
    public void addConnection(int index) {
        if (!weights.containsKey(index)) {
            weights.put(index, new Weight(1.0));
        }
    }

    public double process(double input) {
         return (weights.get(0).get() * input) + bias;
    }
    
    double process(ValueTracker inputs) {
        double tracker = 0.0;

        for (Map.Entry<Integer, Weight> entry : weights.entrySet()) {// Adjust all inputs for weight and add them together
            tracker += (inputs.get(entry.getKey()) * entry.getValue().get());// TODO What is inputs doesn't have the right indexes?  Would crash atm
        }
        tracker += bias;
        
        if (useActivationFunction) {
            if (tracker > 0.0) {
                return 1.0;
            } else {
                return 0.0;
            }
        } else {
            return tracker;
        }
    }

    public void adjustForError(double error) {
        int weightOrBias = r.nextInt(2);
        oldBias = bias;
                
        switch (weightOrBias) {
            case 0:// TODO magic numbers actually just bin this whole thing because it's awful
                //int index = r.nextInt(weights.size());
                for (int i = 0; i < weights.size(); i++) {
                    double weight = weights.get(i).get();
                    if (weight == 0.0) {// Multiplying doesn't do anything if it's already 0
                        int upOrDown = r.nextInt(2);
                        if (upOrDown == 0) {
                            weight += r.nextDouble();
                        }
                        else if (upOrDown == 1) {
                            weight -= r.nextDouble();
                        }
                    } else {
                        weight *= ((99.5 + r.nextDouble())) / 100.0;
                    }
                    weights.get(i).set(weight);
                }
                break;
            case 1:
                int upOrDown = r.nextInt(2);
                if (bias == 0.0) {
                    if (upOrDown == 0) {
                        bias += r.nextDouble();
                    }
                    else if (upOrDown == 1) {
                        bias -= r.nextDouble();
                    }
                } else {
                    bias *= ((99.5 + r.nextDouble())) / 100.0;
                }
                break;
            default:
                System.out.println("What how did this happen");
                System.exit(1);
                break;
        }
    }
    
    public void undoAdjust() {
        //weight = oldWeight;// TODO This only works when there's one weight really.  Can't just loop through them all because you'll start resetting stuff when you shouldn't
        for (Integer weightIndex : weights.keySet()) {
            weights.get(weightIndex).reset();
        }
        bias = oldBias;
    }    
    
    @Override
    public String toString() {
        String output = "Node start ";
        
        for (Integer weightIndex : weights.keySet()) {
            output += weights.get(weightIndex) + " ";
        }
        
        output += bias + " ";
        
        return output;
    }
    
    public void setUseActivationFunction(boolean useActivationFunction) {
        this.useActivationFunction = useActivationFunction;
    }
    
    protected void setWeights(List<Double> newWeights) {
        weights.clear();
        
        for (int i = 0; i < newWeights.size(); i++) {
            weights.put(i, new Weight(newWeights.get(i)));
        }
    }
    
    protected void setBias(double newBias) {
        bias = newBias;
    }
}
