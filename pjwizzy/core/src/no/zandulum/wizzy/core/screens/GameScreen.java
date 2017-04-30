package no.zandulum.wizzy.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import no.zandulum.wizzy.core.WizzyGame;
import no.zandulum.wizzy.core.gamecontext.GameContext;
import no.zandulum.wizzy.core.gameobjects.SimpleMan;

public class GameScreen extends AbstractGameScreen {
	
	private double elapsed = 0;
	private GameContext gameContext;

	public GameScreen(WizzyGame game) {
		super(game);
		gameContext = new GameContext(game);
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
		gameContext.spawn(new SimpleMan(50, 50));
	}

}
