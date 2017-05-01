package no.zandulum.wizzy.core;

import com.badlogic.gdx.Game;

import no.zandulum.wizzy.core.assets.Assets;
import no.zandulum.wizzy.core.screens.GameScreen;
import no.zandulum.wizzy.core.screens.MenuScreen;

public class WizzyGame extends Game {

	@Override
	public void create() {
		Assets.load();
		setScreen(new MenuScreen(this));
		// WebSocketImpl.DEBUG = true;
	}

}
