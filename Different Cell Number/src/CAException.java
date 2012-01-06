/*
+--------------------------------------------------------------------------+
|                             IAYC 2010 - GM                               |
|                   Galaxy formation on cellular automata                  |
|                     (c) Alex, Josh, Michael, Wasilij                     |
+--------------------------------------------------------------------------+

CAException.java: the Exception thrown by the CA project
need not to be decared (... throws ...), just thrown

*/

public class CAException extends RuntimeException{

    // special format
    public CAException(String who, String hasbeenaskedforwhat){
	super(who + " has been asked for " + hasbeenaskedforwhat);
    }


}