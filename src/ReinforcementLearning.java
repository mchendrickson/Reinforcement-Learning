import java.awt.Point;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.HashMap;

public class ReinforcementLearning {
	private Board board;
	private Timer timer;
	private float probDesiredDirection, constantReward, epsilonPercent;

	/**
	 * Constructor for ReinforcementLearning
	 * @param board
	 * @param timeToRun
	 * @param probDesiredDirection
	 * @param constantReward
	 * @param epsilonPercent
	 */
	public ReinforcementLearning(Board board, float timeToRun, float probDesiredDirection, float constantReward, float epsilonPercent) {
		this.board = board;
		this.probDesiredDirection = probDesiredDirection;
		this.constantReward = constantReward;
		this.epsilonPercent = epsilonPercent;
		timer = new Timer(timeToRun);
		timer.start();

		runReinforcement();
	}
	
	/**
	 * Method that runs for as long as the timer is set and calls learn, the method that updates the board values
	 */
	public void runReinforcement() {

		do {
			
			int randomXCoordinate = ThreadLocalRandom.current().nextInt(0, board.width);
			int randomYCoordinate = ThreadLocalRandom.current().nextInt(0, board.height);
			
			System.out.println("Starting at: (" + randomXCoordinate + " " + randomYCoordinate + ")");
			
			Integer initCordValue = board.boardInt[randomYCoordinate][randomXCoordinate];
			Coordinate currentCoordinate = new Coordinate(CoordinateType.CURRENT, randomXCoordinate,randomYCoordinate,initCordValue);
			learn(currentCoordinate);
		}
		while(!timer.finished());
		finalPrint();
	}

	public Coordinate updateQTable(Coordinate currCoord, Direction dir){
		Coordinate nextCoord;
		Point top, bottom, left, right;
		float alpha = (float) 0.10;
		top = new Point(currCoord.col, currCoord.row - 1);
		bottom = new Point(currCoord.col, currCoord.row + 1);
		left = new Point(currCoord.col - 1, currCoord.row);
		right = new Point(currCoord.col + 1, currCoord.row);
		switch(dir){
			case LEFT:
				if(left.x >= 0) {
					nextCoord = board.getBoard()[currCoord.row][currCoord.col - 1];
					currCoord.leftCost = currCoord.leftCost + alpha * (constantReward + 1 * (nextCoord.value)) - currCoord.leftCost;
				}
				break;
			case RIGHT:
				if(right.x < board.width) {
					nextCoord = board.getBoard()[currCoord.row][currCoord.col + 1];
					currCoord.rightCost = currCoord.rightCost + alpha * (constantReward + 1 * (nextCoord.value)) - currCoord.rightCost;
				}
				break;
			case UP:
				if(top.y >= 0) {
					nextCoord = board.getBoard()[currCoord.row - 1][currCoord.col];
					currCoord.upCost = currCoord.upCost + alpha * (constantReward + 1 * (nextCoord.value) - currCoord.upCost);
				}
				break;

			case DOWN:
				if(bottom.y < board.height) {
					nextCoord = board.getBoard()[currCoord.row + 1][currCoord.col];
					currCoord.downCost = currCoord.downCost + alpha * (constantReward + 1 * (nextCoord.value) - currCoord.downCost);
				}
				break;
		}

		return currCoord;

	}

	/**
	 * Prints the final result (with arrows)
	 */
	public void finalPrint(){
		String[][] finalPrint = new String[board.height][board.width];
		for(int row = 0; row < board.height; row++) {
			for(int col = 0; col < board.width; col++) {
				Coordinate printCoord = board.board[row][col];
				Direction printDir = printCoord.highestDir();
				CoordinateType type = printCoord.type;
				switch(type){
					case TERMINAL:
						finalPrint[row][col] = String.valueOf((int)printCoord.value);
						break;
					case CURRENT:
						switch (printDir){
							case UP:
								finalPrint[row][col] = "^";
								break;
							case LEFT:
								finalPrint[row][col] = "<";
								break;
							case RIGHT:
								finalPrint[row][col] = ">";
								break;
							case DOWN:
								finalPrint[row][col] = "v";
								break;
						}
						break;
				}
			}
		}
		for(String[] s: finalPrint){
			for(String s1 : s){
				System.out.print(s1 + "\t");
			}
			System.out.println();
		}
	}

