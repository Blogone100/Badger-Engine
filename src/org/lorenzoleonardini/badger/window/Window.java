package org.lorenzoleonardini.badger.window;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import org.lorenzoleonardini.badger.Camera;
import org.lorenzoleonardini.badger.renderer.Screen;

/**
 * The Window class manage the window
 * it
 * 
 * @author Lorenzo Leonardini
 *
 */
public class Window extends Canvas
{
	private static final long serialVersionUID = 1L;

	public final int WIDTH, HEIGHT;

	private JFrame frame;

	private BufferedImage image;
	private int[] pixels;

	private Screen screen;

	/**
	 * Initialize the window
	 * @param width
	 *            the window width
	 * @param height
	 *            the window height
	 * @param scale
	 *            the scale
	 * @param title
	 *            the window title
	 */
	public Window(int width, int height, int scale, String title)
	{
		WIDTH = width;
		HEIGHT = height;
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

		screen = new Screen(width, height, pixels, this);
		
		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size);

		setFocusable(true);

		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle(title);
		frame.add(this);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	/**
	 * Render to the screen
	 */
	public void render(Camera camera)
	{
		BufferStrategy bs = getBufferStrategy();
		if (bs == null)
		{
			createBufferStrategy(3);
			return;
		}

		screen.render(camera);

		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
	}
}
