import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RubikXZPos implements Move
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
		return "XZ+";
	}
	
	public State move(State stateIn)
	{
		if(!isRubikState(stateIn))
		{
			return null;
		}
		List<RubikBlock> list = ((RubiksState)stateIn).getListCopy();
		//xz plane is 2, 3, 6, 7
		ArrayList<Integer> blocks = new ArrayList<Integer>(Arrays.asList(2, 3, 6, 7));
		//move cubes
		RubikBlock temp = list.get(7);
		list.set(7, list.get(6));
		list.set(6, list.get(2));
		list.set(2, list.get(3));
		list.set(3, temp);
		
		for(Integer blockNum : blocks)
		{
			//rotate faces.
			list.get(blockNum).rotateXZ();
		}
		
		return new RubiksState(list);
	}
}
