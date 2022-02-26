import java.awt.Point;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.HashMap;
public class ReinforcementLearning {
	private Board board;
	private Timer timer;
	private HashMap<int[],float[]> qTable =  new HashMap<int[],float[]>();
	private float timeToRun, probDesiredDirection, constantReward, sigmaPercent;

	public ReinforcementLearning(Board board, float timeToRun, float probDesiredDirection, float constantReward, float sigmaPercent) {
		this.board = board;
		this.timeToRun = timeToRun;
		this.probDesiredDirection = probDesiredDirection;
		this.constantReward = constantReward;
		this.sigmaPercent = sigmaPercent;
		timer = new Timer(timeToRun);
		timer.start();
		
		runReinforcement();
	}
	
	public void runReinforcement() {
		
		do {
			int randomXCoordinate = ThreadLocalRandom.current().nextInt(0, board.width);
			int randomYCoordinate = ThreadLocalRandom.current().nextInt(0, board.height);
			Integer initCordValue = board.boardInt[randomXCoordinate][randomYCoordinate];
			Coordinate currentCoordinate = new Coordinate(CoordinateType.CURRENT, randomXCoordinate,randomYCoordinate,initCordValue);
			learn(currentCoordinate);
		}
		while(!timer.finished());
		
	}
	private void updateQtable(Coordinate currCoord, Direction dir){
		int []key = {currCoord.x, currCoord.y};
		float []values = qTable.get(key);
		switch(dir){
			case UP:
				values[0] = currCoord.getUpCost();
				qTable.put(key,values);
				break;

			case LEFT:
				values[1] = currCoord.getLeftCost();
				qTable.put(key,values);
				break;

			case RIGHT:
				values[2] = currCoord.getRightCost();
				qTable.put(key,values);
				break;
			case DOWN:
				values[3] = currCoord.getDownCost();
				qTable.put(key,values);
				break;
		}



	}
	public void learn(Coordinate currCoord){
		for(Coordinate c: board.terminalStates){ //check if on terminal state
			if(currCoord.equals(c)){
				return; //return if terminal
			}
		}
		Direction dir = calculateBestDirection(currCoord); // if not, make move
		updateQtable(currCoord,dir);

		//todo - each float is saved in the currCoordinate
		//todo - iterate through the currCoordinate and find the qMAX float value and update in table
		//todo - save new qMax float in table?

		switch(dir){
			case UP:
				if(currCoord.y == 0){ // check if at top bound
					learn(currCoord);
					break;
				}
				else{
					learn(new Coordinate(currCoord.type, currCoord.x, currCoord.y - 1, board.board[currCoord.x-1][currCoord.y].value));
				}
				break;
			case DOWN:
				if(currCoord.y == board.height){ // check if at bottom bound
					learn(currCoord);
					break;
				}
				else{
					learn(new Coordinate(currCoord.type, currCoord.x, currCoord.y + 1, board.board[currCoord.x-1][currCoord.y].value));
				}
				break;
			case LEFT:
				if(currCoord.x == 0){ // check if at left bound
					learn(currCoord);
					break;
				}
				else{
					learn(new Coordinate(currCoord.type, currCoord.x - 1, currCoord.y, board.board[currCoord.x-1][currCoord.y].value));
				}
				break;
			case RIGHT:
				if(currCoord.x == 0){ // check if at right bound
					learn(currCoord);
					break;
				}
				else{
					learn(new Coordinate(currCoord.type, currCoord.x + 1, currCoord.y, board.board[currCoord.x-1][currCoord.y].value));
				}
				break;
		}
	}
	
