package no.zandulum.wizzy.core.assets.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

import no.zandulum.wizzy.core.graphics.Draw;

public class Button extends Actor {

	private TextureRegion image;
	private Sprite sprite;

	public Button(TextureRegion image, float x, float y, float width, float height) {
		this.image = image;
		this.setBounds(x, y, width, height);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(image, getX(), getY(), getWidth(), getHeight());
	}
}
