/*
+--------------------------------------------------------------------------+
|                             IAYC 2010 - GM                               |
|                   Galaxy formation on cellular automata                  |
|                     (c) Alex, Josh, Michael, Wasilij                     |
+--------------------------------------------------------------------------+

CACellGalaxy.java: CACell providing a special rule in the changestate method


*/

import java.util.*;

public class CACellGalaxy extends CACell{
    public static final int STAR = CACell.ACTIVE;
    public static final int DUST = CACell.INACTIVE;
    public static final double maximum_age = 0.02;
    public double age;
    public double timestep;

    public CACellGalaxy(int state, double timestep){
	super(state);
	age = 0;
	this.timestep = timestep;
    }

    public void changestate(CACellGalaxy left, CACellGalaxy right, CACellGalaxyWithWeight[] above, CACellGalaxyWithWeight[] under){

	int stars = 0;

	if (this.state == CACellGalaxy.STAR){
	    age += timestep;
	}
	
	//Random r = new Random();

	/*if ((right.state == CACell.ACTIVE) || (left.state == CACell.ACTIVE) || (this.state == CACell.ACTIVE)){
	    this.newstate = CACell.ACTIVE;
	} else {
	    this.newstate = CACell.INACTIVE;
	    }*/
	
    	/*if ((under[0].state == CACell.ACTIVE) || (under[1].state == CACell.ACTIVE) || (state == CACell.ACTIVE)){
	    this.newstate = CACell.ACTIVE;
	} else {
	    this.newstate = CACell.INACTIVE;
	    }*/

	if (left.state == CACellGalaxy.STAR){
	    stars++;
	}
	if (right.state == CACellGalaxy.STAR){
	    stars++;
	}
	if (above[0].state == CACellGalaxy.STAR){
	    stars++;
	}
	if (above[1].state == CACellGalaxy.STAR){
	    stars++;
	}
	if (under[0].state == CACellGalaxy.STAR){
	    stars++;
	}
	if (under[1].state == CACellGalaxy.STAR){
	    stars++;
	}
	
	// spirals:
	/*
	if ( (under[0].state == CACellGalaxy.STAR) || (under[1].state == CACellGalaxy.STAR)  ) {          //|| 
	   
	    this.newstate = CACellGalaxy.STAR;
	} else {
	    this.newstate = CACellGalaxy.DUST;
	    }*/
	
	
	// here the try-rules for galaxies

	if (this.state == CACellGalaxy.STAR){
	    if (age > maximum_age){
		// RULE II
		age = 0;
		this.newstate = CACellGalaxy.DUST;
	    } else {
		this.newstate = CACellGalaxy.STAR;
	    }
	} else {
	    if (Math.random() < 0.002 * Math.pow((6 - stars) / 6, 2)){
		this.newstate = CACellGalaxy.STAR;
	    } else {
		if ((stars == 4) || (stars == 5)){
		    //System.out.println(stars);
		    this.newstate = CACellGalaxy.STAR;
		} else {
		    //System.out.println(stars);
		    this.newstate = CACellGalaxy.DUST;
		}
	    }
	}


	/*
	if (this.state == CACellGalaxy.STAR){
	    if ((under[0].state == CACellGalaxy.DUST) && (under[1].state == CACellGalaxy.DUST) && (left.state == CACellGalaxy.DUST) && 
		(above[0].state != CACellGalaxy.DUST) && (above[1].state != CACellGalaxy.DUST) && (right.state != CACellGalaxy.DUST) ){
		this.newstate = CACellGalaxy.DUST;
		} else {


	    this.newstate = CACellGalaxy.STAR;
		//}

	    // spontaneous death because of being alone
	    if (stars <= 1){
		if (Math.random() < 0.002){
		    this.newstate = CACellGalaxy.DUST;
		}
	    }
	    if  ((stars >= 3) &&(stars <= 4)){
		if (Math.random() < 0.09){
		    this.newstate = CACellGalaxy.DUST;
		}
	    }

	} else {
	    this.newstate = CACellGalaxy.DUST;

	    // spontaneous star creation out of nothing
	    if (Math.random() < 0.00001){
		this.newstate = CACellGalaxy.STAR;
	    }
	    
	    /*if ((above[0].state == CACellGalaxy.DUST) && (above[1].state == CACellGalaxy.DUST) && (right.state == CACellGalaxy.DUST) && 
	      (under[0].state != CACellGalaxy.DUST) && (under[1].state != CACellGalaxy.DUST) && (left.state != CACellGalaxy.DUST) ){
	    if ((stars >= 1) && (Math.random() < 0.01)){
		this.newstate = CACellGalaxy.STAR;
	    }
	}*/
	
	/*if (age > maximum_age){
	    age = 0;
	    this.newstate = CACellGalaxy.DUST;
	    }*/
	
	
    }

    


}