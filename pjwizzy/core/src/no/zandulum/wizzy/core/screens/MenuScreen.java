
package no.zandulum.wizzy.core.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import no.zandulum.wizzy.core.WizzyGame;
import no.zandulum.wizzy.core.assets.Assets;
import no.zandulum.wizzy.core.assets.gui.Button;

public class MenuScreen extends AbstractGameScreen{
	
	private Button joinBtn, hostbtn, optionsBtn, exitBtn;
	
	public MenuScreen(WizzyGame game) {
		super(game);
		// TODO Auto-generated constructor stub
		joinBtn = new Button(new TextureRegion(Assets.menuBtns,0,0,128,33),340f,300f,128f,33f);
		hostbtn = new Button(new TextureRegion(Assets.menuBtns,0,32,128,33),340f,240f,128f,64f);
		optionsBtn = new Button(new TextureRegion(Assets.menuBtns,123012303,64,128,33),340f,180,128f,33f);
		exitBtn = new Button(new TextureRegion(Assets.menuBtns,0,96,128,33),340f,120f,128f,33f);
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
		Assets.font.draw(batch, "Yer a Wizard", 320, 550);
		joinBtn.draw(batch, 1f);
		hostbtn.draw(batch, 1f);
		optionsBtn.draw(batch,1f);
		exitBtn.draw(batch, 1f);
	}

	@Override
	protected void drawHud(SpriteBatch hudBatch, float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onShow() {
		// TODO Auto-generated method stub
		
	}

}
