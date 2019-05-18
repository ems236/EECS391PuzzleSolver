import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RubikXYNeg implements Move
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
		return "XY-";
	}
	
	public State move(State stateIn)
	{
		if(!isRubikState(stateIn))
		{
			return null;
		}
		List<RubikBlock> list = ((RubiksState)stateIn).getListCopy();
		//xy plane is 4, 5, 6, 7
		ArrayList<Integer> blocks = new ArrayList<Integer>(Arrays.asList(4, 5, 6, 7));
		//move cubes
		RubikBlock temp = list.get(7);
		list.set(7, list.get(6));
		list.set(6, list.get(4));
		list.set(4, list.get(5));
		list.set(5, temp);
		
		for(Integer blockNum : blocks)
		{
			//rotate faces.
			list.get(blockNum).rotateXY();
		}
		
		return new RubiksState(list);
	}
}