	/**
	 * Search for a goal state
	 * @param currCoord
	 */
	public void learn(Coordinate currCoord){
		boolean explore = false; //check if we are exploring in which case we update qTable with move cost instead of the highest move cost
		for(Coordinate c: board.terminalStates){ //check if on terminal state
			if(currCoord.equals(c)){
				return; //return if terminal state
			}
		}
		Direction dir = null;
		//Math.random() generates a value between 0.0 and 1.0, if that number is lower than sigmaPercent, we move in a random direction (exploration)
		if(Math.random() <= epsilonPercent) {
			explore = true; // we are exploring
			int i = ThreadLocalRandom.current().nextInt(0, 4);

			//Move in a random direction
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
		}
		else {
			dir = calculateBestDirection(currCoord); //get the best direction to move based on values in the board
		}
		currCoord = updateQTable(currCoord,calculateBestDirection(currCoord));
//		System.out.println();
//		board.printBoard();
		//Clone the coordinate, add it to the board with the updated value
		Coordinate newCoord = currCoord.clone();
		if(explore) {
			float moveFloat = calculateCoordinateValue(currCoord, dir); // cost for move with explore
			if (currCoord.getType() != CoordinateType.TERMINAL) { // check if next move not terminal
				newCoord.value = moveFloat; // update qTable
				this.board.board[currCoord.row][currCoord.col] = newCoord; // save on coordinate board
			}
		}
		else{
			float highestFloat = currCoord.highestFloat(); // cost to move when not exploring (recording the best value)
			if(currCoord.getType() != CoordinateType.TERMINAL) { // check if terminal state (doesnt make update if)
				newCoord.value = highestFloat;  // save value as highest float
				this.board.board[currCoord.row][currCoord.col] = newCoord; // update coordinate board
			}
		}


		//System.out.println("Currently at: (" + currCoord.col + " " + currCoord.row + ") " + "Moving: " + dir);

		if(!explore) {
			//chance to go in the not desired direction via randomness
			double notDesiredDir = Math.random();
			boolean left = false;//chance to go left of attempted direction
			boolean right = false;//change to go right from attempted direction
			double NotDesiredChance = (1 - probDesiredDirection) / 2; //individual chance to go left vs right
			if (notDesiredDir <= NotDesiredChance) { // check if chance is between 0 and half of 1-the chance to go the right way
				left = true;//goes left from attempted direction
			} else if (notDesiredDir <= (2 * notDesiredDir)) { // checks if greater than half of 1-the chance to go the right way but less than the chance to go the right way
				right = true;//goes right from attempted direction
			}
			if (left || right) {//if we are going either left of right from the desired direction
				switch (dir) {
					case UP:
						if (left) {
							dir = Direction.LEFT;
						}
						if (right) {
							dir = Direction.RIGHT;
						}
						break;
					case DOWN:
						if (left) {
							dir = Direction.RIGHT;
						}
						if (right) {
							dir = Direction.LEFT;
						}
						break;
					case LEFT:
						if (left) {
							dir = Direction.DOWN;
						}
						if (right) {
							dir = Direction.UP;
						}
						break;
					case RIGHT:
						if (left) {
							dir = Direction.UP;
						}
						if (right) {
							dir = Direction.DOWN;
						}
						break;
				}
			}
		}
			//Check and make sure we haven't hit a boundary
		switch(dir){
	
			case UP:
				if(currCoord.row == 0){ // check if at top bound
					learn(new Coordinate(currCoord.type, currCoord.col, currCoord.row, board.board[currCoord.row][currCoord.col].value));
					break;
				}
				else{
					learn(new Coordinate(currCoord.type, currCoord.col, currCoord.row - 1, board.board[currCoord.row-1][currCoord.col].value));
				}
				break;
			case DOWN:
				if(currCoord.row == board.height - 1){ // check if at bottom bound
					learn(new Coordinate(currCoord.type, currCoord.col, currCoord.row, board.board[currCoord.row][currCoord.col].value));
					break;
				}
				else{
					learn(new Coordinate(currCoord.type, currCoord.col, currCoord.row + 1, board.board[currCoord.row+1][currCoord.col].value));
				}
				break;
			case LEFT:
				if(currCoord.col == 0){ // check if at left bound
					learn(new Coordinate(currCoord.type, currCoord.col, currCoord.row, board.board[currCoord.row][currCoord.col].value));
					break;
				}
				else{
					learn(new Coordinate(currCoord.type, currCoord.col - 1, currCoord.row, board.board[currCoord.row][currCoord.col-1].value));
				}
				break;
			case RIGHT:
				if(currCoord.col == board.width - 1){ // check if at right bound
					learn(new Coordinate(currCoord.type, currCoord.col, currCoord.row, board.board[currCoord.row][currCoord.col].value));
					break;
				}
				else{
					learn(new Coordinate(currCoord.type, currCoord.col + 1, currCoord.row, board.board[currCoord.row][currCoord.col+1].value));
				}
				break;
			default:
				break;
		}
	}

