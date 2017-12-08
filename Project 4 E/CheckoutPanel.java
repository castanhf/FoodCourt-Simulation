
import javax.swing.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 * @author   Filipe Castanheira
 * @version  04/21/2017
 */
public class CheckoutPanel extends JPanel 
{
    private Checkout checkout;
    public  CashierPanel[] rightPanel;

    private JLabel cashiersLabel;
    private JButton addButton;
    private JCheckBox showCheckbox;
    private JButton statisticsButton;
    
    
    public CheckoutPanel( Checkout checkout)
    {
        this.checkout = checkout;

        this.setLayout (new GridLayout (0, 5, 1, 10));   
        this.setBorder (BorderFactory.createTitledBorder ("Checkout"));

        Listener listener = new Listener( );
        this.add( this.createControlPanel( listener ) );

        rightPanel = new CashierPanel[checkout.REGISTERS ];
        for ( int n = 0; n < checkout.REGISTERS; n++)
        {
            rightPanel[n] = new CashierPanel(this.checkout.cashier[n]);
            this.add( rightPanel[n] );
        }
        
        for ( int n = checkout.numCashiers; n < checkout.REGISTERS; n++)
        {
            rightPanel[n].setVisible( false );
        }
    }
 
    private JPanel createControlPanel( Listener listener )
    {
        JPanel panel = new JPanel();
        
        panel.setLayout (new GridLayout (0, 1, 4, 10));   
        panel.setBorder (BorderFactory.createLineBorder (Color.blue, 3));

        this.cashiersLabel = new JLabel( "Cashiers: " + this.checkout.numCashiers );
        panel.add (this.cashiersLabel );
       
        this.addButton = new JButton( "Add" );
        this.addButton.addActionListener (listener);
        panel.add (this.addButton);

        showCheckbox = new JCheckBox( "Show", true );       
        showCheckbox.addActionListener (listener);  
        panel.add (showCheckbox);

        this.statisticsButton = new JButton( "Statistics" );
        this.statisticsButton.addActionListener (listener);
        panel.add (this.statisticsButton);

        return panel;
    }
    
    public void addCashier(double serviceTimeEstimate )
    {
        this.checkout.addCashier(serviceTimeEstimate);

        //DONE
// 10) TODO Replace the 0 in the following with the correct info
//     in the Checkout class
        this.cashiersLabel.setText( "Cashiers: " + this.checkout.numCashiers );
    }
    
    private class Listener implements ActionListener
    {
        public void actionPerformed (ActionEvent event)
        {
            Object source = event.getSource();
            if (source == addButton)
            {
                //DONE
// 11) TODO Review the following code to add a cashier. Is it correct?
                addCashier( 10.5 );   // a default value
                int index = checkout.numCashiers - 1;
                
                rightPanel[index].setVisible( true );
                rightPanel[index].cashier = checkout.cashier[ index ];
                checkout.cashier[index].window.setVisible( true );
            }
            else if (source == showCheckbox)
            {
                if (showCheckbox.isSelected())
                {
                    //DONE
// 12) TODO make the checkout window visible with setVisible
                checkout.window.setVisible( true );
                }
                else
                {
                    //DONE
//  13) TODO make the checkout window NOT visible with setVisible
                checkout.window.setVisible( false );
                }
            } 
            else if (source == statisticsButton)
            {
                //DONE
// 14) TODO add checkout statistics to the checkout window by calling 
//    the appropriate method in the Checkout class
            checkout.showStatistics();
            }
        }
    }    
}
