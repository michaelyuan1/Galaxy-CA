/*
+--------------------------------------------------------------------------+
|                             IAYC 2010 - GM                               |
|                   Galaxy formation on cellular automata                  |
|                     (c) Alex, Josh, Michael, Wasilij                     |
+--------------------------------------------------------------------------+

galaxy.java: the main part of the model
the grid consists of a number of rings

*/


public class galaxy {

    
    public galaxyRing[] galaxy;
    public int numberOfCells;
    public int numberOfRings;
    public double initRadius;
    public double timestep;
    private double time = 0;

    // velocity curve is linearl interpolated between these points (got from a real velocity curve)
    private double[] velo_points;
    
    // constructor 1
    public galaxy(int numberOfRings1, double finalRadius, double dt){ //constructs the galaxy
	galaxy = new galaxyRing[numberOfRings1];
	numberOfRings=numberOfRings1;
	timestep = dt;
	initRadius = finalRadius / Math.sqrt(numberOfRings);
	numberOfCells = (int) Math.round(Math.PI * 2.0 * Math.sqrt(numberOfRings) / 0.4142 );

	//(int) Math.round(2 * Math.PI * finalRadius / Math.pow(numberOfRings, 3.0/2.0));
	    
	for(int i=0; i<numberOfRings; i++){
	    galaxy[i] = new galaxyRing(numberOfCells, timestep);
	    galaxy[i].setRingNumber(i);  //1st ring has 0 index
	}

	initialize_galaxy();
    }

    public galaxy(int numberOfRings1, int numberOfCells1, double finalRadius, double dt){
	// create the rings
	galaxy = new galaxyRing[numberOfRings1];
	numberOfRings=numberOfRings1;
	numberOfCells=numberOfCells1;
	
	for(int i=0; i<numberOfRings; i++){
	    galaxy[i] = new galaxyRing(numberOfCells, timestep);
	    galaxy[i].setRingNumber(i);  //1st ring is 0th index
	}
	
	initRadius=finalRadius / Math.sqrt(numberOfRings);
	// calculation and setting of the individual radii of the rings
	
	initialize_galaxy();
    }
    
    // the state to begin with
    public void initialize_galaxy(){
	
	setRadii();

	set_own(0);
	
	// initialization with a srating state
	
	//for (int i = 0; i < 1; i++){
	    //for (int j = 0; j <= 0; j++){
	
	
	/*for (int i = 1; i< 5; i++){
	    for (int j = 0; j < 2; j++){
		this.getGalaxyCell(0, numberOfCells/5 * i + j).state = CACell.ACTIVE;
	    }
	    }*/
		//}
		//}

    }

    private void set_own(int change_curve){
	int[] pos = new int[2];
	
	pos[0] = 2;
	pos[1] = 2; 

	velo_points = new double[23];
	for (int i = 0; i < velo_points.length; i++){
	    /*if (i <= pos[0]){
		velo_points[i] = 200;
	    } else {
		if (i <= pos[1]){
		    velo_points[i] = velo_points[pos[0]] + 10 * (i - pos[0]);
		} else {
		    velo_points[i] = velo_points[pos[1]] + 0.5 * Math.pow((i-pos[1]), 2);
		}
		}*/

	    if (change_curve == 0){
		//if (i <= 20){
		//    velo_points[i] = 400;
		//} else {
		velo_points[i] = Math.pow(i, 3) / 20;
		    //}
	    } else {
		velo_points[i] = 0;
	    }

	}
    }

    private void set_NGC4378(){
	// setting the curve
	// Sa NGC 4378
	velo_points = new double[23];
	velo_points[0] = 0;
	velo_points[1] = 270;
	velo_points[2] = 315;
	velo_points[3] = 320;
	velo_points[4] = 320;
	velo_points[5] = 317;
	velo_points[6] = 312;
	velo_points[7] = 310;
	velo_points[8] = 307;
	velo_points[9] = 302;
	velo_points[10] = 300;
	velo_points[11] = 298;
	velo_points[12] = 296;
	velo_points[13] = 294;
	velo_points[14] = 292;
	velo_points[15] = 290;
	velo_points[16] = 288;
	velo_points[17] = 286;
	velo_points[18] = 285;
	velo_points[19] = 284;
	velo_points[20] = 283;
	velo_points[21] = 282;
	velo_points[22] = 281;
    }
    
    // goes through the cells of all the rings
    // the innermost and outermost ring is never changed in its state
    public void changeAllStates(){
	for(int i=1; i < galaxy.length - 1; i++){
	    for(int j=0; j < galaxy[i].galaxyRow.length; j++){
		galaxy[i].getCell(j).changestate(getLeftCell(i,j), getRightCell(i,j), getTopCell(i,j), getBotCell(i,j));
	    }
	}
	time += timestep;
	if ((time >= 0.15) && (time < 0.15 + timestep)){
		set_own(1);
	}
    }

    // goes through all the states and renews them
    // the innermost and outermost rings are not switched because they would at once be switched on INACTIVE
    public void renewAllStates(){
	for(int i=1; i<galaxy.length - 1; i++){
	    for(int j=0; j<galaxy[i].galaxyRow.length; j++){
		galaxy[i].getCell(j).renewState();
	    }
	}
    }

    // gives you the specified ring 
    public galaxyRing getRing(int ringNum){
	if ((ringNum >= galaxy.length) || (ringNum < 0)){
	    throw new CAException("galaxy.getRing", "ring out of bounds #" + ringNum);
	} else {
	    return galaxy[ringNum];
	}
    }

