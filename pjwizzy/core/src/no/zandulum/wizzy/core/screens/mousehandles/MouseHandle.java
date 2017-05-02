package no.zandulum.wizzy.core.screens.mousehandles;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MouseHandle {
	Camera camera;
	Viewport viewport;

	public MouseHandle(Camera camera, Viewport viewport) {
		this.camera = camera;
		this.viewport = viewport;
	}

	public void screenCoordsToWorldCoords(Vector3 m) {
		camera.unproject(m, 0, 0, viewport.getScreenWidth(), viewport.getScreenHeight());
	}
}
