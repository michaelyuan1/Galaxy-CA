import java.awt.*;
import java.awt.event.*;

public class SimFrame
    extends Frame{

    private int ext = 600;
    private CA autom;
    private int[] step = new int[2];
    
    public SimFrame()
    {
	super("testCA");
	addWindowListener(new WindowClosingAdapter(true));
	setBackground(Color.white);
	setSize(ext, ext);
	setVisible(true);
    }
    
    public void setCA(CA a){
	autom = a;
	step[0] = ext / autom.width;
	step[1] = ext / autom.height;
    }

    public void paint(Graphics g)
    {
	for (int i = 0; i < autom.width; i++){
	    for (int j = 0; j < autom.height; j++){
		if (autom.c[i][j].state == CACell.ACTIVE){
		    g.setColor(Color.white);
		} else {
		    g.setColor(Color.red);
		}
		g.fillRect(i * step[0], j * step[1], step[0], step[1]);
	    }
	}
    }
}