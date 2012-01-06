/*
+--------------------------------------------------------------------------+
|                             IAYC 2010 - GM                               |
|                   Galaxy formation on cellular automata                  |
|                     (c) Alex, Josh, Michael, Wasilij                     |
+--------------------------------------------------------------------------+

CACell.java: A cell of the CA with an abstract changestate method.
Writing access only to <newstate>, reading access only to <state>.
state is set to the value of newstate by calling the renewState
method afterwards.

*/

public abstract class CACell{

    public int state;
    public int newstate;
    public static final int ACTIVE = 1;                // states coded as integers
    public static final int INACTIVE = 0;              // do not use numerical values

    public CACell(int s){
	state = s;
    }

    public abstract void changestate(CACellGalaxy left, CACellGalaxy right, CACellGalaxyWithWeight[] above, CACellGalaxyWithWeight[] under);
    // the neighbours above and under are given in an array because there are many of them.

    public void renewState(){
	state = newstate;
    }
	
}