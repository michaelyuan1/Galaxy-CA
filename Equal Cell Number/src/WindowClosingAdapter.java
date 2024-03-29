/*
+--------------------------------------------------------------------------+
|                             IAYC 2010 - GM                               |
|                   Galaxy formation on cellular automata                  |
|                     (c) Alex, Josh, Michael, Wasilij                     |
+--------------------------------------------------------------------------+

WindowClosingAdapter.java

*/

import java.awt.*;
import java.awt.event.*;

public class WindowClosingAdapter
    extends WindowAdapter
{
    private boolean exitSystem;
    
    /**
     * Erzeugt einen WindowClosingAdapter zum Schliessen
     * des Fensters. Ist exitSystem true, wird das komplette
     * Programm beendet.
     */
    public WindowClosingAdapter(boolean exitSystem)
    {
	this.exitSystem = exitSystem;
    }
    
    /**
     * Erzeugt einen WindowClosingAdapter zum Schliessen
     * des Fensters. Das Programm wird nicht beendet.
     */
    public WindowClosingAdapter()
    {
	this(false);
    }
    
    public void windowClosing(WindowEvent event)
    {
	event.getWindow().setVisible(false);
	event.getWindow().dispose();
	if (exitSystem) {
	    System.exit(0);
	}
    }
}