import java.io.*;

/** @brief Main function used to test a SpreadEvenMap.
 **/
public class Test
{
	public static void main(String[] args)
	{
		//Check params are input
		if(args.length < 2)
		{
			System.out.println("Incorrect parameters.\n\tCorrect: java Test <height> <width>.");
			return;
		}
		
		//Validate params
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

		//Create map
		int[] tempTypes = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		Map newMap = new SpreadEvenMap(height, width, tempTypes);

		//Display map in terminal
		System.out.println(newMap.tostring());

		//GUI
		TileWindow tw = new TileWindow(newMap);
	}
}
