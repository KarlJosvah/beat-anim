package root;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import root.listener.KeyListenerMain;
import root.listener.MouseListenerMain;

import tools.Function;

public class Main extends JFrame {
	public static final String TITLE = "Beat Anim";
	public static final int WIDTH = (int) Function.getScreenResolution().getWidth();
	public static final int HEIGHT = (int) Function.getScreenResolution().getHeight();
	// 16:9

	public static volatile boolean DEBUG_KEY_LISTENER = false;
	public static volatile boolean SYNC_TPS_FPS = false;
	public static volatile boolean YIELD_CPU = false;

	private GameLoop gameLoop = new GameLoop(this);

// ======================================================================================================================================================

	public static void main(String[] args){
		new Main();
	}

// ======================================================================================================================================================

	public Main() {
		this.init();
		this.config();
		this.setVisible(true);
		// Function.showOnScreen(1, this);
		this.run();
	}

	private void init() {
		this.setTitle(Main.TITLE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(Main.WIDTH, Main.HEIGHT);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		this.setUndecorated(true);
	}

	private void config() {
		this.addKeyListener(new KeyListenerMain(this));
		MouseListenerMain mouseListener = new MouseListenerMain(this);
		this.addMouseListener(mouseListener);
		this.addMouseMotionListener(mouseListener);
	}

	public void exit() {
		this.dispose();
		System.exit(0);
	}

// ======================================================================================================================================================

	public void run() {
		long oneSecond = (long)1E9;
		long oneTick = oneSecond / GameLoop.TICK_SPEED;
		long actualTimeNano_FPS = 0;
		long actualTimeNano_LOOP = 0;
		long lastTimeNano_FPS = 0;
		long lastTimeNano_LOOP = 0;

		long nbFrameRendered = 0;
		long nbTickExecuted = 0;
		long loopID = 0;

		while(true) {
			if(Main.YIELD_CPU) {
			    try {
			        Thread.sleep(1);
			    } catch (InterruptedException e) {
			        Thread.currentThread().interrupt();
			        break;
			    }
			}
			actualTimeNano_LOOP = java.lang.System.nanoTime();
			long dt = actualTimeNano_LOOP - lastTimeNano_LOOP;
			if(dt >= oneTick) {
				double elapsedSecond = dt / 1E9;
				this.gameLoop.tick(elapsedSecond, loopID);
				lastTimeNano_LOOP = java.lang.System.nanoTime();

				if(Main.SYNC_TPS_FPS) {
					this.repaint();
					nbFrameRendered++;
				}
				loopID++;
				nbTickExecuted++;
			}
			if(!Main.SYNC_TPS_FPS) {
				this.repaint();
				nbFrameRendered++;
			}

			actualTimeNano_FPS = java.lang.System.nanoTime();
			if(actualTimeNano_FPS - lastTimeNano_FPS >= oneSecond) {
				this.gameLoop.setFPS(nbFrameRendered);
				this.gameLoop.setTPS(nbTickExecuted);
				nbFrameRendered = 0;
				nbTickExecuted = 0;
				lastTimeNano_FPS = actualTimeNano_FPS;
			}
		}
	}

	@Override
	public void paint(java.awt.Graphics g) {
		BufferedImage img = this.gameLoop.render();
		g.drawImage(img, 0, 0, null);
	}

// ======================================================================================================================================================

	public void keyPressed(int keyCode, char keyChar) {
		switch(keyCode) {
			case KeyEvent.VK_ESCAPE:
				this.exit();
				break;
			case KeyEvent.VK_F7:
				break;
			case KeyEvent.VK_F1:
				Main.DEBUG_KEY_LISTENER = !Main.DEBUG_KEY_LISTENER;
				break;
			case KeyEvent.VK_F2:
				Main.SYNC_TPS_FPS = !Main.SYNC_TPS_FPS;
				break;
			case KeyEvent.VK_F3:
				Main.YIELD_CPU = !Main.YIELD_CPU;
				break;
			default:
				break;
		}
		if(Main.DEBUG_KEY_LISTENER) {
			System.out.println(keyCode + " " + keyChar);
		}
		this.gameLoop.keyPressed(keyCode);
	}

	public void keyReleased(int keyCode) {
		this.gameLoop.keyReleased(keyCode);
	}

	public void mouseClicked(int x, int y, int button) {
		this.gameLoop.mouseClicked(x, y, button);
	}

	public void mousePressed(int x, int y, int button) {
		this.gameLoop.mousePressed(x, y, button);
	}

	public void mouseReleased(int x, int y, int button) {
		this.gameLoop.mouseReleased(x, y, button);
	}

	public void mouseDragged(int x, int y) {
		this.gameLoop.mouseDragged(x, y);
	}

	public void mouseMoved(int x, int y) {
		this.gameLoop.mouseMoved(x, y);
	}

// ======================================================================================================================================================

	public static void throwException(Exception e) {
		e.printStackTrace();
	}
}