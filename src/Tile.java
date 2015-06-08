/** @brief Tile stores the information of a single hexagonal map tile.
 ** @details This includes the type of the tile, and its rarity.
 **/
public class Tile
{
	//Fields
	int 	typeID; //0:Ocean, 1:Wood, 2:Ore, 3:Clay, 4:Sheep, 5:Wheat 6:Desert
	int 	rarity; //0:{None}, 1:{2,12}, 2:{3,11}, 3:{4,10}, 4:{5,9}, 5:{6,8}
	
	/** @brief Default constructor for a Tile.
	 ** @details This initialised the fields to default values.
	 **/
	public Tile()
	{
		typeID = 0;
		rarity = 0;
	}
	
	//Getters
	/** @brief Gets the typeID field.
	 ** @return int Returns the typeID field.
	 **/
	int 	getTypeID() 		{return typeID;}
	
	/** @brief Gets the type as a String.
	 ** @details Uses the typeID, and calls a function to give a String representation of that.
	 ** @return String Returns the type as a String.
	 **/
	String 	getType() 		{return findType();}

	/** @brief Gets the rarity field.
	 ** @return int Returns the rarity field.
	 **/
	int 	getRarity() 		{return rarity;}
	
	//Setters
	/** @brief Sets the typeID field.
	 ** @param inID The int to set as the new typeID.
	 ** @return void Returns nothing.
	 **/
	void setTypeID	(int inID) 	{typeID = inID;}
	
	/** @brief Sets the rarity field.
	 ** @param inRarity The int to set as the new rarity.
	 ** @return void Returns nothing.
	 **/
	void setRarity	(int inRarity) 	{rarity = inRarity;}
	
	//Methods
	/** @brief Finds the String of a typeID.
	 ** @details Basically a switch statement for each typeID, spitting out a String representation.
	 ** @return String Returns the String representation of this Tile's typeID.
	 **/
	String findType()
	{
		String out = "";
		switch(typeID)
		{
			default:
				out = "Invalid";
				break;
			case 0:
				out = "Ocean";
				break;
			case 1:
				out = "Wood";
				break;
			case 2:
				out =  "Ore";
				break;
			case 3:
				out =  "Clay";
				break;
			case 4:
				out = "Sheep";
				break;
			case 5:
				out = "Wheat";
				break;
		}
		return out;
	}
	
	/** @brief Builds a string of this Tile.
	 ** @details Makes a String containing all of this Tile's info.
	 ** @return String Returns the String representation of this Tile.
	 **/
	String tostring()
	{
		String outString = getType() + " hex with value of " + getRarity();
		return outString;
	}
}
