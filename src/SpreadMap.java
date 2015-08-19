import java.util.Random;
import java.util.Stack;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

/** @brief A Map where Tiles with like types are not neighboured.
 ** @details This can be used for vanilla, seafarers, and their 6 player expansions.
 ** @field types A list of the type IDs to be used in the randomisation.
 ** @field complete Whether the map has been completed, including randomise.
 **/
public class SpreadMap extends Map
{
	int[] types = {1, 2, 3, 4, 5, 6, 9}; //List of types so that rand can grab one of these IDs
	boolean complete = false;

	public SpreadMap(int inHeight, int inWidth)
	{
		super(inHeight, inWidth);
	}

	/** @brief Default constructor for a SpreadMap.
	 ** @details This generates a SpreadMap of a specified size.
	 ** @param inHeight The height of the new SpreadMap.
	 ** @param inWidth The width at the new SpreadMap's widest point.
	 **/
	public SpreadMap(int inHeight, int inWidth, int[] inCounts)
	{
		super(inHeight, inWidth);

		//Explorers
		if(inCounts[7] > 0 || inCounts[8] > 0)
		{
			if(inCounts[10] == 1)
			{
				placeCouncil();
			}
			fillExplorers(inCounts[7], inCounts[8]);
		}

		//Seafarers
		if(inCounts[0] > 0)
		{
			int remainingOceans = splitLand(inCounts[0]);
			fillOcean(remainingOceans, inCounts[0]);
		}

		int[] myCounts = {0, 0, 0, 0, 0, 0, 0}; //Wood, Ore, Clay, Sheep, Wheat, Desert, Gold
		complete = randomise(0, inCounts, myCounts);

		groupIslands();
	}

	/** @brief Gets the list of typesIDs in use.
	 ** @return int[] Returns the list of typeIDs.
	 **/
	int[] getTypes()
	{
		return types;
	}

	/** @brief Gets whether or not generation is complete.
	 ** @return boolean Returns the 'complete' field.
	 **/
	boolean getComplete()
	{
		return complete;
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

	void placeCouncil()
	{
		Random rand = new Random();
		int[] xY = new int[2];

		xY[1] = rand.nextInt(getHeight());
		xY[0] = rand.nextInt(rows[xY[1]].length);

		if(getRow(xY[1]).getHex(xY[0]).getTypeID() == -1)
		{
			getRow(xY[1]).getHex(xY[0]).setTypeID(10);
		}
	}

	void fillExplorers(int inSunsCount, int inMoonsCount)
	{
		Random rand = new Random();
		int[] xY = new int[2];

		xY[1] = rand.nextInt(getHeight());
		xY[0] = rand.nextInt(rows[xY[1]].length);

		int count = 0;
		//Place suns
		while(count < inSunsCount && count < getTileCount())
		{
			if(getRow(xY[1]).getHex(xY[0]).getTypeID() == -1)
			{
				getRow(xY[1]).getHex(xY[0]).setTypeID(7); //Make tile sun.
				count++;
			}
			moveRandomXY(xY);
		}
		//Place moons
		while(count < (inSunsCount + inMoonsCount) && count < getTileCount())
		{
			if(getRow(xY[1]).getHex(xY[0]).getTypeID() == -1)
			{
				getRow(xY[1]).getHex(xY[0]).setTypeID(8); //Make tile sun.
				count++;
			}
			moveRandomXY(xY);
		}
	}

	/** @brief Initiates seafarers ocean placement.
	 ** @details This is done by drawing a jagged ocean line down the center of the Map.
	 ** @param oceanCount The number of Tiles to make ocean.
	 ** @return void Returns nothing.
	 **/
	int splitLand(int oceanCount)
	{
		Random rand = new Random();
		int[] xY = new int[2];
		xY[1] = 0;
		xY[0] = (int) rows[xY[1]].length/2;

		int currentCount = 0;
		int i = 0;
		int xMod;
		while(i < rows.length && i < getTileCount())
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
			if(getRow(xY[1]).getHex(xY[0]).getTypeID() == -1)
			{
				getRow(xY[1]).getHex(xY[0]).setTypeID(0); //Make tile ocean.
				currentCount++;
			}
			i++;
		}
		return currentCount;
	}

	/** @brief Finalises seafarers ocean placement.
	 ** @details This is done by picking a random Tile, making it ocean, and stepping randomly until complete.
	 ** @param currentCount The number of Tiles already made ocean by splitLand().
	 ** @param oceanCount The number of Tiles to make ocean.
	 ** @return void Returns nothing.
	 **/
	void fillOcean(int currentCount, int oceanCount)
	{
		Random rand = new Random();
		int tile = rand.nextInt(getTileCount()); //Start at random point
		int[] xY = getXY(tile);

		while(currentCount < oceanCount && currentCount < getTileCount())
		{
			if(getRow(xY[1]).getHex(xY[0]).getTypeID() == -1)
			{
				getRow(xY[1]).getHex(xY[0]).setTypeID(0); //Make tile ocean.
				currentCount++;
			}
			xY = moveRandomXY(xY);
		}
	}

