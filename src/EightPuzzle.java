import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.nio.charset.Charset;

public class EightPuzzle extends Game 
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
		//Add moves for 8 puzzle
		moves.add(new EightMoveDown());
		moves.add(new EightMoveRight());
		moves.add(new EightMoveUp());
		moves.add(new EightMoveLeft());
		
		//Allows moves to be found by name.
		moveMap = new HashMap<String, Move>();
		for(Move m : moves)
		{
			moveMap.put(m.getName(), m);
		}
		
		heuristicMap = new HashMap<String, Heuristic>();
		heuristicMap.put("h1", new EightHeuristicMisplaced());
		heuristicMap.put("h2", new EightHeuristicDistance());
	}
	
	public EightPuzzle()
	{
		if(!initialized)
		{
			initializeStaticVars();
		}
		
		//set a state
		state = new EightState();
	}
	
	
	
	//abstract methods of Game
	public Move getMoveByName(String name)
	{
		Move requestedMove = moveMap.get(name);
		if(requestedMove == null)
		{
			System.out.println("Move specified does not exist.  Defaulting to up");
			return moveMap.get("up");
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
			System.out.println("Heuristic specified does not exist.  Defaulting to h2");
			return getDefaultHeuristic();
		}
		else
		{
			return requestedHeur;
		}
	}
	
	public Heuristic getDefaultHeuristic()
	{
		return heuristicMap.get("h2");
	}
	
	public List<Move> getAllMoves()
	{
		return moves;
	}
	
	public int tryAllStatesAStar(Heuristic heur, List<Solution> moveCounter)
	{
		if(heur == null)
		{
			System.out.println("Supplied Heuristic was null. Defaulting to h2.");
			heur = getHeuristicByName("h2");
		}
		
		List<State> solvableStates = EightState.getAllSolvable();
		//System.out.println(solvableStates.size());
		int total = solvableStates.size();
		int solved = 0;
		int unsolved = 0;

		for(State current : solvableStates)
		{
			setState(current);
			//try each state with A-star.
			Solution sol = solveAStar(heur, getAllMoves(), false);
			if(sol.depth >= 0)
			{
				solved++;
				if(moveCounter != null)
				{
					moveCounter.add(sol);	
				}
					
				//debug messages
				if(solved % 1000 == 0)
				{
					System.out.println(solved + " solved so far.");
				}
			}
			else
			{
				unsolved++;
				//debug messages
				/*
				if(unsolved % 1 == 0)
				{
					current.print();
				}
				*/
			}
		}
		
		/*
		System.out.println("max: " + max);
		System.out.println("In " + maxNodes + " nodes:");
		System.out.println("Solved: " + solved + "\r\nUnsolved: " + unsolved + "\r\nFraction: " + ((double)solved / total));
		*/
		return solved;
	}
	
	public int tryAllStatesBeam(int k, List<Solution> moveCounter)
	{
		List<State> solvableStates = EightState.getAllSolvable();
		//System.out.println(solvableStates.size());
		int total = solvableStates.size();
		int solved = 0;
		int unsolved = 0;
		
		for(State current : solvableStates)
		{
			setState(current);
			//try each state with Beam
			Solution sol = solveBeam(k, getAllMoves(), false);
			if(sol.depth >= 0)
			{
				solved++;
				if(moveCounter != null)
				{
					moveCounter.add(sol);	
				}
				//debug messages
				
				if(solved % 5000 == 0)
				 
				{
					System.out.println(solved + " solved so far.");
				}
				
			}
			else
			{
				unsolved++;
				//debug messages
				/*
				if(unsolved % 1 == 0)
				{
					current.print();
					System.out.println("unsolved: " + unsolved);
				}
				*/
			}
		}
		
		System.out.println("In " + maxNodes + " nodes:");
		System.out.println("Solved: " + solved + "\r\nUnsolved: " + unsolved + "\r\nFraction: " + ((double)solved / total));
		return solved;
	}
	/*
	//unused method to test #of solvable as function of max nodes
	//I fully understand that I could have just used getAllSolutionInfo for this.  
	//It works it's just really slow and redundant I only needed to run it once, so not worth changing.
	public void testMaxNodes()
	{
		//expand max nodes logarithmically. say 1 * 10^x -> 5 * 10^x -> 1 * 10^x+1
		String output = "";
		
		int i = 0;
		int currentMax = 50;
		while(currentMax <= 150000)
		{
			maxNodes(currentMax);
			int h1 = tryAllStatesAStar(getHeuristicByName("h1"), null);
			int h2 = tryAllStatesAStar(getHeuristicByName("h2"), null);
			
			System.out.println(h1 + " " + h2);
			output += "Maxnodes: " + currentMax + " | h1 solved: " + h1 + " | h2 solved: " + h2 + "\r\n";
			currentMax = i % 2 == 0 ? currentMax * 2 : currentMax * 5;
			i++;
		}
		System.out.println(output);
	}*/
	
	//This is not well written but it works.  
	//Gets all move counts, run times, and node counts of each search.
	public void getAllSolutionInfo()
	{
		maxNodes(180000);
		ArrayList<Solution> h1Sols = new ArrayList<Solution>();
		long start = System.currentTimeMillis();
		tryAllStatesAStar(getHeuristicByName("h1"), h1Sols);
		long time = System.currentTimeMillis() - start;
		System.out.println("H1 run time: " + time);
		
		ArrayList<Solution> h2Sols = new ArrayList<Solution>();
		start = System.currentTimeMillis();
		tryAllStatesAStar(getHeuristicByName("h2"), h2Sols);
		time = System.currentTimeMillis() - start;
		System.out.println("H2 run time: " + time);
		
		ArrayList<Solution> beamSols = new ArrayList<Solution>();
		start = System.currentTimeMillis();
		tryAllStatesBeam(10, beamSols);
		time = System.currentTimeMillis() - start;
		System.out.println("Beam run time: " + time);

		//Process moves
		ArrayList<Integer> maxNodes = new ArrayList<Integer>();
		maxNodes.add(50);
		maxNodes.add(100);
		maxNodes.add(500);
		maxNodes.add(1000);
		maxNodes.add(5000);
		maxNodes.add(10000);
		maxNodes.add(50000);
		maxNodes.add(100000);
		maxNodes.add(150000);
		
		ArrayList<Integer> h1Counts = new ArrayList<Integer>();
		h1Counts.add(0);
		h1Counts.add(0);
		h1Counts.add(0);
		h1Counts.add(0);
		h1Counts.add(0);
		h1Counts.add(0);
		h1Counts.add(0);
		h1Counts.add(0);
		h1Counts.add(0);
		for(Solution sol : h1Sols)
		{
			for(int i = 0; i < maxNodes.size(); i++)
			{
				if(sol.nodesExplored < maxNodes.get(i))
				{
					h1Counts.set(i, h1Counts.get(i) + 1);
				}
			}
		}
		
		ArrayList<Integer> h2Counts = new ArrayList<Integer>();
		h2Counts.add(0);
		h2Counts.add(0);
		h2Counts.add(0);
		h2Counts.add(0);
		h2Counts.add(0);
		h2Counts.add(0);
		h2Counts.add(0);
		h2Counts.add(0);
		h2Counts.add(0);
		for(Solution sol : h2Sols)
		{
			for(int i = 0; i < maxNodes.size(); i++)
			{
				if(sol.nodesExplored < maxNodes.get(i))
				{
					h2Counts.set(i, h2Counts.get(i) + 1);
				}
			}
		}
		
		StringBuilder output = new StringBuilder();
		for(int i = 0; i < maxNodes.size(); i++)
		{
			output.append("Maxnodes: " + maxNodes.get(i) + " | h1 solved: " + h1Counts.get(i) + " | h2 solved: " + h2Counts.get(i) + "\r\n");
		}
		System.out.println(output);
		
		
		//process moveCounts
		//Process moves
		ArrayList<Integer> maxMoves = new ArrayList<Integer>();
		maxMoves.add(2);
		maxMoves.add(4);
		maxMoves.add(6);
		maxMoves.add(8);
		maxMoves.add(10);
		maxMoves.add(14);
		maxMoves.add(18);
		maxMoves.add(24);
		maxMoves.add(30);
		maxMoves.add(40);
		maxMoves.add(50);
		maxMoves.add(75);
		maxMoves.add(100);
		maxMoves.add(150);
		maxMoves.add(200);
		maxMoves.add(10000);
		
		ArrayList<Integer> h1Moves = new ArrayList<Integer>();
		while(h1Moves.size() != maxMoves.size())
		{
			h1Moves.add(0);
		}
		for(Solution sol : h1Sols)
		{
			for(int i = 0; i < maxMoves.size(); i++)
			{
				if(sol.depth < maxMoves.get(i))
				{
					h1Moves.set(i, h1Moves.get(i) + 1);
				}
			}
		}
		
		ArrayList<Integer> h2Moves = new ArrayList<Integer>();
		while(h2Moves.size() != maxMoves.size())
		{
			h2Moves.add(0);
		}
		for(Solution sol : h2Sols)
		{
			for(int i = 0; i < maxMoves.size(); i++)
			{
				if(sol.depth < maxMoves.get(i))
				{
					h2Moves.set(i, h2Moves.get(i) + 1);
				}
			}
		}
		
		ArrayList<Integer> beamMoves = new ArrayList<Integer>();
		while(beamMoves.size() != maxMoves.size())
		{
			beamMoves.add(0);
		}
		for(Solution sol : beamSols)
		{
			for(int i = 0; i < maxMoves.size(); i++)
			{
				if(sol.depth < maxMoves.get(i))
				{
					beamMoves.set(i, beamMoves.get(i) + 1);
				}
			}
		}
		
		output = new StringBuilder();
		for(int i = 0; i < maxMoves.size(); i++)
		{
			output.append("Max Moves: " + maxMoves.get(i) + " | h1 solved: " + h1Moves.get(i) + " | h2 solved: " + h2Moves.get(i) + " | beam solved: " + beamMoves.get(i) +"\r\n");
		}
		System.out.println(output);
	}
	
	//main
	@SuppressWarnings("resource")
	public static void main(String[] args)
	{
		//initialize EightPuzzle
		EightPuzzle game = new EightPuzzle();
		
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
