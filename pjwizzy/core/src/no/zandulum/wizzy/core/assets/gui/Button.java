package no.zandulum.wizzy.core.assets.gui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

import no.zandulum.wizzy.core.graphics.Draw;

public class Button extends Actor{
	
	private Texture image;
	private Sprite sprite;
	
	public Button(Texture image, float x, float y, float width, float height){
		this.image = image;
		this.sprite = new Sprite(image);
		this.setBounds(x, y, width, height);
		
	}
	
	public Button(TextureRegion image, float x, float y, float width, float height){
		this.sprite = new Sprite(image);
		this.setBounds(x, y, width, height);
		
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		Draw.sprite(batch, sprite, getX(), getY(), getWidth(), getHeight(), 0f);
	}
}
