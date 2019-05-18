import java.util.*;

//top = 0, 1, 4, 5 . y
//bottom = 2, 3, 6, 7 y

//left = 0, 2, 4, 6 x
//right = 1, 3, 5, 7 x

//front = 0, 1, 2, 3 z
//back = 4, 5, 6, 7 z

//xy = 4, 5, 6, 7
//xz = 2, 3, 6, 7
//yz = 1, 3, 5, 7
public class RubiksState extends State
{
	private static List<RubikBlock> goalStateList = new ArrayList<RubikBlock>();
	static
	{
		goalStateList.add(new RubikBlock(Color.GREEN, Color.WHITE, Color.RED, 0));
		goalStateList.add(new RubikBlock(Color.BLUE, Color.WHITE, Color.RED, 1));
		goalStateList.add(new RubikBlock(Color.GREEN, Color.YELLOW, Color.RED, 2));
		goalStateList.add(new RubikBlock(Color.BLUE, Color.YELLOW, Color.RED, 3));
		goalStateList.add(new RubikBlock(Color.GREEN, Color.WHITE, Color.ORANGE, 4));
		goalStateList.add(new RubikBlock(Color.BLUE, Color.WHITE, Color.ORANGE, 5));
		goalStateList.add(new RubikBlock(Color.GREEN, Color.YELLOW, Color.ORANGE, 6));
		goalStateList.add(new RubikBlock(Color.BLUE, Color.YELLOW, Color.ORANGE, 7));
	}
	
	//List reads left to right, top to down, front to back.
	private List<RubikBlock> listRep = new ArrayList<RubikBlock>();
	public List<RubikBlock> getListCopy()
	{
		List<RubikBlock> newState = new ArrayList<RubikBlock>();
		for(RubikBlock block : listRep)
		{
			newState.add(block.copy());
		}
		return newState;
	}
	
	public RubiksState copy()
	{
		List<RubikBlock> newState = getListCopy();
		return new RubiksState(newState);
	}
	
	//constructors
	public RubiksState()
	{
		setGoalState();
	}
	public RubiksState(List<RubikBlock> list)
	{
		listRep = list;
	}
	
	public void setGoalState()
	{
		listRep.clear();
		//initialize blocks
		listRep.add(new RubikBlock(Color.GREEN, Color.WHITE, Color.RED, 0));
		listRep.add(new RubikBlock(Color.BLUE, Color.WHITE, Color.RED, 1));
		listRep.add(new RubikBlock(Color.GREEN, Color.YELLOW, Color.RED, 2));
		listRep.add(new RubikBlock(Color.BLUE, Color.YELLOW, Color.RED, 3));
		listRep.add(new RubikBlock(Color.GREEN, Color.WHITE, Color.ORANGE, 4));
		listRep.add(new RubikBlock(Color.BLUE, Color.WHITE, Color.ORANGE, 5));
		listRep.add(new RubikBlock(Color.GREEN, Color.YELLOW, Color.ORANGE, 6));
		listRep.add(new RubikBlock(Color.BLUE, Color.YELLOW, Color.ORANGE, 7));
	}
	
	public boolean isGoalState()
	{
		//test list equality.
		return listRep.equals(goalStateList);
	}
	
	public String toString()
	{
		
		StringBuilder out = new StringBuilder();
		//top face
		out.append("- - ");
		out.append(listRep.get(4).yFace + " " + listRep.get(5).yFace + " \r\n");
		out.append("- - ");
		out.append(listRep.get(0).yFace + " " + listRep.get(1).yFace + " \r\n");
		//left face -> front -> right -> back top halves
		out.append(listRep.get(4).xFace + " " + listRep.get(0).xFace + " " + listRep.get(0).zFace + " " + listRep.get(1).zFace + " " + listRep.get(1).xFace + " " + listRep.get(5).xFace + " " + listRep.get(5).zFace + " " + listRep.get(4).zFace + " \r\n");
		out.append(listRep.get(6).xFace + " " + listRep.get(2).xFace + " " + listRep.get(2).zFace + " " + listRep.get(3).zFace + " " + listRep.get(3).xFace + " " + listRep.get(7).xFace + " " + listRep.get(7).zFace + " " + listRep.get(6).zFace + " \r\n");
		out.append("- - ");
		out.append(listRep.get(2).yFace + " " + listRep.get(3).yFace + " \r\n");
		out.append("- - ");
		out.append(listRep.get(6).yFace + " " + listRep.get(7).yFace + " \r\n");
		
		return out.toString();
	}
	
	public void print()
	{
		System.out.println(toString());
	}
	
	public int getHash()
	{
		return this.toString().hashCode();
	}
	
	public void setWithString(String in)
	{
		System.out.println("This isn't really implemented.");
	}
	public boolean isValidState()
	{
		System.out.println("This is also not really implemented.");
		return true;
	}
}
