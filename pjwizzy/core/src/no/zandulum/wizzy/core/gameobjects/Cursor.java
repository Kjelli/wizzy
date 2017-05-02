package no.zandulum.wizzy.core.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;

import no.zandulum.wizzy.core.assets.Assets;

public class Cursor extends AbstractGameObject {

	public static final int WIDTH = 8, HEIGHT = 8;
	public static final float SENSITIVITY = 0.2f, RADIUS = 75f;
	private Player player;
	private Color cursorColor = new Color(Color.BLUE);

	public Cursor(Player player, float x, float y) {
		super(x, y, WIDTH, HEIGHT);
		this.player = player;
		setSprite(new Sprite(Assets.cursor));
		sprite.setColor(cursorColor);
	}

	@Override
	public void update(float delta) {
		float targetX = getCenterX() + Gdx.input.getDeltaX() * SENSITIVITY;
		float targetY = getCenterY() + Gdx.input.getDeltaY() * SENSITIVITY;
		// float distance = (float) Math.hypot(targetX - player.getCenterX(), targetY - player.getCenterY());
		float angle = player.angleTo(targetX, targetY);
		targetX = (float) ((player.getCenterX()) + Math.cos(angle) * RADIUS);
		targetY = (float) ((player.getCenterY()) + Math.sin(angle) * RADIUS);
		
		
		
		setX(targetX - WIDTH / 2);
		setY(targetY - HEIGHT / 2);

		setRotation(player.rot);
		
		getGameContext().bringToFront(this);
		
		cursorColor.r = (float) Math.sin(getAliveTime()*3 + 2 * Math.PI / 3) * 0.5f + 0.5f;
		cursorColor.g = (float) Math.sin(getAliveTime()*3 + 4 * Math.PI / 3) * 0.5f + 0.5f;
		cursorColor.b = (float) Math.sin(getAliveTime()*3 + 6 * Math.PI / 3) * 0.5f + 0.5f;
		sprite.setColor(cursorColor);

	}

}
