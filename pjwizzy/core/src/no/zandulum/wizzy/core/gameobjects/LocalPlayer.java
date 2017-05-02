package no.zandulum.wizzy.core.gameobjects;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;

import no.zandulum.wizzy.core.websockets.Client;
import no.zandulum.wizzy.core.websockets.PacketBuilder;

public class LocalPlayer extends Player {

	int lastDir = 0;
	int updateRate = 2;
	double nextUpdate = updateRate;
	Cursor cursor;

	public LocalPlayer(String name, float x, float y) {
		super(name, x, y);
	}

	@Override
	public void update(float delta) {
		super.update(delta);
		networkLogic(delta);
		controllerLogic(delta);
	}

	private void controllerLogic(float delta) {
		setRotation(angleTo(cursor.getCenterX(), cursor.getCenterY()));
	}

	@Override
	public void onSpawn() {
		super.onSpawn();
		initInput();
		cursor = new Cursor(this, getCenterX(), getCenterY());
		getGameContext().spawn(cursor);
	}

	private void initInput() {
		getGameContext().addInputProcessor(new InputAdapter() {

			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {

				if (button == Input.Buttons.LEFT) {
					right.cast(true);
					return true;
				} else if (button == Input.Buttons.RIGHT) {
					left.cast(true);
					return true;
				} else {
					return false;
				}

			}

			@Override
			public boolean touchUp(int screenX, int screenY, int pointer, int button) {

				if (button == Input.Buttons.LEFT) {
					right.cast(false);
					return true;
				} else if (button == Input.Buttons.RIGHT) {
					left.cast(false);
					return true;
				} else {
					return false;
				}

			}

			@Override
			public boolean keyDown(int keycode) {
				switch (keycode) {
				case Keys.A:
					movement.left = true;
					break;
				case Keys.W:
					movement.up = true;
					break;
				case Keys.S:
					movement.down = true;
					break;
				case Keys.D:
					movement.right = true;
					break;
				}
				return true;
			}

			@Override
			public boolean keyUp(int keycode) {
				switch (keycode) {
				case Keys.A:
					movement.left = false;
					break;
				case Keys.W:
					movement.up = false;
					break;
				case Keys.S:
					movement.down = false;
					break;
				case Keys.D:
					movement.right = false;
					break;
				}
				return true;
			}
		});
	}

	@Override
	protected void move(float delta) {
		float oldX = getX(), oldY = getY();
		super.move(delta);
		cursor.setX(cursor.getX() + (getX() - oldX));
		cursor.setY(cursor.getY() + (getY() - oldY));
	}

	@Override
	public void onDespawn() {
		super.onDespawn();
		getGameContext().despawn(left);
		getGameContext().despawn(right);
	}

	private void networkLogic(float delta) {
		if (movement.toInt() != lastDir || getGameContext().getTicks() - updateRate >= nextUpdate) {
			nextUpdate = getGameContext().getTicks() + updateRate;
			lastDir = movement.toInt();
			try {
				Client.getInstance().send(PacketBuilder.move(name, getX(), getY(), lastDir, rot));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		lastDir = movement.toInt();
	}

	public Cursor getCursor() {
		return cursor;
	}
}
