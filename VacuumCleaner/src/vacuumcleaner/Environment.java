package vacuumcleaner;
import java.util.Random;

public class Environment implements EnvironmentInterface {
	private static int CLEAN = 0;
	private static int DIRTY = 1;
	private static int SUCK_PENALTY = 10;
	
	private int n, m; 		// size of Environment
	private int r, c; 		// current location of agent
	
	// Sample environments, each with five parameters:
	//		number of rows in world, n
	//		number of columns in world, m
	//		initial row of agent, r
	//		initial column of agent, c
	//		number of cells to make dirty
	
	private int[][] envs = {
			{4, 4, 0, 0, 8},  		// env 0
			{10, 12, 5, 6, 30}, 		// env 1
			{1, 2, 0, 1, 1},		// env 2
			{5, 6, 0, 4, 5},		// env 3
			{3, 3, 1, 1, 9}			// env 4
	};
	
	private int[][] cell; 		// vacuum world
	private int score;		// penalty score
	private int maxPenalty;
	private int stillDirty;		// to adjust final penalty
	
	// Constructor with single parameter
	// Parameter refers to world defined by envs array
	
	public Environment(int envNum) {
		// default to environment 0 if invalid envNum
		if (envNum<0 || envNum>4) envNum = 0;
		
		// instantiate environment according to parameters
		n = envs[envNum][0];
		m = envs[envNum][1];
		cell = new int[n][m];
		
		// set start cell
		r = envs[envNum][2];
		c = envs[envNum][3];
		
		// set initial penalty score to zero
		score = 0;
		
		// set maximum penalty
		maxPenalty = n*m*SUCK_PENALTY;
		// set dirty cells
		int numDirty = envs[envNum][4];
		stillDirty = numDirty;
		
		if (numDirty == n*m) setAll(DIRTY);
		else {
			// set all to clean
			setAll(CLEAN);
			dirtySome(numDirty);
		}
	}
	
	// Constructor with three parameters
	// Defines size of world and number of cells to make dirty
	
	public Environment(int numRows, int numCols, int nDirty) {
	    // instantiate world
		cell = new int[numRows][numCols];
		n = numRows;
		m = numCols;
		maxPenalty = n*m*SUCK_PENALTY;
                stillDirty = nDirty;

		// handle too many dirty cells by making all DIRTY
		if (nDirty >= numRows*numCols) setAll(DIRTY);
		else {
			setAll(CLEAN);
			dirtySome(nDirty);
		}
		
		// put agent in upper left corner
		r = 0;
		c = 0;
	}
	
	private void setAll(int state) {
		for (int i=0; i<n; i++) {
			for (int j=0; j<m; j++) {
				cell[i][j] = state;
			}
		}
	}
	
	private void dirtySome(int numDirty) {
		// set numDirty random cells to DIRTY
		Random rand = new Random();
		int countDirty = 0;
		while (countDirty < numDirty) {
			int pos = rand.nextInt(n*m);
			if (cell[pos/m][pos%m]==CLEAN) {
				cell[pos/m][pos%m]=DIRTY;
				countDirty++;
			}
		}
	}
	
	@Override
	public int moveLeft() {
		score++;
		if (c>0) {
			c--;
			return 0;
		}
		else return 1; 		// bump
	}

	@Override
	public int moveRight() {
		score++;
		if (c<m-1) {
			c++;
			return 0;
		}
		else return 1; 		// bump
	}

	@Override
	public int moveUp() {
		score++;
		if (r>0) {
			r--;
			return 0;
		}
		else return 1; 		// bump
	}

	@Override
	public int moveDown() {
		score++;
		if (r<n-1) {
			r++;
			return 0;
		}
		else return 1; 		// bump
	}

	@Override
	public void suck() {
		if (cell[r][c] == DIRTY) stillDirty--;
		cell[r][c] = CLEAN;
		score += SUCK_PENALTY;
	}

	@Override
	public boolean isDirty() {
		if (cell[r][c]==DIRTY)
			return true;
		else return false;
	}
        

	@Override
	public int getScore() {
		int tempScore = score + stillDirty*SUCK_PENALTY;
		score = maxPenalty + tempScore;
		return tempScore;
	}
	
	public String toString() {
		String s = "";
		for (int i=0; i<n; i++) {
			for (int j=0; j<m; j++) {
				if (i==r && j==c) s +="A";
				else if (cell[i][j]==CLEAN) s+= ".";
				else s += "*";
			}
			s += "\n";
		}
		return s;
	}
	

	public void reset() {
		// reset to 0,0 location
                r=0;
                c=0;
                score=0;
	}
        
	public int getRow() {
		return r;
	}

	public int getColumn() {
		return c;
	}        

    public boolean isDirty(int i, int y) {
        if (cell[i][y]==DIRTY)
			return true;
		else return false;
    }
}