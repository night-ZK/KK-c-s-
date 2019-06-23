package model;

public class DrawStyleModel {
	private int x;
	private int y;
	private int width;
	private int height;
	private int arcWidth;
	private int arcHeight;
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getArcWidth() {
		return arcWidth;
	}
	public void setArcWidth(int arcWidth) {
		this.arcWidth = arcWidth;
	}
	public int getArcHeight() {
		return arcHeight;
	}
	public void setArcHeight(int arcHeight) {
		this.arcHeight = arcHeight;
	}
	
	public DrawStyleModel(int x, int y, int width, int height, int arcWidth, int arcHeight) {
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.arcWidth = arcWidth;
		this.arcHeight = arcHeight;
	}
	
}
