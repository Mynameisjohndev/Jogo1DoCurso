package graficos;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {

	public JFrame frame;
	private static int WIDTH = 160;
	private static int HEIGHT = 160;
	private static int SCALE = 3;
	private Thread thread;
	private boolean isRunning = true;
	
	private BufferedImage background;
	
	public Game() {
		this.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		frame = new JFrame("Jogo");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		background = new BufferedImage(WIDTH, HEIGHT,BufferedImage.TYPE_INT_RGB);
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	
	public synchronized void stop() {
		
	}
	
	public void tick() {
		
	}
	
	public void render() {
		BufferStrategy buffer = this.getBufferStrategy();
		if(buffer == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = background.getGraphics();
		g.setColor(new Color(20,20,20));
		g.fillRect(0,0,WIDTH, HEIGHT);
		g = buffer.getDrawGraphics();
		g.drawImage(background,0, 0, WIDTH*SCALE,HEIGHT*SCALE, null);
		buffer.show();
	}

	@Override
	public void run() {
		long LASTIME = System.nanoTime();
		double AMOUNTOFTICKS = 60.0f;
		double MS = 1000000000 / AMOUNTOFTICKS;
		double DELTA = 0;
		int FRAMES = 0;
		double TIMER = System.currentTimeMillis();
		while(isRunning) {
			long NOW = System.nanoTime();
			DELTA += (NOW - LASTIME) / MS;
			LASTIME = NOW;
			if(DELTA > 1) {
				tick();
				render();
				FRAMES++;
				DELTA--;
			}
			if(System.currentTimeMillis() - TIMER >= 1000) {
				System.out.println("FPS " + FRAMES);
				FRAMES = 0;
				TIMER += 1000;
			}
		}
	}

}
