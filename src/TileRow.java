/** @brief TileRow stores the information of a single row of Tiles.
 ** @details This includes a list of the tiles, and a count of the row's length.
 **/
public class TileRow
{
	//Fields
	int 	length;
	Tile[]	hexes;
	
	/** @brief Default constructor for a TileRow.
	 ** @details This initialised an array of Tiles, of the input length.
	 ** @param inLength The number of Tiles in this row.
	 **/
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
	/** @brief Gets the length field.
	 ** @return int Returns the length field.
	 **/
	int 	getLength()	{return length;}

	/** @brief Gets the array of Tiles.
	 ** @return int Returns the array of Tiles.
	 **/
	Tile[] 	getHexes()	{return hexes;}

	/** @brief Gets a Tile from the TileRow.
	 ** @param inID The position of the desired Tile in the TileRow.
	 ** @return int Returns the Tile at position inID.
	 **/
	Tile	getHex(int inID){return hexes[inID];}
	
	//Setters
	/** @brief Sets the length of the Tile array.
	 ** @details Also resizes the array to match, cutting off any extra Tiles, or initialising any additional ones.
	 ** @param inLength The new length of the row.
	 ** @return void Returns nothing.
	 **/
	void	setLength(int inLength)	
	{
		length = inLength;
		hexes = resize();
	}
	
	/** @brief Sets a Tile in the row to a given Tile.
	 ** @param inID The position in the row to replace.
	 ** @param inHex The new Tile to replace the old one.
	 ** @return void Returns nothing.
	 **/
	void	setHex(int inID, Tile inHex) {hexes[inID] = inHex;}
	
	//Methods
	/** @brief Resizes the row.
	 ** @details If the row should now be larger, this initialises the new Tiles. If it is smaller, it cuts off the extra Tiles.
	 ** @return Tile[] Returns the new array of Tiles.
	 **/
	Tile[] resize()
	{
		Tile[] newHexes = new Tile[length];
		
		int i;
		for(i = 0; i < length; i++)
		{
			if(i < hexes.length)
				newHexes[i] = hexes[i];
			else
				newHexes[i] = new Tile();
		}
		
		return newHexes;
	}
	
	/** @brief Builds a string of this TileRow.
	 ** @details Makes a string of the typeID of each Tile in the TileRow, for use when displaying the text version of a Map.
	 ** @return String Returns the String representation of this TileRow.
	 **/
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
