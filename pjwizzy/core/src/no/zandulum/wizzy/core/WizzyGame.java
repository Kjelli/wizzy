package no.zandulum.wizzy.core;

import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import no.zandulum.wizzy.core.assets.Assets;
import no.zandulum.wizzy.core.screens.GameScreen;
import no.zandulum.wizzy.core.tweens.TweenGlobal;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class WizzyGame extends Game {

	@Override
	public void create() {
		Assets.load();
		TweenGlobal.init();
		setScreen(new GameScreen(this));
	}

}
