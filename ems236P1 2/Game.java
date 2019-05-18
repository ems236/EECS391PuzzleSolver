import java.util.*;

public abstract class Game 
{
	protected State state;
	protected int maxNodes = 0;
	protected boolean debug = false;
	
	//Reads a text command and executes the correct method
	public void executeInput(String input)
	{
		StringBuilder s = new StringBuilder();
		int i = 0;
		while(i < input.length() && input.charAt(i) != ' ')
		{
			s.append(input.charAt(i));
			i++;
		}
		
		if(s.toString().equals("solve"))
		{
			//add another word
			s.append(" ");
			i++;
			while(i < input.length() && input.charAt(i) != ' ')
			{
				s.append(input.charAt(i));
				i++;
			}
		}
		
		String command = s.toString();
		//set to next non-space char
		//-1 prevents some exceptions being thrown and should never really matter.
		if(i < input.length() - 1)
		{
			i++;
		}
		
		//try every command.
		//custom commands
		if(command.equals("debug"))
		{
			if(!debug)
			{
				System.out.println("Setting debug to true.  This will make the search algorithms print a lot more output.");
				debug = true;
			}
			else
			{
				System.out.println("Setting debug to false.  This limits how much the algorithms print.");
				debug = false;
			}
			
		}
		
		else if(command.equals("solveAllAStar"))
		{
			System.out.println("Trying every puzzle with A* and heuristic " + input.substring(i));
			tryAllStatesAStar(getHeuristicByName(input.substring(i)), null);
		}
		
		else if(command.equals("solveAllBeam"))
		{
			System.out.println("Trying every puzzle with beam and k " + input.substring(i));
			//test if a real int.
			Integer beamWidth;
			try
			{
				beamWidth = Integer.parseInt(input.substring(i));
			}
			catch(Exception e)
			{
				System.out.println("Error parsing integer value. Defaulting to 10");
				beamWidth = 10;
			}
			tryAllStatesBeam(beamWidth, null);
		}
		
		//specified in assignment
		else if(command.equals("setState"))
		{
			System.out.println("Setting state to " + input.substring(i));
			setState(input.substring(i));
		}
		
		else if(command.equals("randomizeState"))
		{
			System.out.println("Randomizing state " + input.substring(i) + " times.");
			//Make sure a real int is passed.
			Integer randTimes;
			try
			{
				randTimes = Integer.parseInt(input.substring(i));
			}
			catch(Exception e)
			{
				System.out.println("Error parsing integer value. Defaulting to 100");
				randTimes = 100;
			}
			randomizeState(randTimes, getAllMoves());
		}
		
		else if(command.equals("printState"))
		{
			System.out.println("Printing State:");
			printState();
		}
		
		else if(command.equals("move"))
		{
			System.out.println("Making move: " + input.substring(i));
			makeMove(getMoveByName(input.substring(i)));
		}
		
		else if(command.equals("solve A-star"))
		{
			System.out.println("Solving A-Star with heuristic " + input.substring(i));
			solveAStar(getHeuristicByName(input.substring(i)), getAllMoves(), true);
		}
		
		else if(command.equals("solve beam"))
		{
			System.out.println("Solving beam with k = " + input.substring(i));
			//test if a real int.
			Integer beamWidth;
			try
			{
				beamWidth = Integer.parseInt(input.substring(i));
			}
			catch(Exception e)
			{
				System.out.println("Error parsing integer value. Defaulting to 10");
				beamWidth = 10;
			}
			solveBeam(beamWidth, getAllMoves(), true);
		}
		
		else if(command.equals("maxNodes"))
		{
			System.out.println("Setting max nodes to " + input.substring(i));
			//test if a real int.
			Integer maxNodes;
			try
			{
				maxNodes = Integer.parseInt(input.substring(i));
			}
			catch(Exception e)
			{
				System.out.println("Error parsing integer value. Defaulting to 10000");
				maxNodes = 10000;
			}
			maxNodes(maxNodes);
		}
		
		else
		{
			System.out.println("Command not recognized (" + input + ")");
		}
	}
	
	//Abstract methods
	public abstract Move getMoveByName(String name);
	public abstract Heuristic getHeuristicByName(String name);
	public abstract Heuristic getDefaultHeuristic();
	public abstract List<Move> getAllMoves();
	public abstract int tryAllStatesAStar(Heuristic h, List<Solution> moveCounter);
	public abstract int tryAllStatesBeam(int width, List<Solution> moveCounter);
	
