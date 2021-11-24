package com.smallgameprj.ui;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Random;

import com.smallgameprj.item.Background;
import com.smallgameprj.item.HealthBar;
import com.smallgameprj.item.Healthy;
import com.smallgameprj.item.Infectee;
import com.smallgameprj.item.Item;

public class GameCanvas extends Canvas implements Runnable{

	private static GameCanvas instance;
	
//	private Infectee infectee;
//	private Background background;
	private Healthy healthy;
//	private HealthBar healthbar;
	
	private Item[] items;
	private int itemIndex;
	private int itemsMax;
	
	private int direction;
	private Thread thread;
	private int enemyInterval;
	
	private Random rand;
	

	public static GameCanvas getInstance() {
		if (instance == null)
			instance = new GameCanvas();
		return instance;
	}

	private GameCanvas() {
//		background = new Background();
//		infectee = new Infectee[30];
		healthy = new Healthy(300, 250);
//		healthbar = new HealthBar(600,100);
		
		itemsMax = 30;
		items = new Item[itemsMax];
		itemIndex = 0;
		
		items[itemIndex++] = new Background();
		items[itemIndex++] = healthy;
		items[itemIndex++] = new HealthBar(600,100);
		
		direction = 0;
		enemyInterval = 0;
		rand = new Random();
		
		this.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				healthy.move(x, y);
			}
		});


		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				healthy.move(x, y);
			}
		});

		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

				switch (e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					direction |= Healthy.MOVE_LEFT;					
					break;
				case KeyEvent.VK_UP:
					direction |= Healthy.MOVE_UP;
					break;
				case KeyEvent.VK_RIGHT:
					direction |= Healthy.MOVE_RIGHT;
					break;
				case KeyEvent.VK_DOWN:
					direction |= Healthy.MOVE_DOWN;
					break;
					
				
				}

				healthy.moveBy(direction);
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					direction -= Healthy.MOVE_LEFT;					
					break;
				case KeyEvent.VK_UP:
					direction -= Healthy.MOVE_UP;
					break;
				case KeyEvent.VK_RIGHT:
					direction -= Healthy.MOVE_RIGHT;
					break;
				case KeyEvent.VK_DOWN:
					direction -= Healthy.MOVE_DOWN;
					break;
				
				
				}
				healthy.moveBy(direction);
			}
		});
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void update(Graphics g) {

		
		paint(g);
		
	}

	@Override
	public void paint(Graphics g) {
		Image buf = createImage(getWidth(),getHeight());
		Graphics g2 = buf.getGraphics();
		
		for (int i = 0; i < itemIndex; i++) {
			items[i].draw(g2);
		}
		
//		background.draw(g2);
//		healthy.draw(g2);
//		infectee.draw(g2);
//		healthbar.draw(g2);
		
		
		g.drawImage(buf,0,0,this);
	}

	@Override
	public void run() {
		while(true) {
			
			if(itemIndex>itemsMax-1) {
				Item[] temp = new Item[itemsMax+30];
				for (int i = 0; i < itemIndex; i++) 
					temp[i] = items[i];
					items = temp;
					itemsMax +=30;
				
			}
			
			for(int i = 0; i<itemIndex; i++) {
				items[i].update();
				if(items[i].outsideOfBounds()) {
					for (int j = 0; j < itemIndex; j++) 
						items[i+j] = items[i+j+1];
						itemIndex--;
						i--;
					
				}
			}
			if(enemyInterval == 0)
			items[itemIndex++] = new Infectee();
			enemyInterval++;
			enemyInterval %= rand.nextInt(41)+20;
			
			//화면갱신은 무조건 보조쓰레드에서
			repaint();
			//모든 컴퓨터에서 같은 프레임으로 동작
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	public Healthy getHealthy() {
		// TODO Auto-generated method stub
		return healthy;
	}


}
