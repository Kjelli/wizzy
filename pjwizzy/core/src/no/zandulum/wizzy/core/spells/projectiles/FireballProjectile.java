package no.zandulum.wizzy.core.spells.projectiles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import aurelienribon.tweenengine.Tween;
import no.zandulum.wizzy.core.assets.Assets;
import no.zandulum.wizzy.core.gameobjects.particles.FireParticle;
import no.zandulum.wizzy.core.spells.Fireball;
import no.zandulum.wizzy.core.tweens.TweenGlobal;
import no.zandulum.wizzy.core.tweens.accessors.ColorAccessor;

public class FireballProjectile extends AbstractProjectile {

	public static final int WIDTH = 12, HEIGHT = 12;
	public static final float SPEED = 200f, TTL = 2.0f;

	private Texture[] textures;
	private int ti;
	private Color fireColor;

	public FireballProjectile(Fireball spell, float x, float y) {
		super(spell, x, y, WIDTH, HEIGHT, TTL);
		setRotation(spell.getCaster().getRotation());
		velocity().x = spell.getCaster().velocity().x + (float) (Math.cos(rot) * SPEED);
		velocity().y = spell.getCaster().velocity().y + (float) (Math.sin(rot) * SPEED);

		textures = new Texture[] { Assets.fire, Assets.fire2, Assets.fire3 };
		setSprite(new Sprite(textures[0]));
		fireColor = new Color(Color.RED);
		sprite.setColor(fireColor);
		TweenGlobal.start(Tween.to(fireColor, ColorAccessor.TYPE_RGBA, TTL / 4)
				.target(fireColor.r, 1.0f, fireColor.b, fireColor.a).repeatYoyo(4, 0));
	}

	@Override
	public void updateProjectile(float delta) {
		sprite.setColor(fireColor);
		if (getGameContext().getTicks() % 8 == 0) {
			getGameContext().spawn(
					new FireParticle(getCenterX() - FireParticle.WIDTH / 2, getCenterY() - FireParticle.HEIGHT / 2));
			sprite.setTexture(textures[(ti++) % textures.length]);
		}
	}

}