	/** @brief Randomly steps X and Y.
	 ** @details Used in filling oceans and explorers.
	 ** @param inXY An array containing the current X and Y values.
	 ** @return void Returns the new X and Y values.
	 **/
	int[] moveRandomXY(int[] inXY)
	{
		Random rand = new Random();
		int xMod = rand.nextInt(3)-1;
		int yMod = rand.nextInt(3)-1;
		while(rows.length - 1 < inXY[1] + yMod || inXY[1] + yMod < 0) //Move in random direction.
		{
			yMod = rand.nextInt(3)-1;
		}
		while(rows[inXY[1] + yMod].length - 1 < inXY[0] + xMod || inXY[0] + xMod < 0)
		{
			xMod = rand.nextInt(3)-1;
		}
		inXY[0] += xMod;
		inXY[1] += yMod;
		return inXY;
	}

	/** @brief Fills in the land tiles randomly.
	 ** @details Starting at the top left, moving left to right, top to bottom. This works recursively, and if the current map cannot be completed, steps back to a point where a change can be made, and tries again.
	 ** @param currentTileID The tile count of where the randomise is up to.
	 ** @param inCounts The count of tiles available given the current settings of the map.
	 ** @param currentCounts The count of tiles used thus far in the randomise.
	 ** @return boolean Returns whether the randomise is successful.
	 **/
	boolean randomise(int currentTileID, int[] inCounts, int[] currentCounts)
	{
		int[] xY = getXY(currentTileID);
		int currentType = getRow(xY[1]).getHex(xY[0]).getTypeID();

		if(currentType == -1)
		{
			//Make list of types remaining to try
			Stack<Integer> remainingTypes = new Stack<Integer>();
			for(int i = 0; i < types.length; i++)
			{
				if(currentCounts[i]+1 <= inCounts[types[i]])
				{
					remainingTypes.push(types[i]);
				}
			}
			boolean ret = false;
			Random rand = new Random();
			int randomType, random;
			do
			{
				//Pick random type
				do
				{
					random = rand.nextInt(7);
					randomType = types[random];
				}
				while(remainingTypes.search(randomType) == -1 && remainingTypes.size() > 0);
				
				//Check it fits(neighbours, currentCount)
				boolean possible = true;
				int changeChance = checkNeighbours(xY[0], xY[1], randomType);
				int randChance = rand.nextInt(101);
				if(changeChance > randChance)
				{
					possible = false;
				}

				if(currentCounts[random]+1 > inCounts[randomType])
				{
					possible = false;
				}
				remainingTypes.removeElement(randomType); //Remove from remaining
				if(possible)
				{
					getRow(xY[1]).getHex(xY[0]).setTypeID(randomType);
					//Increment currentCount[type]
					if(currentTileID >= getTileCount() - 1)
						return true;
					else
					{
						ret = randomise(currentTileID + 1, inCounts, updateCounts(random, currentCounts));
					}
					if(!ret)
					{
						//Decrement currentcount[type]
						getRow(xY[1]).getHex(xY[0]).setTypeID(-1);
					}
				}
			}
			while(ret == false && remainingTypes.size() > 0);
			return ret;
		}
		else
		{
			if(currentTileID >= getTileCount() - 1)
			{
				return true;
			}
			else
				return randomise(currentTileID + 1, inCounts, currentCounts);
		}
	}

