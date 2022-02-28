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
		//initializeQtable();
		runReinforcement();
	}
	
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

	public void updateQTable(Coordinate currCoord, Direction dir){
		Coordinate nextCoord;
		switch(dir){
			case LEFT:
				nextCoord =  board.getBoard()[currCoord.row][currCoord.col - 1];
				currCoord.leftCost = currCoord.leftCost + probDesiredDirection*(constantReward + 1*(nextCoord.highestFloat())) - currCoord.leftCost;

				break;
			case RIGHT:
				nextCoord =  board.getBoard()[currCoord.row][currCoord.col + 1];
				currCoord.rightCost = currCoord.rightCost + probDesiredDirection*(constantReward + 1*(nextCoord.highestFloat())) - currCoord.rightCost;
				break;
			case UP:
				nextCoord =  board.getBoard()[currCoord.row - 1][currCoord.col];
				currCoord.upCost = currCoord.upCost + probDesiredDirection*(constantReward + 1*(nextCoord.highestFloat())) - currCoord.upCost;
				break;

			case DOWN:
				nextCoord =  board.getBoard()[currCoord.row + 1][currCoord.col];
				currCoord.downCost = currCoord.downCost + probDesiredDirection*(constantReward + 1*(nextCoord.highestFloat())) - currCoord.downCost;
				break;
		}

		board.board[currCoord.row][currCoord.col] = currCoord;

	}


	public void finalPrint(){
		String[][] finalPrint = new String[board.height][board.width];
		for(int row = 0; row < board.height; row++) {
			for(int col = 0; col < board.width; col++) {
				Coordinate printCoord = board.board[row][col];
				Direction printDir = printCoord.highestDir();
				CoordinateType type = printCoord.type;
				switch(type){
					case TERMINAL:
						finalPrint[row][col] = String.valueOf(printCoord.value);
						break;
					case CURRENT:
						switch (printDir){
							case UP:
								finalPrint[row][col] = "^\t";
								break;
							case LEFT:
								finalPrint[row][col] = "<\t";
								break;
							case RIGHT:
								finalPrint[row][col] = ">\t";
								break;
							case DOWN:
								finalPrint[row][col] = "v\t";
								break;
						}
						break;
				}
			}
		}
		for(String[] s: finalPrint){
			for(String s1 : s){
				System.out.print(s1 + " ");
			}
			System.out.println();
		}
	}

	public void learn(Coordinate currCoord){
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
		for(Coordinate c: board.terminalStates){ //check if on terminal state
			if(currCoord.equals(c)){
				System.out.println("Found Terminal State");
				return; //return if terminal
			}
		}
		board.printBoard();
		Direction dir = calculateBestDirection(currCoord); // if not, make move
		updateQTable(currCoord,dir);

		float highestFloat = currCoord.highestFloat();
		Coordinate newCoord = currCoord.clone();
		if(currCoord.getType() != CoordinateType.TERMINAL) {
			newCoord.value = highestFloat;
			this.board.board[currCoord.row][currCoord.col] = newCoord;
			
		}


		//todo - save new qMax float in table?
		
		System.out.println("Currently at: (" + currCoord.col + " " + currCoord.row + ") " + "Moving: " + dir);
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
		}
	}
	
	//Calculate which direction is the best direction to travel in
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
			for (int j = 0; j < height; j++) {
				int key[] = {i,j};
				float values[] = {0,0,0,0};
				qTable.put(key,values);
			}
		}
	}

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
		
		System.out.println("\nDir " + dir);

		System.out.println("topVal " + topVal);
		System.out.println("bottomVal " + bottomVal);
		System.out.println("leftVal " + leftVal);
		System.out.println("rightVal " + rightVal);

		System.out.println("\n\n");

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