	/**
	 * Calculate which direction is the best direction to travel in
	 * @param currCoord
	 * @return dir
	 */
	private Direction calculateBestDirection(Coordinate currCoord) {
	
		Direction bestDir = null;
		float highestValue = currCoord.highestFloat();
		if(currCoord.leftCost == highestValue){
			bestDir = Direction.LEFT;
		}else if(currCoord.rightCost == highestValue){
			bestDir = Direction.RIGHT;
		}else if(currCoord.upCost == highestValue){
			bestDir = Direction.UP;
		}else if(currCoord.downCost == highestValue){
			bestDir = Direction.DOWN;
		}
		//Calculate for each direction
//		for(int i = 0; i <= 3; i++) {
//
//			switch(i) {
//			case 0:
//				dir = Direction.UP;
//				break;
//			case 1:
//				dir = Direction.DOWN;
//				break;
//			case 2:
//				dir = Direction.RIGHT;
//				break;
//			case 3:
//				dir = Direction.LEFT;
//				break;
//			}
//
//			float currValue = calculateCoordinateValue(currCoord, dir); //Get the value of the specific direction
//
//			if(currValue >= highestValue) {
//				highestValue = currValue;
//				bestDir = dir;
//			}
//		}
//
//		//If the random value is high enough, just go in any random direction
//		Random rand = new Random();
//
//		//Math.random() generates a value between 0.0 and 1.0, if that number is lower than sigmaPercent, we move in a random direction (exploration)
//		if(Math.random() <= sigmaPercent) {
//			int i = ThreadLocalRandom.current().nextInt(0, 4);
//
//			//Move in a random direction
//			switch(i) {
//			case 0:
//				bestDir = Direction.UP;
//				break;
//			case 1:
//				bestDir = Direction.DOWN;
//				break;
//			case 2:
//				bestDir = Direction.RIGHT;
//				break;
//			case 3:
//				bestDir = Direction.LEFT;
//				break;
//			}
//		}
		
		return bestDir;
		
	}

	/**
	 * Calculate the highest coordinate value
	 * @param currCoord
	 * @param dir
	 * @return value
	 */
	private float calculateCoordinateValue(Coordinate currCoord, Direction dir) {
		
		//Initialize points
		Point top, bottom, left, right;
		top = new Point(currCoord.col, currCoord.row - 1);
		bottom = new Point(currCoord.col, currCoord.row + 1);
		left = new Point(currCoord.col - 1, currCoord.row);
		right = new Point(currCoord.col + 1, currCoord.row);
		
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
		if(top.y >= 0) {
			topVal = board.getBoard()[top.y][top.x].getValue();
		}else {
			topVal = constantReward + currCoord.getValue();
		}
		
		if(bottom.y < board.height) {
			bottomVal = board.getBoard()[bottom.y][bottom.x].getValue();
		}else {
			bottomVal = constantReward + currCoord.getValue();
		}
		
		if(left.x >= 0) {
			leftVal = board.getBoard()[left.y][left.x].getValue();
		}else {
			leftVal = constantReward + currCoord.getValue();
		}
		
		if(right.x < board.width) {
			rightVal = board.getBoard()[right.y][right.x].getValue();
		}else {
			rightVal = constantReward + currCoord.getValue();
		}
		
		//System.out.println("\nDir " + dir);

		//System.out.println("topVal " + topVal);
		//System.out.println("bottomVal " + bottomVal);
		//System.out.println("leftVal " + leftVal);
		//System.out.println("rightVal " + rightVal);

		//System.out.println("\n\n");

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
