package org.bitducks.angrypidge.client.gui.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class MiniMapView extends JPanel {

	private static final long serialVersionUID = -6360424280856903468L;

	private int stationNumber = 1;
	
	private Image background;

	public MiniMapView() {
		super();

		this.setBackground(Color.YELLOW);
		this.setPreferredSize(new Dimension(200, 200));

		ImageIcon imageicon = new ImageIcon("images/mini-map-background.jpg");
		background = imageicon.getImage();
		
		//Border
		Border raisedbevel = BorderFactory.createRaisedBevelBorder();
		Border loweredbevel = BorderFactory.createLoweredBevelBorder();
		Border compound = BorderFactory.createCompoundBorder(raisedbevel, loweredbevel);
		this.setBorder(compound);
		
		
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (background != null)
			g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
		

		g.setColor(Color.BLACK);
		g.drawLine(45, 85, 55, 125); // 1-2
		g.drawLine(55, 125, 75, 150); // 2-3
		g.drawLine(75, 150, 100, 155); // 3-4
		g.drawLine(100, 155, 125, 150); // 4-5
		g.drawLine(125, 150, 145, 125); // 5-6
		g.drawLine(145, 125, 160, 85); // 6-7
		
		// Join to middle
		g.drawLine(45, 85, 103, 85);
		g.drawLine(55, 125, 103, 85);
		g.drawLine(75, 150, 103, 85);
		g.drawLine(100, 155, 103, 85);
		g.drawLine(125, 150, 103, 85);
		g.drawLine(145, 125, 103, 85);
		g.drawLine(155, 85, 103, 85);
		
		
		changeStationColor(g, 1);
		g.fillOval(40, 80, 15, 15);  //1
		
		changeStationColor(g, 2);
		g.fillOval(50, 120, 15, 15); //2
		
		changeStationColor(g, 3);
		g.fillOval(70, 145, 15, 15); //3
		
		changeStationColor(g, 4);
		g.fillOval(95, 150, 15, 15); //4
		
		changeStationColor(g, 5);
		g.fillOval(120, 145, 15, 15); //5
		
		changeStationColor(g, 6);
		g.fillOval(140, 120, 15, 15); //6
		
		changeStationColor(g, 7);
		g.fillOval(150, 80, 15, 15); //7
		
		changeStationColor(g, 8);
		
		g.fillOval(95, 80, 15, 15); //8
		
		g.setColor(Color.BLACK);
		
	}
	
	private void changeStationColor(Graphics g, int pos)
	{
		if (stationNumber > pos)
		{
			g.setColor(Color.RED);
		}
		else if (stationNumber == pos)
		{
			g.setColor(Color.BLUE);
		}
		else
		{
			g.setColor(Color.black);
		}
	}

	public void movePlayer() {
		++stationNumber;
		repaint();
	}

	public int getStationNumber()
	{
		return stationNumber;
	}
	
}
