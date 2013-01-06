package org.bitducks.angrypidge.client.gui.views;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;



public class GameInfoView extends JPanel {

	private static final long serialVersionUID = 3373376499958482047L;

	private JLabel scoreTeam1 = null;
	private JLabel scoreTeam2 = null;


	public GameInfoView() {
		super(new BorderLayout());

		scoreTeam1 = new JLabel();
		scoreTeam1.setFont(new Font("Serif", Font.BOLD, 20));
		scoreTeam1.setForeground(Color.BLACK);

		scoreTeam2 = new JLabel();
		scoreTeam2.setFont(new Font("Serif", Font.BOLD, 20));
		scoreTeam2.setForeground(Color.BLACK);

		this.add(scoreTeam1, BorderLayout.NORTH);
		this.add(scoreTeam2, BorderLayout.SOUTH);

		updateScore(0, 0);

//		//Border
//		Border raisedbevel = BorderFactory.createRaisedBevelBorder();
//		Border loweredbevel = BorderFactory.createLoweredBevelBorder();
//		Border compound = BorderFactory.createCompoundBorder(raisedbevel, loweredbevel);
//		this.setBorder(compound);

	}

	public void updateScore(int scoreTeam1, int scoreTeam2)
	{
		this.scoreTeam1.setText(" TEAM 1 : " + String.format("%02d", scoreTeam1));
		this.scoreTeam2.setText(" TEAM 2 : " + String.format("%02d", scoreTeam2));
	}

}
