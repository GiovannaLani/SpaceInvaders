package game;

import java.text.SimpleDateFormat;

public class Chronometer {
	private Thread chronometer;
	private long milliSeconds;
	private boolean count;
	private boolean finish = false;
	private SimpleDateFormat formatoHora = new SimpleDateFormat("mm:ss");

	public Chronometer() {
		milliSeconds = 0;
		count = true;
		chronometer = new Thread() {
			@Override
			public void run() {
				while (!finish) {
					if (count) {
						milliSeconds += 1000;

					}
					try {
						sleep(1000);
					} catch (InterruptedException e) {
					}
				}
			}
		};
	}

	public void startChronometer() {
		if (!chronometer.isAlive()) {
			chronometer.start();
		}
	}

	public void stopChronometer() {
		count = false;
	}

	public void continueChronometer() {
		count = true;
	}

	public void restartChronometer() {
		count = true;
		milliSeconds = 0;
	}

	public void finishChronometer() {
		finish = true;
	}

	public long getMilliSeconds() {
		return milliSeconds;
	}

	public void setMilliSeconds(long milliSeconds) {
		this.milliSeconds = milliSeconds;
	}

	@Override
	public String toString() {
		return formatoHora.format(milliSeconds);
	}
}
