
import java.awt.Color;
import java.awt.Graphics2D;

public class Enemy {
	private double x;
	private double y;
	private int r;

	private double dx;
	private double dy;
	private double rad;
	private double speed;

	private int health;
	private int type;
	private int rank;

	private Color color1;
	private boolean ready;
	private boolean dead;

	public Enemy(int type, int rank) {
		this.type = type;
		this.rank = rank;
		if (type == 1) {
			color1 = Color.CYAN;
			if (rank == 1) {
				speed = 3;
				r = 10;
				health = 2;
			}
			
		}
		if (type == 2) {
			color1 = Color.YELLOW;
			if (rank == 1) {
				speed = 1;
				r = 20;
				health = 5;
			}
			
		}
		if (type == 3) {
			color1 = Color.PINK;
			if (rank == 1) {
				speed = 5;
				r = 6;
				health = 1;
			}
			
		}
		x = Math.random() * GamePanel.width / 2 + GamePanel.width / 4;
		y = -r;
		double angle = Math.random() * 140 + 20;
		rad = Math.toRadians(angle);
		dx = Math.cos(rad) * speed;
		dy = Math.sin(rad) * speed;
		ready = false;
		dead = false;
	}

	public double getx() {
		return x;
	};

	public double gety() {
		return y;
	};

	public double getr() {
		return r;
	};

	public void hit() {
		health--;
		if (health <= 0) {
			dead = true;
		}

	}

	public void update() {
		x += dx;
		y += dy;
		if (!ready) {
			if (x > r && x < GamePanel.width - r && y > r && y < GamePanel.height - r) {
				ready = true;
			}
		}
		if(x<r&&dx<0){dx=-dx;}
		if(y<r&&dy<0){dy=-dy;}
		if(x>GamePanel.width-r&& dx >0)dx=-dx;
		if(y>GamePanel.height-r&&dy>0)dy=-dy;
	}
	public void draw(Graphics2D g){
		g.setColor(color1);
		g.drawOval((int)(x-r), (int)(y-r), r*2, r*2);
	}

	public boolean isDead() {

		return dead;
	}
}
