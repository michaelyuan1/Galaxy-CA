/*
+--------------------------------------------------------------------------+
|                             IAYC 2010 - GM                               |
|                   Galaxy formation on cellular automata                  |
|                     (c) Alex, Josh, Michael, Wasilij                     |
+--------------------------------------------------------------------------+

Sim.java: Contoller containing public static void main and scheduling the 
phase transitions of the CA and its display.

*/

import java.util.*;

public class Sim{

    protected static CA autom;
    private static ViewCA VCA;
    protected static CAFrame frame;
    protected static StopWatch sw = new StopWatch();

    private static final double timeStep = 0.0005;   // timestep in 10^9 y
    private static final double finalRadius = 20;
    private static final int numRings = 400;
    private static final int refreshTime = 50;     // in ms
    private static final int ring_drawing_step = 1;      // number of rings to be drawn
    protected static final int refreshRatio = 1;
    private static final boolean draw_grid = false;

    public static void main(String[] wuergs){

	// CA initialization
	autom = new CA(numRings, finalRadius, timeStep);
	VCA = new ViewCA(autom);
	initialize();

	// Frame intialization
	frame = new CAFrame(700, 700, VCA);    // size
	frame.set_ring_drawing_step(ring_drawing_step);
	frame.set_draw_grid(draw_grid);

	// Simulation
	Timer t = new Timer();
	CATimerTask tt = new CATimerTask();
	Date d = new Date();

	t.schedule(tt, d, refreshTime);
    }

    // CA initialization
    public static void initialize(){
	// todo
    }
}

  
class CATimerTask extends TimerTask{
    private static int counter = 0;

    public void run(){
	counter++;
	if (counter == Sim.refreshRatio){
	    Sim.frame.repaint();
	    counter = 0;
	}
	Sim.autom.perform_step();
    }
    
}