	/** @brief Checks the neighbours of a Tile for likeness.
	 ** @details This checks the neighbours of a Tile based on the X/Y coordinate given, and decides how much change there should be of changing the specified Tile's typeID.
	 ** @param inX The X coordinate of the Tile to be compared.
	 ** @param inY The Y coordinate of the Tile to be compared.
	 ** @param myType The Tile's typeID.
	 ** @return int Returns the chance of changing the Tile's typeID.
	 **/
	int checkNeighbours(int inX, int inY, int myType)
	{
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

	/** @brief Creates an updated copy of the int array.
	 ** @details This is used during the construction of a random map; making a copy of the array, incrementing the type chosen, and passing it to the next state.
	 ** @param inID The ID of the type to increment within the array.
	 ** @param inCounts The array containing the count of tiles used in the current state of randomisation.
	 ** @return int[] Returns the updated copy of the int array.
	 **/
	int[] updateCounts(int inID, int[] inCounts)
	{
		int[] tempCounts = inCounts.clone();
		tempCounts[inID]++;
		return tempCounts;
	}

	/** @brief Groups the Tiles by their connectivity.
	 ** @details This is done by checking neighbouring Tiles to see if they are in landTypes[]. Once grouped, rarities can be given based on island size.
	 ** @return int[][] Returns a 2D int array, reflecting the layout of the Map, and labelling each Tile with its corresponding island ID.
	 **/
	int[][] groupIslands()
	{
		String[] landTypes = {"1", "2", "3", "4", "5", "6", "9"};
		int[][] islands = new int[rows.length][];
		HashMap<Integer, Integer> sameIDs = new HashMap<Integer, Integer>();

		for(int i = 0; i < rows.length; i++)
		{
			islands[i] = new int[rows[i].length];
		}

		int x = 0;
		int y = 0;

		//First pass
		int counter = 0;
		for(int ii = 0; ii < getTileCount(); ii++)
		{
			if(Arrays.asList(landTypes).contains("" + rows[y].getHex(x).getTypeID()))
			{
				List<Integer> setIsl = new ArrayList<Integer>();

				setIsl = checkConnectivity(islands, x, y, landTypes);

				if(setIsl.isEmpty())
				{
					setIsl.add(counter);
					counter++;
				}
				else
				{
					for(int i = 0; i < setIsl.size(); i++)
					{
						for(int j = 0; j < setIsl.size(); j++)
						{
							int islandI = setIsl.get(i), islandJ = setIsl.get(j);
							if(islandI != islandJ)
							{
								if(sameIDs.get(islandI) != null)
								{
									if(sameIDs.get(islandI) > islandJ)
									{
										sameIDs.put(islandI, islandJ);
									}
								}
								else
								{
									sameIDs.put(islandI, islandJ);
								}
								if(sameIDs.get(islandJ) != null)
								{
									if(sameIDs.get(islandJ) > islandI)
									{
										sameIDs.put(islandJ, islandI);
									}
								}
								else
								{
									sameIDs.put(islandJ, islandI);
								}
							}
						}
					}
				}

				islands[y][x] = setIsl.get(0);
				rows[y].getHex(x).setRarity(setIsl.get(0)); //For visualisation
			}
			x++;
			if(x >= islands[y].length)
			{
				x = 0;
				y++;
			}
		}
		////////////


		//Second pass
		x = 0;
		y = 0;

		for(int i = 0; i < getTileCount(); i++)
		{
			if(sameIDs.containsKey(islands[y][x]))
			{
				if(sameIDs.get(islands[y][x]) < islands[y][x])
				{
					islands[y][x] = sameIDs.get(islands[y][x]);
					rows[y].getHex(x).setRarity(islands[y][x]); //For visualisation
				}
			}

			x++;
			if(x >= islands[y].length)
			{
				x = 0;
				y++;
			}
		}
		/////////////

		return islands;
	}

	/** @brief The function which compares neighbouring Tiles' connectivity.
	 ** @details This is used by the groupIslands() function.
	 ** @param inIslands A 2D int array, reflecting the layout of the Map, and labelling each Tile with its corresponding island ID at the current state.
	 ** @param inX The X position of the current Tile in inIslands.
	 ** @param inY The Y position of the current Tile in inIslands.
	 ** @param inLandTypes A list of Tile typeIDs which are considered land.
	 ** @return List Returns a list containing the island ID of any land neighbours.
	 **/
	List checkConnectivity(int[][] inIslands, int inX, int inY, String[] inLandTypes)
	{
		List<Integer> out = new ArrayList<Integer>();

		if(inY > 0)
		{
			if((height+1)/2 >= inY + 1) //Top half
			{
				if(inX < rows[inY - 1].length)
				{
					if(Arrays.asList(inLandTypes).contains("" + rows[inY - 1].getHex(inX).getTypeID())) //Check ^> 
					{
						out.add(inIslands[inY-1][inX]);
					}
				}
				if(inX > 0)
				{
					if(Arrays.asList(inLandTypes).contains("" + rows[inY - 1].getHex(inX - 1).getTypeID())) //Check <^ 
					{
						out.add(inIslands[inY-1][inX-1]);
					}
				}
			}
			else
			{
				if(Arrays.asList(inLandTypes).contains("" + rows[inY - 1].getHex(inX).getTypeID())) //Check <^
				{
					out.add(inIslands[inY-1][inX]);
				}
				if(Arrays.asList(inLandTypes).contains("" + rows[inY - 1].getHex(inX + 1).getTypeID())) //Check ^> 
				{
					out.add(inIslands[inY-1][inX+1]);
				}
			}
		}

		if(inX > 0)
		{
			if(Arrays.asList(inLandTypes).contains("" + rows[inY].getHex(inX - 1).getTypeID())) //Check < 
			{
				out.add(inIslands[inY][inX-1]);
			}
		}

		return out;
	}

	//Label rarity based on island size
	void labelRarity(int[][] inIslands)
	{

	}
}