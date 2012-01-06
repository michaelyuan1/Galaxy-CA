public class CA{
    
    public CACell[][] c;
    public int width;
    public int height;

    public CA(int width, int height){
	c = new CACell[width][height];
	for (int i = 0; i<width; i++){
	    for (int j = 0; j<height; j++){
		c[i][j] = new CACellfredkin(CACell.INACTIVE);
	    }
	}
	this.width = width;
	this.height = height;
    }

    public CACell getCell(int i, int j){
	if (i < 0) {i += width;}
	if (i >= width) {i -= width;}
	if (j < 0) {j += width;}
	if (j >= height) {j -= height;}
	
	return c[i][j];
    }

    public void perform_step(){
	CACell[] a = new CACell[4];

	//System.out.println("reached the step");
	for (int i = 0; i < width; i++){
	    for (int j = 0; j < height; j++){
		a[0] = getCell(i + 1, j);
		a[1] = getCell(i, j + 1);
		a[2] = getCell(i - 1, j);
		a[3] = getCell(i, j - 1);
		
		c[i][j].changestate(a);
	    }
	}

	for (int i = 0; i < width; i++){
	    for (int j = 0; j < height; j++){
		c[i][j].renew();
	    }
	}
    }
}