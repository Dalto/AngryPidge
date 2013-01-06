package org.bitducks.angrypidge.client.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import org.bitducks.angrypidge.client.Client;

class ImagePanel extends JPanel implements ActionListener, MouseListener {

	private static final long serialVersionUID = -875673643932408627L;
	private Image img;

	public ImagePanel(String img) {
		this(new ImageIcon(img).getImage());
	}

	public ImagePanel(Image img) {
		this.img = img;
		//Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
		Dimension size = new Dimension(800, 600);
		this.addMouseListener(this);
		this.setPreferredSize(size);
		this.setMinimumSize(size);
		this.setMaximumSize(size);
		this.setSize(size);
		this.setLayout(null);
	}

	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, null);
	}

	public void actionPerformed(ActionEvent e) {
		Client.getInstance().getGuiManager().getFrame().actionPerformed(e);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent e) {
		Client.getInstance().getGuiManager().getFrame().actionPerformed(e);

	}

}
