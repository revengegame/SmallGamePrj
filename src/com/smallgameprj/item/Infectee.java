package com.smallgameprj.item;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Random;

import com.smallgameprj.ui.GameCanvas;

public class Infectee implements Item{

	private static Image img;
	private double x;
	private double y;
	private double vx;
	private double vy;
	private double dx;
	private double dy;
	private int width;
	private int height;
	private double speed;
	

	static {
		img = Toolkit.getDefaultToolkit().getImage("res/images/infect.png");
	}

	public Infectee() {
		Random rand = new Random();
		width = 108;
		height = 64;
		speed = rand.nextInt(4)+1;
	
		
		x = rand.nextInt(701);
		y = 0;
		Healthy healthy = GameCanvas.getInstance().getHealthy();
		move(healthy.getX(),healthy.getY());
	}

	private void move(double x, double y) {
//
//		if((this.x-speed<x && x<this.x+speed)&&
//				(this.y-speed<y && y<this.y+speed))
//			return;
		dx = x;
		dy = y;

		double w = (dx - this.x);
		double h = (dy - this.y);

		int distance = (int) Math.sqrt(w * w + h * h);

		vx = (dx - this.x) / distance * speed;
		vy = (dy - this.y) / distance * speed;
	}

	public void draw(Graphics g) {

		GameCanvas canvas = GameCanvas.getInstance();

		int x = (int) this.x;
		int y = (int) this.y;
		int dx = (int) this.dx;
		int dy = (int) this.dy;

		int w = width;
		int h = height;
		int offx = w / 2;
		int offy = h / 2;

		int dx1 = x - offx;
		int dy1 = y - offy;
		int dx2 = dx1 + w;
		int dy2 = dy1 + h;

		int sx1 = 0;
		int sy1 = 0;
		int sx2 = sx1 + w;
		int sy2 = sy1 + h;

//		int offx = 32;//img.getWidth(canvas)/7/2;
//		int offy = 32;//img.getHeight(canvas)/7/2;

		// System.out.printf("%d : %d\n",offx,offy);

		// g.drawImage(img,x-offx,y-offy,canvas);
		// g.drawImage(img, x-32, y-32, x+32, y+32, 192, 0, 256, 64, canvas);
		// g.drawImage(img, x-offx, y-offy, x-offx+64, y-offy+64, 0+64*imgIndex, 0,
		// 0+64*imgIndex+64, 64, canvas);
		g.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, canvas);

	}

	public void update() {
		x += vx;
		y += vy;
	}

	@Override
	public boolean outsideOfBounds() {
		// TODO Auto-generated method stub
		return false;
	}

}
