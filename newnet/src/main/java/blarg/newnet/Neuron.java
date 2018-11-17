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

    public void setWeight(double weight) {
        this.weight = weight;
    }
    
    public void setBias(double bias) {
        this.bias = bias;
    }
}
