import java.util.Random;

/** @brief 
 ** @details 
 **/
public class EvenMap extends Map
{
	int typeCounter[];
	int typeCount;

	public EvenMap(int inTypeCount)
	{
		height = 5;
		maxWidth = 5;
		rows = new TileRow[height];
		makeRows();

		typeCount = inTypeCount;
		typeCounter = new int[typeCount];
		makeRandom();
	}

	public EvenMap(int inHeight, int inWidth, int inTypeCount)
	{
		if(inHeight%2 == 0)
		{
			System.out.println("Even height is not valid. Incrementing.");
			inHeight++;
		}
		height = inHeight;
		maxWidth = inWidth;
		rows = new TileRow[height];
		makeRows();

		typeCount = inTypeCount;
		typeCounter = new int[typeCount];
		makeRandom();
	}

	int getTypeCount() {return typeCount;}
	int getTypeCounter(int i) {return typeCounter[i];}
	int[] getTypeCounters() {return typeCounter;}

	int getTileCount()
	{
		int count = 0;
		for(int y = 0; y < rows.length; y++)
		{
			for(int x = 0; x < rows[y].length; x++)
			{
				count++;
			}
		}
		return count;
	}

	void makeRandom()
	{
		int tileCount = 0;
		tileCount = getTileCount();
		tileCount--; //For desert
		double maxTypeCount = (double)tileCount/(double)typeCount;
		
		int confirmCount = 0;
		//Until confirmCount == tileCount;
		randomRecurse(confirmCount, maxTypeCount);

		//find 0 in map
			//set to 6.
	}

	void randomRecurse(int confirmCount, double maxTypeCount)
	{
		//If typeCounter not full
		boolean isFull = true;
		for(int i = 0; i < typeCount; i++)
		{
			if(typeCounter[i] < maxTypeCount)
				isFull = false;
		}
		if(!isFull)
		{
			Random rand = new Random();
			int  randomType = rand.nextInt(typeCount); //Generate randomType
			while(typeCounter[randomType] >= maxTypeCount) //While typeCounter[randomType] full
			{
				randomType = rand.nextInt(typeCount); //Generate randomType
			}
			int randomY = rand.nextInt(height);
			int randomX = rand.nextInt(rows[randomY].length);
			while(rows[randomY].getHex(randomX).getTypeID() != 0)
			{
				randomY = rand.nextInt(height);
				randomX = rand.nextInt(rows[randomY].length);
			}
			rows[randomY].getHex(randomX).setTypeID(randomType + 1); //Place randomType at randomX randomY
			typeCounter[randomType]++; //Increment typeCounter[random]
			confirmCount++; //Increment confirmCount
		}

		if(confirmCount == (getTileCount() - 1))
			return;
		else if(isFull)
			System.out.println("Error: Failed to set up EvenMap.");
		else
			randomRecurse(confirmCount, maxTypeCount);
	}
}