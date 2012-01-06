import java.util.*;

public class CACellfredkin extends CACell{

    public CACellfredkin(int a){
	super(a);
    }

    public void changestate(CACell[] neighb){
	int counter = 0;
	Random r = new Random();

	for (CACell n : neighb){
	    if (n.state == CACell.ACTIVE){
		counter++;
	    }
	}
	
	// fredkin:
	/*if ((counter / 2) == ((double) counter / 2.0)){
	    //System.out.println("even");
	    newstate = CACell.INACTIVE;
	} else {
	    //System.out.println("odd");
	    if (counter==1){
		newstate = CACell.ACTIVE;
	    } else {
		newstate = CACell.ACTIVE;
	    }
	    }*/

	if (counter >= 1){
	    newstate = (int) Math.round(r.nextDouble());          //CACell.INACTIVE;
	} else {
	    newstate = CACell.INACTIVE;
	    }

    }

}