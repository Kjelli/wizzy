package no.zandulum.wizzy.core.screens;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import no.zandulum.wizzy.core.WizzyGame;
import no.zandulum.wizzy.core.defs.Options;
import no.zandulum.wizzy.core.gameobjects.GameObject;
import no.zandulum.wizzy.core.gameobjects.players.LocalPlayer;
import no.zandulum.wizzy.core.gameobjects.players.Player;
import no.zandulum.wizzy.core.websockets.Client;
import no.zandulum.wizzy.core.websockets.ClientListener;
import no.zandulum.wizzy.core.websockets.PacketBuilder;

public class GameScreen extends AbstractGameScreen {
	private HashMap<String, Player> players;
	private ClientListener clientListener;
	LocalPlayer player;

	public GameScreen(WizzyGame game) {
		super(game);
		clientListener = new ClientListener() {

			@Override
			public void onOpen(String name) {

			}

			@Override
			public void onMessage(String message) {

			}

			@Override
			public void onClose(int code, String reason, boolean remote) {
			}

			@Override
			public void onHello(String name) {
				Player player = new Player(name, 50, 50);
				players.put(name, player);
				gameContext.spawn(player);
			}

			@Override
			public void onHelloBack(String[] names) {
				for (String name : names) {
					Player player = new Player(name, 50, 50);
					players.put(name, player);
					gameContext.spawn(player);
				}
			}

			@Override
			public void onGoodbye(String name) {
				Player old = players.remove(name);
				gameContext.despawn(old);
			}

			@Override
			public void onMove(String name, float x, float y, int dir, float lookDir) {
				if (players.containsKey(name)) {
					Player p = players.get(name);
					p.updateFromPacket(x, y, dir, lookDir);
				} else {
					System.out.println("Could not find player " + name);
				}
			}
		};
		Client.getInstance().addListener(clientListener);
	}

	@Override
	protected void debugDraw(ShapeRenderer renderer) {
		if (Options.DEBUG) {
			renderer.begin();
			for (GameObject go : gameContext.getObjects()) {
				go.debugDraw(renderer);
			}
			renderer.end();
		}

	}

	@Override
	protected void update(float delta) {
		gameContext.update(delta);
		cameraLogic(delta);
	}

	private void cameraLogic(float delta) {
		float targetX = player.getCursor().getCenterX() + player.velocity().x / 4;
		float targetY = player.getCursor().getCenterY() + player.velocity().y / 4;
		camera.position.x = camera.position.x * 0.95f + targetX * 0.05f;
		camera.position.y = camera.position.y * 0.95f + targetY * 0.05f;
		camera.zoom = camera.zoom * 0.95f + player.targetZoom * 0.05f;
	}

	@Override
	protected void draw(SpriteBatch batch, float delta) {
		gameContext.draw(batch);
	}

	@Override
	protected void drawHud(SpriteBatch hudBatch, float delta) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onShow() {
		Gdx.input.setCursorCatched(true);
		players = new HashMap<>();

		Client c = Client.getInstance();
		try {
			c.connectBlocking();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		c.send(PacketBuilder.hello(c.getName()));
		player = new LocalPlayer(c.getName(), 50, 50);
		gameContext.spawn(player);
	}

}
