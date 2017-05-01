package no.zandulum.wizzy.core.gameobjects;

public class MovementDirection {
	boolean left, right, up, down;

	public int toInt() {
		int dir = (left ? 1 : 0) + (right ? 2 : 0) + (up ? 4 : 0) + (down ? 8 : 0);
		return dir;
	}

	public void updatefromInt(int dir) {
		left = ((dir & 1) > 0);
		right = ((dir & 2) > 0);
		up = ((dir & 4) > 0);
		down = ((dir & 8) > 0);
	}
}