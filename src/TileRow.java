public class TileRow
{
	//Fields
	int 	length;
	Tile[]	hexes;
	
	//Default Constructor
	public TileRow(int inLength)
	{
		length = inLength;
		hexes = new Tile[length];
		int i;
		for(i = 0; i < inLength; i++)
		{
			hexes[i] = new Tile();
		}
	}
	
	//Getters
	int 	getLength()	{return length;}
	Tile[] 	getHexes()	{return hexes;}
	Tile	getHex(int inID){return hexes[inID];}
	
	//Setters
	void	setLength(int inLength)	
	{
		length = inLength;
		hexes = resize();
	}
	
	void	setHex(int inID, Tile inHex) {hexes[inID] = inHex;}
	
	//Methods
	Tile[] resize()
	{
		Tile[] newHexes = new Tile[length];
		
		int i;
		for(i = 0; i < length; i++)
		{
			newHexes[i] = hexes[i];
		}
		
		return newHexes;
	}
	
	String tostring()
	{
		String out = "";
		
		int i;
		for(i = 0; i < length; i++)
		{
			out += hexes[i].getTypeID() + " ";
		}
		return out;
	}
}
