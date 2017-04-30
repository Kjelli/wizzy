package no.zandulum.wizzy.core.gamecontext.physics;

import no.zandulum.wizzy.core.gameobjects.GameObject;

public interface Collidable extends GameObject {
	void onCollide(Collision collision);
}