import java.io.*;
import java.util.Random;

public class Test
{
	public static void main(String[] args)
	{
		Map newMap = new Map(15, 12);
		
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
