
import javax.swing.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 * @author   Filipe Castanheira
 * @version  04/21/2017
 */
public class FoodCourtPanel extends JPanel 
{
    private FoodCourt foodCourt;

    public EateryPanel[] rightPanel;
    private JLabel eateriesLabel;
    private JButton addButton;
    private JCheckBox showCheckbox;
    private JButton statisticsButton;

    private static String[] name = {"Flavors", "Delights", "Wonders", "Whatever"};

    public FoodCourtPanel(FoodCourt foodCourt)
    {
        this.foodCourt = foodCourt;

        this.setLayout (new GridLayout (0, 5, 1, 10));     
        this.setBorder (BorderFactory.createTitledBorder ("Food Court"));

        Listener listener = new Listener( );

        this.add( this.createControlPanel( listener ) );

        rightPanel = new EateryPanel[this.foodCourt.MAX_EATERIES ];
        for ( int n = 0; n < this.foodCourt.MAX_EATERIES; n++ )
        {
            rightPanel[n] = new EateryPanel(foodCourt.eatery[n], name[n]);
            this.add (rightPanel[n]);            
        }
        for ( int n = this.foodCourt.eateries; n < this.foodCourt.MAX_EATERIES; n++)
        {
            rightPanel[n].setVisible( false );
        }

        //         this.setBackground (Color.cyan);
        //         this.setLocation (1400, 25);
        //         this.setPreferredSize (new Dimension(300, 40));
    }

    private JPanel createControlPanel( Listener listener )
    {
        JPanel panel = new JPanel();

        panel.setLayout (new GridLayout (0, 1, 5, 10));   
        panel.setBorder (BorderFactory.createLineBorder (Color.blue, 3));

        this.eateriesLabel = new JLabel( "Eateries: " + this.foodCourt.eateries );
        panel.add (this.eateriesLabel );

        panel.add (new JLabel( "") );

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

    public void addEatery(double serviceTimeEstimate, int n )
    {
        Eatery food = new Eatery(this.foodCourt.clock, name[ n ], serviceTimeEstimate );     
        this.foodCourt.add(food);
        //DONE
        // 1) TODO replace the 0 in the following the appropriate info in the FoodCourt class
        this.eateriesLabel.setText( "Eateries: " + n );
    }

    private class Listener implements ActionListener
    {
        public void actionPerformed (ActionEvent event)
        {
            Object source = event.getSource();
            if (source == addButton)
            {
                //DONE
                // 2) TODO: Review the following code to add an eatery. Is it correct? 
                addEatery( 10.5, foodCourt.eateries );
                int index = foodCourt.eateries - 1;
                rightPanel[index ].setVisible( true );
                rightPanel[index].eatery = foodCourt.eatery[ index ];

                foodCourt.eatery[ index ].window.setVisible( true );
            }
            else if (source == showCheckbox)
            {
                if (showCheckbox.isSelected())
                {
                    //CONFIRM
                    // 3) TODO make the foodCourt window visible with setVisible
                    foodCourt.window.setVisible( true );
                }
                else
                {
                    //DONE
                    // 4) TODO make the foodCourt window NOT visible with setVisible
                    foodCourt.window.setVisible( false );
                }
            }
            else if (source == statisticsButton)
            {
                //DONE
                // 5) TODO add foodCourt statistics to the foodCourt window by calling 
                //    the appropriate method in the FoodCourt class
                foodCourt.showStatistics(  );
            }
        }
    }
}
