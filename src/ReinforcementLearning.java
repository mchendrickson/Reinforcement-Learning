import java.awt.Point;

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
			
		
		}
		while(!timer.finished());
		
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
