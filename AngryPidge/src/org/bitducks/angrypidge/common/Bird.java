package org.bitducks.angrypidge.common;

import java.awt.Rectangle;

public class Bird {

	public enum BirdType {
		PULL, MARK, BOTH
	}
	
	public BirdType getType() {
		return type;
	}

	public void setType(BirdType type) {
		this.type = type;
	}

	public Rectangle getPosition() {
		return position;
	}
	
	public void setPosition(int x, int y) {
		this.position.setLocation(x, y);
	}
	
	public void setSize(int x, int y) {
		this.position.setSize(x, y);
	}
	
	public int getX()
	{
		return (int) this.position.getX();
	}
	
	public int getY()
	{
		return (int) this.position.getY();
	}
	
	public int getWidth()
	{
		return (int) this.position.getWidth();
	}
	
	public int getHeight()
	{
		return (int) this.position.getHeight();
	}
	
	public int nbPigeonHit() {
		return nbPigeonHit;
	}

	public void hasBeenHit(int i) {
		this.nbPigeonHit = i;
	}
	
	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
	
	public static final int SIZE_X = 80;
	public static final int SIZE_Y = 35;
	

	private BirdType type;
	private Rectangle position = new Rectangle(0, 0, SIZE_X, SIZE_Y);
	
	private int nbPigeonHit = 0;
	private boolean isVisible = true;
	
	public Bird(BirdType type) {
		this.type = type;
	}



}
