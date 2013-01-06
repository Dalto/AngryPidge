package org.bitducks.angrypidge.client.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.bitducks.angrypidge.client.Client;
import org.bitducks.angrypidge.common.Player;
import org.bitducks.angrypidge.common.Team;
import org.bitducks.angrypidge.network.event.serverevent.JoinTeamEvent;

//ListSelectionListener is used to represent if players are ready or not
public class Lobby extends GuiFrame implements ListSelectionListener {

	//JLabel team1Title = new JLabel("Team 1");
	//JLabel team2Title = new JLabel("Team 2");

	private static final long serialVersionUID = 1L;
	DefaultListModel< String > modelJList1 = new DefaultListModel< String >();
	DefaultListModel< String > modelJList2 = new DefaultListModel< String >();

	JList<String> listNameTeam1 = new JList<String>(modelJList1);
	JList<String> listNameTeam2 = new JList<String>(modelJList2);

	GridLayout layout = new GridLayout(3, 3);
	ImagePanel panel;

	public Lobby()
	{
		super();
		initLayout();

		listNameTeam1.addListSelectionListener(this);
		listNameTeam2.addListSelectionListener(this);
		listNameTeam1.setFocusable(false);
		listNameTeam2.setFocusable(false);
	}

	private void initLayout()
	{

		panel = new ImagePanel(new ImageIcon("images/angry-birds.jpg").getImage());

		//Tests
		/*Client.getInstance().addPlayerToRandomTeam( new Player(0, "LOLOL") );
		Client.getInstance().addPlayerToRandomTeam( new Player(1, "LOLOLYYY") );
		Client.getInstance().addPlayerToRandomTeam( new Player(2, "LOLOLYY123123Y") );
		Client.getInstance().addPlayerToRandomTeam( new Player(3, "LOLO123123LYYY") );*/
		//addPlayer qui throw une exception 

		panel.setLayout(this.layout);


		JLabel team1 = new JLabel(new ImageIcon("images/Team1.png"));
		team1.setHorizontalAlignment(JLabel.CENTER);
		panel.add( team1 );

		panel.add( new JLabel() ); //dummy label

		JLabel team2 = new JLabel(new ImageIcon("images/Team2.png"));
		team1.setHorizontalAlignment(JLabel.CENTER);
		panel.add(team2);

		//team1Title.setHorizontalAlignment(JLabel.CENTER);
		//team2Title.setHorizontalAlignment(JLabel.CENTER);
		//this.add( this.team2Title );

		//Following test JList (ARWRARWA)
		/*DefaultListModel< String > model = new DefaultListModel< String >();
		model.addElement("CRISS");
		JList< String > bob = new JList< String > (model);
		this.add( bob );*/

		//this.add( this.team2Title );

		//Border
		Border raisedbevel = BorderFactory.createRaisedBevelBorder();
		Border loweredbevel = BorderFactory.createLoweredBevelBorder();
		Border compound = BorderFactory.createCompoundBorder(raisedbevel, loweredbevel);
		listNameTeam1.setBorder(compound);

		//Border
		//Border raisedbevel2 = BorderFactory.createRaisedBevelBorder();
		//Border loweredbevel2 = BorderFactory.createLoweredBevelBorder();
		//Border compound2 = BorderFactory.createCompoundBorder(raisedbevel, loweredbevel);
		listNameTeam2.setBorder(compound);



		panel.add( listNameTeam1, BorderLayout.CENTER );
		panel.add( new JLabel() ); //dummy label
		panel.add( listNameTeam2, BorderLayout.CENTER );

		//Add buttons
		addButton();
		reloadPlayerLists();
		this.invalidate();
		//this.repaint();
		this.add(panel);
		this.pack();
	}

