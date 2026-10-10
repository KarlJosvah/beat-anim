package tools;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import java.util.Random;
import java.util.Vector;

import java.lang.NumberFormatException;
import java.lang.IllegalArgumentException;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

public class Function {
	public static final int COLOR_VARIATION = 80;

// ======================================================================================================================================================

	public static Dimension getScreenResolution() {
		return Toolkit.getDefaultToolkit().getScreenSize();
	}

	public static void showOnScreen(int screen, JFrame frame) {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] gs = ge.getScreenDevices();
		if(screen > -1 && screen < gs.length) {
			gs[screen].setFullScreenWindow(frame);
		} else if(gs.length > 0) {
			gs[0].setFullScreenWindow(frame);
		} else {
			throw new RuntimeException("No Screens Found");
		}
	}

// ======================================================================================================================================================

	public static int randInt(int min, int max) {
		return new Random().nextInt(max - min + 1) + min;
	}

	public static String numberFormat(long number) {
		return Function.numberFormat(number, ',', 3);
	}
	public static String numberFormat(long number, char separator) {
		return Function.numberFormat(number, separator, 3);
	}
	public static String numberFormat(long number, int inBetween) {
		return Function.numberFormat(number, '.', inBetween);
	}
	public static String numberFormat(long number, char separator, int inBetween) {
		String str = "" + number;
		String result = "";
		char[] charArray = str.toCharArray();
		int count = 0;
		for(int i = charArray.length - 1; i >= 0; i--) {
			if(count == inBetween) {
				result = separator + result;
				count = 0;
			}
			result = charArray[i] + result;
			count++;
		}
		return result;
	}

	public static String rewriteNumber(int nb, int nbCharacter) {
		return String.format("%0" + nbCharacter + "d", nb);
	}

	public static double mapRange(double value, double inMin, double inMax, double outMin, double outMax) {
		return (value - inMin) * (outMax - outMin) / (inMax - inMin) + outMin;
	}

	public static double percent(int nb, double percentage) {
		return nb * percentage / 100;
	}

	public static int dividableBy(int nb, int q) {
		return nb + (q - (nb % q));
	}

// ======================================================================================================================================================

	public static Color getColorFromHex(String hexColor) {
		if (hexColor.startsWith("#")) {
			hexColor = hexColor.substring(1);
		}
		int rgb = Integer.parseInt(hexColor, 16);
		return new Color(rgb);
	}

	public static Color newColor(int x, int y, int totalRow, int totalCol) {
		float pctX = (float) x / totalCol;
		float pctY = (float) y / totalRow;
		
		float hue = pctX;
		float saturation = 1.0f;
		float brightness = pctY;
		
		return Color.getHSBColor(hue, saturation, brightness);
	}

	public static Color lighterColor(Color color) {
		int r = Math.min(color.getRed() + Function.COLOR_VARIATION, 255);
		int g = Math.min(color.getGreen() + Function.COLOR_VARIATION, 255);
		int b = Math.min(color.getBlue() + Function.COLOR_VARIATION, 255);

		return new Color(r, g, b);
	}

	public static Color darkerColor(Color color) {
		int r = Math.max(color.getRed() - Function.COLOR_VARIATION, 0);
		int g = Math.max(color.getGreen() - Function.COLOR_VARIATION, 0);
		int b = Math.max(color.getBlue() - Function.COLOR_VARIATION, 0);

		return new Color(r, g, b);
	}

	// public static void print(String... toPrint) {
	// 	for(String str: toPrint) {
	// 		System.out.print(str + "\t");
	// 	}
	// 	System.out.println();
	// }

	// public static void print(int... toPrint) {
	// 	for(int integer: toPrint) {
	// 		System.out.print(integer + "\t");
	// 	}
	// 	System.out.println();
	// }

	public static void print(Object... toPrint) {
		for(Object obj: toPrint) {
			System.out.print(obj.toString() + "\t\t");
		}
		System.out.println();
	}

// ======================================================================================================================================================

	public static void changeClipVolume(Clip clip, double volume) {
		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

		volume = Math.max(0, volume);
		volume = Math.min(100, volume);

		volume = Function.mapRange(volume, 0, 100, gainControl.getMinimum(), gainControl.getMaximum());
		gainControl.setValue((float)volume);
	}
	
// ======================================================================================================================================================

	public static BufferedImage openImage(String filePath) throws IOException {
		return ImageIO.read(new File(filePath));
	}

	public static BufferedImage scaleImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
		BufferedImage scaledImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics2D = scaledImage.createGraphics();
		graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
		graphics2D.dispose();
		return scaledImage;
	}


// ======================================================================================================================================================

	public static String[] readAllLines(String filePath) throws FileNotFoundException, IOException {
		return Function.readAllLines(new File(filePath));
	}

	public static String[] readAllLines(File toRead) throws FileNotFoundException, IOException {
		FileReader fReader = new FileReader(toRead);
		BufferedReader bReader = new BufferedReader(fReader);
		Vector<String> array = new Vector<String>();

		String temp = "";
		while(true) {
			temp = bReader.readLine();
			if(temp == null) {
				break;
			}
			array.add(temp);
		}
		fReader.close();
		bReader.close();

		String[] result = new String[array.size()];
		for(int i=0; i<result.length; i++) {
			result[i] = array.elementAt(i);
		}
		return result;
	}

// ======================================================================================================================================================

	public static int stringToInt(String str) {
		try {
			return Integer.parseInt(str);
		} catch(NumberFormatException e) {
			return 0;
		}
	}
}