
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable, KeyListener {
	public static int width = 600;
	public static int height = 600;
	private Thread thread;
	private boolean running;
	private BufferedImage image;
	private Graphics2D g;
	private int FPS = 30;
	private double averageFPS;

	public static Player player;
	public static ArrayList<Bullet> bullets;
	public static ArrayList<Enemy> enemies;

	public GamePanel() {
		super();
		setPreferredSize(new Dimension(width, height));
		setFocusable(true);
		requestFocus();
	}

	public void addNotify() {
		super.addNotify();
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
		addKeyListener(this);
	}
	public void run() {
		running = true;
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		player = new Player();
		bullets = new ArrayList<Bullet>();
		enemies = new ArrayList<Enemy>();
		for (int i = 0; i < 10; i++) {
			enemies.add(new Enemy(1, 1));
			enemies.add(new Enemy(2, 1));
			enemies.add(new Enemy(3, 1));
		}
		long startTime;
		long URDTimeMillis;
		long waitTime;
		long targetTime = 1000 / FPS;
		while (running) {

			startTime = System.nanoTime();
			gameUpdate();
			gameRender();
			gameDraw();
			URDTimeMillis = (System.nanoTime() - startTime) / 1000000;
			waitTime = targetTime - URDTimeMillis;

			try {
				Thread.sleep(waitTime);
			} catch (Exception e) {

			}

		}
	}

	private void gameUpdate() {
		// TODO Auto-generated method stub
		player.update();
		for (int i = 0; i < bullets.size(); i++) {
			boolean remove = bullets.get(i).update();
			if (remove) {
				bullets.remove(i);
				i--;
			}
		}

		for (int j = 0; j < enemies.size(); j++) {

			Enemy e = enemies.get(j);
			double ex = e.getx();
			double ey = e.gety();
			double er = e.getr();

			double dx = player.getx() - ex;
			double dy = player.gety() - ey;
			double sq = Math.sqrt(dx * dx + dy * dy);
			if (sq <= player.getr() + er) {
				e.hit();
				player.hit();
				break;
			}
		}
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).update();
		}
		for (int i = 0; i < bullets.size(); i++) {
			Bullet b = bullets.get(i);
			double bx = b.getx();
			double by = b.gety();
			double br = b.getr();
			for (int j = 0; j < enemies.size(); j++) {
				Enemy e = enemies.get(j);
				double ex = e.getx();
				double ey = e.gety();
				double er = e.getr();

				double dx = bx - ex;
				double dy = by - ey;
				double sq = Math.sqrt(dx * dx + dy * dy);
				if (sq <= br + er) {
					e.hit();
					bullets.remove(i);
					i--;
					break;
				}
			}
		}

		for (int i = 0; i < enemies.size(); i++) {
			if (enemies.get(i).isDead()) {
				enemies.remove(i);
				i--;
			}
		}

	}

	private void gameRender() {
		// TODO Auto-generated method stub

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.black);
		if (player.getLive() <= 0) {
			  int fontSize = 100;
			 Font f = new Font("Comic Sans MS", Font.BOLD, fontSize);
		        g.setFont(f);
			g.setColor(Color.WHITE);
			g.drawString("Lose!!!!!!", width/2-150, height/2);
			running=false;
		}

		if(enemies.size()==0){
			  int fontSize = 100;
		        Font f = new Font("Comic Sans MS", Font.BOLD, fontSize);
		        g.setFont(f);
			g.setColor(Color.WHITE);
			g.drawString("Win!!!!!!", width/2-150, height/2);
		}

		player.draw(g);
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).draw(g);
		}
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(g);
		}
	}

	private void gameDraw() {
		// TODO Auto-generated method stub
		Graphics g2 = this.getGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
	}

	@Override
	public void keyTyped(KeyEvent key) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent key) {
		// TODO Auto-generated method stub
		int keyCode = key.getKeyCode();
		if (keyCode == KeyEvent.VK_LEFT) {
			player.setLeft(true);
		}
		if (keyCode == KeyEvent.VK_RIGHT) {
			player.setRight(true);
		}
		if (keyCode == KeyEvent.VK_UP) {
			player.setUp(true);
		}
		if (keyCode == KeyEvent.VK_DOWN) {
			player.setDown(true);
		}
		if (keyCode == KeyEvent.VK_Z) {
			player.setFire(true);
		}
	}

	@Override
	public void keyReleased(KeyEvent key) {
		// TODO Auto-generated method stub
		int keyCode = key.getKeyCode();
		if (keyCode == KeyEvent.VK_LEFT) {
			player.setLeft(false);
		}
		if (keyCode == KeyEvent.VK_RIGHT) {
			player.setRight(false);
		}
		if (keyCode == KeyEvent.VK_UP) {
			player.setUp(false);
		}
		if (keyCode == KeyEvent.VK_DOWN) {
			player.setDown(false);
		}
		if (keyCode == KeyEvent.VK_Z) {
			player.setFire(false);
		}
	}
}