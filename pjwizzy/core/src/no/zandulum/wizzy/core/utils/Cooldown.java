package no.zandulum.wizzy.core.utils;

public class Cooldown {
	float seconds;
	float counter;

	public Cooldown(float seconds) {
		this.seconds = seconds;
	}

	public boolean isReady() {
		return counter <= 0;
	}

	public void start() {
		counter = seconds;
	}

	public void update(float delta) {
		if (counter > 0) {
			counter -= delta;
		} else {
			counter = 0;
		}

	}
}
