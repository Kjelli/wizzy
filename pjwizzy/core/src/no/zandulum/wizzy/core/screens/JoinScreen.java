package no.zandulum.wizzy.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import no.zandulum.wizzy.core.WizzyGame;
import no.zandulum.wizzy.core.assets.Assets;
import no.zandulum.wizzy.core.assets.gui.Button;
import no.zandulum.wizzy.core.defs.Defs;

public class JoinScreen extends AbstractGameScreen{
	
	private Button backbtn, connectbtn;
	
	private TextField ipv4input; 

	float centerX;
	
	public JoinScreen(WizzyGame game) {
		super(game);
		
		centerX = camera.viewportWidth / 2 - Defs.BTN_WIDTH / 2;
		
		TextureRegion[][] regions = TextureRegion.split(Assets.menuBtns, Defs.BTN_TEXTURE_WIDTH, Defs.BTN_TEXTURE_HEIGHT);
		TextureRegion connectTexture = regions[6][0];
		TextureRegion backTexture = regions[4][0];
		
		connectbtn = new Button(connectTexture, centerX, 150f + 2 * (Defs.BTN_HEIGHT + Defs.SPACING), Defs.BTN_WIDTH, Defs.BTN_HEIGHT);
		backbtn = new Button(backTexture, centerX, 160f + 3 * (Defs.BTN_HEIGHT + Defs.SPACING), Defs.BTN_WIDTH, Defs.BTN_HEIGHT);

		ipv4input = new TextField("IPv4", Assets.skin_sdx);
		
		initElements();
		
		stage.addActor(ipv4input);
		stage.addActor(backbtn);
		stage.addActor(connectbtn);
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
	}

	@Override
	protected void drawHud(SpriteBatch hudBatch, float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onShow() {
		// TODO Auto-generated method stub
		
	}
	
	protected void initElements() {
		backbtn.addListener(new ClickListener(){
			@Override
			public void touchUp(InputEvent arg0, float arg1, float arg2, int arg3, int arg4) {
				super.touchUp(arg0, arg1, arg2, arg3, arg4);
				game.setScreen(new MenuScreen(game));
			}
		});
		
		connectbtn.addListener(new ClickListener(){
			@Override
			public void touchUp(InputEvent arg0, float arg1, float arg2, int arg3, int arg4) {
				super.touchUp(arg0, arg1, arg2, arg3, arg4);
				System.out.println("Connect");
			}
		});
	
	}

}
