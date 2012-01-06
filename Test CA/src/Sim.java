import java.util.*;

public class Sim{

    public static final int ext = 100;
    public static CA autom;
    public static SimFrame frame;

    public static void main(String[] wuergs){
	
	// Frame intialization
	frame = new SimFrame();
	frame.setLocation(100, 100);
	frame.setVisible(true);
	//frame.setDoubleBuffered(true);

	// CA initialization
	autom = new CA(ext, ext);
	frame.setCA(autom);
	initialize();

	// Simulation
	Timer t = new Timer();
	CATimerTask tt = new CATimerTask();
	Date d = new Date();

	t.schedule(tt, d, 800);
    }

    // CA initialization
    public static void initialize(){
	for (int i = ext / 3; i < 2 * ext / 3; i++){
	   autom.c[i][ext/2].state = CACell.ACTIVE;
	}
    }

}

  
class CATimerTask extends TimerTask{


    public void run(){
	Sim.autom.perform_step();
	Sim.frame.repaint();
    }
    
}