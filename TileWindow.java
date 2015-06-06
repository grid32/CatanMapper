import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class TileWindow extends JFrame
{
	Map map;

	public TileWindow(Map inMap)
	{
		map = inMap;
		BufferedImage surface = new BufferedImage(100 + (100*inMap.getWidth()),150 + (83*inMap.getHeight()),BufferedImage.TYPE_INT_RGB);
		JLabel view = new JLabel(new ImageIcon(surface));
		final Graphics g = surface.getGraphics();
		g.setColor(Color.BLUE);
		g.fillRect(0,0,100 + (100*inMap.getWidth()),150 + (83*inMap.getHeight()));
		g.setColor(Color.BLACK);
		g.dispose();

		int x, y;
		for(y = 0; y < inMap.getHeight(); y++)
		{
			TileRow row = inMap.getRow(y);
			for(x = 0; x < row.getLength(); x++)
			{
					makeHex(surface, x, y);
					view.repaint();
			}
		}

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setContentPane(view);
		this.pack();
		this.setLocationByPlatform(true);
		this.setVisible(true);
	}

	public void makeHex(BufferedImage inSurface, int inX, int inY)
	{
		Graphics g = inSurface.getGraphics();		

		int diff = map.getWidth() - map.getRow(inY).getLength();
		diff /= 2;
		diff--;
		inX += diff;

		if(inY%2 == 0)
		{
			if((map.getHeight() + 1)%4 == 0)
				inX += 1;
			g.drawLine(100 + (100*inX), 100 + (83*inY), 150 + (100*inX), 66 + (83*inY)); //Top left
			g.drawLine(150 + (100*inX), 66 + (83*inY), 200 + (100*inX), 100 + (83*inY)); //Top right
			g.drawLine(200 + (100*inX), 100 + (83*inY), 200 + (100*inX), 150 + (83*inY)); //Right
			g.drawLine(100 + (100*inX), 100 + (83*inY), 100 + (100*inX), 150 + (83*inY)); //Left
			g.drawLine(100 + (100*inX), 150 + (83*inY), 150 + (100*inX), 183 + (83*inY)); //Bottom left
			g.drawLine(150 + (100*inX), 183 + (83*inY), 200 + (100*inX), 150 + (83*inY)); //Bottom right
		}
		else
		{
			g.drawLine(150 + (100*inX), 100 + (83*inY), 200 + (100*inX), 66 + (83*inY)); //Top left
			g.drawLine(200 + (100*inX), 66 + (83*inY), 250 + (100*inX), 100 + (83*inY)); //Top right
			g.drawLine(250 + (100*inX), 100 + (83*inY), 250 + (100*inX), 150 + (83*inY)); //Right
			g.drawLine(150 + (100*inX), 100 + (83*inY), 150 + (100*inX), 150 + (83*inY)); //Left
			g.drawLine(150 + (100*inX), 150 + (83*inY), 200 + (100*inX), 183 + (83*inY)); //Bottom left
			g.drawLine(200 + (100*inX), 183 + (83*inY), 250 + (100*inX), 150 + (83*inY)); //Bottom right
		}

		g.dispose();
	}
}