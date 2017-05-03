package no.zandulum.wizzy.core.gamecontext;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Queue;

import no.zandulum.wizzy.core.defs.Options;
import no.zandulum.wizzy.core.gamecontext.physics.BruteForcePhysicsHandler;
import no.zandulum.wizzy.core.gamecontext.physics.PhysicsHandler;
import no.zandulum.wizzy.core.gameobjects.GameObject;
import no.zandulum.wizzy.core.screens.mousehandles.MouseHandle;
import no.zandulum.wizzy.core.tweens.TweenGlobal;

public class GameContext {

	private final Game game;

	private final List<GameObject> objects;
	private final Queue<GameObject> add;
	private final Queue<GameObject> remove;
	private final Queue<GameObject> newlySpawned;
	private final Queue<GameObject> newlyDespawned;

	private Stage stage;
	private PhysicsHandler physics;
	private InputMultiplexer inputMux;
	private MouseHandle mouseHandle;
	private long ticks = 0;
	private double elapsedTime = 0;

	private float timeModifier = 1.0f;

	private boolean paused = false;

	public GameContext(Game game, Stage stage, MouseHandle mouseHandle) {
		this.game = game;
		this.stage = stage;
		this.mouseHandle = mouseHandle;

		objects = new ArrayList<>();
		add = new Queue<>();
		remove = new Queue<>();
		newlySpawned = new Queue<>();
		newlyDespawned = new Queue<>();

		inputMux = new InputMultiplexer(stage);
		inputMux.addProcessor(new InputAdapter() {
			@Override
			public boolean keyDown(int keycode) {
				if (keycode == Keys.F4) {
					Options.DEBUG = !Options.DEBUG;
					return true;
				}else if (keycode == Keys.F5) {
					Options.SLOWMO = !Options.SLOWMO;
						timeModifier = Options.SLOWMO ? 0.25f : 1.0f;
					return true;
				}
				return false;
			}
		});

		Gdx.input.setInputProcessor(inputMux);

		// TODO Optimize in the future
		physics = new BruteForcePhysicsHandler();
	}

	public void addInputProcessor(InputAdapter adapter) {
		inputMux.addProcessor(adapter);
	}

	public void update(float delta) {
		if (isPaused()) {
			return;
		}
		ticks++;
		elapsedTime += delta * timeModifier;

		TweenGlobal.update(delta * timeModifier);

		for (int i = 0; i < objects.size(); i++) {
			GameObject object = objects.get(i);
			object.update(delta * timeModifier);
			object.updateAliveTime(delta * timeModifier);
		}
		while ((add.size > 0)) {
			GameObject n = add.removeFirst();
			n.setGameContext(this);
			newlySpawned.addFirst(n);
			objects.add(n);
		}
		while ((remove.size > 0)) {
			GameObject o = remove.removeFirst();
			newlyDespawned.addFirst(o);
			objects.remove(o);
		}

		while ((newlySpawned.size > 0)) {
			GameObject o = newlySpawned.removeFirst();
			if (o != null) {
				o.onSpawn();
			}
		}

		while ((newlyDespawned.size > 0)) {
			GameObject o = newlyDespawned.removeFirst();
			if (o != null) {
				o.onDespawn();
			}
		}

		physics.collisonCheck(objects);
	}

	public final List<GameObject> getObjects() {
		return objects;
	}

	public final List<GameObject> getByClass(Class<? extends GameObject>[] classes) {
		List<GameObject> returnVals = new ArrayList<>();
		obj: for (GameObject object : objects) {
			for (int i = 0; i < classes.length; i++) {
				if (classes[i].isAssignableFrom(object.getClass())) {
					returnVals.add(object);
					continue obj;
				}
			}
		}
		return returnVals;

	}

	public void spawn(GameObject object) {
		add.addFirst(object);
	}

	public void despawn(GameObject object) {
		remove.addFirst(object);
	}

	public void draw(SpriteBatch batch) {
		for (GameObject object : objects) {
			object.draw(batch);
		}
	}

	public void dispose() {
		for (GameObject object : objects) {
			object.dispose();
		}
	}

	public void bringToFront(GameObject go) {
		int index = objects.indexOf(go);
		objects.remove(index);
		objects.add(go);
	}

	public void bringToBack(GameObject go) {
		int index = objects.indexOf(go);
		objects.remove(index);
		objects.add(0, go);
	}

	public long getTicks() {
		return ticks;
	}

	public Stage getStage() {
		return stage;
	}

	public double getElapsedTime() {
		return elapsedTime;
	}

	public Game getGame() {
		return game;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}

	public boolean isPaused() {
		return paused;
	}

	public void setTimeModifier(float timeModifier) {
		this.timeModifier = timeModifier;
	}

	public void clear() {
		add.clear();
		remove.clear();
		objects.clear();
	}

	public float getTimeModifier() {
		return timeModifier;
	}

	public MouseHandle getMouseHandle() {
		return mouseHandle;
	}

}