//********************************************************************
//  BorderDemo.java       Java Foundations
//
//  Demonstrates the use of various types of borders.
//********************************************************************

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
/**
 * @author   Filipe Castanheira
 * @version  04/21/2017
 */
public class SimulationGUI
{

    private JButton step;
   
   public static void main (String[] args)
   {
      JFrame frame = new JFrame ("See the Border Demo in chapter 6");
      frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);

      JPanel panel = new JPanel();
      panel.setLayout (new BorderLayout ());
      panel.setBorder (BorderFactory.createEmptyBorder (8, 8, 8, 8));

      JPanel simulationPanel = new SimulationPanel( frame );
      simulationPanel.setBorder (BorderFactory.createLineBorder (Color.red, 3));
      
      panel.add (simulationPanel, BorderLayout.CENTER);     
      frame.getContentPane().add (panel);
      frame.pack();
      frame.setVisible(true);
   }
}
