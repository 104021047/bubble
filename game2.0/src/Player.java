

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class Player {
	private int x;
	private int y;
	private int r;

	private int dx;
	private int dy;
	private int speed;

	private int lives;

	private boolean up;
	private boolean down;
	private boolean left;
	private boolean right;
	private boolean fire;
	private long fireTime;
	private long fireDelay;
	private Color color1;
	private Color color2;


	public Player() {
		x = GamePanel.width /2;
		y = GamePanel.height /2;
		r = 10;
color1 = Color.GREEN;
color2 = Color.RED;
fire = false;
fireTime = System.nanoTime();
fireDelay=200;
		dx = 0;
		dy = 0;
		speed = 8;
		lives = 3;
	}
	public int hit() {
		return lives --;
	}
	public int getx() {
		return x;
	}
	public int gety() {
		return y;
	}
	public int getr() {
		return r;
	}
	public int getLive() {
		return lives;
	}
	public void setUp(boolean b) {
		up = b;
	}

	public void setDown(boolean b) {
		down = b;
	}

	public void setLeft(boolean b) {
		left = b;
	}

	public void setRight(boolean b) {
		right = b;
	}
	public void setFire(boolean b) {
		fire = b;
	}

	public void update() {
		if (up) {
			dy = -speed;
		}
		if (down) {
			dy = speed;
		}
		if (left) {
			dx = -speed;
		}
		if (right) {
			dx = speed;
		}
		y += dy;
		x += dx;
		if (x < r)
			x = r;
		if (y < r)
			y = r;
		if (x > GamePanel.width - r)
			x = GamePanel.width - r;
		if (y > GamePanel.width - r)
			y = GamePanel.height - r;
		dx=0;
		dy=0;
		if(fire){
			long elapsed = (System.nanoTime()-fireTime)/1000000;
			if(elapsed>fireDelay){
				GamePanel.bullets.add(new Bullet(270,x,y));
				fireTime = System.nanoTime();
			}
		}
	}

	public void draw(Graphics2D g) {
		g.setColor(color1);
		g.setStroke(new BasicStroke(2));
		g.drawOval(x - r, y - r, 2 * r, 2 * r);

		g.fillRect(x -1, y - 17, 2, 10);

	}

	
}
