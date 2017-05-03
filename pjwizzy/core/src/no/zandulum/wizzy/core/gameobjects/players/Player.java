package no.zandulum.wizzy.core.gameobjects.players;

import com.badlogic.gdx.graphics.g2d.Sprite;

import no.zandulum.wizzy.core.assets.Assets;
import no.zandulum.wizzy.core.gameobjects.AbstractGameObject;

public class Player extends AbstractGameObject {
	public static final int WIDTH = 32, HEIGHT = 32;
	public static final float ACCELERATION = 2000;
	public static final float MAX_SPEED = 150;

	public MovementDirection movement;
	String name;

	Hand left;
	Hand right;
	
	public Player(String name, float x, float y) {
		super(x, y, WIDTH, HEIGHT);
		this.name = name;
		setSprite(new Sprite(Assets.wizzy));
		sprite.setOrigin(WIDTH / 2, HEIGHT / 2);
		movement = new MovementDirection();
		setMaxSpeed(MAX_SPEED);
		this.bounds = new CircularHitbox(x, y, WIDTH, HEIGHT).poly;
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

	public void updateFromPacket(float new_x, float new_y, int new_dir, float lookDir) {
		setX(new_x);
		setY(new_y);
		setRotation(lookDir);
		this.movement.updatefromInt(new_dir);
	}

}
