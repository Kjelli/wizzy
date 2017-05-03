
package no.zandulum.wizzy.core.screens;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import no.zandulum.wizzy.core.WizzyGame;
import no.zandulum.wizzy.core.assets.Assets;
import no.zandulum.wizzy.core.assets.gui.Button;
import no.zandulum.wizzy.core.defs.Defs;
import no.zandulum.wizzy.core.websockets.Server;

public class MenuScreen extends AbstractGameScreen {

	private Button joinBtn, hostbtn, optionsBtn, exitBtn;
	private GlyphLayout titleGlyph;

	
	float centerX;

	public MenuScreen(WizzyGame game) {
		super(game);
		// TODO Auto-generated constructor stub
		centerX = camera.viewportWidth / 2 - Defs.BTN_WIDTH / 2;

		TextureRegion[][] regions = TextureRegion.split(Assets.menuBtns, Defs.BTN_TEXTURE_WIDTH, Defs.BTN_TEXTURE_HEIGHT);

		TextureRegion joinTexture = regions[0][0];
		TextureRegion hostTexture = regions[1][0];
		TextureRegion optionsTexture = regions[2][0];
		TextureRegion exitTexture = regions[3][0];

		joinBtn = new Button(joinTexture, centerX, 130f, Defs.BTN_WIDTH, Defs.BTN_HEIGHT);
		hostbtn = new Button(hostTexture, centerX, 140f + Defs.BTN_HEIGHT + Defs.SPACING, Defs.BTN_WIDTH, Defs.BTN_HEIGHT);
		optionsBtn = new Button(optionsTexture, centerX, 150f + 2 * (Defs.BTN_HEIGHT + Defs.SPACING), Defs.BTN_WIDTH, Defs.BTN_HEIGHT);
		exitBtn = new Button(exitTexture, centerX, 160f + 3 * (Defs.BTN_HEIGHT + Defs.SPACING), Defs.BTN_WIDTH, Defs.BTN_HEIGHT);

		initElements();

		stage.addActor(joinBtn);
		stage.addActor(exitBtn);
		stage.addActor(hostbtn);

		titleGlyph = new GlyphLayout(Assets.font, "Im a what?");

	}

	@Override
	protected void debugDraw(ShapeRenderer renderer) {
		/*
	    renderer.begin();
		renderer.end();
		renderer.rect(joinBtn.getX(), joinBtn.getY(), joinBtn.getWidth(), joinBtn.getHeight());
		 */
	}

	@Override
	protected void update(float delta) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void draw(SpriteBatch batch, float delta) {
		Assets.font.draw(batch, titleGlyph, centerX + Defs.BTN_WIDTH / 2 - titleGlyph.width / 2, 30f);

		optionsBtn.draw(batch, 1f);

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
		joinBtn.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				// TODO Auto-generated method stub
				return super.touchDown(event, x, y, pointer, button);
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
				game.setScreen(new JoinScreen(game));
			}

		});

		hostbtn.addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent arg0, float arg1, float arg2, int arg3, int arg4) {
				super.touchUp(arg0, arg1, arg2, arg3, arg4);
				if (!Server.isInitialized()) {
					try {
						InetSocketAddress adress = new InetSocketAddress("localhost", 1337);
						Server.newInstance(adress).start();
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.out.println(e);
					}
				}
				game.setScreen(new Lobby(game));
			}
		});

		exitBtn.addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent arg0, float arg1, float arg2, int arg3, int arg4) {
				// TODO Auto-generated method stub
				super.touchUp(arg0, arg1, arg2, arg3, arg4);
				System.exit(0);
			}
		});
	}

}
