package no.zandulum.wizzy.core;

import org.java_websocket.WebSocketImpl;

import com.badlogic.gdx.Game;

import no.zandulum.wizzy.core.assets.Assets;
import no.zandulum.wizzy.core.screens.GameScreen;
import no.zandulum.wizzy.core.tweens.TweenGlobal;

public class WizzyGame extends Game {

	@Override
	public void create() {
		Assets.load();
		TweenGlobal.init();
		setScreen(new GameScreen(this));
		// WebSocketImpl.DEBUG = true;
	}

}
