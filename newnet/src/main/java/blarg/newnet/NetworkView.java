package blarg.newnet;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
            
            int layerOffset = (int) ((double) NETWORK_VIEW_WIDTH / (double) (network.layers.size() + 1));
            int neuronRadius = 20;
            
            for (int i = 0; i < network.layers.size(); i++) {
                int neuronOffset = (int) ((double) NETWORK_VIEW_HEIGHT / (double) (network.layers.get(i).getNumberOfNeurons() + 1));
                int previousLayerNeuronOffset = (i == 0 ? -1 : (int) ((double) NETWORK_VIEW_HEIGHT / (double) (network.layers.get(i - 1).getNumberOfNeurons() + 1)));// Can't check previous layer if we're on layer 0
                g.setColor(Color.BLACK);
                
                for (int j = 0; j < network.layers.get(i).getNumberOfNeurons(); j++) {
                    g.fillOval((layerOffset * (i + 1)) - neuronRadius, (neuronOffset * (j + 1)) - neuronRadius, neuronRadius * 2, neuronRadius * 2);
                    
                    if (i == 0) {// Input layer will have proper lines at some point
                        
                    } else {
                        g.drawLine((layerOffset * (i + 1)) - neuronRadius, neuronOffset * (j + 1), (layerOffset * i) + neuronRadius, (previousLayerNeuronOffset * (0 + 1)));
                    }
                }
            }
            
//            boolean startSquareIsWhite = true;
            
//            for (int i = 0; i < 8; i++) {                
//                boolean isWhite = startSquareIsWhite;
//                for (int j = 0; j < 8; j++) {
//                    if (isWhite) {
//                        g.setColor(Color.WHITE);
//                    } else {
//                        g.setColor(Color.GRAY);
//                    }
//                    g.fillRect((BOARD_WIDTH / 8) * j, (BOARD_HEIGHT / 8) * i, (BOARD_WIDTH / 8), (BOARD_HEIGHT / 8));
//                     isWhite = !isWhite;
//                }
//                startSquareIsWhite = !startSquareIsWhite;
//            }
//            
//            Image image = null;
//            
//            try {
//                image = ImageIO.read(new File("images\\mydonechessbits.png"));
//            } catch (IOException ex) {
//                Logger.getLogger(BoardView.class.getName()).log(Level.SEVERE, null, ex);
//                System.exit(1);
//            }
//            
//            if (boardInts != null) {
//                if (boardInts.length != 0) {
//                    if (boardInts[0].length != 0) {
//                        for (int i = 0; i < boardInts.length; i++) {
//                            for (int j = 0; j < boardInts[i].length; j++) {
//                                if (boardInts[i][j] != Board.NONE) {
//                                    int piece = boardInts[i][j];
//                                    int color = Piece.BLACK;
//                                    if (piece > Board.BLACK_KING) {
//                                        color = Piece.WHITE;
//                                    }
//                                    
//                                    piece = piece % Board.WHITE_PAWN;
//                                    
//                                    //int adjustedPiece = piece - 1;// 0 is NONE/no piece but the image starts with pawns so just need to take one off to line them up
//                                    
//                                    g.drawImage(image, 100 * i, 100 * j, (100 * i) + 100, (100 * j) + 100, (piece * 200), (color * 200), (piece * 200) + 200, (color * 200) + 200, rootPane);
//                                }
//                            }
//                        }
//                    }
//                }
//            }
        }
    }
}
