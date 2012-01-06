/*
+--------------------------------------------------------------------------+
|                             IAYC 2010 - GM                               |
|                   Galaxy formation on cellular automata                  |
|                     (c) Alex, Josh, Michael, Wasilij                     |
+--------------------------------------------------------------------------+

CA.java: the CA consisting of a grid to wich it mainly delegates all that
is to be done.

km/sec = kpc / 10^9 y

1 LU = 1 kpc
1 TU = 10^9 y

*/

public class CA{
    
    private galaxy grid;
    private double timestep;
    private StopWatch sw = new StopWatch();
    public double time = 0;

    // constructor
    public CA(int numrings, int numcells, double finalRadius, double timestep){
	// numcells: number of cells in each ring
	// initradius: the radius of the innermost ring being a circle

	grid = new galaxy(numrings, numcells, finalRadius);
	// initialization happens in the constructor of galaxy
	this.timestep = timestep;
    }

    public CA(int numrings, double finalRadius, double timestep){
	// numcells: number of cells in each ring
	// initradius: the radius of the innermost ring being a circle

	grid = new galaxy(numrings, finalRadius, timestep);
	// initialization happens in the constructor of galaxy
	this.timestep = timestep;
    }

    // getter / setter methods
    public int numrings(){
	return grid.numberOfRings;
    }
    public int numcells(){
	return grid.numberOfCells;
    }

    public double initradius(){
	return grid.initRadius;
    }

    // provides you the cell at the specified position by asking the grif for it
    public CACell getCell(int ring, int number){
	return grid.getGalaxyCell(ring, number);
    }

    // the shift of a sspecified ring (amount of cells it has been rotated)
    public double ringRotation(int ring){
	if ((ring >= 0) && (ring < numrings())){
	    return grid.getRing(ring).difference;
	} else {
	    throw new CAException("CA.ringRotation", "outofbounds-ring #" + ring);
	}
    }

    public double ringRadius(int ring){
	if ((ring >= 0) && (ring < numrings())){
	    return grid.getRing(ring).ringRadius;
	} else {
	    throw new CAException("CA.ringRadius", "outofbounds-ring #" + ring);
	}
    }

    // performs the phase transition consisting of moving the rings, calculating the new states and renewing them all at once afterwards
    public void perform_step(){
	CACell[] a = new CACell[4];
	double[] b = new double[3];

	//if (time < 1){
	    
	    sw.start();
	    // call for a move of the ring
	    grid.moveRingsInTime();           // does a step (v dt)
	    b[0] = sw.stop();
	    
	    sw.start();
	    // call for a state transition
	    grid.changeAllStates();
	    b[1] = sw.stop();
	    
	    sw.start();
	    // state change
	    grid.renewAllStates();
	    b[2] = sw.stop();
	    //System.out.println(b[0] + " " + b[1] + " " + b[2]);
	    
	    time += timestep;	    
	    //}
    }
}