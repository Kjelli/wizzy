package no.zandulum.wizzy.core.gameobjects;

import com.badlogic.gdx.graphics.g2d.Sprite;

import no.zandulum.wizzy.core.assets.Assets;

public class Player extends AbstractGameObject {
	public static final int WIDTH = 64, HEIGHT = 64;
	public static final float ACCELERATION = 4000;
	public static final float MAX_SPEED = 250;

	public MovementDirection movement;
	String name;

	Hand left;
	Hand right;

	public Player(String name, float x, float y) {
		super(x, y, WIDTH, HEIGHT);
		this.name = name;
		setSprite(new Sprite(Assets.wizzy));
		movement = new MovementDirection();
		setMaxSpeed(MAX_SPEED);
	}

	@Override
	public void onSpawn() {
		spawnHands();
	}

	private void spawnHands() {
		left = new Hand(this, getCenterX(), getCenterY(), Hand.LEFT);
		right = new Hand(this, getCenterX(), getCenterY(), Hand.RIGHT);
		getGameContext().spawn(left);
		getGameContext().spawn(right);
	}

	@Override
	public void update(float delta) {
		movementLogic(delta);
	}

	private void movementLogic(float delta) {
		if (movement.left) {
			acceleration.x = -ACCELERATION;
		} else if (movement.right) {
			acceleration.x = ACCELERATION;
		} else {
			acceleration.x = 0;
		}

		if (movement.up) {
			acceleration.y = ACCELERATION;
		} else if (movement.down) {
			acceleration.y = -ACCELERATION;
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