	//Other methods
	//State setters
	public void setState(String stateString)
	{
		state.setWithString(stateString);
	}
	
	public void setState(State state)
	{
		this.state = state.copy();
	}
	
	public void randomizeState(int n, List<Move> moves)
	{
		state.setGoalState();
		state = State.randomizeState(state, moves, n, null);
	}
	
	public void makeMove(Move m)
	{
		if(!m.isValid(state))
		{
			System.out.println("Invalid move, no change");
		}
		else
		{
			state = m.move(state);
		}
	}
	
	public void printState()
	{
		state.print();
	}
	
	//Search control
	//returns #of nodes explored, -1 if not found.
	public Solution solveAStar(Heuristic heuristic, List<Move> moves, boolean shouldPrint)
	{
		PriorityQueue<SearchNode> pQueue = new PriorityQueue<SearchNode>();
		pQueue.add(new SearchNode(state, heuristic.calculate(state)));
		
		//map to help ignore duplicates
		Map<Integer, Boolean> explored = new HashMap<Integer, Boolean>();
		
		//will stare goal node
		SearchNode goal = null;
		int nodesConsidered = 0;
		boolean found = false;
		while(!found && nodesConsidered < maxNodes)
		{
			//Get next unexplored node from heap
			SearchNode node = pQueue.poll();
			while(explored.containsKey(node.getState().getHash()))
			{
				node = pQueue.poll();
				if(node == null)
				{
					System.out.println("Error, queue was empty.");
					return new Solution(-1, nodesConsidered);
				}
			}
			
			//add state to list of explored states
			explored.put(node.getState().getHash(), true);
			
			//debug
			if(debug)
			{
				node.getState().print();				
				System.out.println("Moves :" + node.getMoves());
				System.out.println("Heuristic :" + heuristic.calculate(node.getState()));
				System.out.println("Path cost :" + node.getCostToHere() + "\r\n");
			}
			
			if(!node.getState().isGoalState())
			{
				//Make every move from this state
				for(Move move : moves)
				{
					//check for valid.
					if(move.isValid(node.getState()) )
					{
						State nextState = move.move(node.getState());
						
						//Check if move already explored
						if(!explored.containsKey(nextState.getHash()))
						{
							List<String> nextMoves = node.getMoves();
							nextMoves.add(move.getName());
							pQueue.add(
								new SearchNode(
									nextMoves
									, nextState
									, heuristic.calculate(nextState)
									, node.getCostToHere() + 1
								)
							);
						} //end if is unexplored
					} //end if is valid
				} //end for
			} //end if is goal state
			
			else
			{
				//found goal state
				found = true;
				goal = node;
			}
			
			nodesConsidered++;
		} //end while
		
		//do whatever with the goal state here
		if(goal != null)
		{
			if(shouldPrint)
			{
				System.out.println("Nodes considered: " + nodesConsidered);
				System.out.println(goal.getCostToHere() + " moves to the goal.");
				StringBuilder output = new StringBuilder();
				output.append("Moves sequence: ");
				String prefix = "";
				for(String move : goal.getMoves())
				{
					output.append(prefix);
					output.append(move);
					prefix = ", ";
				}
			
				System.out.println(output);
			}
			
			return new Solution(goal.getMoves().size(), nodesConsidered);
		}
		
		else
		{
			if(shouldPrint)
			{
				System.out.println("Goal was not found within " + maxNodes + " nodes");
			}
			return new Solution(-1, nodesConsidered);
		}
		
		
	} //end method
	
	
	public Solution solveBeam(int k, List<Move> moves, boolean shouldPrint)
	{
		//use h2 as an evaluation func
		Heuristic heuristic = getDefaultHeuristic();
		
		//Use priority queue to sort successor states, empty it at each step.
		PriorityQueue<SearchNode> pq = new PriorityQueue<SearchNode>();
		//stores k nodes at this step of beam.
		List<SearchNode> nodes = new ArrayList<SearchNode>();
		
		//Helps prevent loops.
		Map<Integer, Boolean> explored = new HashMap<Integer, Boolean>();
		
		//number of moves to make from the initial state to diversify initial states 
		int randVal = 10;
		
		SearchNode goal = null;
		boolean found = false;
		int iterationCount = 0;
				
		//generate k initial states
		for(int i = 0; i < k && !found; i++)
		{
			//store history at each node.
			List<String> history = new ArrayList<String>();
			//first one doesn't have to be randomized.
			State next;
			if(i == 0)
			{
				next = state.copy();
			}
			else
			{
				next = State.randomizeState(state, moves, randVal, history);
			}
			
			SearchNode newNode = new SearchNode(history, next, heuristic.calculate(next));
			if(newNode.getState().isGoalState())
			{
				//Goal found, break loop.
				goal = newNode;
				found = true;
			}
			
			else
			{
				nodes.add(newNode);
			}
		}
		
		//set max number of repeated moves to make in a row.
		int maxRepeat = 25;
		int repeats = 0;
		
		while(!found && repeats < maxRepeat)
		{
			if(debug)
			{
				System.out.println("------------------\r\n iteration: " + iterationCount);
			}
			
			for(int i = 0; i < nodes.size() && !found; i++)
			{
				SearchNode node = nodes.get(i);
				State current = node.getState();
				
				//test if first move is a repeat.  non-repeated moves are prioritized when picking next k states.
				if(i == 0)
				{
					repeats = explored.containsKey(current.getHash()) ? repeats + 1 : 0;
				}
				
				//add state to explored
				explored.put(current.getHash(), true);
				
				//debug
				if(debug)
				{
					current.print();
					System.out.println("Moves here: " + node.getMoves());
					System.out.println("value: " + heuristic.calculate(current) + "\r\n");
				}
				
				//make every move and add to heap.
				for(int j = 0; j < moves.size() && !found; j++)
				{
					Move move = moves.get(j);
					if(move.isValid(current))
					{
						State next = move.move(current);
						List<String> history = node.getMoves();
						history.add(move.getName());
						SearchNode newNode = new SearchNode(history, next, heuristic.calculate(next));
						
						if(next.isGoalState())
						{
							found = true;
							goal = newNode;
						}
						
						else
						{
							pq.add(newNode);
						}
						
					}//end if isvalid
				}//end for each move
			}//end for each node
			
			//get top k from queue and add to explored list.
			//set up next step of beam.
			if(!found)
			{
				nodes.clear();
				//Store explored nodes if not enough new ones.
				Queue<SearchNode> exploredTemp = new LinkedList<SearchNode>();
				
				boolean outofNodes = false;
				
				int i = 0;
				while(i < k)
				{
					SearchNode nextNode;
					if(!outofNodes)
					{
						nextNode = pq.poll();
						if(nextNode == null)
						{
							outofNodes = true;
							nextNode = exploredTemp.poll();
						}
					}
					else
					{
						nextNode = exploredTemp.poll();
						if(nextNode == null)
						{
							if(shouldPrint)
							{
								System.out.println("Out of moves. Failed after " + iterationCount + " iterations.");
							}
							return new Solution(-1, iterationCount * k);
						}
					}	
					
					if(outofNodes || !explored.containsKey(nextNode.getState().getHash()))
					{
						nodes.add(nextNode);
						i++;
					}
					//node is explored but should store it temporarily in case heap runs out of new nodes.
					else
					{
						exploredTemp.add(nextNode);
					}
				}//end while (found k nodes to add.)
				pq.clear();
			}// end if (done setting up next iteration.)
			
			iterationCount++;
		}//end while !found.
		
		//process goal:
		if(!found)
		{
			if(shouldPrint)
			{
				System.out.println("Failed to find goal in " + iterationCount + " iterations of k successor states");
				System.out.println("Moved to already explored states " + maxRepeat + " times in a row.  Beam stuck at local max.");
			}
			return new Solution(-1, iterationCount * k);
		}
		else
		{
			if(shouldPrint)
			{
				StringBuilder output = new StringBuilder();
			
				output.append("Goal found in " + iterationCount + " iterations of k states(" + goal.getMoves().size() + " moves)\r\n");
				output.append("Moves sequence: ");
				String prefix = "";
				for(String move : goal.getMoves())
				{
					output.append(prefix);
					output.append(move);
					prefix = ", ";
				}
				
				System.out.println(output);
			}
			return new Solution(goal.getMoves().size(), iterationCount * k);
		}
	}
	
	public void maxNodes(int n)
	{
		maxNodes = n;
	}
}
