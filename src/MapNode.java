import java.util.Random;
import java.util.Stack;
 
public class MapNode
{
	MapNode(SpreadEvenMap M, int current, int end, int[] types)
	{
		search(M, current, end, types);
	}

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