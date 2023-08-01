/* -----------------------------------------------------------------------------
    Author: Saif Mahmud
    Date: 2023-22-07
    Course: COMP 452
    Student ID: 3433058
    Assignment: 2
    Part: 1
    Description: Main class to run the game
*/

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class App {
    public static void main(String[] args) throws Exception {
        // ask the user for the number of ants

        // int numAnts = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the number of ants:"));
        int numAnts = 1;
        Simulation sim = new Simulation(numAnts);
        
        JFrame frame = new JFrame("A2_P2");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 700);

        frame.add(sim);
        // frame.pack();
        frame.setVisible(true);
    }
}