	//Calculate which direction is the best direction to travel in
	private Direction calculateBestDirection(Coordinate currCoord) {
	
		Direction bestDir = null;
		float highestValue = Float.MIN_VALUE;
		Direction dir = null;
		
		//Calculate for each direction
		for(int i = 0; i <= 3; i++) {
			
			switch(i) {
			case 0:
				dir = Direction.UP;
				break;
			case 1:
				dir = Direction.DOWN;
				break;
			case 2:
				dir = Direction.RIGHT;
				break;
			case 3:
				dir = Direction.LEFT;
				break;
			}
			
			float currValue = calculateCoordinateValue(currCoord, dir); //Get the value of the specific direction
			
			if(currValue > highestValue) {
				highestValue = currValue;
				bestDir = dir;
			}
		}
		
		//If the random value is high enough, just go in any random direction
		Random rand = new Random();
		
		//Math.random() generates a value between 0.0 and 1.0, if that number is lower than sigmaPercent, we move in a random direction (exploration)
		if(Math.random() <= sigmaPercent) {
			int i = ThreadLocalRandom.current().nextInt(0, 4);

			//Move in a random direction
			switch(i) {
			case 0:
				bestDir = Direction.UP;
				break;
			case 1:
				bestDir = Direction.DOWN;
				break;
			case 2:
				bestDir = Direction.RIGHT;
				break;
			case 3:
				bestDir = Direction.LEFT;
				break;
			}				
		}
		
		return bestDir;
		
	}

	public void initializeQtable(){
		int height = this.board.height;
		int width = this.board.width;

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; i++) {
				int key[] = {i,j};
				float values[] = {0,0,0,0};
				qTable.put(key,values);

			}
		}
	}
	private float calculateCoordinateValue(Coordinate currCoord, Direction dir) {
		
		//Initialize points
		Point top, bottom, left, right;
		top = new Point(currCoord.x, currCoord.y + 1);
		bottom = new Point(currCoord.x, currCoord.y - 1);
		left = new Point(currCoord.x - 1, currCoord.y);
		right = new Point(currCoord.x + 1, currCoord.y);
		
		//Total value of the coordinate locations
		float topVal = 0; 
		float bottomVal = 0;
		float leftVal = 0;
		float rightVal = 0;
		
		//Multipliers based on what direction we want to travel
		double topValMult = 0; 
		double bottomValMult = 0;
		double leftValMult = 0;
		double rightValMult = 0;
		
		//Get the value of the coordinate on the board. If it doesn't exist, simply return the cost to bounce back (the constant reward)
		if(top.y <= board.height) {
			topVal = board.getBoard()[top.x][top.y].getValue();
		}else {
			topVal = constantReward;
		}
		
		if(bottom.y >= 0) {
			bottomVal = board.getBoard()[bottom.x][bottom.y].getValue();
		}else {
			bottomVal = constantReward;
		}
		
		if(left.x >= 0) {
			leftVal = board.getBoard()[left.x][left.y].getValue();
		}else {
			leftVal = constantReward;
		}
		
		if(right.x <= board.width) {
			rightVal = board.getBoard()[right.x][right.y].getValue();
		}else {
			rightVal = constantReward;
		}
		
		
		//Assign multiplication weights based on what direction we travel. (It is impossible to travel backwards)
		switch(dir) {
		
		case UP:
			topValMult = probDesiredDirection;
			bottomValMult = 0;
			leftValMult = (1.0 - probDesiredDirection) / 2;
			rightValMult = (1.0 - probDesiredDirection) / 2;
			break;
		case DOWN:
			topValMult = 0;
			bottomValMult = probDesiredDirection;
			leftValMult = (1.0 - probDesiredDirection) / 2;
			rightValMult = (1.0 - probDesiredDirection) / 2;
			break;
		case LEFT:
			topValMult = (1.0 - probDesiredDirection) / 2;
			bottomValMult = (1.0 - probDesiredDirection) / 2;
			leftValMult = probDesiredDirection;
			rightValMult = 0;
			break;
		case RIGHT:
			topValMult = (1.0 - probDesiredDirection) / 2;
			bottomValMult = (1.0 - probDesiredDirection) / 2;
			leftValMult = 0;
			rightValMult = probDesiredDirection;
			break;
		}


		//Calculate the total value
		double totalValue = (topVal * topValMult) + (bottomVal * bottomValMult) + (rightVal * rightValMult) + (leftVal * leftValMult);
		float returnValue = (float)(totalValue + constantReward);

		switch(dir){
			case UP:
				currCoord.upCost = returnValue;
				break;
			case DOWN:
				currCoord.downCost = returnValue;
				break;
			case LEFT:
				currCoord.leftCost = returnValue;
				break;
			case RIGHT:
				currCoord.rightCost = returnValue;
				break;
		}

		//return the value + the cost of moving
		return (float)(totalValue + constantReward);
	}
	
}
