package tools;

import java.lang.NullPointerException;

import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;

import java.util.Vector;

import java.awt.FontFormatException;
import java.awt.image.BufferedImage;

import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.awt.Font;

public class AssetsLoader {

	public static final String IMG_FILE_PATH = "./assets/img/";
	public static final String FONT_FILE_PATH = "./assets/fonts/";
	public static final String TEXT_FILE_PATH = "./assets/text/";
	public static final String CLIP_FILE_PATH = "../../assets/sounds/";

// ======================================================================================================================================================

	/**
	 * @throws java.io.IOException
	*/
	public static BufferedImage loadImage(String filePath) throws IOException {
		return Function.openImage(AssetsLoader.IMG_FILE_PATH + filePath);
	}

	/**
	 * @throws java.io.IOException
	*/
	public static BufferedImage loadSaveImg(String filePath) throws IOException {
		return AssetsLoader.loadImage("save_img/" + filePath);
	}

	/**
	 * @throws java.io.IOException
	*/
	public static BufferedImage loadTilesTexture(String filePath) throws IOException {
		return AssetsLoader.loadImage("tiles/" + filePath);
	}

	/**
	 * @throws java.io.IOException
	*/
	public static BufferedImage loadEntitySprites(String filePath) throws IOException {
		return AssetsLoader.loadImage("entity/" + filePath);
	}

// ======================================================================================================================================================

	/**
	 * @throws FileNotFoundException
	 * @throws java.io.IOException
	*/
	public static String[] loadText(String filePath) throws FileNotFoundException, IOException {
		return Function.readAllLines(AssetsLoader.TEXT_FILE_PATH + filePath);
	}

	/**
	 * @throws FileNotFoundException
	 * @throws java.io.FileNotFoundException.IOException
	*/
	public static String[] loadConf(String filePath) throws FileNotFoundException, IOException {
		return AssetsLoader.loadText("conf/" + filePath);
	}

// ======================================================================================================================================================

	/**
	 * @throws java.awt.FontFormatException
	 * @throws java.io.IOException
	*/
	public static Font loadFont(String filePath, int style, int size) throws FontFormatException, IOException {
		Font font = Font.createFont(Font.TRUETYPE_FONT, new File(AssetsLoader.FONT_FILE_PATH + filePath));
		return font.deriveFont(style, size);
	}

// ======================================================================================================================================================

	/**
	 * @throws javax.sound.sampled.LineUnavailableException.LineUnavailableException
	 * @throws javax.sound.sampled.UnsupportedAudioFileException.UnsupportedAudioFileException
	 * @throws java.io.FileNotFoundException.IOException
	 * @throws java.lang.NullPointerException.NullPointerException
	*/
	public static Clip loadClip(String filePath, double volume, Object source) throws LineUnavailableException, UnsupportedAudioFileException, IOException, NullPointerException {
		Clip result = null;

		result = AudioSystem.getClip();
		AudioInputStream inputStream = AudioSystem.getAudioInputStream(
			source.getClass().getResourceAsStream(AssetsLoader.CLIP_FILE_PATH + filePath)
		);
		result.open(inputStream);

		Function.changeClipVolume(result, volume);

		return result;
	}
}

/*
	String[] fonts = {
		"Android 101.ttf"
		,"Assassin$.ttf"
		,"Azonix.otf"
		,"DragonForcE.ttf"
		,"OptimusPrinceps.ttf"
		,"OptimusPrincepsSemiBold.ttf"
		,"pricedown bl.ttf"
		,"Vampire Wars Italic.ttf"
		,"Vampire Wars.ttf"
	};
*/