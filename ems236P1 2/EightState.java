import java.util.*;

public class EightState extends State
{
	private ArrayList<Integer> stateList;
	public ArrayList<Integer> getStateList()
	{
		//Copies the Array
		return new ArrayList<Integer>(stateList);
	}
	
	public EightState()
	{
		setGoalState();
	}
	
	public EightState(ArrayList<Integer> value)
	{
		stateList = value;
	}
	
	public State copy()
	{
		return new EightState(getStateList());
	}
	
	public void setGoalState()
	{
		stateList = new ArrayList<Integer>();
		stateList.add(0);
		stateList.add(1);
		stateList.add(2);
		stateList.add(3);
		stateList.add(4);
		stateList.add(5);
		stateList.add(6);
		stateList.add(7);
		stateList.add(8);
	}
	
	public boolean isGoalState()
	{
		for(int i = 0; i < stateList.size(); i++)
		{
			if(stateList.get(i) != i)
			{
				return false;
			}
		}
		return true;
	}
	
	public void setWithString(String value)
	{
		//Expect space delimited groups of 3 with b as the blank.
		if(value.length() != 11)
		{
			System.out.println("invalid value string");
			return;
		}
		
		stateList.clear();
		
		for(int i = 0; i < value.length(); i++)
		{
			if(value.charAt(i) == 'b')
			{
				//b and 0 are blank.
				stateList.add(0);
			}
			else if(!value.substring(i, i + 1).equals(" "))
			{
				stateList.add(Integer.parseInt(value.substring(i, i + 1)));
			}
		}
		
		if(!isValidState())
		{
			System.out.println("Invalid state, was set to goal state instead.");
			setGoalState();
		}
	}
	
	public boolean isValidState()
	{
		if(stateList.size() != 9)
		{
			return false;
		}
		
		for(int i = 0; i < 9; i++)
		{
			if(!stateList.contains(i))
			{
				return false;
			}
		}
		
		return true;
	}
	
	public int getHash()
	{
		//pretty confident this is unique across all states
		return stateList.hashCode();
	}
	
	public void print()
	{
		StringBuilder s = new StringBuilder();
		for(int i = 0; i < stateList.size(); i++)
		{
			if(stateList.get(i) == 0)
			{
				s.append("b");
			}
			else
			{
				s.append(stateList.get(i));
			}
			
			if(i % 3 == 2)
			{
				s.append("\r\n");
			}
			else
			{
				s.append(" ");
			}
		}
		
		System.out.print(s.toString());
	}
	
	public static List<State> getAllSolvable()
	{
		List<Integer> initial = new ArrayList<Integer>();
		initial.add(0);
		initial.add(1);
		initial.add(2);
		initial.add(3);
		initial.add(4);
		initial.add(5);
		initial.add(6);
		initial.add(7);
		initial.add(8);
		//makes a list of every permutation of tiles (only half are actually possible.)
		List<List<Integer>> permutations = Permute.generate(initial);
		
		List<State> solvableList = new ArrayList<State>();
		//sorts through the unsolvable ones.
		for(List<Integer> current : permutations)
		{
			EightState state = new EightState((ArrayList<Integer>)current);
			if(state.isSolvable())
			{
				solvableList.add(state);
			}
		}
		
		return solvableList;
	}
	public boolean isSolvable()
	{
		//assumes is valid.
		int inversionCount = 0;
		for(int i = 0; i < stateList.size() - 1; i++)
		{
			if(stateList.get(i) != 0)
			{
				for(int j = i + 1; j < stateList.size(); j++)
				{
					if(stateList.get(i) > stateList.get(j) && stateList.get(j) != 0)
					{
						inversionCount++;
					}
				}
			}
		}
		return inversionCount % 2 == 0;
	}
}
