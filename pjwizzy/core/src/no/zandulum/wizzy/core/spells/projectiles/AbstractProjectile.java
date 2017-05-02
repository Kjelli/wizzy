package no.zandulum.wizzy.core.spells.projectiles;

import no.zandulum.wizzy.core.gameobjects.AbstractGameObject;
import no.zandulum.wizzy.core.spells.AbstractSpell;

public abstract class AbstractProjectile extends AbstractGameObject {

	float timeToLive;
	AbstractSpell spell;

	public AbstractProjectile(AbstractSpell spell, float x, float y, float width, float height, float timeToLive) {
		super(x, y, width, height);
		this.timeToLive = timeToLive;
		this.spell = spell;
		this.acceleration.x = EPSILON;
		this.acceleration.y = EPSILON;
	}
	
	@Override
	public void onSpawn() {
		super.onSpawn();
		getGameContext().bringToBack(this);
	}

	@Override
	public void update(float delta) {
		if (timeToLive >= 0 && getAliveTime() > timeToLive) {
			getGameContext().despawn(this);
			return;
		}
		move(delta);
		updateProjectile(delta);
	}

	public abstract void updateProjectile(float delta);

}
