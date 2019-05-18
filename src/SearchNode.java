import java.util.ArrayList;
import java.util.List;

class SearchNode implements Comparable<SearchNode>
{
	private List<String> movesHere;
	private State state;
	private double heuristic;
	
	//Only for Astar
	private int costToHere = 0;
	
	public List<String> getMoves()
	{
		return new ArrayList<String>(movesHere);
	}
	public State getState()
	{
		return state;
	}
	public double getHeuristic()
	{
		return heuristic;
	}
	public int getCostToHere()
	{
		return costToHere;
	}
	
	public SearchNode(State state, double funcVal)
	{
		this.state = state;
		heuristic = funcVal;
		
		movesHere = new ArrayList<String>();
		costToHere = 0;
	}
	
	//for A star
	public SearchNode(List<String> movesHere, State state, double funcValue, int cost)
	{
		this.movesHere = movesHere;
		this.state = state;
		heuristic = funcValue;
		costToHere = cost;
	}
	
	//for beam
	public SearchNode(List<String> movesHere, State state, double funcValue)
	{
		this.movesHere = movesHere;
		this.state = state;
		heuristic = funcValue;
	}
	
	public int compareTo(SearchNode other)
	{
		if((heuristic + costToHere) == (other.heuristic + other.costToHere))
		{
			return 0;
		}
		
		else if((heuristic + costToHere) > (other.heuristic + other.costToHere))
		{
			return 1;
		}
		
		else
		{
			return -1;
		}
	}
}