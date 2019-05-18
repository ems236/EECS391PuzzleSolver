import java.util.*;
public class EightHeuristicMisplaced implements Heuristic
{
	//Name = misplaced
	//Name = h1
	public String getName()
	{
		return "h1";
	}
	
	private void isEightState(State stateIn)
	{
		if(!(stateIn instanceof EightState))
		{
			System.out.println("passed invalid state");
			System.exit(1);
		}
	}
	
	public double calculate(State state)
	{
		isEightState(state);
		ArrayList<Integer> stateList = ((EightState)state).getStateList();
		
		int sum = 0;
		for(int i = 0; i < stateList.size(); i++)
		{
			if(stateList.get(i) != i && stateList.get(i) != 0)
			{
				sum++;
			}
		}
		return sum;
	}
}
