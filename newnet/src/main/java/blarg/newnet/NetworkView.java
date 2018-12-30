package blarg.newnet;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
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
            
            int layerOffset = (int) ((double) NETWORK_VIEW_WIDTH / (double) (network.layers.size() + 1));// This splits the panel up neatly and avoids the edges
            int neuronRadius = 20;
            
            for (int i = 0; i < network.layers.size(); i++) {
                int neuronOffset = (int) ((double) NETWORK_VIEW_HEIGHT / (double) (network.layers.get(i).getNumberOfNeurons() + 1));
                int previousLayerNeuronOffset = (i == 0 ? -1 : (int) ((double) NETWORK_VIEW_HEIGHT / (double) (network.layers.get(i - 1).getNumberOfNeurons() + 1)));// Can't check previous layer if we're on layer 0
                g.setColor(Color.BLACK);
                
                for (int j = 0; j < network.layers.get(i).getNumberOfNeurons(); j++) {
                    g.fillOval((layerOffset * (i + 1)) - neuronRadius, (neuronOffset * (j + 1)) - neuronRadius, neuronRadius * 2, neuronRadius * 2);
                    
                    if (i == 0) {// Input layer will have proper lines at some point
                        
                    } else {
                        for (Map.Entry<Integer, Double> entry : network.layers.get(i).getNeuron(j).getWeights().entrySet()) {// Draw a line for each connection backwards from this node
                            g.drawLine((layerOffset * (i + 1)) - neuronRadius, neuronOffset * (j + 1), (layerOffset * i) + neuronRadius, (previousLayerNeuronOffset * (entry.getKey() + 1)));
                        }
                    }
                }
            }
        }
    }
}
