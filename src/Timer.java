import java.util.Date;

public class Timer extends Thread {
	private long startTime;
	private long elapsedTime = 0L;
	private float timerTime;

	/**
	 * Main constructor
	 * @param timerTime
	 */
	public Timer(float timerTime) {
		this.timerTime = timerTime * 1000;
	}

	/**
	 * Run the thread
	 */
	public void run() {
		startTime = System.currentTimeMillis();

		while (elapsedTime < timerTime) {
			// perform db poll/check
			elapsedTime = (new Date()).getTime() - startTime;

		}
		//System.out.println("\n\nTimer finished! ");
	}

	/**
	 * Determines if the time is done
	 * @return true if timer is finished
	 */
	public boolean finished() {
		return elapsedTime >= timerTime;
	}
}