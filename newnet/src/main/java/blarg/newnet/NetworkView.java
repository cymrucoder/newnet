package blarg.newnet;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JPanel;

/**
 *
 * @author cymrucoder
 */
public class NetworkView extends JFrame {

    private static final int NETWORK_VIEW_WIDTH = 800;
    private static final int NETWORK_VIEW_HEIGHT = 400;
    
    private Network network;
    
    public static void main(String args[]) throws FileNotFoundException, IOException {
        Network network = new Network(new FileReader("networks\\smallnetwork.csv"));
        network.enableView();
        
    }
    
    public NetworkView(Network network) {
        this.network = network;
        network.adjustForError(0.0);
        DrawCanvas dc = new DrawCanvas();
        dc.setPreferredSize(new Dimension(NETWORK_VIEW_WIDTH, NETWORK_VIEW_HEIGHT));
        setContentPane(dc);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setTitle("Network");
        setVisible(true);
    }
    
    public void drawNetwork() {
        //this.boardInts = board;
        repaint();
    }    
    
    private class DrawCanvas extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            Graphics2D g2 = (Graphics2D) g;// Needs to be Graphics2D so we can use setStroke() later on
                        
            int layerOffset = (int) ((double) NETWORK_VIEW_WIDTH / (double) (network.layers.size() + 1));// This splits the panel up neatly and avoids the edges
            int neuronRadius = 20;
            
            for (int i = 0; i < network.layers.size(); i++) {
                int neuronOffset = (int) ((double) NETWORK_VIEW_HEIGHT / (double) (network.layers.get(i).getNumberOfNeurons() + 1));
                int previousLayerNeuronOffset = (i == 0 ? -1 : (int) ((double) NETWORK_VIEW_HEIGHT / (double) (network.layers.get(i - 1).getNumberOfNeurons() + 1)));// Can't check previous layer if we're on layer 0
                                
                for (int j = 0; j < network.layers.get(i).getNumberOfNeurons(); j++) {
                    g.setColor(Color.BLACK);
                    g.fillOval((layerOffset * (i + 1)) - neuronRadius, (neuronOffset * (j + 1)) - neuronRadius, neuronRadius * 2, neuronRadius * 2);
                    
                    double bias = network.layers.get(i).getNeuron(j).getBias();
                    
                    if (bias == 0.0) {// Green is +ive, red if -ive
                        g.setColor(Color.LIGHT_GRAY);
                    } else if (bias > 0.0) {
                        g.setColor(new Color(0.0f, 1.0f, 0.0f));
                    } else {
                        g.setColor(new Color(1.0f, 0.0f, 0.0f));
                    }
                    
                    g.fillOval((layerOffset * (i + 1)) - (neuronRadius / 2), (neuronOffset * (j + 1)) - (neuronRadius / 2), (neuronRadius), (neuronRadius));
                    
                    if (i == 0) {// Input layer will have proper lines at some point
                        
                    } else {
                        for (Map.Entry<Integer, Double> entry : network.layers.get(i).getNeuron(j).getWeights().entrySet()) {// Draw a line for each connection backwards from this node
                            float weight = entry.getValue().floatValue();
                            
                            if (weight == 0.0) {// Green is +ive, red if -ive, fade out grey if 0
                                g.setColor(Color.LIGHT_GRAY);
                                g2.setStroke(new BasicStroke(0.1f));
                            } else if (weight > 0.0) {
                                g.setColor(new Color(0.0f, 1.0f, 0.0f));
                                g2.setStroke(new BasicStroke((weight * weight) * 5.0f));// Square the weight to exaggerate the differences, multiplier so you can actually see something
                            } else {
                                g.setColor(new Color(1.0f, 0.0f, 0.0f));
                                g2.setStroke(new BasicStroke((weight * weight) * 5.0f));
                            }
                            
                            g.drawLine((layerOffset * (i + 1)) - neuronRadius, neuronOffset * (j + 1), (layerOffset * i) + neuronRadius, (previousLayerNeuronOffset * (entry.getKey() + 1)));
                        }
                    }
                }
            }
        }
    }
}
