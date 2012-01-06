/*
+--------------------------------------------------------------------------+
|                             IAYC 2010 - GM                               |
|                   Galaxy formation on cellular automata                  |
|                     (c) Alex, Josh, Michael, Wasilij                     |
+--------------------------------------------------------------------------+

StopWatch.java: used for measuering the time.
Simply call start() for starting it, stop() provides you the time elapsed.

*/


class StopWatch {

    private long startTime;
    private boolean isRunning;

    // constructor
    public StopWatch() {
        isRunning = false;
        startTime = 0;
    }

    // start
    public void start() {
        startTime = System.nanoTime();
        isRunning = true;
    }

    // stop
    public long stop() {
        if(isRunning) {
            isRunning = false;
            return  System.nanoTime() - startTime;
        } else {
            return 0;
        }
    }
}