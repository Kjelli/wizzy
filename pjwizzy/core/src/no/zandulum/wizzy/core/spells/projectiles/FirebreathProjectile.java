package no.zandulum.wizzy.core.spells.projectiles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import no.zandulum.wizzy.core.assets.Assets;
import no.zandulum.wizzy.core.gameobjects.particles.FireParticle;
import no.zandulum.wizzy.core.spells.Firebreath;

public class FirebreathProjectile extends AbstractProjectile {

	public static final int WIDTH = 6, HEIGHT = 6;
	public static final float SPEED = 100f, TTL = 0.8f;

	private Texture[] textures;
	private int ti;

	public FirebreathProjectile(Firebreath spell, float x, float y) {
		super(spell, x, y, WIDTH, HEIGHT, TTL);
		
		textures = new Texture[] { Assets.fire, Assets.fire2, Assets.fire3 };
		ti = (int) (Math.random() * 2);
		setSprite(new Sprite(textures[ti]));

		Color color = new Color(Color.ORANGE);
		color.g = (float) (Math.random() * 0.5f + 0.5f);
		sprite.setColor(color);
	}
	
	@Override
	public void updateProjectile(float delta) {
		if (getGameContext().getTicks() % 8 == 0) {
			getGameContext().spawn(
					new FireParticle(getCenterX() - FireParticle.WIDTH / 2, getCenterY() - FireParticle.HEIGHT / 2));
			sprite.setTexture(textures[(ti++) % textures.length]);
		}
	}

}
