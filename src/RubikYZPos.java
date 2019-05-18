import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RubikYZPos implements Move
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
	
	public boolean isValid(State state)
	{
		return isRubikState(state);
	}
	
	public String getName()
	{
		return "YZ+";
	}
	
	public State move(State stateIn)
	{
		if(!isRubikState(stateIn))
		{
			return null;
		}
		List<RubikBlock> list = ((RubiksState)stateIn).getListCopy();
		//yz plane is 1, 3, 5, 7
		ArrayList<Integer> blocks = new ArrayList<Integer>(Arrays.asList(1, 3, 5, 7));
		//move cubes
		RubikBlock temp = list.get(3);
		list.set(3, list.get(7));
		list.set(7, list.get(5));
		list.set(5, list.get(1));
		list.set(1, temp);
		
		for(Integer blockNum : blocks)
		{
			//rotate faces.
			list.get(blockNum).rotateYZ();
		}
		
		return new RubiksState(list);
	}
}
