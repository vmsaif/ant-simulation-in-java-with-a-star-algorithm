/* -----------------------------------------------------------------------------
    Author: Saif Mahmud
    Date: 2023-22-07
    Description: Main class to run the game
*/

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class App {
    public static void main(String[] args) throws Exception {
        // ask the user for the number of ants

        int numAnts;
        do{
            numAnts = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the number of ants:"));
            if(numAnts < 1 || numAnts > 10){
                JOptionPane.showMessageDialog(null, "Please enter a number between 1 and 10");
            }
        } while(numAnts < 1 || numAnts > 10);

        int gameSpeed = 500; // in milliseconds
        Simulation sim = new Simulation(numAnts, gameSpeed);
        
        JFrame frame = new JFrame("A2_P2");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 700);

        frame.add(sim);
        // frame.pack();
        frame.setVisible(true);
    }
}
