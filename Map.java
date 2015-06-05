public class Map
{
	//Fields
	int 		height;
	int 		maxWidth;
	TileRow[] 	rows;
	
	//Default Constructor
	public Map()
	{
		height = 5;
		maxWidth = 5;
		rows = new TileRow[height];
		makeRows();
	}
	
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
	}
	
	//Getters
	int 		getHeight()	 	{return height;}
	int 		getWidth()	 	{return maxWidth;}
	TileRow[]	getRows() 		{return rows;}
	TileRow		getRow(int inY)		{return rows[inY];}
	
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
}
