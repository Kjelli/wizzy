package no.zandulum.wizzy.core.spells.projectiles;

import com.badlogic.gdx.math.Polygon;

import no.zandulum.wizzy.core.gameobjects.AbstractGameObject;
import no.zandulum.wizzy.core.gameobjects.players.CircularHitbox;
import no.zandulum.wizzy.core.spells.AbstractSpell;

public abstract class AbstractProjectile extends AbstractGameObject {

	float timeToLive;
	AbstractSpell spell;
	protected CircularHitbox hitbox;

	public AbstractProjectile(AbstractSpell spell, float x, float y, float width, float height, float timeToLive) {
		super(x, y, width, height);
		this.timeToLive = timeToLive;
		this.spell = spell;
		this.acceleration.x = EPSILON;
		this.acceleration.y = EPSILON;
		this.hitbox = new CircularHitbox(x, y, width, height);
	}
	
	@Override
	public void onSpawn() {
		super.onSpawn();
		getGameContext().bringToBack(this);
	}
	
	@Override
	public Polygon getBounds() {
		return hitbox.poly;
	}
	

	@Override
	public void update(float delta) {
		if (timeToLive >= 0 && getAliveTime() > timeToLive) {
			getGameContext().despawn(this);
			return;
		}
		hitbox.update(getX(), getY(), getWidth(), getHeight(), rot, getScale(), getScale());
		move(delta);
		updateProjectile(delta);
	}

	public abstract void updateProjectile(float delta);

}
