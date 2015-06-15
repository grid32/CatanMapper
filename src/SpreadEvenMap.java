import java.util.Random;

/** @brief A Map where Tiles with like types are not neighboured.
 ** @details This can be used for vanilla, seafarers, and their 6 player expansions.
 ** @field types The array containing the vanilla Tile typeIDs.
 **/
public class SpreadEvenMap extends Map
{
	int[] types;

	/** @brief Default constructor for a SpreadEvenMap.
	 ** @details This generates a SpreadEvenMap of a specified size.
	 ** @param inHeight The height of the new SpreadEvenMap.
	 ** @param inWidth The width at the new SpreadEvenMap's widest point.
	 **/
	public SpreadEvenMap(int inHeight, int inWidth)
	{
		super(inHeight, inWidth);

		int[] tempTypes = {1, 2, 3, 4, 5};
		types = tempTypes;
	}

	/** @brief Gets the list of typesIDs in use.
	 ** @return int[] Returns the list of typeIDs.
	 **/
	int[] getTypes()
	{
		return types;
	}

	/** @brief Gets the X/Y coordinates of a Tile.
	 ** @details This is done by iterating through the Map, until a count is reached matching the specified number.
	 ** @param inIndex The count position of the Tile within the Map.
	 ** @return int[] Returns the X and Y coordinate of the Tile contained in an array.
	 **/
	int[] getXY(int inIndex)
	{
		int[] xy = new int[2];
		int count = 0;
		for(int y = 0; y < rows.length; y++)
		{
			for(int x = 0; x < rows[y].length; x++)
			{
				if(count == inIndex)
				{
					xy[0] = x;
					xy[1] = y;
				}
				count++;
			}
		}
		return xy;
	}

	/** @brief Checks the neighbours of a Tile for likeness.
	 ** @details This checks the neighbours of a Tile based on the X/Y coordinate given, and decides how much change there should be of changing the specified Tile's typeID.
	 ** @param inX The X coordinate of the Tile to be compared.
	 ** @param inY The Y coordinate of the Tile to be compared.
	 ** @return int Returns the chance of changing the Tile's typeID.
	 **/
	int checkNeighbours(int inX, int inY)
	{
		int myType = rows[inY].getHex(inX).getTypeID();
		double chance = 75;

		if(inX > 0)
		{
			if(rows[inY].getHex(inX - 1).getTypeID() == myType) //Check <
			{
				chance *= 1.15;
			}
		}

		if(inY > 0)
		{
			if((height+1)/2 >= inY + 1) //Top half
			{
				if(inX < rows[inY - 1].length)
				{
					if(rows[inY - 1].getHex(inX).getTypeID() == myType) //Check ^>
					{
						chance *= 1.15;
					}
				}
				if(inX > 0)
				{
					if(rows[inY - 1].getHex(inX - 1).getTypeID() == myType) //Check <^
					{
						chance *= 1.15;
					}
				}
			}
			else
			{
				if(rows[inY - 1].getHex(inX).getTypeID() == myType) //Check <^
				{
					chance *= 1.15;
				}
				if(rows[inY - 1].getHex(inX + 1).getTypeID() == myType) //Check ^>
				{
					chance *= 1.15;
				}
			}

		}
		
		if(chance == 75)
			chance = 0;
		else
			chance = 101;
		return (int) chance;
	}

	/** @brief Initiates seafarers ocean placement.
	 ** @details This is done by drawing a jagged ocean line down the center of the Map.
	 ** @param oceanCount The number of Tiles to make ocean.
	 ** @return void Returns nothing.
	 **/
	void splitLand(int oceanCount)
	{
		Random rand = new Random();
		int[] xY = new int[2];
		xY[1] = 0;
		xY[0] = (int) rows[xY[1]].length/2;

		int currentCount = 0;
		int i = 0;
		int xMod;
		while(i < rows.length)
		{
			xY[1] = i;

			if(((height + 1) / 2) > i) //Top half
			{
				xMod = rand.nextInt(2);
				while(rows[xY[1]].length - 1 < xY[0] + xMod || xY[0] + xMod < 0)
				{
					xMod = rand.nextInt(2);
				}
			}
			else
			{
				xMod = rand.nextInt(2) - 1;
				while(rows[xY[1]].length - 1 < xY[0] + xMod || xY[0] + xMod < 0)
				{
					xMod = rand.nextInt(2) - 1;
				}
			}
			xY[0] += xMod;
			
			getRow(xY[1]).getHex(xY[0]).setTypeID(0); //Make tile ocean.
			currentCount++;
			i++;
		}
		tileOcean(currentCount, oceanCount); //Call next part(currentCount, oceanCount);
	}

	/** @brief Finalises seafarers ocean placement.
	 ** @details This is done by picking a random Tile, making it ocean, and stepping randomly until complete.
	 ** @param currentCount The number of Tiles already made ocean by splitLand().
	 ** @param oceanCount The number of Tiles to make ocean.
	 ** @return void Returns nothing.
	 **/
	void tileOcean(int currentCount, int oceanCount)
	{
		Random rand = new Random();
		int tile = rand.nextInt(getTileCount()); //Start at random point
		int[] xY = getXY(tile);

		while(currentCount < oceanCount)
		{
			if(getRow(xY[1]).getHex(xY[0]).getTypeID() != 0)
			{
				getRow(xY[1]).getHex(xY[0]).setTypeID(0); //Make tile ocean.
				currentCount++;
			}
			int xMod = rand.nextInt(3)-1;
			int yMod = rand.nextInt(3)-1;
			while(rows.length - 1 < xY[1] + yMod || xY[1] + yMod < 0) //Move in random direction.
			{
				yMod = rand.nextInt(3)-1;
			}
			while(rows[xY[1] + yMod].length - 1 < xY[0] + xMod || xY[0] + xMod < 0)
			{
				xMod = rand.nextInt(3)-1;
			}
			xY[0] += xMod;
			xY[1] += yMod;
		}
	}

	/** @brief Places the desert Tile.
	 ** @return void Returns nothing.
	 **/
	void placeDesert()
	{
		Random rand = new Random();
		int tile = rand.nextInt(getTileCount());
		int[] xY = getXY(tile);
		while(getRow(xY[1]).getHex(xY[0]).getTypeID() < 1 || getRow(xY[1]).getHex(xY[0]).getTypeID() > 5)
		{
			tile = rand.nextInt(getTileCount());
			xY = getXY(tile);
		}
		getRow(xY[1]).getHex(xY[0]).setTypeID(6);
	}
}