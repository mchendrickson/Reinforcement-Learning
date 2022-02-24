import java.awt.Point;
import java.util.concurrent.ThreadLocalRandom;

public class ReinforcementLearning {
	private Board board;
	private Timer timer;
	private float timeToRun, probDesiredDirection, constantReward;

	public ReinforcementLearning(Board board, float timeToRun, float probDesiredDirection, float constantReward) {
		this.board = board;
		this.timeToRun = timeToRun;
		this.probDesiredDirection = probDesiredDirection;
		this.constantReward = constantReward;
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

	public void learn(Coordinate current){
		//check if on terminal state
		for(Coordinate c: board.terminalStates){
			if(current.equals(c)){
				return;
			}
		}
		calculateCoordinateValue(current);


	}
	
	private float calculateCoordinateValue(Coordinate currCoord) {
		Point top, bottom, left, right;
		top = new Point(currCoord.x, currCoord.y + 1);
		bottom = new Point(currCoord.x, currCoord.y - 1);
		left = new Point(currCoord.x - 1, currCoord.y);
		right = new Point(currCoord.x + 1, currCoord.y);
		
		float topVal = constantReward; 
		float bottomVal = constantReward;
		float leftVal = constantReward;
		float rightVal = constantReward;
		
		if(top.y <= board.height) {
			topVal = board.getBoard()[top.x][top.y].getValue();
		}
		
		if(bottom.y >= 0) {
			bottomVal = board.getBoard()[bottom.x][bottom.y].getValue();
		}
		
		if(left.x >= 0) {
			leftVal = board.getBoard()[left.x][left.y].getValue();
		}
		
		if(right.x <= board.width) {
			rightVal = board.getBoard()[right.x][right.y].getValue();
		}
		
		return topVal + bottomVal + leftVal + rightVal + constantReward;
	}
	
}
