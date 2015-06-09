import java.util.Random;

/** @brief A vanilla Map, with even types.
 ** @details This is created using a constraint on the number of each type, based on the total number of tiles. Also places desert tile in last space.
 ** @field typeCounter An array of counters, one for each possible typeID.
 ** @field typeCount The number of different types possible in this Map.
 **/
public class EvenMap extends Map
{
	int[] typeCounter;
	int typeCount;

	/** @brief Default constructor for an EvenMap.
	 ** @details This generates a map of default size, 5x5. Initialises all tiles, somewhat evenly, as one of the 5 default resources.
	 ** @param inTypeCount The number of possible types to be used in this EvenMap.
	 **/
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

	/** @brief Constructor for a EvenMap.
	 ** @details This generates an EvenMap of custom size. Initialises all tiles, somewhat evenly, as one of the 5 default resources.
	 ** @param inHeight The height of the new Map.
	 ** @param inWidth The width at the new Map's widest point.
	 ** @param inTypeCount The number of possible types to be used in this EvenMap.
	 **/
	public EvenMap(int inHeight, int inWidth, int inTypeCount)
	{
		if(inHeight%2 == 0)
		{
			System.out.println("Error: Even height is not valid. Incrementing.");
			inHeight++;
		}
		if(inWidth - (inHeight - 1)/2 < 1)
		{
			System.out.println("Error: Width is not sufficient for height. Incrementing.");
			while(inWidth - (inHeight - 1)/2 < 1)
			{
				inWidth++;
			}
		}
		height = inHeight;
		maxWidth = inWidth;
		rows = new TileRow[height];
		makeRows();

		typeCount = inTypeCount;
		typeCounter = new int[typeCount];
		makeRandom();
	}

	//Getters
	/** @brief Gets the typeCount field.
	 ** @return int Returns the typeCount field.
	 **/
	int getTypeCount() {return typeCount;}
	
	/** @brief Gets the typeCounter at in index.
	 ** @param i The index of typeCounter to retrieve.
	 ** @return int Returns the typeCounter at the requested index.
	 **/
	int getTypeCounter(int i) {return typeCounter[i];}
	
	/** @brief Gets the list of all typeCounters.
	 ** @return int[] Returns the array of typeCounters.
	 **/
	int[] getTypeCounters() {return typeCounter;}

	/** @brief Gets the number of Tiles within this EvenMap.
	 ** @return int Returns the number of Tiles within this EvenMap.
	 **/
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

	/** @brief Prepares for randomising the EvenMap.
	 ** @details This involves setting up the maxTypeCount, and initialising the confirmCount, then begins randomRecurse(). Once this is complete, sets the last Tile to a desert.
	 ** @return void Returns nothing.
	 **/
	void makeRandom()
	{
		int tileCount = 0;
		tileCount = getTileCount();
		tileCount--; //For desert
		double maxTypeCount = (double)tileCount/(double)typeCount;
		
		int confirmCount = 0;
		//Until confirmCount == tileCount;
		randomRecurse(confirmCount, maxTypeCount);

		//Fill last Tile with Desert.
		for(int y = 0; y < rows.length; y++)
		{
			for(int x = 0; x < rows[y].length; x++)
			{
				if(rows[y].getHex(x).getTypeID() == 0)
					rows[y].getHex(x).setTypeID(6);
			}
		}
	}

	/** @brief Sets the EvenMap's typeIDs somewhat evenly.
	 ** @details Sets the typeIDs of each Tile a maximum number of times equal to 1/5th of the tileCount.
	 ** @param confirmCount The counter of how many nodes are correctly set.
	 ** @param maxTypeCount The maximum number of occurences of a single type.
	 ** @return void Returns nothing.
	 **/
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