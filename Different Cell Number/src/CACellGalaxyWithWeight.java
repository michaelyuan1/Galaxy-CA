/*
+--------------------------------------------------------------------------+
|                             IAYC 2010 - GM                               |
|                   Galaxy formation on cellular automata                  |
|                     (c) Alex, Josh, Michael, Wasilij                     |
+--------------------------------------------------------------------------+

CACellGalaxyWithWeight.java: CaCellGalaxy with a double weight.
above and under-neighbours get a weight coding there relative positions
to the particular element they are the neighbours of.
There are two constructors.

*/


public class CACellGalaxyWithWeight extends CACellGalaxy {
    public double weight;
    
    public CACellGalaxyWithWeight(int s, double weight1, double dt){
	super(s, dt);
	weight=weight1;
    }

    public CACellGalaxyWithWeight(CACellGalaxy c, double w){
	super(c.state, c.timestep);
	weight = w;
    }
}
