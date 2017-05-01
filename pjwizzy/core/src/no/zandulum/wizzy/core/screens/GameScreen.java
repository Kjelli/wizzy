package no.zandulum.wizzy.core.screens;

import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import no.zandulum.wizzy.core.WizzyGame;
import no.zandulum.wizzy.core.gameobjects.LocalPlayer;
import no.zandulum.wizzy.core.gameobjects.Player;
import no.zandulum.wizzy.core.websockets.Client;
import no.zandulum.wizzy.core.websockets.ClientListener;
import no.zandulum.wizzy.core.websockets.PacketBuilder;

public class GameScreen extends AbstractGameScreen {
	private HashMap<String, Player> players;
	private ClientListener clientListener;

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
				System.out.println("Spawning " + name);
			}

			@Override
			public void onHelloBack(String[] names) {
				for (String name : names) {
					Player player = new Player(name, 50, 50);
					players.put(name, player);
					gameContext.spawn(player);
					System.out.println("Spawning " + name);
				}
			}

			@Override
			public void onGoodbye(String name) {
				Player old = players.remove(name);
				gameContext.despawn(old);
			}

			@Override
			public void onMove(String name, float x, float y, int dir) {
				if (players.containsKey(name)) {
					Player p = players.get(name);
					p.updateFromPacket(x, y, dir);
					System.out.println("Updating " + name + ": (x: " + p.getX() + ", y: " + p.getY() + ")");
				} else {
					System.out.println("Could not find player " + name);
				}
			}
		};
		Client.getInstance().addListener(clientListener);
	}

	@Override
	protected void debugDraw(ShapeRenderer renderer) {

	}

	@Override
	protected void update(float delta) {
		gameContext.update(delta);
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

		players = new HashMap<>();

		Client c = Client.getInstance();
		try {
			c.connectBlocking();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		c.send(PacketBuilder.hello(c.getName()));
		gameContext.spawn(new LocalPlayer(c.getName(), 50, 50));
	}

}
