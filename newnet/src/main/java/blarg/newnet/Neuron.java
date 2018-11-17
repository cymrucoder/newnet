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

    // Weight and bias probably shouldn't be calculated in Network, should pass in error then let Neuron work it out
    public void setWeight(double weight) {
        this.weight = weight;
    }
    
    public void setBias(double bias) {
        this.bias = bias;
    }
    
    public void adjustForError(double error) {
        
    }
}