	private void addButton()
	{
		//READY
		Image imgReady = new ImageIcon("images/READY.png").getImage();
		JButton readyBtn = new JButton(new ImageIcon("images/READY.png"));
		readyBtn.setActionCommand("READY");
		readyBtn.addActionListener(this);
		readyBtn.setLocation(GuiFrame.WIDTH/2-imgReady.getWidth(null)/2,  
				GuiFrame.HEIGHT/2-imgReady.getHeight(null)/2+100);
		readyBtn.setSize(imgReady.getWidth(null), imgReady.getHeight(null));

		//Team1
		Image imgTeam1 = new ImageIcon("images/JOIN.png").getImage();
		JButton team1Btn = new JButton(new ImageIcon("images/JOIN.png"));
		team1Btn.setActionCommand("JOINTEAM1");
		team1Btn.addActionListener(this);
		team1Btn.setLocation(GuiFrame.WIDTH/2-imgTeam1.getWidth(null)/2,  
				GuiFrame.HEIGHT/2-imgTeam1.getHeight(null)/2+100);
		team1Btn.setSize(imgTeam1.getWidth(null), imgTeam1.getHeight(null));

		//Team2
		Image imgTeam2 = new ImageIcon("images/JOIN.png").getImage();
		JButton team2Btn = new JButton(new ImageIcon("images/JOIN.png"));
		team2Btn.setActionCommand("JOINTEAM2");
		team2Btn.addActionListener(this);
		team2Btn.setLocation(GuiFrame.WIDTH/2-imgTeam2.getWidth(null)/2,  
				GuiFrame.HEIGHT/2-imgTeam2.getHeight(null)/2+100);
		team2Btn.setSize(imgTeam2.getWidth(null), imgTeam2.getHeight(null));

		//GOTTA ADD THEM ALL
		panel.add( team1Btn );
		panel.add( readyBtn );
		panel.add( team2Btn );

		this.invalidate();
	}

	public void reloadPlayerLists()
	{
		ArrayList< Team > teams = Client.getInstance().getTeamList();
		int teamsSize = teams.size();

		if ( teamsSize > 2 )
		{
			System.out.println("TEAM SIZE > 2 TBK");
			return;
		}

		int[] selectedIndices = new int[ 2 ];

		for ( int i = 0; i < selectedIndices.length; ++i )
		{
			selectedIndices[ i ] = -1;
		}
		int teamSize = teams.get( 0 ).getPlayers().size();
		modelJList1.clear();
		for ( int i = 0; i < teamSize; ++i )
		{
			modelJList1.addElement(teams.get( 0 ).getPlayers().get( i ).getName());
			if ( teams.get( 0 ).getPlayers().get( i ).isReady() )
			{
				selectedIndices[ i ] = i;
			}
		}
		listNameTeam1.setSelectedIndices( selectedIndices );

		for ( int i = 0; i < selectedIndices.length; ++i )
		{
			selectedIndices[ i ] = -1;
		}
		teamSize = teams.get( 1 ).getPlayers().size();
		modelJList2.clear();
		for ( int i = 0; i < teamSize; ++i )
		{
			modelJList2.addElement(teams.get( 1 ).getPlayers().get( i ).getName());
			if ( teams.get( 1 ).getPlayers().get( i ).isReady() )
			{
				selectedIndices[ i ] = i;
			}
		}
		listNameTeam2.setSelectedIndices( selectedIndices );
		//this.pack();

		return;
	}

	public void onPlayerReadyReceive(Player player)
	{
		reloadPlayerLists();
	}

	public GuiFrame getNextFrame() {
		return new Game();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if ( e.getActionCommand().startsWith("JOINTEAM"))
		{
			Client.getInstance().getNetworkHandlerClient().sendEvent(
					new JoinTeamEvent(
							Client.getInstance().getClientName(), 
							e.getActionCommand().endsWith("1") ? 0 : 1)); //For user, 1-->0, 2-->1

		}
		else if ( e.getActionCommand().equals("READY"))
		{
			//ouf; le joueur qui envoi son Id lol
			Client.getInstance().getNetworkHandlerClient().sendEvent(
					new org.bitducks.angrypidge.network.event.serverevent.ReadyEvent(Client.getInstance().getPlayerId()));
		}

	}

	@Override
	public void actionPerformed(MouseEvent e) {
		//Unimportant
	}

	private boolean canReceiveSelectionEvent = true;
	@Override
	public void valueChanged(ListSelectionEvent e) {

		if ( canReceiveSelectionEvent && e.getValueIsAdjusting() )
		{
			canReceiveSelectionEvent = false;
			reloadPlayerLists();
			canReceiveSelectionEvent = true;
		}
	}

}
