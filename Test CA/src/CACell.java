public abstract class CACell{

    public int state;
    public int newstate;
    public static int ACTIVE = 1;
    public static int INACTIVE = 0;

    public CACell(int s){
	state = s;
    }

    public abstract void changestate(CACell[] neighb);

    public void renew(){
	state = newstate;
    }
	
}