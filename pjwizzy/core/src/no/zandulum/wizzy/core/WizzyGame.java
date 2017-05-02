package no.zandulum.wizzy.core;

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
	}

}
