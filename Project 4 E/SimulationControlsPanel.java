import javax.swing.border.Border;
import javax.swing.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDate;
/**
 * @author   Filipe Castanheira
 * @version  04/21/2017
 */
public class SimulationControlsPanel extends JPanel 
{
    private Simulation simulation;
    private JFrame parentFrame;

    private JLabel  clockLabel;
    private JCheckBox showClockCheckBox;

    private JLabel personArrivalsLabel;
    private JCheckBox showProducerCheckBox;

    private JLabel terminateLabel;
    private JCheckBox showTerminateCheckBox;

    private JButton stepButton;
    private JButton runButton;
    private JButton inRealTimeButton;

    private JLabel arrivalTimeLabel;
    private JTextField arrivalTimeTextField;

    private JLabel clockSpeedLabel;
    private JTextField clockSpeedTextField;

    private JButton startButton;

    public int        continuousTime;    // simulated continuousTime
    
    javax.swing.Timer timer;
    final int DELAY = 50;  //60;  // 20

    public SimulationControlsPanel( Simulation simulation, JFrame parentFrame )
    {
        this.simulation = simulation;
        this.parentFrame = parentFrame;

        this.setLayout (new GridLayout (0, 3, 3, 10));     

        Listener listener = new Listener();

        this.add( this.createClockPanel( listener ) );
        this.add( this.createPersonProducerPanel( listener ) );
        this.add( this.createTerminatePanel( listener ) );

        this.stepButton = new JButton( "Step" );
        this.stepButton.addActionListener (listener);
        this.add (this.stepButton);
        this.stepButton.setEnabled( false );

        this.runButton = new JButton( "Run" );
        this.runButton.addActionListener (listener);  
        this.add (runButton);
        this.runButton.setEnabled( false );

        this.inRealTimeButton = new JButton( "In real time" );
        this.inRealTimeButton.addActionListener (listener);        
        this.add (inRealTimeButton);
        this.inRealTimeButton.setEnabled( false );

        this.add( this.createInterArrivalPanel( listener) );
        this.add( this.createClockSpeedPanel(listener) );
        
        this.startButton = new JButton( "Start simulation" );
        this.startButton.addActionListener (listener);        
        this.add( this.startButton );

        timer = new javax.swing.Timer( DELAY, new TimerListener() );

//         this.setBackground (Color.cyan);
//         this.setLocation (1400, 700);
//         this.setPreferredSize (new Dimension(300, 200));
    }

    private JPanel createPersonProducerPanel( Listener listener )
    {
        JPanel panel = new JPanel();
        Border border1 = BorderFactory.createLineBorder (Color.blue, 3);
        Border border2 = BorderFactory.createTitledBorder ( "Person Producer" );
        panel.setBorder( BorderFactory.createCompoundBorder (border1, border2));

        panel.setLayout (new GridLayout (0, 1, 4, 10));   

        this.personArrivalsLabel = new JLabel( "Arrivals: " + simulation.producer.arrivals );
        panel.add (this.personArrivalsLabel );

        showProducerCheckBox = new JCheckBox( "Show", true );       
        showProducerCheckBox.addActionListener (listener);  
        panel.add (showProducerCheckBox);
        return panel;
    }

    private JPanel createClockPanel( Listener listener )
    {
        JPanel panel = new JPanel();
        Border border1 = BorderFactory.createLineBorder (Color.blue, 3);
        Border border2 = BorderFactory.createTitledBorder ( "Clock" );
        panel.setBorder( BorderFactory.createCompoundBorder (border1, border2));

        panel.setLayout (new GridLayout (0, 1, 4, 10));   

        this.clockLabel = new JLabel( "Simulation time: " + simulation.clock.inSeconds( simulation.clock.time) );
        panel.add (this.clockLabel );

        showClockCheckBox = new JCheckBox( "Show", true );       
        showClockCheckBox.addActionListener (listener);  
        panel.add (showClockCheckBox);
        return panel;
    }

    private JPanel createTerminatePanel( Listener listener )
    {
        JPanel panel = new JPanel();
        Border border1 = BorderFactory.createLineBorder (Color.blue, 3);
        Border border2 = BorderFactory.createTitledBorder ( "Terminate" );
        panel.setBorder( BorderFactory.createCompoundBorder (border1, border2));

        panel.setLayout (new GridLayout (0, 1, 4, 10));   

        this.terminateLabel = new JLabel( "Departures: " + this.simulation.terminate.departures );
        panel.add (this.terminateLabel );

        showTerminateCheckBox = new JCheckBox( "Show", true );       
        showTerminateCheckBox.addActionListener (listener);  
        panel.add (showTerminateCheckBox);
        return panel;
    }    

    private JPanel createInterArrivalPanel( Listener listener )
    {
        JPanel panel = new JPanel();
        Border border1 = BorderFactory.createLineBorder (Color.blue, 3);
        Border border2 = BorderFactory.createTitledBorder ( "Interarrival" );
        panel.setBorder( BorderFactory.createCompoundBorder (border1, border2));

        panel.setLayout (new GridLayout (0, 1, 2, 10));   

        this.arrivalTimeLabel = new JLabel( "Inter arrival times: 10.5" );
        panel.add( this.arrivalTimeLabel );

        this.arrivalTimeTextField = new JTextField( "10.5" );
        this.arrivalTimeTextField.addActionListener (listener);        
        panel.add( this.arrivalTimeTextField );

        return panel;
    }

