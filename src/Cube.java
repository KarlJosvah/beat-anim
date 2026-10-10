package entity;

import java.awt.Color;
import java.awt.Polygon;
import java.awt.Graphics2D;

import tools.Function;

public class Cube {
	public static final int RADIUS = 30;
	public static final int BORDER_RADIUS = (int) Function.percent(Cube.RADIUS, 10);

	private int posX = 0;
	private int posY = 0;
	private Color color = Color.WHITE;
	private boolean isActive = false;

	public Cube(int posX, int posY, Color color) {
		this.posX = posX;
		this.posY = posY;
		this.color = color;
	}

	public void init() {
	}

	public void tick() {
	}

	public void render(Graphics2D g) {
		if(!this.isActive) {
			return;
		}
		this.drawLighterBorder(g);
		this.drawDarkerBorder(g);
		this.drawInnerCube(g);
	}

	private void drawLighterBorder(Graphics2D g) {
		g.setColor(Function.lighterColor(this.color));

		Polygon triangle = new Polygon();
		triangle.addPoint(this.posX, this.posY);
		triangle.addPoint(this.posX + Cube.RADIUS, this.posY);
		triangle.addPoint(this.posX, this.posY + Cube.RADIUS);

		g.fillPolygon(triangle);
	}

	private void drawDarkerBorder(Graphics2D g) {
		g.setColor(Function.darkerColor(this.color));

		Polygon triangle = new Polygon();
		triangle.addPoint(this.posX + Cube.RADIUS, this.posY);
		triangle.addPoint(this.posX + Cube.RADIUS, this.posY + Cube.RADIUS);
		triangle.addPoint(this.posX, this.posY + Cube.RADIUS);

		g.fillPolygon(triangle);
	}

	private void drawInnerCube(Graphics2D g) {
		g.setColor(this.color);

		g.fillRect(
			this.posX + Cube.BORDER_RADIUS,
			this.posY + Cube.BORDER_RADIUS,
			Cube.RADIUS - (Cube.BORDER_RADIUS * 2),
			Cube.RADIUS - (Cube.BORDER_RADIUS * 2)
		);
	}

	public void setActive(boolean active) {
		this.isActive = active;
	}
}