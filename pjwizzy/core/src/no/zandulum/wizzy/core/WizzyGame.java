package no.zandulum.wizzy.core;

import com.badlogic.gdx.Game;

import no.zandulum.wizzy.core.assets.Assets;
import no.zandulum.wizzy.core.screens.GameScreen;

public class WizzyGame extends Game {

	@Override
	public void create() {
		Assets.load();
		setScreen(new GameScreen(this));
		// WebSocketImpl.DEBUG = true;
	}

}
