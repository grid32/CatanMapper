import java.io.*;

public class TileTest
{
	public static void main(String[] args)
	{
		int i;
		for(i = 0; i < 6; i++)
		{
			Tile newTile = new Tile();
			newTile.setTypeID(i);
			System.out.println(newTile.tostring());
		}
	}
}
