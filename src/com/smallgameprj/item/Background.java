package com.smallgameprj.item;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import com.smallgameprj.ui.GameCanvas;

public class Background implements Item{
	
	private Image img;

	public Background() {
		
		img = Toolkit.getDefaultToolkit().getImage("res/images/background.png");
	}
	
	public void update() {
		
	}
	
	public void draw(Graphics g) {
		g.drawImage(img, 0, 0, GameCanvas.getInstance());
	}

	@Override
	public boolean outsideOfBounds() {
		// TODO Auto-generated method stub
		return false;
	}
}
