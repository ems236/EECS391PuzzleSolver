import java.util.*;

public abstract class State 
{
	public abstract void setGoalState();
	public abstract void setWithString(String value);
	public abstract boolean isValidState();
	public abstract void print();
	public abstract boolean isGoalState();
	public abstract State copy();
	public abstract int getHash();
	
	public static State randomizeState(State state, List<Move> moves, int steps, List<String> history)
	{
		if(history == null)
		{
			history = new ArrayList<String>();
		}
		
		State start = state.copy();
		int moveCount = moves.size();
		//Set seed to 1 so it's not really that random.
		Random randGen = new Random(1);
		
		for(int i = 0; i < steps; i++)
		{
			int moveNum = randGen.nextInt(moveCount);
			while(!moves.get(moveNum).isValid(start))
			{
				moveNum = randGen.nextInt(moveCount);
			}
			start = moves.get(moveNum).move(start);
			history.add(moves.get(moveNum).getName());
		}
		
		return start;
	}
}
