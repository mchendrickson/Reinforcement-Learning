
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
	}
	
	public void runReinforcement() {
		
		do {
			
		
		}
		while(!timer.finished());
		
	}
}
