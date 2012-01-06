/*
+--------------------------------------------------------------------------+
|                             IAYC 2010 - GM                               |
|                   Galaxy formation on cellular automata                  |
|                     (c) Alex, Josh, Michael, Wasilij                     |
+--------------------------------------------------------------------------+

CAFrame showing the current state of the CA.

*/

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class CAFrame extends Frame{

    // soze of the window
    private int xsize = 500;
    private int ysize = 500;
    // used for calculations
    private double phi = (Math.PI/180);
    // scalefactor used for adjusting the image such that it fits competely into the frame
    private double CAscalefactor = 1;
    // origin of the coordinate system
    private double originx;
    private double originy;
    // saved for not calculating it every time; angle under which every cell is seen
    private double cell_angle;  // in degrees!
	    
    // lookup-table of sines and cosines
    private double sinTable[] = new double[362];
    private double cosTable[] = new double[362];
    // ca hiding the real ca and providing only methods really needed
    private ViewCA VCA;
    // used for double-buffering (db)
    private Image dbImage;
    private Graphics dbGraphics;
    // number of rings that are averaged while drwing
    private int ring_drawing_step = 1;
    // draw rings?
    private boolean draw_grid = true;

    // constructor
    public CAFrame(int x, int y, ViewCA c){
	super("Galaxy Simulation");
	addWindowListener(new WindowClosingAdapter(true));
	this.setBackground(Color.white);
	// startup position
	this.setLocation(10, 10);

	
	setSize(x, y);
	setVisible(true);

	xsize = x;
	ysize = y;
	originx = xsize / 2;
	originy = ysize / 2;

	VCA = c;
	CAscalefactor = Math.min(xsize/2 - 2, ysize/2 - 40) / VCA.ringRadius(VCA.numrings() - 1);
	
	for(int i=0;i<sinTable.length;i++){
	    sinTable[i] = Math.sin(i*phi);
	    cosTable[i] = Math.cos(i*phi);
	}

	cell_angle = 360.0/VCA.numcells();
    }

    // proper color management
    public static Color ACTIVE_COLOR(double weight){
	return new Color((int) ((1-weight) * 255), (int) ((1-weight) * 255), (int) ((1-weight) * 255));
    }
    public static Color ACTIVE_COLOR_zero(double weight){
	return new Color((int) ((1-weight) * 255), 255, (int) ((1-weight) * 255));
    }
    public static Color ACTIVE_COLOR(){
	return ACTIVE_COLOR(1);
    }
    public static Color ACTIVE_COLOR_zero(){
	return ACTIVE_COLOR_zero(1);
    }


    public void set_ring_drawing_step(int step){
	ring_drawing_step = step;
    }
    
    public void set_draw_grid(boolean d){
	draw_grid = d;
    }

    // needed for double buffering
    public void update(Graphics g)
    {
	//Double-Buffer initialisieren
	if (dbImage == null) {
	    dbImage = createImage(
				  this.getSize().width,
				  this.getSize().height
				  );
	    dbGraphics = dbImage.getGraphics();
	}
	//Hintergrund löschen
	dbGraphics.setColor(getBackground());
	dbGraphics.fillRect(
			    0,
			    0,
			    this.getSize().width,
			    this.getSize().height
			    );
	//Vordergrund zeichnen
	dbGraphics.setColor(getForeground());
	paint(dbGraphics);
	//Offscreen anzeigen
	g.drawImage(dbImage,0,0,this);
    }
    
    public void paint(Graphics g){
	Color curcolor;
	double ring_radius_under;
	boolean firsttime;
	double state;

	curcolor = CAFrame.ACTIVE_COLOR_zero();

	g.drawString("#cells: " + String.valueOf(VCA.numrings()) + " x " + String.valueOf(VCA.numcells()) + " = " + String.valueOf(VCA.numrings() * VCA.numcells()), 10, 50);
	g.drawString("outer Radius: " + String.valueOf(Math.sqrt(VCA.numrings()) * VCA.initradius()), 10, 64);
	g.drawString(String.valueOf("time: "  + Math.round(VCA.elapsed_time() * 1000) / 1000.0), 10, 78);
	//g.drawString(String.valueOf(Math.round(VCA.ringRadius(VCA.numrings() - 1) * 1000) / 1000.0), 100, 120);
	
	for (int i = VCA.numrings()-1; i>=0; i-=ring_drawing_step){
	    curcolor = CAFrame.ACTIVE_COLOR(0.1);
	    
	    if (draw_grid == true){
		drawRing(CAscalefactor * VCA.ringRadius(i), curcolor, g);
	    }
		
	    if (i < ring_drawing_step){
		ring_radius_under = 0;
	    } else {
		ring_radius_under = VCA.ringRadius(i-ring_drawing_step);
	    }
	    
	    firsttime = true;
	    curcolor = CAFrame.ACTIVE_COLOR_zero();
	    
	    for(int j=0; j<VCA.numcells(); j++){
		
		if (firsttime == false){
		    curcolor = CAFrame.ACTIVE_COLOR(0.1);
		}
		if (draw_grid == true){
		    if ((i == 0) && (j == 0)){
			//System.out.println("celloutlines: " + angular_extent_of_cell(i, j)[0] + " " + angular_extent_of_cell(i, j)[1]);
		    }
		    drawCellOutlines(CAscalefactor*ring_radius_under, CAscalefactor*VCA.ringRadius(i), angular_extent_of_cell(i, j), curcolor, g);
		}
		state = VCA.state_of_cell(i, Math.max(0, i - ring_drawing_step), j);
		//if ((i == 0) && (j == 0)){ System.out.println("state: " + state); }
		if (state != CACell.INACTIVE){
		    if (firsttime == false){
			curcolor = CAFrame.ACTIVE_COLOR(state);
		    }
		    if ((i == 0) && (j == 0)){
			//System.out.println("fillcell: " + angular_extent_of_cell(i, j)[0] + " " + angular_extent_of_cell(i, j)[1]);
		    }
		    fillCell(CAscalefactor*ring_radius_under, CAscalefactor*VCA.ringRadius(i), angular_extent_of_cell(i, j), curcolor, g);
		} 
				
		if (firsttime == true){firsttime = false;}
		curcolor = CAFrame.ACTIVE_COLOR();
	    }
	}
    } 


    //Takes length of line, angle (zero point being x axis, clockwise), initial x coord, initial y coord and the frame
    private void drawPolarLine(double startlength, double length, int theta, Graphics g){
	double angle = (Math.PI/180)*theta;
	
	double startx = originx + startlength * cosTable[theta];
	double starty = originy - startlength * sinTable[theta];
	
	double endx = originx + (startlength + length)*cosTable[theta];
	double endy = originy - (startlength + length)*sinTable[theta];
	
	g.drawLine((int) (startx), (int) (starty),(int) (endx), (int) (endy));
    }
    
    private void drawRing(double radius, Color c, Graphics g){
	g.setColor(c);
	g.drawArc((int) (originx-radius), (int) (originy-radius), (int) (radius*2),(int) (radius*2), 0, 360);
	g.setColor(Color.black);
    }
    
    // frwas the grid
    private void drawCellOutlines(double inner, double outer, int[] theta, Color c, Graphics g)
    {
	g.setColor(c);
		
	drawPolarLine(inner, (outer-inner), theta[0], g);
	g.setColor(Color.black);
    }
    
    private void fillCell(double inner, double outer, int[] theta, Color c, Graphics g)
    {

	int counter;
	counter = 0;
	for(int i=theta[0]; i<=theta[1]; i++){
	    counter++;
	}
	for(int i=theta[1]; i >= theta[0]; i--){
	    counter++;
	}
	
	int xpointArray[] = new int[counter];
	int ypointArray[] = new int[counter];
	
	counter = 0;
	
	for (int i=theta[0]; i<=theta[1]; i++){
	    xpointArray[counter] = (int) (cosTable[i%362]*inner+originx);
	    ypointArray[counter] = (int) (-sinTable[i%362]*inner+originy);
	    counter++;
	}
	for (int i=theta[1]; i >= theta[0]; i--){
	    xpointArray[counter] = (int) (cosTable[i%362]*outer+originx);
	    ypointArray[counter] = (int) (-sinTable[i%362]*outer+originy);
	    counter++;
	}
	
	g.setColor(c);
	g.fillPolygon(xpointArray, ypointArray, xpointArray.length);
	g.setColor(Color.black);
    }
    
    
    // returns an array of angles of the beginning of the cell and of its end
    private int[] angular_extent_of_cell(int ring, int number){
	double tmp;
	int res[] = new int[2];
	tmp = (number + VCA.ringRotation(ring)) * cell_angle;
	res[0] = (int) (Math.round(tmp)) % 361;
	res[1] = (int) (Math.round(tmp + cell_angle)) % 361;
	return res;
    }
}
