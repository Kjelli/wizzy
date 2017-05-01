package no.zandulum.wizzy.core.gameobjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import no.zandulum.wizzy.core.assets.Assets;
import no.zandulum.wizzy.core.graphics.Draw;

public class Hand extends AbstractGameObject {

	public static final int WIDTH = 24, HEIGHT = 24, LEFT = -1, RIGHT = 1, DIAMETER = Player.WIDTH * 2 / 3;
	public static final float DELAY = 0.5f;

	Player player;
	int orientation;

	public Hand(Player player, float x, float y, int orientation) {
		super(x, y, WIDTH, HEIGHT);
		Sprite sprite = new Sprite(Assets.hand);
		setSprite(sprite);
		this.player = player;
		this.orientation = orientation;
	}

	@Override
	public void draw(SpriteBatch batch) {
		Draw.sprite(batch, sprite, getX(), getY(), getWidth() * getScale(), getHeight() * getScale(), scale, scale, rot,
				Color.WHITE, orientation == LEFT);
	}

	@Override
	public void update(float delta) {
		followPlayer(player, delta);
	}

	private void followPlayer(Player player, float delta) {
		float targetX = player.getCenterX() + orientation * DIAMETER - WIDTH / 2;
		float targetY = player.getCenterY();
		setX(getX() * DELAY + targetX * (1 - DELAY));
		setY(getY() * DELAY + targetY * (1 - DELAY));
	}

}