    private JPanel createClockSpeedPanel( Listener listener )
    {
        JPanel panel = new JPanel();
        Border border1 = BorderFactory.createLineBorder (Color.blue, 3);
        Border border2 = BorderFactory.createTitledBorder ( "Clock speed" );
        panel.setBorder( BorderFactory.createCompoundBorder (border1, border2));

        panel.setLayout (new GridLayout (0, 1, 2, 10));   

        this.clockSpeedLabel = new JLabel( "clock speed: 50" );
        panel.add( this.clockSpeedLabel );

        this.clockSpeedTextField = new JTextField( "50" );
        this.clockSpeedTextField.addActionListener (listener);        
        panel.add( this.clockSpeedTextField );

        return panel;
    }    

    /**
     * Determines the time in hours, minutes, and seconds
     * 
     * @return     current time
     */
    public String getCurrentTime()
    {
        String[ ] AM_PM = {"AM", "PM"};
        GregorianCalendar date;
        int    hour;
        String minute, second;

        date   = new GregorianCalendar();
        hour   = date.get(Calendar.HOUR);
        minute = "0" + date.get(Calendar.MINUTE);
        second = "0" + date.get(Calendar.SECOND);
        
        LocalDateTime dateTime = LocalDateTime.now();
        LocalDate date0 = dateTime.toLocalDate();

        return 
        (
            date0 + " " +
            hour + ":" + 
            minute.substring( minute.length()-2 ) + ":" + 
            second.substring( second.length()-2 ) + " " +
            AM_PM[ date.get(Calendar.AM_PM) ]
        );
    }

    private void updatePerformanceParameters()
    {        
        String clockStr = "Simulation time: " + simulation.clock.inSeconds( simulation.clock.time);
        clockLabel.setText( clockStr);
        
        //DONE
// 19) TODO  replace the second string in each of the followig two lines of code
        personArrivalsLabel.setText( "Arrivals: " + simulation.producer.arrivals );
        terminateLabel.setText( "Departures: " + simulation.terminate.departures );
    }

    private void continuousSteps()
    {
        simulation.clock.continuousSteps(this.continuousTime);
        updatePerformanceParameters();
    }

    private class Listener implements ActionListener
    {

        public void actionPerformed (ActionEvent event)
        {
            Object source = event.getSource();
            if ( source == stepButton)
            {
                //DONE
// 20) TODO give the command to invoke nextStep method in the clock class
                simulation.clock.nextStep();
                updatePerformanceParameters();
            }
            else if (source == runButton)
            {
                simulation.clock.run();
                updatePerformanceParameters();
            }
            else if (source == inRealTimeButton)
            {
                timer.start();
            }
            else if (source == startButton)
            {
                String text = arrivalTimeTextField.getText();
                double arrivalTimes = Double.parseDouble (text);

                simulation.clock.scheduleAllArrivals( arrivalTimes, arrivalTimes * 20);   
                
                startButton.setEnabled( false );
                
                stepButton.setEnabled( true );
                runButton.setEnabled( true );
                inRealTimeButton.setEnabled( true );
            }
            else if (source == arrivalTimeTextField)
            {
                /*
                 * This event happens when a user changes the "arrivalTimeTextField" text field
                 * and then presses the Enter key.
                 * 
                 * The action that needs to be taken is to copy/echo the newly edited text in the
                 * arrivalTimeTextField to the arrivalTimeLabel, appropriately using getText and
                 * setText methods. 
                 */

                //DONE
// 21) TODO in one line of code
                arrivalTimeLabel.setText(arrivalTimeTextField.getText());
            }
            else if (source == clockSpeedTextField)
            {
                clockSpeedLabel.setText( "Inter arrival time: " + clockSpeedTextField.getText() );
                timer.setDelay( Integer.parseInt(clockSpeedTextField.getText()) );
            }
            else if (source == showClockCheckBox)
            {
                if (showClockCheckBox.isSelected())
                {
                    simulation.clock.window.setVisible( true );
                }
                else
                {
                    simulation.clock.window.setVisible( false );
                }
            }            
            else if (source == showProducerCheckBox)
            {
                if (showProducerCheckBox.isSelected())
                {
                    simulation.producer.window.setVisible( true );
                }
                else
                {
                    simulation.producer.window.setVisible( false );
                }
            }    
            else if (source == showTerminateCheckBox)
            {
                if (showTerminateCheckBox.isSelected())
                {
                    simulation.terminate.window.setVisible( true );
                }
                else
                {
                    simulation.terminate.window.setVisible( false );
                }
            }
        }
    }

    /**
     * Defines TimerListener as an ActionListener type,
     * to handle timer (action) events.
     */
    private class TimerListener implements ActionListener
    {
        /**
         * Updates the time
         * 
         * @param  event    a timer interrupt
         */
        public void actionPerformed (ActionEvent event)
        {
            continuousTime++;
            continuousSteps();

            parentFrame.setTitle( "CIS 162-07 Food Court Simulation: " + getCurrentTime() );
        }
    }
}
