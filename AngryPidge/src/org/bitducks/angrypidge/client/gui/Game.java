package org.bitducks.angrypidge.client.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import org.bitducks.angrypidge.client.Client;
import org.bitducks.angrypidge.client.gui.views.GameInfoView;
import org.bitducks.angrypidge.client.gui.views.GameLogView;
import org.bitducks.angrypidge.client.gui.views.GameView;
import org.bitducks.angrypidge.client.gui.views.MiniMapView;
import org.bitducks.angrypidge.common.Bird.BirdType;
import org.bitducks.angrypidge.common.Player;

public class Game extends GuiFrame {

	private static final long serialVersionUID = 1L;

	private GameView gameView;
	private MiniMapView minimap;
	private GameInfoView gameInfo;
	private GameLogView gameLog;

	public Game()
	{
		super();
		this.setLayout(new BorderLayout());
		init();
	}

	public GuiFrame getNextFrame() {
		return new Lobby();
	}

	public void updateScore(int scoreTeam1, int scoreTeam2)
	{
		gameInfo.updateScore(scoreTeam1, scoreTeam2);
	}

	public void move()
	{
		minimap.movePlayer();
		gameLog.changeStation(minimap.getStationNumber());
	}

	public void init() {

		JPanel rightPanel = new JPanel(new BorderLayout());

		//Border
		Border raisedbevel = BorderFactory.createRaisedBevelBorder();
		Border loweredbevel = BorderFactory.createLoweredBevelBorder();
		Border compound = BorderFactory.createCompoundBorder(raisedbevel, loweredbevel);
		rightPanel.setBorder(compound);

		gameView = new GameView();
		minimap = new MiniMapView();
		gameInfo = new GameInfoView();
		gameLog = new GameLogView();
		
		rightPanel.add(gameInfo, BorderLayout.NORTH);
		rightPanel.add(minimap, BorderLayout.SOUTH);
		rightPanel.add(gameLog,BorderLayout.CENTER);

		this.add(rightPanel, BorderLayout.EAST);
		this.add(gameView, BorderLayout.CENTER);
	}

	public void yourTurn() {
		// Kinda useless here
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(MouseEvent e) {
		// TODO Auto-generated method stub
	}


	public void bird(BirdType type, Player p) {
		if (p.getId() == Client.getInstance().getPlayerId())
		{
			gameLog.countDown();
			if (type == BirdType.PULL)
			{
				gameView.throwLowerBird();
			}
			else if (type == BirdType.MARK)
			{
				gameView.throwUpperBird();
			}
			else
			{
				gameView.throwBoth();
			}
		}
		else
		{
			gameLog.addEntry("It's " + p.getName() + "'s turn");
		}
	}

	public void shotResult(boolean isSucessful) {
		gameLog.shootResult(isSucessful);
	}
}
