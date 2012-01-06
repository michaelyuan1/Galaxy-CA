/*
+--------------------------------------------------------------------------+
|                             IAYC 2010 - GM                               |
|                   Galaxy formation on cellular automata                  |
|                     (c) Alex, Josh, Michael, Wasilij                     |
+--------------------------------------------------------------------------+

galaxyRing.java: is a onedimensional closed-loop array

*/

public class galaxyRing{
    
    public CACellGalaxy[] galaxyRow; //galaxyRow is the array of the galaxyRing containing the cells
    public int ringNumber;
    public double ringRadius;
    public double difference;        // current shift due to rotation
    private double timestep;

    // constructor
    public galaxyRing(int numCells, double dt){
	galaxyRow = new CACellGalaxy[numCells];
	timestep = dt;
	// cells starting with index 0 going to numCells-1
	for(int i=0; i<numCells; i++){
	    galaxyRow[i]=new CACellGalaxy(CACell.INACTIVE, dt);
	}
	difference=0;
    }

    public void setRingNumber(int Ringnum){
	ringNumber=Ringnum;
    }

    public void setRingRadius(double radius){
	ringRadius=radius;
    }
    
    public void setDifference(double origDifference, double cellShift){
	origDifference=mod(origDifference, cellShift, origDifference>=0);
	difference=origDifference;
    }

    public static double mod(double num, double divider, boolean positive){
	if(positive){	
	    while(num-divider>=0){
		num=num-divider;
	    }
	    return num;
	}
	else{
	    while(num+divider<=0){
		num=num+divider;
	    }
	    return num;
	}
    }
    
    public void stepRingForward(double rotSpeed){
	setDifference(difference + rotSpeed*timestep, galaxyRow.length);
    }

    public CACellGalaxy getCell(int origCell){
	// cyclic boundary conditions
	if (origCell >= galaxyRow.length){
	    origCell -= galaxyRow.length;
	}
	if (origCell < 0){
	    origCell += galaxyRow.length;
	}
	return galaxyRow[origCell];
    }

    public CACellGalaxy getRightCell(int origCell){ //returns the cell to the right of the original cell
	return getCell(origCell + 1);
    }
    public CACellGalaxy getLeftCell(int origCell){ //returns the cell to the right of the original cell
	return getCell(origCell - 1);
    }

}
