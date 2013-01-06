package org.bitducks.angrypidge.client.gui.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class GameLogView extends JPanel {

	private static final long serialVersionUID = -5796522534850402904L;

	private JLabel log = null;

	public GameLogView() {
		super(new BorderLayout());

		log = new JLabel();
		log.setFont(new Font("Serif", Font.BOLD, 14));
		log.setForeground(Color.BLACK);
		log.setText(" LET'S GET STARTED !!!");
		this.add(log, BorderLayout.NORTH);

		//Border
		Border raisedbevel = BorderFactory.createRaisedBevelBorder();
		Border loweredbevel = BorderFactory.createLoweredBevelBorder();
		Border compound = BorderFactory.createCompoundBorder(raisedbevel, loweredbevel);
		this.setBorder(compound);
	}
	
	public void shootResult(boolean success)
	{
		if (success)
		{
			addEntry("He's got it !");
		}
		else
		{
			addEntry("He missed...");
		}
	}

	public void addEntry(String newEntry)
	{
		log.setText("<html>" + newEntry + "<br> " + log.getText());
	}

	public void countDown() {
		addEntry("****************************");
		addEntry(" It's your turn ! Get ready ! ");
		addEntry("****************************");
		try {
			Thread.sleep(1000);
			addEntry("3");
			Thread.sleep(1000);
			addEntry("2");
			Thread.sleep(1000);
			addEntry("1");
			Thread.sleep(1000);
			addEntry("SHOOT !");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void changeStation(int stationNumber) {
		addEntry("**** Going to station # " + stationNumber + " ****");
	}

}
