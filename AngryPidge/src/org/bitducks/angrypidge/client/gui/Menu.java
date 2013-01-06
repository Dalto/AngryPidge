package org.bitducks.angrypidge.client.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.InetAddress;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.bitducks.angrypidge.client.Client;
import org.bitducks.angrypidge.network.event.serverevent.JoinTeamEvent;
import org.bitducks.angrypidge.server.Server;

public class Menu extends GuiFrame implements ActionListener {

	private static final long serialVersionUID = 3784698234728633213L;
	private JLabel errorMessageLabel = new JLabel();

	public Menu() {
		super();
		init();
	}

	private void init() {

		// JOIN
		Image imgJoin = new ImageIcon("images/JOIN.png").getImage();
		JButton joinBtn = new JButton(new ImageIcon("images/JOIN.png"));
		joinBtn.setActionCommand("JOIN");
		joinBtn.addActionListener(this);
		joinBtn.setLocation(GuiFrame.WIDTH/2-imgJoin.getWidth(null)/2,  GuiFrame.HEIGHT/2-imgJoin.getHeight(null)/2);
		joinBtn.setSize(imgJoin.getWidth(null)+15, imgJoin.getHeight(null)+15);

		// HOST
		Image imgHost = new ImageIcon("images/HOST.png").getImage();
		JButton hostBtn = new JButton(new ImageIcon("images/HOST.png"));
		hostBtn.setActionCommand("HOST");
		hostBtn.addActionListener(this);
		hostBtn.setLocation(GuiFrame.WIDTH/2-imgHost.getWidth(null)/2,  GuiFrame.HEIGHT/2-imgHost.getHeight(null)/2+100);
		hostBtn.setSize(imgHost.getWidth(null)+15, imgHost.getHeight(null)+15);

		ImagePanel panel = new ImagePanel(new ImageIcon("images/angry-birds.jpg").getImage());
		ImagePanel title = new ImagePanel(new ImageIcon("images/title.png").getImage());
		
		/*Experimental*/
		errorMessageLabel.setSize(600, 40);
		errorMessageLabel.setFont(new Font("Serif", Font.BOLD, 24));
		errorMessageLabel.setForeground(Color.RED);
		errorMessageLabel.setLocation(GuiFrame.WIDTH/6,  GuiFrame.HEIGHT/4);
		
		this.add(errorMessageLabel);
		this.add(joinBtn);
		this.add(hostBtn);

		this.getContentPane().add(title);
		this.getContentPane().add(panel);
	}

	public void actionPerformed(ActionEvent e) {
		String name = JOptionPane.showInputDialog(null,
				"Please enter your name",
				Client.DEFAULT_NAME);
		
		if ( name == null )
			return;
		
		if (e.getActionCommand().equals("HOST"))
		{
			Client.getInstance().setClientName(name);
			
			try {
				InetAddress ip = InetAddress.getLocalHost();
				System.out.println("Starting server on " + ip.getHostAddress());

				Server.getInstance().start();
				
				connectAndJoinRandomTeam( "127.0.0.1" );
			}
			catch (IOException e1) {
				errorMessageLabel.setText("Error: " + e1.getMessage());
				e1.printStackTrace();
			}
		}
		else if (e.getActionCommand().equals("JOIN"))
		{
			
			String IP = JOptionPane.showInputDialog(null,
					"Enter server's IP adress",
					"127.0.0.1");
			
			if (IP != null && !IP.equals(""))
			{
				Client.getInstance().setClientName(name);
				connectAndJoinRandomTeam(IP);
				/*try {
					Client.getInstance().getNetworkHandlerClient().connect(IP, Client.PORT);
					
					//Client.getInstance().getGuiManager().getNextGuiFrame();
					//getNextGuiFrame should be called only upon successfully joining a team(for this, 
					//lets use a name to recognize ourself !)
				} catch (IOException e2) {
					e2.printStackTrace();
				}*/
			}
		}

	}
	
	private void connectAndJoinRandomTeam( String ipAdress )
	{
		try {
			errorMessageLabel.setText("");
			
			Client.getInstance().getNetworkHandlerClient().connect(ipAdress, Client.PORT);
	
			Client.getInstance().getNetworkHandlerClient().sendEvent(
					new JoinTeamEvent(Client.getInstance().getClientName(), -1));
			
		} catch (IOException e1) {
			errorMessageLabel.setText("Error: Could not connect to "+ ipAdress);
			e1.printStackTrace();
		}
	}

	public GuiFrame getNextFrame() {
		return new Lobby();
		//return new Game();		//To test...
	}

	@Override
	public void actionPerformed(MouseEvent e) {


	}
}
