import java.util.Random;

public class SpreadEvenMap extends Map
{
	int[] types;

	public SpreadEvenMap(int inHeight, int inWidth)
	{
		super(inHeight, inWidth);

		int[] tempTypes = {1, 2, 3, 4, 5};
		types = tempTypes;
	}

	int[] getTypes()
	{
		return types;
	}

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

	int checkNeighbours(int inX, int inY)
	{
		int myType = rows[inY].getHex(inX).getTypeID();
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

	void tileOcean(int currentCount, int oceanCount)
	{
		Random rand = new Random();
		int tile = rand.nextInt(getTileCount()); //Start at random point
		int[] xY = getXY(tile);

		while(currentCount < oceanCount)
		{
			if(getRow(xY[1]).getHex(xY[0]).getTypeID() != 0)
			{
				getRow(xY[1]).getHex(xY[0]).setTypeID(0); //Make tile ocean.
				currentCount++;
			}
			int xMod = rand.nextInt(3)-1;
			int yMod = rand.nextInt(3)-1;
			while(rows.length - 1 < xY[1] + yMod || xY[1] + yMod < 0) //Move in random direction.
			{
				yMod = rand.nextInt(3)-1;
			}
			while(rows[xY[1] + yMod].length - 1 < xY[0] + xMod || xY[0] + xMod < 0)
			{
				xMod = rand.nextInt(3)-1;
			}
			xY[0] += xMod;
			xY[1] += yMod;
		}
	}

	void splitLand(int oceanCount)
	{
		Random rand = new Random();
		int[] xY = new int[2];
		xY[1] = 0;
		xY[0] = (int) rows[xY[1]].length/2;

		int currentCount = 0;
		int i = 0;
		int xMod;
		while(i < rows.length)
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
			
			getRow(xY[1]).getHex(xY[0]).setTypeID(0); //Make tile ocean.
			currentCount++;
			i++;
		}
		tileOcean(currentCount, oceanCount); //Call next part(currentCount, oceanCount);
	}
}