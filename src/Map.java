import java.util.Random;

/** @brief A completely random, vanilla map.
 ** @details This is created using no constraints, other than that it is using only the 5 default resource tiles.
 **/
public class Map
{
	//Fields
	int 		height;
	int 		maxWidth;
	TileRow[] 	rows;
	
	/** @brief Default constructor for a TileRow.
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
	
	/** @brief Constructor for a TileRow.
	 ** @details This generates a map of custom size. Initialises all tiles randomly as one of the 5 default resources.
	 ** @param inHeight The height of the new Map.
	 ** @param inWidth The width at the new Map's widest point.
	 **/
	public Map(int inHeight, int inWidth)
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
		makeRandom();
	}
	
	//Getters
	/** @brief
	 ** @return
	 **/
	int 		getHeight()	 	{return height;}
	
	int 		getWidth()	 	{return maxWidth;}
	TileRow[]	getRows() 		{return rows;}
	TileRow		getRow(int inY)	{return rows[inY];}
	
	//Setters
	
	//Methods
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
