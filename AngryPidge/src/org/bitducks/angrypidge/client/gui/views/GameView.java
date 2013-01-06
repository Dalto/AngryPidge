package org.bitducks.angrypidge.client.gui.views;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.bitducks.angrypidge.client.Client;
import org.bitducks.angrypidge.client.gui.views.BirdAnimation.BirdAnimationState;
import org.bitducks.angrypidge.common.Bird;
import org.bitducks.angrypidge.common.Bird.BirdType;
import org.bitducks.angrypidge.network.event.serverevent.ShootEvent;

public class GameView extends JPanel implements MouseListener {

	private static final long serialVersionUID = -7515822316945037729L;

	private Image background;
	private Image disk;

	private Bird birdPull = null;
	private Bird birdMark = null;

	private Timer birdPullTimer = null;
	private Timer birdMarkTimer = null;

	private BirdAnimation birdPullAnimation = null;
	private BirdAnimation birdMarkAnimation = null;

	private boolean isYourTurn = false;

	public GameView() {
		super(true);

		background = new ImageIcon("images/background_forest.jpg").getImage();
		disk = new ImageIcon("images/clay-disk.png").getImage();

		this.addMouseListener(this);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (background != null)
			g.drawImage(background, 0, 0, getWidth(), getHeight(), this);

		if (birdPull != null) {
			drawBird(g, birdPull);
			if (birdPullAnimation.getAnimationState() == BirdAnimationState.DOWN
					&& BirdAnimation.HEIGHT_BEFORE_DELETE < birdPull.getY()) {
				birdPull.setVisible(false);

				if (!stillHaveBirdOnScreen()) {
					checkIfPlayerShootedBird();
					cleanUpScreen();
				}
			}
		}

		if (birdMark != null) {
			drawBird(g, birdMark);

			if (birdMarkAnimation.getAnimationState() == BirdAnimationState.DOWN
					&& BirdAnimation.HEIGHT_BEFORE_DELETE < birdMark.getY()) {
				birdMark.setVisible(false);

				if (!stillHaveBirdOnScreen()) {
					checkIfPlayerShootedBird();
					cleanUpScreen();
				}
			}
		}
	}

	private void drawBird(Graphics g, Bird bird) {
		if (bird.isVisible()) {
			g.drawImage(disk, bird.getX(), bird.getY(), bird.getWidth(),
					bird.getHeight(), this);
		}
	}

	private boolean stillHaveBirdOnScreen() {
		if (birdPull != null && birdPull.isVisible()) {
			return true;
		}

		if (birdMark != null && birdMark.isVisible()) {
			return true;
		}

		return false;
	}

	private void checkIfPlayerShootedBird() {
		int nbPigeonHit = 0;

		if (birdPull != null && birdMark != null) {
			// Both
			if (birdPull.nbPigeonHit() > 0 && birdMark.nbPigeonHit() > 0) {
				nbPigeonHit = 2;
			}
		} else if (birdPull != null) {
			if (birdPull.nbPigeonHit() > 0) {
				nbPigeonHit = 1;
			}
		} else if (birdMark != null) {
			if (birdMark.nbPigeonHit() > 0) {
				nbPigeonHit = 1;
			}
		}

		Client.getInstance()
				.getNetworkHandlerClient()
				.sendEvent(
						new ShootEvent(Client.getInstance().getPlayerId(),
								nbPigeonHit));
	}

	private void cleanUpScreen() {

		birdPull = null;
		birdMark = null;

		if (birdPullTimer != null && birdPullTimer.isRunning()) {
			birdPullTimer.stop();
			birdPullAnimation = null;
		}

		if (birdMarkTimer != null && birdMarkTimer.isRunning()) {
			birdMarkTimer.stop();
			birdMarkAnimation = null;
		}

		isYourTurn = false;

		this.repaint();
	}

	public void throwBoth() {
		throwLowerBird();
		throwUpperBird();
	}

	public void throwUpperBird() {
		birdMark = new Bird(BirdType.MARK);

		birdMarkAnimation = new BirdAnimation(birdMark, this);
		birdMarkTimer = new Timer(30, birdMarkAnimation);

		birdMarkTimer.start();

		isYourTurn = true;
	}

	public void throwLowerBird() {
		birdPull = new Bird(BirdType.PULL);

		birdPullAnimation = new BirdAnimation(birdPull, this);
		birdPullTimer = new Timer(30, birdPullAnimation);

		birdPullTimer.start();

		isYourTurn = true;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage("images/cursor.gif");
		Cursor cursor = toolkit.createCustomCursor(image , new Point(getX() + 18, getY() + 18), "img");
		setCursor(cursor);

	}

	@Override
	public void mouseExited(MouseEvent e) {
		Cursor cursor = Cursor.getDefaultCursor();
		setCursor(cursor);
	}

	@Override
	public void mousePressed(MouseEvent e) {

		if (isYourTurn) {
			setBirdHit((int) e.getPoint().getX(), (int) e.getPoint().getY());
			checkIfPlayerShootedBird();
			cleanUpScreen();
		} else {
			System.out.println("It's not your turn");
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	private void setBirdHit(int x, int y) {

		if (birdPull != null) {
			if (birdPull.getX() < x
					&& birdPull.getX() + birdPull.getWidth() > x
					&& birdPull.getY() < y
					&& birdPull.getY() + birdPull.getHeight() > y) {
				birdPull.hasBeenHit(1);
			}
		}

		if (birdMark != null) {
			if (birdMark.getX() < x
					&& birdMark.getX() + birdMark.getWidth() > x
					&& birdMark.getY() < y
					&& birdMark.getY() + birdMark.getHeight() > y) {
				birdMark.hasBeenHit(1);
			}
		}
	}
}
