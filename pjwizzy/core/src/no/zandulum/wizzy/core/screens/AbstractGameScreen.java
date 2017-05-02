package no.zandulum.wizzy.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import no.zandulum.wizzy.core.WizzyGame;
import no.zandulum.wizzy.core.defs.Defs;
import no.zandulum.wizzy.core.gamecontext.GameContext;
import no.zandulum.wizzy.core.screens.mousehandles.MouseHandle;

public abstract class AbstractGameScreen implements Screen {

	protected final WizzyGame game;

	public final static float SCALE = 2f;
	public final static float INV_SCALE = 1.f / SCALE;

	public final static float VP_WIDTH = Defs.WIDTH * INV_SCALE;
	public final static float VP_HEIGHT = Defs.HEIGHT * INV_SCALE;

	protected final OrthographicCamera camera;
	protected final OrthographicCamera hudCamera;
	protected final Viewport viewport;
	protected final Stage stage;
	protected GameContext gameContext;

	protected final MouseHandle mouseHandle;

	private final SpriteBatch batch, hudBatch;

	public ShapeRenderer shapes;

	private Texture background;

	private Color bgcolor;

	private boolean paused;

	public AbstractGameScreen(WizzyGame game) {
		this.game = game;
		camera = new OrthographicCamera();
		camera.setToOrtho(true);
		hudCamera = new OrthographicCamera();
		viewport = new ExtendViewport(VP_WIDTH, VP_HEIGHT, camera);
		stage = new Stage(viewport);

		mouseHandle = new MouseHandle(camera, viewport);

		batch = new SpriteBatch();
		shapes = new ShapeRenderer();
		hudBatch = new SpriteBatch();
		hudBatch.enableBlending();
		gameContext = new GameContext(game, mouseHandle);

	}

	@Override
	public void render(float delta) {
		update(delta);
		stage.act(delta);

		Gdx.gl.glClearColor(0.0f, 0.5f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT
				| (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		draw(batch, delta);
		batch.end();
		hudCamera.zoom = camera.zoom;
		hudCamera.update();
		hudBatch.begin();
		drawHud(hudBatch, delta);
		hudBatch.end();
		stage.draw();
		shapes.setProjectionMatrix(camera.combined);
		shapes.setAutoShapeType(true);
		debugDraw(shapes);

	}

	protected abstract void debugDraw(ShapeRenderer renderer);

	protected abstract void update(float delta);

	protected abstract void draw(SpriteBatch batch, float delta);

	protected abstract void drawHud(SpriteBatch hudBatch, float delta);

	protected abstract void onShow();

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
	}

	@Override
	public void show() {
		onShow();
	}

	@Override
	public String toString() {
		return String.format("%s (%d)", this.getClass().getSimpleName(), this.hashCode());
	}

	/**
	 * Game regains focus. (Unpause?)
	 */

	@Override
	public void resume() {
		setPaused(false);
	}

	/**
	 * Hiding the game triggers this method.
	 */
	@Override
	public void hide() {
	}

	/**
	 * Pausing the game triggers this method.
	 */

	@Override
	public void pause() {
		// setPaused(true);
	}

	/**
	 * Dispose of resources used, ie textures and sounds.
	 */
	@Override
	public void dispose() {
		stage.dispose();
		batch.dispose();
		hudBatch.dispose();
		gameContext.dispose();
		Gdx.input.setInputProcessor(null);
	}

	protected final void setBackground(Texture background) {
		this.background = background;
	}

	protected final Texture getBackground() {
		return background;
	}

	public void setBackgroundColor(Color bgcolor) {
		this.bgcolor = bgcolor;
	}

	public Color getBackgroundColor() {
		return bgcolor;
	}

	protected final void setGameContext(GameContext gameContext) {
		this.gameContext = gameContext;
	}

	public boolean getPaused() {
		return paused;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
		gameContext.setPaused(paused);
	}

	public GameContext getGameContext() {
		return gameContext;
	}

	public OrthographicCamera getCamera() {
		return camera;
	}

	public WizzyGame getGame() {
		return game;
	}

	public MouseHandle getMouseHandle() {
		return mouseHandle;
	}

	protected SpriteBatch getSpriteBatch() {
		return batch;
	}

}
