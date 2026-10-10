package root.gamestate;

import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics2D;

import java.awt.event.KeyEvent;

import javax.sound.sampled.Clip;

import tools.Function;
import tools.AssetsLoader;

import root.Main;

public class MenuState extends GameState {
	private Font menuFont = null;
	private String[] menuOptions = null;
	private int selectedIndex = 0;

	public static final Color OPTION_COLOR = Color.WHITE;
	public static final Color SELECTED_OPTION_COLOR = Function.getColorFromHex("#0206BA");

	private static final String COIN_SOUND_FILE_PATH = "pickup-coin-2.wav";
	private static final String MENU_FONT_FILE_PATH = "Android 101.ttf";
	private Clip coinSound = null;

	public MenuState() {
	}

// ======================================================================================================================================================

	@Override
	public void init(GameStateHandler stateHandler) {
		__setGameStateHandler(stateHandler);
		try {
			this.menuFont = AssetsLoader.loadFont(MenuState.MENU_FONT_FILE_PATH, Font.BOLD, 40);
		} catch(Exception e) {
			Main.throwException(e);
		}
		String[] options = {
			"Continue"
			,"New Game"
			,"Settings"
			,"Quit Game"
		};
		this.menuOptions = options;
		this.selectedIndex = 0;

		try {
			this.coinSound = AssetsLoader.loadClip(MenuState.COIN_SOUND_FILE_PATH, 80, this);
		} catch(Exception e) {
			Main.throwException(e);
		}
	}

	@Override
	public void tick(double elapsedSecond, long loopID) {
	}

	@Override
	public void render(Graphics2D g, int renderWidth, int renderHeight) {
		super.renderMouseHover(g);

		g.setFont(this.menuFont);

		int fontHeight = g.getFontMetrics().getHeight();
		int yOffset = (renderHeight - fontHeight * (menuOptions.length)) / 2;

		for(int i = 0; i < menuOptions.length; i++) {
			if(i == this.selectedIndex) {
				g.setColor(MenuState.SELECTED_OPTION_COLOR);
			} else {
				g.setColor(MenuState.OPTION_COLOR);
			}

			int x = (renderWidth - g.getFontMetrics().stringWidth(menuOptions[i])) / 2;
			g.drawString(menuOptions[i], x, yOffset);
			yOffset += fontHeight;
		}
	}

// ======================================================================================================================================================
	
	@Override
	public void closeState() {
	}

	@Override void keyPressed(int keyCode) {
		if(keyCode == KeyEvent.VK_DOWN) {
			this.coinSound();
			this.selectedIndex++;
			if(this.selectedIndex >= this.menuOptions.length) {
				this.selectedIndex -= this.menuOptions.length;
			}
		}else if(keyCode == KeyEvent.VK_UP) {
			this.coinSound();
			this.selectedIndex--;
			if(this.selectedIndex < 0) {
				this.selectedIndex += this.menuOptions.length;
			}
		}
		if(keyCode == KeyEvent.VK_ENTER) {
			this.coinSound();
			this.changeState();
		}
	}

	@Override
	public void mouseClicked(int x, int y, int button) {
	}

	@Override
	public void mousePressed(int x, int y, int button) {
	}

	@Override
	public void mouseReleased(int x, int y, int button) {
	}

	@Override
	public void mouseDragged(int x, int y) {
	}

	@Override
	public void mouseMoved(int x, int y) {
		super.updateMousePosition(x, y);
	}

// ======================================================================================================================================================

	private void coinSound() {
		this.coinSound.stop();
		this.coinSound.setFramePosition(0);
		this.coinSound.start();
	}

// ======================================================================================================================================================

	private void changeState() {
		switch(this.menuOptions[this.selectedIndex]) {
			case "Continue":
				break;
			case "New Game":
				break;
			case "Settings":
				break;
			case "Quit Game":
				super.stateHandler.exit();
				break;
			default:
				break;
		}
	}
}