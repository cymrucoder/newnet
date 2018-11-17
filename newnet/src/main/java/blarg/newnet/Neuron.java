package blarg.newnet;

/**
 *
 * @author cymrucoder
 */
public class Neuron {

    double weight;
    double bias;
        
    public Neuron(double weight, double bias) {
        this.weight = weight;
        this.bias = bias;
    }

    public double process(double input) {
         return (weight * input) + bias;
    }    
}
