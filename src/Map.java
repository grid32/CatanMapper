import java.util.Random;

/** @brief A completely random, vanilla map.
 ** @details This is created using no constraints, other than that it is using only the 5 default resource tiles.
 ** @field height The amount of TileRows contained in this Map.
 ** @field maxWidth The length of the TileRows at the Map's widest point.
 ** @field rows The array of TileRows.
 **/
public class Map
{
	//Fields
	int 		height;
	int 		maxWidth;
	TileRow[] 	rows;
	
	/** @brief Default constructor for a Map.
	 ** @details This generates a map of default size, 5x5. Initialises all tiles randomly as one of the 5 default resources.
	 **/
	public Map()
	{
		height = 5;
		maxWidth = 5;
		rows = new TileRow[height];
		makeRows();
		makeRandom();
	}
	
	/** @brief Constructor for a Map.
	 ** @details This generates a Map of custom size. Initialises all tiles randomly as one of the 5 default resources.
	 ** @param inHeight The height of the new Map.
	 ** @param inWidth The width at the new Map's widest point.
	 **/
	public Map(int inHeight, int inWidth)
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
		makeRandom();
	}
	
	//Getters
	/** @brief Gets the height field.
	 ** @return int Returns the height field.
	 **/
	int 		getHeight()	 	{return height;}
	
	/** @brief Gets the width field.
	 ** @return int Returns the width field.
	 **/
	int 		getWidth()	 	{return maxWidth;}

	/** @brief Gets the array of TileRows.
	 ** @return int Returns the array of TileRows.
	 **/
	TileRow[]	getRows() 		{return rows;}

	/** @brief Gets the a row from the array of TileRows.
	 ** @param inY The index of the row in the array to get.
	 ** @return int Returns the requested row in the array of TileRows.
	 **/
	TileRow		getRow(int inY)	{return rows[inY];}
	
	//Setters
	
	//Methods
	/** @brief Initialises the array of TileRows.
	 ** @details Sets each TileRow's length based on its position in the map, to give a correct shape.
	 ** @return void Returns nothing.
	 **/
	void makeRows()
	{
		int i;
		int change = (height - 3) / 2;
		int middleID = (height - 1) / 2;
		for(i = 0; i < height; i++)
		{
			if(i < middleID)//Top half
			{
				//rows[i] = new TileRow(maxWidth - (change - i));
				rows[i] = new TileRow(maxWidth - ((change - i) + 1));
			}
			else if(i == middleID)//Middle
			{
				//rows[i] = new TileRow(maxWidth - 1);
				rows[i] = new TileRow(maxWidth);
			}
			else if(i > middleID)//Bottom half
			{
				//rows[i] = new TileRow(maxWidth + (((height - (2 * i)) + 1) / 2));
				rows[i] = new TileRow(maxWidth + ((((height - (2 * i)) + 1) / 2) - 1));
			}
		}
	}
	
	//Methods
	/** @brief Builds a String of the Map.
	 ** @details Utilises the TileRow.tostring() function, to construct a full String representation of the Map and its typeIDs.
	 ** @return String Returns the String representation of this Map.
	 **/
	String tostring()
	{
		String out = "";
		
		int i;
		int change = (height - 3) / 2;
		int middleID = (height - 1) / 2;
		for(i = 0; i < height; i++)
		{
			int j;
			for(j = 0; j < (maxWidth - rows[i].getLength()); j++)
			{
				out += " ";
			}
			out += rows[i].tostring();
			if(i+1 < height)
				out += "\n";
		}
		return out;
	}

	/** @brief Sets the Map's typeIDs entirely randomly.
	 ** @details Due to that the only restriction is that the types can only be the default 5 resources, leads to fairly unbalanced Maps. Sometimes no tile of a certain resource.
	 ** @return void Returns nothing.
	 **/
	void makeRandom()
	{
		//Randomise
		int i;
		for(i = 0; i < this.getHeight(); i++)
		{
			int j;
			for(j = 0; j < this.getRow(i).getLength(); j++)
			{
				Random rand = new Random();
				int  n = rand.nextInt(5) + 1;

				this.getRow(i).getHex(j).setTypeID(n);
			}
		}
	}
}
