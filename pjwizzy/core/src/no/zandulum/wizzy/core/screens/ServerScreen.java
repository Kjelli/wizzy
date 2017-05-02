package no.zandulum.wizzy.core.screens;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map.Entry;

import org.java_websocket.WebSocket;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import no.zandulum.wizzy.core.WizzyGame;
import no.zandulum.wizzy.core.assets.Assets;
import no.zandulum.wizzy.core.websockets.Server;
import no.zandulum.wizzy.core.websockets.ServerListener;

public class ServerScreen extends AbstractGameScreen {
	HashMap<String, String> connections;
	ServerListener listener;
	
	String logString = "";

	public ServerScreen(WizzyGame game) {
		super(game);
		connections = new HashMap<>();
		listener = new ServerListener() {

			@Override
			public void onOpen(WebSocket conn) {
				connections.put(conn.getRemoteSocketAddress().toString(), "connecting");
			}

			@Override
			public void onMessage(WebSocket conn, String message) {

			}

			@Override
			public void onClose(WebSocket conn, int code, String reason, boolean remote) {
				connections.remove(conn.getRemoteSocketAddress().toString());

			}

			@Override
			public void onHello(WebSocket conn, String name) {
				connections.put(conn.getRemoteSocketAddress().toString(), name);
			}
		};
		try {
			Server.getInstance().addListener(listener);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void debugDraw(ShapeRenderer renderer) {

	}

	@Override
	protected void update(float delta) {
		logString = "Connections: \n";
		for(Entry<String, String> e : connections.entrySet()){
			logString += e.getKey() + "\n";
		}
	}

	@Override
	protected void draw(SpriteBatch batch, float delta) {
		Assets.font.draw(batch, logString, 50, 50);
	}

	@Override
	protected void drawHud(SpriteBatch hudBatch, float delta) {

	}

	@Override
	protected void onShow() {

	}

}