    public CACellGalaxy getRightCell(int ringNum, int origCell){ //returns the cell to the right of the original cell
	if ((ringNum >= galaxy.length) || (ringNum < 0)){
	    throw new CAException("galaxy.getRightCell", "ring out of bounds #" + ringNum);
	} else {
	    return galaxy[ringNum].getRightCell(origCell);
	}
    }

    public CACellGalaxy getLeftCell(int ringNum, int origCell){ //returns the cell to the left of the original cell
	if ((ringNum >= galaxy.length) || (ringNum < 0)){
	    throw new CAException("galaxy.getLeftCell", "ring out of bounds #" + ringNum);
	} else {
	    return galaxy[ringNum].getLeftCell(origCell);
	}
    }	

    public CACellGalaxy getGalaxyCell(int ringNum, int origCell ){
	if ((ringNum >= galaxy.length) || (ringNum < 0)){
	    throw new CAException("galaxy.getGalaxyCell", "ring out of bounds #" + ringNum);
	} else {
	    return galaxy[ringNum].getCell(origCell);
	}
    }
        
    public CACellGalaxyWithWeight[] getTopCell(int ringNum, int cellNum){
	return verticalNeighbour(ringNum, cellNum, 1);
    }

    public CACellGalaxyWithWeight[] getBotCell(int ringNum, int cellNum){
	return verticalNeighbour(ringNum, cellNum, -1);
    }

    // gets the array of vertical (top / bottom) neighbours
    private CACellGalaxyWithWeight[] verticalNeighbour(int ringNum, int cellNum, int direction){
	CACellGalaxyWithWeight[] nums=new CACellGalaxyWithWeight[2];
	
	if ((ringNum >= galaxy.length-1) || (ringNum < 0)){
	    throw new CAException("galaxy.verticalNeighbour", "out of bounds ring #" + ringNum);
	} else {
	    double difference = galaxy[ringNum].difference - galaxy[ringNum+direction].difference;		
	    double roughCellIndex = galaxyRing.mod(cellNum+difference,numberOfCells, cellNum+difference>=0);
	    
	    if(roughCellIndex<0){
		roughCellIndex=(double)galaxy[1].galaxyRow.length+roughCellIndex-1;
	    }
	    int lowerNum=(int)Math.floor(roughCellIndex);
	    int higherNum=(int)Math.ceil(roughCellIndex);
	    
	    nums[0] = new CACellGalaxyWithWeight(galaxy[ringNum + direction].getCell(lowerNum), roughCellIndex-lowerNum);
	    nums[1] = new CACellGalaxyWithWeight(galaxy[ringNum + direction].getCell(lowerNum), higherNum-roughCellIndex);
	    return nums;
	}
    }	
    
    // calculates the radii of all the rings
    public void setRadii(){
	double scalefactor = 1;
	double currentinnerradius;
	double currentouterradius;
	double previousouterradius = 1;
	
	for(int i=1; i<=numRings(); i++){
	    //draw the first ring without any scaling
	    if(i==1){
		scalefactor = 1;
		currentinnerradius = 0;
		currentouterradius = initRadius;
	    }
	    //set current radii to those set by previous rings
	    else{
		currentinnerradius = previousouterradius;
		currentouterradius = previousouterradius*scalefactor;
	    }
	    //scaling factor is x = sqrt((2b^2-a^2)/b^2), b is the outer radius of the previous ring, a is the inner radius of the previous ring
	    double a = 2*currentouterradius*currentouterradius-currentinnerradius*currentinnerradius;
	    double b = currentouterradius*currentouterradius;
	    scalefactor = Math.sqrt(a/b);
	    
	    previousouterradius = currentouterradius;
	    // *+* previousinnerradius = currentinnerradius;

	    galaxy[i-1].setRingRadius(currentouterradius);
	}
    }
    
    // provides the velocity at a radius r
    public double velocity(double r){
	int i = (int) Math.floor(r);
	if (r < 0){
	    throw new CAException("galaxy.velocity", "negative r = " + r);
	} else {
	    if (i >= velo_points.length - 1){
		return velo_points[velo_points.length - 1];
	    } else {
		return intermediate_value(i, velo_points[i], velo_points[i+1], r);
	    }
	}
    }
        
    // needed for linear interpolation
    private double intermediate_value(double x1, double y1, double y2, double x){
	// x2 - x1 = 1
	return y1 + (y2 - y1) * (x - x1);
    }
    
    // caclulated the rtation speed in units of cells
    public double calculateRotationSpeed(int ringNum){
	double angRotationSpeed;
	double radius;
	
	if (ringNum >= numberOfRings - 1){
	    radius = getRing(numberOfRings - 1).ringRadius;
	} else {
	    radius = (getRing(ringNum).ringRadius + getRing(ringNum + 1).ringRadius) / 2;
	}
	if (radius == 0){
	    angRotationSpeed = 0;
	} else {
	    angRotationSpeed = velocity(radius)*numberOfCells/(2*Math.PI*radius); //in units of cells, not of radians
	}
	return angRotationSpeed;
    }

    // sets the rings forward
    public void moveRingsInTime(){
	for(int i =0; i<galaxy.length; i++){
	    galaxy[i].stepRingForward(calculateRotationSpeed(i));
	}
    }

    public int numRings(){
	return galaxy.length;
    }

    

}
