
import javax.swing.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
/**
 * @author   Filipe Castanheira
 * @version  04/21/2017
 */
public class CashierPanel extends JPanel 
{
    public Cashier cashier;

    private JTextField serviceTimeTextField;
    private JLabel serviceTimeLabel;
    private JCheckBox showCheckbox;

    private JButton statisticsButton;

    public CashierPanel( Cashier cashier)
    {
        this.cashier = cashier;

        this.setLayout (new GridLayout (0, 1, 5, 10));   

        Border border1 = BorderFactory.createLineBorder (Color.magenta, 6);
        Border border2 = BorderFactory.createTitledBorder (this.cashier.name);
        this.setBorder( BorderFactory.createCompoundBorder (border1, border2));

        Listener listener = new Listener( );

        this.add (new JLabel( "service time est" ) );

        this.serviceTimeTextField = new JTextField( "20.5" );
        this.serviceTimeTextField.addActionListener (listener);
        this.add (serviceTimeTextField);

        this.serviceTimeLabel = new JLabel( "Echo: " + this.serviceTimeTextField.getText());
        this.add( serviceTimeLabel );

        this.showCheckbox = new JCheckBox( "Show", true );
        this.add (showCheckbox);
        this.showCheckbox.addActionListener (listener); 

        this.statisticsButton = new JButton( "Statistics" );
        this.statisticsButton.addActionListener (listener);        
        this.add (this.statisticsButton);
    }

    private class Listener implements ActionListener
    {
        public void actionPerformed (ActionEvent event)
        {
            Object source = event.getSource();
            if (source == statisticsButton)
            {
                //DONE
// 15) TODO call the cashier showStatistics, which will print on the cashier window.
            cashier.showStatistics();
            }
            else if (source == showCheckbox)
            {
                if (showCheckbox.isSelected())
                {
                    //DONE
// 16) TODO set the cashier window to be visible
                cashier.window.setVisible( true );
                }
                else
                {
                    //DONE
// 17) TODO set the cashier window to be visible
                cashier.window.setVisible( false );
                }
            }
            else if ( source == serviceTimeTextField)  // See the Chap 6 Fahrenheit example
            {
                //DONE
// This method is called whenever a user edits the serviceTimeTextField
// and then presses the Enter key.

// 18) TODO three lines of code
//      1) With getText and setText methods echo the string/text in 
//         the serviceTimeTextField to the serviceTimeLabel
//      2) use Double.parseDouble with this string/text value to reassign the
//         cashier.serviceTimeEstimate 
            serviceTimeLabel.setText(serviceTimeTextField.getText());
                
            cashier.serviceTimeEstimate = Double.parseDouble(serviceTimeLabel.getText());



            }
        }
    }
}
