import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.TexturePaint;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;

import java.io.File;

public class TileWindow extends JFrame
{
	Map map;
	BufferedImage[] bi;

	public TileWindow(Map inMap)
	{
		map = inMap;

		//Set up textures
		bi = new BufferedImage[6];
		String[] texFiles = {"Ocean.png", "Wood.png", "Ore.png", "Clay.png", "Sheep.png", "Wheat.png"};
		for(int i = 0; i < bi.length; i++)
		{
			String imageURL = "res/" + texFiles[i];
			try
			{
				bi[i] = ImageIO.read(new File(imageURL));
			}
			catch(Exception e)
			{
				System.out.println("Error: Tile texture file '" + imageURL + "' not found");
			}
		}
		

		BufferedImage surface = new BufferedImage(100 + (100*inMap.getWidth()),150 + (83*inMap.getHeight()),BufferedImage.TYPE_INT_RGB);
		JLabel view = new JLabel(new ImageIcon(surface));
		final Graphics g = surface.getGraphics();
		g.setColor(Color.BLUE);
		g.fillRect(0,0,100 + (100*inMap.getWidth()),150 + (83*inMap.getHeight()));
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
		Graphics2D g = (Graphics2D)inSurface.getGraphics();
		int typeID = map.getRow(inY).getHex(inX).getTypeID();
		String rarity = "" + map.getRow(inY).getHex(inX).getRarity();

		int diff = map.getWidth() - map.getRow(inY).getLength();
		diff /= 2;
		diff--;
		inX += diff;

		int x[] = new int[6];
		int y[] = new int[6];

		if(inY%2 == 0)
		{
			if((map.getHeight() + 1)%4 == 0)
				inX += 1;
			x[0] = 100 + (100*inX);			y[0] = 100 + (83*inY);
			x[1] = 150 + (100*inX);			y[1] = 66 + (83*inY);
			x[2] = 200 + (100*inX);			y[2] = 100 + (83*inY);
			x[3] = 200 + (100*inX);			y[3] = 150 + (83*inY);
			x[4] = 150 + (100*inX);			y[4] = 183 + (83*inY);
			x[5] = 100 + (100*inX);			y[5] = 150 + (83*inY);
		}
		else
		{
			x[0] = 150 + (100*inX);			y[0] = 100 + (83*inY);
			x[1] = 200 + (100*inX);			y[1] = 66 + (83*inY);
			x[2] = 250 + (100*inX);			y[2] = 100 + (83*inY);
			x[3] = 250 + (100*inX);			y[3] = 150 + (83*inY);
			x[4] = 200 + (100*inX);			y[4] = 183 + (83*inY);
			x[5] = 150 + (100*inX);			y[5] = 150 + (83*inY);
		}		
		
		Polygon p = new Polygon(x, y, 6);
		Rectangle r = p.getBounds();
		BufferedImage tmp = new BufferedImage(r.width+2,r.height+2,BufferedImage.TYPE_INT_ARGB);
		g.setClip(p);
		if(bi[typeID] != null)
		{
			TexturePaint tex = new TexturePaint(bi[typeID], r);
			g.setPaint(tex);
			g.fill(p);
		}
		g.setColor(Color.BLACK);
		g.drawPolygon(x, y, 6);

		int centerX, centerY;
		centerX = (x[2] + x[0]) / 2;
		centerY = (y[4] + y[1]) / 2;
		g.setColor(new Color(245, 237, 184));
		g.fillOval(centerX-10, centerY-10, 20, 20);
		g.setColor(Color.BLACK);
		g.drawOval(centerX-10, centerY-10, 20, 20);

		g.drawString(rarity, centerX-5, centerY+5);

		g.dispose();
	}
}