import java.util.ArrayList;
public class EightHeuristicDistance implements Heuristic
{
	//Name = misplaced
	//Name = h2
	public String getName()
	{
		return "h2";
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
			if(stateList.get(i) != 0)
			{
				//calculate distance:
				//horizontal
				sum += Math.abs((stateList.get(i) % 3) - (i % 3));
				//vertical
				sum += Math.abs((stateList.get(i) / 3) - (i / 3));
			}
		}
		return sum;
	}
}
