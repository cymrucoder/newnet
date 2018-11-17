package blarg.newnet;

import java.util.Random;

/**
 *
 * @author cymrucoder
 */
public class Neuron {

    double weight;
    double bias;
    
    double oldWeight;
    double oldBias;
    Random r;
        
    public Neuron(double weight, double bias) {
        this.weight = weight;
        this.bias = bias;
        this.oldWeight = weight;
        this.oldBias = bias;
        r = new Random();
    }

    public double process(double input) {
         return (weight * input) + bias;
    }

    // Weight and bias probably shouldn't be calculated in Network, should pass in error then let Neuron work it out
    public void setWeight(double weight) {
        this.weight = weight;
    }
    
    public void setBias(double bias) {
        this.bias = bias;
    }
    
    public void adjustForError(double error) {
        int weightOrBias = r.nextInt(2);
        
        oldWeight = weight;
        oldBias = bias;
        
        switch (weightOrBias) {
            case 0:
                if (weight == 0.0) {
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
                break;
        }
    }
    
    public void undoAdjust() {
        weight = oldWeight;
        bias = oldBias;
    }
}
