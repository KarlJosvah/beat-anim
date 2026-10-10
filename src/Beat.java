package entity;

import java.util.concurrent.ThreadLocalRandom;

import java.awt.Color;
import java.awt.Graphics2D;

import root.Main;
import entity.Cube;
import tools.Function;

public class Beat {
	private Cube[][] cubes = null;
	private int[] beats = null;
	public static final int MIN_IMPULSE_INTENSITY = 5;
	public static final int MAX_IMPULSE_INTENSITY = 50;
	public static final int IMPULSE_EFFECT_RADIUS = 3;
	public static final int IMPULSE_EFFECT_WEIGHT = 4;

	private int row = 0;
	private int col = 0;
	private int xOffset = 0;
	private int yOffset = 0;

	public static final double MIN_IMPULSE_SPLIT_TIME = 0.0;
	public static final double MAX_IMPULSE_SPLIT_TIME = 0.06;
	private double impulseSplitTime = 0.0;
	private double impulseElapsedTime = 0.0;
	private double beatSplitTime = 0.05;
	private double beatElapsedTime = 0.0;

	public void init() {
		this.row = (int)(Main.HEIGHT / Cube.RADIUS);
		this.col = (int)(Main.WIDTH / Cube.RADIUS);
		this.xOffset = (Main.WIDTH % Cube.RADIUS) / 2;
		this.yOffset = (Main.HEIGHT % Cube.RADIUS) / 2;

		this.initCubes();
		this.initBeat();
	}

	private void initCubes() {
		this.cubes = new Cube[row][col];
		int x = xOffset;
		int y = yOffset;

		for(int rowIndex = 0; rowIndex < row; rowIndex++) {
			x = xOffset;
			for(int colIndex = 0; colIndex < col; colIndex++) {
				Color color = Function.newColor(colIndex, rowIndex, row, col);
				this.cubes[rowIndex][colIndex] = new Cube(x, y, color);
				x += Cube.RADIUS;
			}
			y += Cube.RADIUS;
		}
	}

	private void initBeat() {
		this.beats = new int[col];
		for(int colIndex = 0; colIndex < col; colIndex++) {
			beats[colIndex] = 0;
		}
	}

	public void tick(double elapsedSecond, long loopID) {
		this.processImpulse(elapsedSecond, loopID);
		this.processBeats(elapsedSecond, loopID);
		this.updateBeat();
	}

	private void processImpulse(double elapsedSecond, long loopID) {
		this.impulseElapsedTime += elapsedSecond;
		if(this.impulseElapsedTime >= this.impulseSplitTime) {
			this.impulseSplitTime = ThreadLocalRandom.current().nextDouble(this.MIN_IMPULSE_SPLIT_TIME, this.MAX_IMPULSE_SPLIT_TIME);
			this.impulseElapsedTime = 0.0;
			this.randomImpulse();
		}
	}

	private void randomImpulse() {
		int min = (int) Function.percent(row, Beat.MIN_IMPULSE_INTENSITY);
		int max = (int) Function.percent(row, Beat.MAX_IMPULSE_INTENSITY);
		int impulseIntensity = ThreadLocalRandom.current().nextInt(min, max);

		int colIndex = ThreadLocalRandom.current().nextInt(0, col);
		this.impulseAt(colIndex, impulseIntensity);
	}

	private void impulseAt(int colIndex, int impulseIntensity) {
		for(int i = - Beat.IMPULSE_EFFECT_RADIUS; i <= + Beat.IMPULSE_EFFECT_RADIUS; i++) {
			int tempIndex = colIndex + i;
			int tempImpulse = impulseIntensity;
			if( i < 0 ) {
				tempImpulse += i * Beat.IMPULSE_EFFECT_WEIGHT;
			} else {
				tempImpulse -= i * Beat.IMPULSE_EFFECT_WEIGHT;
			}
			tempImpulse = Math.max(0, tempImpulse);

			if(tempIndex < 0 || tempIndex >= col) {
				continue;
			}
			this.beats[tempIndex] += tempImpulse;
		}
	}

	private void processBeats(double elapsedSecond, long loopID) {
		this.beatElapsedTime += elapsedSecond;
		if(this.beatElapsedTime >= this.beatSplitTime) {
			this.beatElapsedTime = 0.0;
			for(int colIndex = 0; colIndex < col; colIndex++) {
				int newValue = this.beats[colIndex] - 1;
				this.beats[colIndex] = Math.max(0, newValue);
			}
		}
	}

	private void updateBeat() {
		for(int colIndex = 0; colIndex < col; colIndex++) {
			for(int rowIndex = 0; rowIndex < row; rowIndex++) {
				boolean activate = rowIndex >= row - this.beats[colIndex];
				this.cubes[rowIndex][colIndex].setActive(activate);
			}
		}
	}

	public void render(Graphics2D g) {
		for(int rowIndex = 0; rowIndex < row; rowIndex++) {
			for(int colIndex = 0; colIndex < col; colIndex++) {
				this.cubes[rowIndex][colIndex].render(g);
			}
		}
	}
}