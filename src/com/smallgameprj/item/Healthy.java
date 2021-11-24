package com.smallgameprj.item;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.ImageObserver;

import com.smallgameprj.ui.GameCanvas;

public class Healthy implements Item{

	// 싱글톤 관리를 위해 static //final은 잠금장치
	public static final int MOVE_NONE = 0;
	public static final int MOVE_UP = 0b0001;// 1;
	public static final int MOVE_RIGHT = 0b0010;// 2;
	public static final int MOVE_DOWN = 0b0100;// 4;
	public static final int MOVE_LEFT = 0b1000;// 8;

	private double x;
	private double y;
	private double dx;
	private double dy;
	private double vx;
	private double vy;

	private int direction;

	private int speed;

	private int distance;

	private Image img;
	private int imgIndex;
	private int imgIndexDuration;

	public Healthy(int x, int y) {
		dx = dy = vx = vy = 0;
		distance = 1;
		speed = 3;
		direction = MOVE_NONE;
		imgIndexDuration = 0;

		this.x = x;
		this.y = y;
		img = Toolkit.getDefaultToolkit().getImage("res/images/unit.png");
		imgIndex = 0;
	}

	public void moveBy(int direction) {

		// 누를 때 멈추는 것을 없애기 위해
		// update가 입력 된 것을 계속 돌리기 위해 넘김
		this.direction = direction;
	}

	public void move(int x, int y) {

		dx = x;
		dy = y;

		double w = (dx - this.x);
		double h = (dy - this.y);

		distance = (int) Math.sqrt(w * w + h * h);

		vx = (dx - this.x) / distance * speed;
		vy = (dy - this.y) / distance * speed;
	}

	// 현재위치에 벡터값을 누적시키기
	// 보조쓰레드를 통해 계속 최신화
	public void update() {

		if ((vy > 0 || (direction & MOVE_DOWN) == MOVE_DOWN) && imgIndexDuration == 0) {

			imgIndex++;
			if (imgIndex == 4) {
				imgIndex -= 4;
			}
			System.out.println("아래쪽");
			System.out.println(imgIndex);

		} else if ((vx < 0 || (direction & MOVE_LEFT) == MOVE_LEFT) && imgIndexDuration == 0) {

			if(imgIndex<4)
				imgIndex=4;
				imgIndex++;
			if (imgIndex == 8) {
				imgIndex -= 4;
			}
			System.out.println("왼쪽");
			System.out.println(imgIndex);

		} else if ((vx > 0 || (direction & MOVE_RIGHT) == MOVE_RIGHT) && imgIndexDuration == 0) {


			if(imgIndex<8)
				imgIndex=8;
				imgIndex++;
			if (imgIndex == 12) {
				imgIndex -= 4;
			}
			System.out.println("오른쪽");
			System.out.println(imgIndex);

		} else if ((vy < 0 || (direction & MOVE_UP) == MOVE_UP) && imgIndexDuration == 0) {

			
			if(imgIndex<12)
				imgIndex=12;
				imgIndex++;
			if (imgIndex == 16)
				imgIndex -= 4;

			System.out.println("위쪽");
			System.out.println(imgIndex);

		} else if (vx == 0 && direction == 0) {
			imgIndex = 0;
		} else if (imgIndex > 16)
			imgIndex = 0;

		imgIndexDuration++;
		imgIndexDuration %= 3;

		if ((direction & MOVE_UP) == MOVE_UP)
			y -= speed;
		if ((direction & MOVE_RIGHT) == MOVE_RIGHT)
			x += speed;
		if ((direction & MOVE_DOWN) == MOVE_DOWN)
			y += speed;
		if ((direction & MOVE_LEFT) == MOVE_LEFT)
			x -= speed;

		x += vx;
		y += vy;



		if((dx-speed<x && x<dx +speed)&&(dy-speed<y && y<dy +speed)) {
			vx = 0;
			vy = 0;
			return;
		}

		//distance -= speed;
		

	}

	public void moveOut() {

	}

	public void draw(Graphics g) {
		GameCanvas canvas = GameCanvas.getInstance();

		int x = (int) this.x;
		int y = (int) this.y;

		int w = img.getWidth(canvas) / 4;
		int h = img.getHeight(canvas) / 4;
		int offx = w / 2;
		int offy = h / 2;

		int dx1 = x - offx;
		int dy1 = y - offy;
		int dx2 = dx1 + w;
		int dy2 = dy1 + h;

		int sx1 = 0;
		int sy1 = 0;

		if (0 <= imgIndex && imgIndex < 4) {
			sx1 = 0 + w * imgIndex;
			sy1 = 0;
		} else if (imgIndex < 8) {
			sx1 = 0 + w * (imgIndex - 4);
			sy1 = h;
		} else if (imgIndex < 12) {
			sx1 = 0 + w * (imgIndex - 8);
			sy1 = 2 * h;
		} else if (imgIndex < 16) {
			sx1 = 0 + w * (imgIndex - 12);
			sy1 = 3 * h;
		}
		int sx2 = sx1 + w;
		int sy2 = sy1 + h;
		
		if(dy1<=0) {
			dy1=0;
			dy2=64;
			vx=0;
			vy=0;
			y=32;
		}
		
		if(dy1>=400) {
			dy1=400;
			dy2=464;
			vx=0;
			vy=0;
			y=432;
		}
		if(dx1<0) {
			dx1=0;
			dx2=64;
			vx=0;
			vy=0;
			x=32;
		}
		if(dx1>=650) {
			dx1=650;
			dx2=714;
			vx=0;
			vy=0;
			x=682;
		}
		
		g.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, canvas);

	}

	public double getX() {
		// TODO Auto-generated method stub
		return x;
	}

	public double getY() {
		// TODO Auto-generated method stub
		return y;
	}

	@Override
	public boolean outsideOfBounds() {
		// TODO Auto-generated method stub
		return false;
	}

}