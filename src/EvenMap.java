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
	 **/
	public EvenMap()
	{
		super();

		typeCount = 5;
		typeCounter = new int[typeCount];
		for(int i = 0; i < typeCount; i++)
		{
			typeCounter[i] = 0;
		}
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
		super(inHeight, inWidth);

		typeCount = inTypeCount;
		typeCounter = new int[typeCount];
		for(int i = 0; i < typeCount; i++)
		{
			typeCounter[i] = 0;
		}
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
	 ** @details Sets the typeIDs of each Tile a maximum number of times equal to 1/5th of the tileCount. Sets last tile as desert.
	 ** @return void Returns nothing.
	 **/
	void makeRandom()
	{
		int tileCount = 0;
		tileCount = getTileCount();
		tileCount--; //For desert
		double maxTypeCount = (double)tileCount/(double)typeCount;
		
		int confirmCount = 0;
		boolean isFull = false;
		while(confirmCount < tileCount && !isFull)
		{
			isFull = true;
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
			if(isFull)
				System.out.println("Error: Failed to set up EvenMap (during #" + confirmCount + ").");
		}

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
}