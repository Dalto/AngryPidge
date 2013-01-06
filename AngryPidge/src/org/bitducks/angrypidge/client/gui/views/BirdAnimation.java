package org.bitducks.angrypidge.client.gui.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import org.bitducks.angrypidge.common.Bird;
import org.bitducks.angrypidge.common.Bird.BirdType;

public class BirdAnimation implements ActionListener {

	private Bird bird;
	private JPanel panel;

	private static final int MAX_SCREEN_X = 592;

	private static final int STARTING_Y = 400;
	public static final int HEIGHT_BEFORE_DELETE = 300;

	private static final float MIN_SIZE_HEIGHT = 5f;

	private static final float SPEED_RESIZE = 0.25f;
	private static final int SPEED_X = 4;

	private static final int MAX_PULL_HEIGHT = 12;
	private static final int MAX_MARK_HEIGHT = 10;

	private static final float MOD_BOUNCE_DOWN = 0.15f;

	private static final float GRAVITY = 0.30f;

	private int direction = 1;
	private float ySpeed = 0;
	private BirdAnimationState state = BirdAnimationState.UP;
	private float birdHeight = Bird.SIZE_Y;
	private float birdWidth = Bird.SIZE_X;
	
	private double gravity = GRAVITY;

	public enum BirdAnimationState {
		UP, DOWN
	};

	public BirdAnimation(Bird disk, JPanel panel) {
		this.bird = disk;
		this.panel = panel;

		if (disk.getType() == BirdType.MARK) {
			ySpeed = MAX_PULL_HEIGHT;
			direction = -1;
		} else if (disk.getType() == BirdType.PULL) {
			ySpeed = MAX_MARK_HEIGHT;
			direction = 1;
		}
		
		double min = GRAVITY - 0.05f;
		double max = GRAVITY + 0.05f;
		gravity = min + (Math.random() * ((max - min)));
		
		disk.setPosition(direction == 1 ? 0 : MAX_SCREEN_X - Bird.SIZE_X, STARTING_Y);
	}

	public BirdAnimationState getAnimationState() {
		return state;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		float moveY = -ySpeed;
		if (state == BirdAnimationState.UP) {
			bird.setPosition(bird.getX() + direction * SPEED_X, bird.getY()
					+ (int) moveY);
			if (ySpeed < 0) {
				state = BirdAnimationState.DOWN;
			}
		} else if (state == BirdAnimationState.DOWN) {
			moveY *= MOD_BOUNCE_DOWN;
			bird.setPosition(bird.getX() + direction * SPEED_X, bird.getY()
					+ (int) moveY);
		}
		
		bird.setSize((int)birdWidth , (int)birdHeight);

		
				
		if (birdHeight > MIN_SIZE_HEIGHT)
		{
			birdHeight -= SPEED_RESIZE;
			birdWidth = birdWidth * birdHeight / (birdHeight + SPEED_RESIZE);
		}
		
		
		ySpeed -= gravity;
		
		panel.repaint();
	}

}
