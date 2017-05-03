package no.zandulum.wizzy.core.screens;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.java_websocket.WebSocket;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import no.zandulum.wizzy.core.WizzyGame;
import no.zandulum.wizzy.core.assets.Assets;
import no.zandulum.wizzy.core.assets.gui.Button;
import no.zandulum.wizzy.core.defs.Defs;
import no.zandulum.wizzy.core.websockets.Server;
import no.zandulum.wizzy.core.websockets.ServerListener;

public class Lobby extends AbstractGameScreen{
	
	private GlyphLayout titleGlyph;
	private GlyphLayout ipv4;
	private String hostAddress;
	
	private Button backBtn, startGameBtn;
	
	
	float centerX;
	
	public Lobby(WizzyGame game) {
		super(game);
		
		centerX = camera.viewportWidth / 2 - Defs.BTN_WIDTH / 2;
		
		titleGlyph = new GlyphLayout(Assets.font, "Game Lobby");
		
		try {
			hostAddress = InetAddress.getLocalHost().getHostAddress();
			ipv4 = new GlyphLayout(Assets.font, hostAddress);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.out.println("Could not find address");
		}
		
		
		TextureRegion[][] regions = TextureRegion.split(Assets.menuBtns, Defs.BTN_TEXTURE_WIDTH, Defs.BTN_TEXTURE_HEIGHT);
		
		float offset = 120f;
		backBtn = new Button(regions[4][0], centerX-offset, 250f, Defs.BTN_WIDTH, Defs.BTN_HEIGHT);
		startGameBtn = new Button(regions[5][0], centerX+offset, 250f, Defs.BTN_WIDTH, Defs.BTN_HEIGHT);
		
		
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
		Assets.font.draw(batch, titleGlyph, centerX + Defs.BTN_WIDTH / 2 - titleGlyph.width / 2, 30f);
		Assets.font.draw(batch, ipv4, viewport.getWorldWidth()-ipv4.width, 10f);
		
		batch.draw(Assets.border_large, 30f, 100f,viewport.getWorldWidth()-60f,130f);
	}

	@Override
	protected void drawHud(SpriteBatch hudBatch, float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onShow() {
		try {
			Server.getInstance().addListener(new ServerListener() {
				
				@Override
				public void onOpen(WebSocket conn) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onMessage(WebSocket conn, String message) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onHello(WebSocket conn, String name) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onClose(WebSocket conn, int code, String reason, boolean remote) {
					// TODO Auto-generated method stub
					
				}
			});
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void initElements(){
		backBtn.addListener(new ClickListener(){
			@Override
			public void touchUp(InputEvent arg0, float arg1, float arg2, int arg3, int arg4) {
				super.touchUp(arg0, arg1, arg2, arg3, arg4);
				try {
					Server.getInstance().stop();
					
				} catch (Exception e) {
					e.printStackTrace();
				} 
				game.setScreen(new MenuScreen(game));
			}
		});
	}
	
}
