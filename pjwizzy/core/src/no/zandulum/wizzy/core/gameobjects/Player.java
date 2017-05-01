package no.zandulum.wizzy.core.gameobjects;

import com.badlogic.gdx.InputAdapter;

import java.net.URISyntaxException;
import java.nio.channels.NotYetConnectedException;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Sprite;

import no.zandulum.wizzy.core.assets.Assets;
import no.zandulum.wizzy.core.websockets.Client;
import no.zandulum.wizzy.core.websockets.PacketBuilder;

public class Player extends AbstractGameObject {
	public static final int WIDTH = 200;
	public static final int HEIGHT = 200;

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

	public MovementDirection movement;
	String name;

	public Player(String name, float x, float y) {
		super(x, y, WIDTH, HEIGHT);
		this.name = name;
		setSprite(new Sprite(Assets.plain));
		movement = new MovementDirection();
		setMaxSpeed(500);
	}

	@Override
	public void onSpawn() {
		System.out.println("Spawned " + name + "!");
	}

	@Override
	public void update(float delta) {
		movementLogic(delta);
	}

	private void movementLogic(float delta) {
		if (movement.left) {
			acceleration.x = -4000;
		} else if (movement.right) {
			acceleration.x = 4000;
		} else {
			acceleration.x = 0;
		}

		if (movement.up) {
			acceleration.y = 4000;
		} else if (movement.down) {
			acceleration.y = -4000;
		} else {
			acceleration.y = 0;
		}
		move(delta);
	}

	public void updateFromPacket(float new_x, float new_y, int new_dir) {
		setX(new_x);
		setY(new_y);
		this.movement.updatefromInt(new_dir);
	}

}
