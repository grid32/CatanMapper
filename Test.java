import java.io.*;
import java.util.Random;

public class Test
{
	public static void main(String[] args)
	{
		if(args.length < 2)
		{
			System.out.println("Incorrect parameters.\n\tCorrect: java Test <height> <width>.");
			return;
		}
		
		int height, width;
		try
		{
			height = Integer.parseInt(args[0]);
			width = Integer.parseInt(args[1]);
		}
		catch(Exception e)
		{
			System.out.println("Invalid parameters.\n\tOnly accept integers.");
			return;
		}

		Map newMap = new Map(height, width);
		
		int i;
		for(i = 0; i < newMap.getHeight(); i++)
		{
			int j;
			for(j = 0; j < newMap.getRow(i).getLength(); j++)
			{
				Random rand = new Random();
				int  n = rand.nextInt(5) + 1;

				newMap.getRow(i).getHex(j).setTypeID(n);
			}
		}
		
		System.out.println(newMap.tostring());
	}
}
