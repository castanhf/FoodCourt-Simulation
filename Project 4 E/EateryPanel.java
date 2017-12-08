
import javax.swing.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
/**
 * @author   Filipe Castanheira
 * @version  04/21/2017
 */
public class EateryPanel extends JPanel 
{
    public  Eatery eatery;

    private JTextField serviceTimeTextField;
    private JLabel serviceTimeLabel;
    private JCheckBox showCheckbox;
    private JButton statisticsButton;

    private static RandomNormal randomNormal = new RandomNormal();

    public EateryPanel( Eatery eatery, String name)
    {
        this.eatery = eatery;

        this.setLayout (new GridLayout (0, 1, 5, 10));   

        Border border1 = BorderFactory.createLineBorder (Color.magenta, 6);
        Border border2 = BorderFactory.createTitledBorder ( name );
        this.setBorder( BorderFactory.createCompoundBorder (border1, border2));

        Listener listener = new Listener( );

        this.add (new JLabel( "service time est" ) );

        this.serviceTimeTextField = new JTextField( "" + 10.5 );
        this.serviceTimeTextField.addActionListener (listener);
        this.add (serviceTimeTextField);

        this.serviceTimeLabel = new JLabel( "Echo: " + this.serviceTimeTextField.getText() );
        this.add( serviceTimeLabel );

        this.showCheckbox = new JCheckBox( "Show", true );
        this.showCheckbox.addActionListener (listener);  
        this.add (showCheckbox);

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
                // 6) TODO call the eatery showStatistics, which will print on the eatery window.
                eatery.showStatistics();
            }
            else if (source == showCheckbox)
            {
                if (showCheckbox.isSelected())
                {
                    //DONE
                    // 7) TODO set the eatery window to be visible
                    eatery.window.setVisible( true );
                }
                else
                {
                    //DONE
                    // 8) TODO set the eatery window to be NOT visible
                    eatery.window.setVisible( false );
                }

            }
            else if ( source == serviceTimeTextField)  // See the Chap 6 Fahrenheit example
            {
                // This method is called whenever a user edits the serviceTimeTextField and then presses
                // the Enter key.

                // 9) TODO: three lines of code
                //      1) With getText and setText methods, echo the string/text in the serviceTimeTextField
                //         to the serviceTimeLabel
                //      2) use Double.parseDouble with this string/text value to reassign the
                //         eatery.serviceTimeEstimate 
                serviceTimeLabel.setText(serviceTimeTextField.getText());
                
                eatery.serviceTimeEstimate = Double.parseDouble(serviceTimeLabel.getText());
            }
        }
    }
}
