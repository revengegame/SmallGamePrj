package com.smallgameprj.item;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import com.smallgameprj.ui.GameCanvas;

public class HealthBar implements Item{

	private int x;
	private int y;
	private Image img;
	private int imgIndex;
	private int dx;
	private int dy;

	public HealthBar(int x, int y) {

		imgIndex = 0;
		this.x = x;
		this.y = y;
		img = Toolkit.getDefaultToolkit().getImage("res/images/hpbar.png");
	}

	public void update() {

	}

	public boolean pointIn(int x, int y) {

		return (this.x < x && x < this.x + 100) && (this.y < y && y < this.y + 100);
	}

	public void draw(Graphics g) {
		// double형을 int형으로 형변환

		GameCanvas canvas = GameCanvas.getInstance();

		int x = (int) this.x;
		int y = (int) this.y;
		int dx = (int) this.dx;
		int dy = (int) this.dy;

		int w = img.getWidth(canvas);
		int h = img.getHeight(canvas) / 6;
		int offx = w / 2;
		int offy = h / 2;

		int dx1 = x - (offx + 20);
		int dy1 = y - (offy + 40);
		int dx2 = dx1 + w;
		int dy2 = dy1 + h;

		int sx1 = 0;
		int sy1 = h * imgIndex + 0;
		int sx2 = sx1 + w;
		int sy2 = sy1 + h;

		g.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, canvas);

	}

	@Override
	public boolean outsideOfBounds() {
		// TODO Auto-generated method stub
		return false;
	}

}
