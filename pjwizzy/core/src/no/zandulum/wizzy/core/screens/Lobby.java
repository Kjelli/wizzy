package no.zandulum.wizzy.core.screens;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import no.zandulum.wizzy.core.WizzyGame;
import no.zandulum.wizzy.core.assets.Assets;
import no.zandulum.wizzy.core.assets.gui.Button;

public class Lobby extends AbstractGameScreen{
	
	public static final int BTN_TEXTURE_WIDTH = 128;
	public static final int BTN_TEXTURE_HEIGHT = 32;
	
	public static final float UNSCALED_SPACING = 5;
	public static final float BTN_SCALE = 0.7f;
	
	public static final float BTN_WIDTH = BTN_TEXTURE_WIDTH * BTN_SCALE;
	public static final float BTN_HEIGHT = BTN_TEXTURE_HEIGHT * BTN_SCALE;
	public static final float SPACING = UNSCALED_SPACING * BTN_SCALE;
	
	private GlyphLayout titleGlyph;
	private Button backBtn, startGameBtn;
	
	float centerX;
	
	public Lobby(WizzyGame game) {
		super(game);
		
		centerX = camera.viewportWidth / 2 - BTN_WIDTH / 2;
		
		titleGlyph = new GlyphLayout(Assets.font, "Game Lobby");
		
		TextureRegion[][] regions = TextureRegion.split(Assets.menuBtns, BTN_TEXTURE_WIDTH, BTN_TEXTURE_HEIGHT);
		
		float offset = 120f;
		backBtn = new Button(regions[4][0], centerX-offset, 250f, BTN_WIDTH, BTN_HEIGHT);
		startGameBtn = new Button(regions[5][0], centerX+offset, 250f, BTN_WIDTH, BTN_HEIGHT);
		
		stage.addActor(backBtn);
		stage.addActor(startGameBtn);
		
		initElements();
	}

	@Override
	protected void debugDraw(ShapeRenderer renderer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void update(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void draw(SpriteBatch batch, float delta) {
		Assets.font.draw(batch, titleGlyph, centerX + BTN_WIDTH / 2 - titleGlyph.width / 2, 30f);
		
		
	}

	@Override
	protected void drawHud(SpriteBatch hudBatch, float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onShow() {
		// TODO Auto-generated method stub
		
	}
	
	protected void initElements(){
		backBtn.addListener(new ClickListener(){
			@Override
			public void touchUp(InputEvent arg0, float arg1, float arg2, int arg3, int arg4) {
				super.touchUp(arg0, arg1, arg2, arg3, arg4);
				game.setScreen(new MenuScreen(game));
			}
		});
	}
	
}
