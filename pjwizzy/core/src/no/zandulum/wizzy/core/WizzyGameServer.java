package no.zandulum.wizzy.core;

import java.net.UnknownHostException;

import org.java_websocket.WebSocketImpl;

import com.badlogic.gdx.Game;

import no.zandulum.wizzy.core.assets.Assets;
import no.zandulum.wizzy.core.screens.GameScreen;
import no.zandulum.wizzy.core.screens.ServerScreen;
import no.zandulum.wizzy.core.tweens.TweenGlobal;
import no.zandulum.wizzy.core.websockets.Server;

public class WizzyGameServer extends WizzyGame {

	@Override
	public void create() {
		Assets.load();
		TweenGlobal.init();
				
		try {
			Server s = Server.getInstance();
			s.start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		setScreen(new ServerScreen(this));
	}

}
