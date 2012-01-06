/*
+--------------------------------------------------------------------------+
|                             IAYC 2010 - GM                               |
|                   Galaxy formation on cellular automata                  |
|                     (c) Alex, Josh, Michael, Wasilij                     |
+--------------------------------------------------------------------------+

A CA hinding the real CA from the View-layer, providing only methods that are really used for displaying the current state of the CA and hinding all the information the GUI should not know about the CAm e.g. is it impossible to access a single cell - only its state.

*/

public class ViewCA{
    // this is the complete CA the ViewCA is asking
    private CA known_ca;

    // constructor
    public ViewCA(CA c){
	known_ca = c;
    }
    
    public int numrings(){
	return known_ca.numrings();
    }
    public int numcells(int ring){
	return known_ca.numcells(ring);
    }
    public double initradius(){
	return known_ca.initradius();
    }

    // provides the state of a specified cell
    public int state_of_cell(int ring, int number){
	return known_ca.getCell(ring, number).state;
    }

    public double state_of_cell(int ring1, int ring2, int number){
	if (ring1 - ring2 >= 1){
	    return state_of_cell(ring1, number);
	} else {
	    double sumstate = 0;
	    for (int i = Math.min(ring1, ring2); i <= Math.max(ring1, ring2); i++){
		sumstate += state_of_cell(i, (int) Math.round(number - this.ringRotation(i)));
	    }
	    return sumstate / (Math.abs(ring2 - ring1) + 1);
	}
    }

    public double ringRotation(int ring){
	return known_ca.ringRotation(ring);
    }

    public double ringRotation(int ring1, int ring2){
	return 0;
    }

    public double ringRadius(int ring){
	return known_ca.ringRadius(ring);
    }

    public double elapsed_time(){
	return known_ca.time;
    }
    
}