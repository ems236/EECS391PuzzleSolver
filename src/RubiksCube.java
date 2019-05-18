import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.nio.charset.Charset;

public class RubiksCube extends Game
{
	private static boolean initialized = false;
	private static List<Move> moves;
	private static Map<String, Move> moveMap;
	private static Map<String, Heuristic> heuristicMap;
	
	//Initialization
	private static void initializeStaticVars()
	{
		initialized = true;
		
		moves = new ArrayList<Move>();
		//Add moves for Rubiks cube
		moves.add(new RubikXYNeg());
		moves.add(new RubikXYPos());
		moves.add(new RubikXZNeg());
		moves.add(new RubikXZPos());
		moves.add(new RubikYZNeg());
		moves.add(new RubikYZPos());
		
		//Allows moves to be found by name.
		moveMap = new HashMap<String, Move>();
		for(Move m : moves)
		{
			moveMap.put(m.getName(), m);
		}
		
		heuristicMap = new HashMap<String, Heuristic>();
		heuristicMap.put("hr", new RubikHeuristic());
	}
	
	public RubiksCube()
	{
		if(!initialized)
		{
			initializeStaticVars();
		}
		
		//set a state
		state = new RubiksState();
	}
	
	//abstract methods of Game
	public Move getMoveByName(String name)
	{
		Move requestedMove = moveMap.get(name);
		if(requestedMove == null)
		{
			System.out.println("Move specified does not exist.  Defaulting to YZ plane clockwise (YZ+)");
			return moveMap.get("YZ+");
		}
		else
		{
			return requestedMove;
		}
	}
	
	public Heuristic getHeuristicByName(String name)
	{
		Heuristic requestedHeur = heuristicMap.get(name);
		if(requestedHeur == null)
		{
			System.out.println("Heuristic specified does not exist.  Defaulting to hr");
			return getDefaultHeuristic();
		}
		else
		{
			return requestedHeur;
		}
	}
	
	public Heuristic getDefaultHeuristic()
	{
		return heuristicMap.get("hr");
	}
	
	public List<Move> getAllMoves()
	{
		return moves;
	}
	
	public int tryAllStatesAStar(Heuristic heur, List<Solution> moveCounter)
	{
		System.out.println("Not implemented for this puzzle.");
		return -1;
	}
	
	public int tryAllStatesBeam(int k, List<Solution> moveCounter)
	{
		System.out.println("Not implemented for this puzzle.");
		return -1;
	}
	
	//main
	@SuppressWarnings("resource")
	public static void main(String[] args)
	{
		//initialize EightPuzzle
		RubiksCube game = new RubiksCube();
		
		//get file from args.
		//bunch of tests for args existing or nah
		if(args == null || args.length == 0 || args[0] == null)
		{
			System.out.println("Error no file supplied.");		
		}
	
		else
		{
			Path path = Paths.get(args[0]);
			try
			{
				BufferedReader fileRead = Files.newBufferedReader(path, Charset.defaultCharset());
				String line = fileRead.readLine();
				while(line != null)
				{
					game.executeInput(line);
					line = fileRead.readLine();
				}
				fileRead.close();
			}
			catch(IOException e)
			{
				System.out.println("Error reading file.");
				System.exit(1);
			}
		}
		
		System.out.println("End of file. You may execute commands from the command line.");
		
		Scanner input = new Scanner(System.in);
		while(true)
		{
			String line = input.nextLine();
			game.executeInput(line);
		}
		
	}//end main
}
