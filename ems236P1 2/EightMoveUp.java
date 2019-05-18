import java.util.*;
public class EightMoveUp implements Move 
{
	private String name = "up";
	
	private void isEightState(State stateIn)
	{
		if(!(stateIn instanceof EightState))
		{
			System.out.println("passed invalid state");
			System.exit(1);
		}
	}
	
	public State move(State stateIn)
	{
		isEightState(stateIn);
		ArrayList<Integer> stateList = ((EightState)stateIn).getStateList();
		if(isValid(stateIn))
		{
			//switch the 9 with the one above it
			int index = stateList.indexOf(0);
			Integer temp = stateList.get(index);
			stateList.set(index, stateList.get(index - 3));
			stateList.set(index - 3, temp);
			
			return new EightState(stateList);
		}
		else
		{
			return stateIn;
		}
		
	}
	
	public boolean isValid(State stateIn)
	{
		isEightState(stateIn);
		ArrayList<Integer> state = ((EightState)stateIn).getStateList();
		int index = state.indexOf(0);
		//invalid if top of board
		if(index < 3)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public String getName()
	{
		return name;
	}
}
