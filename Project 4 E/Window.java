 

/*
reference: http://www.java2s.com/Code/Java/Swing-JFC/TextPaneSample.htm

Definitive Guide to Swing for Java 2, Second Edition
By John Zukowski     
ISBN: 1-893115-78-X
Publisher: APress
 */

/*
 * An adaptation of Java Sun Swing Tutorial, September 2002
 * 
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;

//static final long serialVersionUID = 0;

public class Window extends JFrame
{
    protected JTextPane             textPane;

    protected DefaultStyledDocument document;

    protected String                newline = "\n";

    protected SimpleAttributeSet    font;
    protected SimpleAttributeSet    fontStyle;


    public Window( )
    {
        this( "Window", 100, 200, 1000, 700, Color.GRAY, false );
    }

    public Window( String title, int x, int y, int width, int height, Color color, boolean disableClose )
    {
        super( title );

        initializeFrame( x, y, width, height, color, disableClose );
    }

    private void initializeFrame( int x, int y, int width, int height, Color color, boolean disableClose )
    {
        this.document = new DefaultStyledDocument( );

        this.textPane = new JTextPane( document );
        this.textPane.setCaretPosition( 0 );
        this.textPane.setMargin( new Insets( 5,5,5,5 ) );

        JScrollPane scrollPane = new JScrollPane( textPane );
        scrollPane.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS );
        scrollPane.setPreferredSize( new Dimension( width, height ) );
        scrollPane.setMinimumSize( new Dimension( 10, 10 ) );
        this.getContentPane( ).add( scrollPane );

        JPanel contentPane = new JPanel( new BorderLayout( ) );
        contentPane.add( scrollPane, BorderLayout.CENTER )  ;

        setContentPane( contentPane );

        this.font = new SimpleAttributeSet( );
    //  this.presetTextStyle( "Calibri", 14 );
        this.presetTextStyle( "Courier New", 16 );

        this.setLocation( x, y );
    //  this.setSize( width, height );
        this.setColor( color );


        if ( disableClose )
        {
            this.setDefaultCloseOperation( WindowConstants.DO_NOTHING_ON_CLOSE );
        }       


        this.addWindowListener( new WindowAdapter( )
        {
            public void windowClosing( WindowEvent e )
            {
                System.exit( 0 );
                //              e.getWindow().setVisible( false ); // added 2 October 2002
            }  

            public void windowActivated( WindowEvent e )
            {
                //              textPane.requestFocus();  // 2 October 2002
            }
        }                                                );

        this.pack();
        this.setVisible( true );
    }

    private void presetTextStyle( String fontName, int fontSize )
    {   
        StyleConstants.setFontFamily( font, fontName );
        StyleConstants.setFontSize( font, fontSize );

        fontStyle = new SimpleAttributeSet( font );
    }
    
    public void setTextStyle( String fontName, int fontSize )
    {
        Color color = this.getColor( );
        
        StyleConstants.setFontFamily( font, fontName );
        StyleConstants.setFontSize( font, fontSize );

        fontStyle = new SimpleAttributeSet( font );
        
        this.setColor( color );
    }

    public void setTextStyle( String fontName )
    {
        Color color = this.getColor( );
        int size = this.getFontSize( );
        
//      this.font = new SimpleAttributeSet( );
        StyleConstants.setFontFamily( font, fontName );
        StyleConstants.setFontSize( font, size );

        fontStyle = new SimpleAttributeSet( font );
        
        this.setColor( color );
    }

    public Color getColor( )
    {
        return StyleConstants.getForeground( fontStyle );
    }    

    public void setColor( Color color )
    {
        StyleConstants.setForeground( fontStyle, color );
    }

    public void setBackColor( Color color )
    {
        StyleConstants.setBackground( fontStyle, color );
    }

    public int getFontSize( )
    {
        //       StyleConstants.getFontSize( textPane.getStyle( "style" ) );
        return StyleConstants.getFontSize( fontStyle );
    }

    public void setFontSize( int size )
    {
        //       StyleConstants.setFontSize( textPane.getStyle( "style" ), size );
        StyleConstants.setFontSize( fontStyle, size );
    }

    public void setBold( boolean flag )
    {
        StyleConstants.setBold( fontStyle, flag );
    }

    public void setItalic( boolean flag )
    {
        StyleConstants.setItalic( fontStyle, flag );
    }

    public void setUnderline( boolean flag )
    {
        StyleConstants.setUnderline( fontStyle, flag );
    }

    public void print( String str )
    {
        try
        {
            document.insertString( document.getLength(), str, fontStyle );
        }
        catch( BadLocationException ble )
        {
            System.err.println( "Error inserting text into JTextPane document." );
        }
    }

    public void print( int datum )
    {
        this.print( "" + datum );
    }

    public void print( long datum )
    {
        this.print( "" + datum );
    }

    public void print( double datum )
    {
        this.print( "" + datum );
    }

    public void print( float datum )
    {
        this.print( "" + datum );
    }

    public void print( boolean datum )
    {
        this.print( "" + datum );
    }

    public void print( char datum )
    {
        this.print( "" + datum );
    }

    public void print( Object datum )
    {
        this.print( datum.toString( ) );
    }

    public void println( Object datum )
    {
        this.print( datum );
        this.println( );
    }

    public void println( String datum )
    {
        this.print( datum );
        this.println( );
    }

    public void println( int datum )
    {
        this.print( datum );
        this.println( );
    }

    public void println( long datum )
    {
        this.print( datum );
        this.println( );
    }

    public void println( double datum )
    {
        this.print( datum );
        this.println( );
    }

    public void println( float datum )
    {
        this.print( datum );
        this.println( );
    }

    public void println( boolean datum )
    {
        this.print( datum );
        this.println( );
    }

    public void println( char datum )
    {
        this.print( datum );
        this.println( );
    }

    public void println(  )
    {
        this.print( "\n" );
    }



    public static void main(String[] args)
    {
        Window window = new Window( "Testing Color", 100, 200, 350, 150, Color.RED, false );
    //  window.setFontSize( 12 );
        window.print( "\t RED " );
        
        window.setColor( Color.GREEN );
        window.setFontSize( 24 );
        window.print( "GREEN " );
        
        window.setFontSize( 48 );
        window.setColor( Color.BLUE );
        window.print( "BLUE " );
    }

}