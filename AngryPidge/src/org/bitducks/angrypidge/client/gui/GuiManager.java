package org.bitducks.angrypidge.client.gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import org.bitducks.angrypidge.common.Bird.BirdType;
import org.bitducks.angrypidge.common.Player;

public class GuiManager {
	
	private GuiFrame frame;
	private int screenWidth;
	private int screenHeight;

	public GuiManager()
	{
		setFrame(new Menu());
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		screenHeight = (int) dimension.height;
		screenWidth = (int) dimension.width;
	}
	
	public void getNextGuiFrame()
	{
		frame.dispose();
		setFrame(getFrame().getNextFrame());
		displayGuiFrame();
	}
	
	public void displayGuiFrame()
	{
		//getFrame().draw();
		frame.setLocation(screenWidth/2 - 400,screenHeight/2 - 300);
		//frame.setLocation(0,0);
		//frame.setLocationRelativeTo(null); 	//Deprecated... ?
		getFrame().pack();
		getFrame().setVisible(true); 
	}

	public GuiFrame getFrame() {
		return frame;
	}

	public void setFrame(GuiFrame frame) {
		this.frame = frame;
	}

	public void updateScore(int scoreTeam1, int scoreTeam2) {
		((Game)frame).updateScore(scoreTeam1, scoreTeam2);
	}

	public void move() {
		((Game)frame).move();
	}

	public void yourTurn() {
		((Game)frame).yourTurn();
	}

	public void bird(BirdType type, Player p) {
		((Game)frame).bird(type,p);
	}

	public void shotResult(boolean isSucessful) {
		((Game)frame).shotResult(isSucessful);
	}

}
