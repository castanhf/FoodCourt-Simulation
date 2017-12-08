
/**
 * Write a description of class Simulation here.
 * 
 * @author   Filipe Castanheira
 * @version  04/21/2017
 */
public class Simulation
{
    /** 
     * The clock removes Event163 events from the futureEvent,
     * priority queue and calls the appropriate listener that
     * is specified by the event */
     
    public Clock clock;   

    public PersonProducer producer; // A Clock listener
    public FoodCourt foodCourt;     // A Clock listener
    public Checkout checkout;       // A Clock listener
    public Terminate terminate;     // A Clock listener
    public Simulation()
    {
        this.clock = new Clock();

        this.producer  = new PersonProducer( this.clock); 
        this.foodCourt = new FoodCourt( this.clock);
        this.checkout  = new Checkout(this.clock);
        this.terminate = new Terminate(this.clock);

        this.producer.nextBlock  = this.foodCourt;
        this.foodCourt.nextBlock = this.checkout;
        this.checkout.nextBlock  = this.terminate;

        clock.add(this.producer);   // placed into position for block 0
        clock.add(this.foodCourt);  // placed into position for block 1
        clock.add(this.checkout);   // placed into position for block 2
        clock.add(this.terminate);  // placed into position for block 3

    }

    public static void main( String[] args )
    {       
        double estimateOrderTime    = 12;
        double estimateCheckoutTime = 12;

        Simulation simulation = new Simulation();

        double estimateOrderTime1    = 24;
        Eatery food = new Eatery(simulation.clock, "Flavors", estimateOrderTime );
        simulation.foodCourt.add( food );

        double estimateCheckoutTime1 = 24;
        simulation.checkout.addCashier(estimateCheckoutTime);

        simulation.clock.scheduleAllArrivals( 10, 200);       
        simulation.clock.run(10000);

        simulation.clock.showStatistics();
        simulation.producer.showStatistics();
        simulation.foodCourt.showStatistics();
        simulation.checkout.showStatistics();
        simulation.terminate.showStatistics();
    }
}
