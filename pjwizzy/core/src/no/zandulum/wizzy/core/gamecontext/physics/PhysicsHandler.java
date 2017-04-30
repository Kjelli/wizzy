package no.zandulum.wizzy.core.gamecontext.physics;

import java.util.List;

import no.zandulum.wizzy.core.gameobjects.GameObject;

public interface PhysicsHandler {
	void collisonCheck(List<? extends GameObject> gameobjects);
}