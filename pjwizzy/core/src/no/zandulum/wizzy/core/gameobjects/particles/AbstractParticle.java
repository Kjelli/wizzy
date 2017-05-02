package no.zandulum.wizzy.core.gameobjects.particles;

import no.zandulum.wizzy.core.gameobjects.AbstractGameObject;

public abstract class AbstractParticle extends AbstractGameObject {

	float timeToLive;

	public AbstractParticle(float x, float y, float width, float height, float timeToLive) {
		super(x, y, width, height);
		this.timeToLive = timeToLive;
	}

	@Override
	public void update(float delta) {
		if (timeToLive >= 0 && getAliveTime() > timeToLive) {
			getGameContext().despawn(this);
			return;
		}
		updateParticle(delta);
		move(delta);
	}
	
	public abstract void updateParticle(float delta);

}
