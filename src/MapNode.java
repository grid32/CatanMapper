import java.util.Random;
import java.util.Stack;

/** @brief A state within the search of a spread map.
 ** @details Each node contains the state of a Tile within a Map, giving it a typeID.
 **/
public class MapNode
{
	/** @brief Default constructor for a MapNode.
	 ** @details Begins the search for the correct arrangement of Tiles.
	 ** @param M The Map to be randomised and spread.
	 ** @param current The ID of the Tile in the Map currently being set.
	 ** @param end The ID of the Tile to end arrangement at.
	 ** @param types A list of possible typeIDs for the Tiles.
	 **/
	public MapNode(SpreadEvenMap M, int current, int end, int[] types)
	{
		search(M, current, end, types);
	}

	/** @brief The recursive function for creating the next MapNode.
	 ** @details Randomly sets the current Tile's type. Checks already assigned neighbours: if too many neighbours are of the same type, repicks. Once satisfied, moves to next MapNode.
	 ** @param M The Map to be randomised and spread.
	 ** @param current The ID of the Tile in the Map currently being set.
	 ** @param end The ID of the Tile to end arrangement at.
	 ** @param types A list of possible typeIDs for the Tiles.
	 **/
	boolean search(SpreadEvenMap M, int current, int end, int[] types)
	{
		int w = -1;
		boolean done = false;
		int xy[] = M.getXY(current);

		if(current != end)
		{
			if(M.getRow(xy[1]).getHex(xy[0]).getTypeID() != -1)
			{
				done = search(M, current + 1, end, types);
			}
			else
			{
				Stack<Integer> remainingTypes = new Stack<Integer>();
				for(int i = 0; i < types.length; i++)
				{
					remainingTypes.push(types[i]);
				}
				done = false;
				while(remainingTypes.size() != 0 && !done) //While neighbour and done
				{
					Random rand = new Random();
					int next = rand.nextInt(remainingTypes.size()); //Pick random neighbour w	

					w = remainingTypes.elementAt(next);
					M.getRow(xy[1]).getHex(xy[0]).setTypeID(w);
					int changeChance = M.checkNeighbours(xy[0], xy[1]);
					int randChance = rand.nextInt(101);
					if(changeChance < randChance)
					{
						w = remainingTypes.remove(next);
						done = search(M, current + 1, end, types);
					}
				}
			}
			if(!done)
			{
				return false;
			}
		}
		return true;
	}
}