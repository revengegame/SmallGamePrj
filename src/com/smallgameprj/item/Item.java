package com.smallgameprj.item;

import java.awt.Graphics;

public interface Item {

	void draw(Graphics g);
	void update();
	boolean outsideOfBounds();
}
