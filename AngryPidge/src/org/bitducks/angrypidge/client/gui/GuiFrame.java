package org.bitducks.angrypidge.client.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

abstract public class GuiFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1795442098528680336L;
	private static final String TITLE = "ANGRY PIDGE";
	protected static final int WIDTH = 800;
	protected static final int HEIGHT = 600;

	public GuiFrame() {
		super(TITLE);

		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int screenHeight = (int) dimension.height;
		int screenWidth = (int) dimension.width;
		this.setLocation(screenHeight/2, screenWidth/2);
		
		this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setResizable(false);

	}

	public abstract GuiFrame getNextFrame();
	//public abstract void draw();
	public abstract void actionPerformed(MouseEvent e);
}
