package root;

import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import tools.Function;
import tools.AssetsLoader;

import root.gamestate.GameStateHandler;

public class GameLoop {
	public static final int TICK_SPEED = 100;
	public static final Color DEFAULT_BG_COLOR = Color.BLACK;

	private Main mainFrame = null;
	private GameStateHandler stateHandler = null;

	private long fps = 0;
	private long tps = 0;
	private BufferedImage renderImg = null;

	private Font fpsFont = null;

	public GameLoop(Main mainFrame) {
		this.mainFrame = mainFrame;
		this.init();
	}

	public void exit() {
		this.mainFrame.exit();
	}

// ======================================================================================================================================================

	public void setFPS(long fps) {
		this.fps = fps;
	}

	public void setTPS(long tps) {
		this.tps = tps;
	}

	public void keyPressed(int keyCode) {
		this.stateHandler.keyPressed(keyCode);
	}

	public void keyReleased(int keyCode) {
		this.stateHandler.keyReleased(keyCode);
	}

	public void mouseClicked(int x, int y, int button) {
		this.stateHandler.mouseClicked(x, y, button);
	}

	public void mousePressed(int x, int y, int button) {
		this.stateHandler.mousePressed(x, y, button);
	}

	public void mouseReleased(int x, int y, int button) {
		this.stateHandler.mouseReleased(x, y, button);
	}

	public void mouseDragged(int x, int y) {
		this.stateHandler.mouseDragged(x, y);
	}

	public void mouseMoved(int x, int y) {
		this.stateHandler.mouseMoved(x, y);
	}

// ======================================================================================================================================================

	public void init() {
		this.stateHandler = new GameStateHandler(this);
		this.renderImg = new BufferedImage(Main.WIDTH, Main.HEIGHT, BufferedImage.TYPE_INT_RGB);
		try {
			this.fpsFont = AssetsLoader.loadFont("Android 101.ttf", Font.BOLD, 12);
		} catch(Exception e) {
			Main.throwException(e);
		}
	}

// ======================================================================================================================================================

	public void tick(double elapsedSecond, long loopID) {
		this.stateHandler.tick(elapsedSecond, loopID);
	}

// ======================================================================================================================================================

	public BufferedImage render() {
		Graphics2D g = this.renderImg.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(GameLoop.DEFAULT_BG_COLOR);
		g.fillRect(0, 0, this.renderImg.getWidth(), this.renderImg.getHeight());

		this.stateHandler.render(g, this.renderImg.getWidth(), this.renderImg.getHeight());

		this.displayFPS(g);
		g.dispose();
		return this.renderImg;
	}

	private void displayFPS(Graphics2D g) {
		// g.setColor(java.awt.Color.WHITE);
		// g.fillRect(0, 0, 100, 20);
		g.setFont(this.fpsFont);
		g.setColor(Color.GREEN);
		String fpsSTR = "" + Function.numberFormat(this.fps) + " FPS - " + Function.numberFormat(this.tps) + " TPS";
		g.drawString(fpsSTR, 5, 15);
	}
}