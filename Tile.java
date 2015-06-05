public class Tile
{
	//Fields
	int 	typeID; //0:Ocean, 1:Wood, 2:Ore, 3:Clay, 4:Sheep, 5:Wheat
	int 	rarity; //0:{None}, 1:{2,12}, 2:{3,11}, 3:{4,10}, 4:{5,9}, 5:{6,8}
	
	//Default constructor
	public Tile()
	{
		typeID = 0;
		rarity = 0;
	}
	
	//Getters
	int 	getTypeID() 		{return typeID;}
	String 	getType() 		{return findType();}
	int 	getRarity() 		{return rarity;}
	
	//Setters
	void setTypeID	(int inID) 	{typeID = inID;}
	void setRarity	(int inRarity) 	{rarity = inRarity;}
	
	//Methods
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
	
	String tostring()
	{
		String outString = getType() + " hex with value of " + getRarity();
		return outString;
	}
}
