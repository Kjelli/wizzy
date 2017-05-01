package no.zandulum.wizzy.core.gameobjects;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;

import no.zandulum.wizzy.core.websockets.Client;
import no.zandulum.wizzy.core.websockets.PacketBuilder;

public class LocalPlayer extends Player {

	int lastDir = 0;
	int updateRate = 2;
	double nextUpdate = updateRate;

	public LocalPlayer(String name, float x, float y) {
		super(name, x, y);
	}

	@Override
	public void update(float delta) {
		super.update(delta);
		networkLogic(delta);
		lastDir = movement.toInt();
	}

	@Override
	public void onSpawn() {
		super.onSpawn();
		initInput();
	}

	private void initInput() {
		getGameContext().addInputProcessor(new InputAdapter() {
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

	private void networkLogic(float delta) {
		if (movement.toInt() != lastDir || getGameContext().getTicks() - updateRate >= nextUpdate) {
			nextUpdate = getGameContext().getTicks() + updateRate;
			lastDir = movement.toInt();
			try {
				Client.getInstance().send(PacketBuilder.move(name, getX(), getY(), lastDir));
				System.out.println("Sending movement packet...");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
