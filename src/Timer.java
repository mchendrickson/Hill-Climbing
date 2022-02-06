import java.util.Date;

public class Timer extends Thread {
	private long startTime;
	private long elapsedTime = 0L;
	private float timerTime; 
	
	public Timer(float timerTime) {
		this.timerTime = timerTime * 1000;
	}
	
	public void run() {
		startTime = System.currentTimeMillis();

		while (elapsedTime < timerTime) {
		    //perform db poll/check
		    elapsedTime = (new Date()).getTime() - startTime;
		    
		}
		System.out.println("\nTimer finished!");
	}
	
	public boolean outOfTime() {
		return elapsedTime < timerTime;
	}
}
