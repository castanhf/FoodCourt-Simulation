import javax.swing.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 * @author   Filipe Castanheira
 * @version  04/21/2017
 */
public class SimulationPanel extends JPanel 
{
    private Simulation simulation;

    public SimulationPanel(JFrame parentFrame)
    {
        this.setLayout (new GridLayout (0, 1, 3, 10));   
        
        this.createSimulation();
        
        JPanel row1 = new FoodCourtPanel(simulation.foodCourt);
        JPanel row2 = new CheckoutPanel( simulation.checkout );
        JPanel row3 = new SimulationControlsPanel( simulation, parentFrame );
        
        add (row1);
        add (row2);
        add (row3);

        setBackground (Color.cyan);
        setLocation (1400, 700);
        setPreferredSize (new Dimension(600, 700));
    }

    private void createSimulation()
    {
        simulation = new Simulation();

        double estimateOrderTime1 = 24;
        Eatery food = new Eatery(simulation.clock, "Flavors", estimateOrderTime1 );
        simulation.foodCourt.add( food );

        double estimateCheckoutTime1 = 12;
        simulation.checkout.addCashier(estimateCheckoutTime1);        
    }
}
