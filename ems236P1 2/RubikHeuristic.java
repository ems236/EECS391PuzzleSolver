import java.util.*;
public class RubikHeuristic implements Heuristic
{
	private boolean isRubikState(State stateIn)
	{
		if(!(stateIn instanceof RubiksState))
		{
			System.out.println("passed invalid state");
			return false;
		}
		return true;
	}
	
	public String getName()
	{
		return "hr";
	}
	
	public double calculate(State stateIn)
	{
		if(!isRubikState(stateIn))
		{
			return Integer.MAX_VALUE;
		}
		List<RubikBlock> list = ((RubiksState)stateIn).getListCopy();
		
		//Simple heurstic: distance of cubes not in correct spot / 4.  / 4 because 4 move every turn, can't overestimate cost to goal to make admissible.
		double sum = 0;
		for(int i = 0; i < list.size(); i++)
		{
			//Definitely not the best way to do this, but it works.
			
			int id = list.get(i).id;
			if(id == 0)
			{
				if(i == 1 || i == 2 || i == 4)
				{
					sum++;
				}
				else if(i == 3 || i == 5 || i == 6)
				{
					sum += 2;
				}
				else if(i == 7)
				{
					sum += 3;
				}
			}
			else if(id == 1)
			{
				if(i == 0 || i == 3 || i == 5)
				{
					sum++;
				}
				else if(i == 4 || i == 7 || i == 2)
				{
					sum += 2;
				}
				else if(i == 6)
				{
					sum += 3;
				}
			}
			else if(id == 2)
			{
				if(i == 0 || i == 3 || i == 6)
				{
					sum++;
				}
				else if(i == 1 || i == 4 || i == 2)
				{
					sum += 2;
				}
				else if(i == 5)
				{
					sum += 3;
				}
			}
			else if(id == 3)
			{
				if(i == 1 || i == 2 || i == 7)
				{
					sum++;
				}
				else if(i == 0 || i == 5 || i == 6)
				{
					sum += 2;
				}
				else if(i == 4)
				{
					sum += 3;
				}
			}
			else if(id == 4)
			{
				if(i == 0 || i == 5 || i == 6)
				{
					sum++;
				}
				else if(i == 1 || i == 2 || i == 7)
				{
					sum += 2;
				}
				else if(i == 3)
				{
					sum += 3;
				}
			}
			else if(id == 5)
			{
				if(i == 1 || i == 4 || i == 7)
				{
					sum++;
				}
				else if(i == 0 || i == 3 || i == 6)
				{
					sum += 2;
				}
				else if(i == 2)
				{
					sum += 3;
				}
			}
			else if(id == 6)
			{
				if(i == 2 || i == 4 || i == 7)
				{
					sum++;
				}
				else if(i == 0 || i == 3 || i == 5)
				{
					sum += 2;
				}
				else if(i == 1)
				{
					sum += 3;
				}
			}
			else
			{
				if(i == 3 || i == 5 || i == 6)
				{
					sum++;
				}
				else if(i == 1 || i == 2 || i == 4)
				{
					sum += 2;
				}
				else if(i == 0)
				{
					sum += 3;
				}
			}
		}
		return sum / 4;
	}
}
