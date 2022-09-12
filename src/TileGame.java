import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class TileGame {

	//private static boolean verbose;
	File myObj;
	File myObjv;
	static Scanner sc;
	
	////C:\Users\1234z\eclipse-workspace\TileGame\src\test.txt
	public static void main(String[] args) throws FileNotFoundException {
		
		/*
		File myObj = new File(args[0]);
	    Scanner myReader = new Scanner(myObj);
	    String data = "";
	    while (myReader.hasNextLine()) {
	        data = data + myReader.nextLine() +"\n";
	        //System.out.println(data);
	      }
	    */
		
		boolean verbose = false;
		
		
	    ArrayList<ArrayList<Integer>> states = new ArrayList<ArrayList<Integer>>();
		
	    //Check for verbose and create Sacanner 
		
		if (args.length == 1) {
			//System.out.println("Worked");
			verbose = false;
			File myObj = new File(args[0]);
			sc = new Scanner(myObj);
		}
		else if (args.length == 2 && args[0].equals("-v")) {
			verbose = true;
			File myObj = new File(args[1]);
			sc = new Scanner(myObj);
		}
		else {
			System.out.println("Invalid arguments");
		}
		
		//Read data from file into arraylist along with adding 0 to end of each list
	
		int iter = 0;
		while (sc.hasNextLine()) {
			states.add(new ArrayList<Integer>());
			for (int i = 0; i < 9; i++) {
				states.get(iter).add(sc.nextInt());
			}
			states.get(iter).add(0);
			//}
			iter++;
			//states.get(iter).add(sc.nextLine().split(" "));
			
		}
		
		
		//System.out.println(states);
		/*
		ArrayList<Integer> initState = new ArrayList<Integer>(10);
		initState.add(1);
		initState.add(3);
		initState.add(5);
		initState.add(4);
		initState.add(2);
		initState.add(0);
		initState.add(7);
		initState.add(8);
		initState.add(6);
		initState.add(0);
		
		ArrayList<Integer> goalState = new ArrayList<Integer>(10);
		goalState.add(1);
		goalState.add(0);
		goalState.add(5);
		goalState.add(4);
		goalState.add(3);
		goalState.add(2);
		goalState.add(7);
		goalState.add(8);
		goalState.add(6);
		goalState.add(0);
		
		Node init = new Node(initState, null, goalState);
		Node goalNode = new Node(goalState, null, goalState);
		//System.out.println(init);
		//init.generateKids(goalNode);
		*/
		//Reading arraylists into nodes in order to call algorithms
		ArrayList<Node> callNodes = new ArrayList<Node>();
		for (int i = 0; i < states.size(); i++) {
			if (i < states.size()-1)
				callNodes.add(new Node(states.get(i), null, states.get(i+1)));
			else
				callNodes.add(new Node(states.get(i), null, states.get(i)));
		}
		//System.out.println(callNodes.size());
		
		//Calling algorithms
		for (int i = 0; i < callNodes.size(); i+=2) {// you were walking this wrong
			//System.out.println("new start :\n" + callNodes.get(i));//trying to find out why you were not getting correct start
			Node bfsoutput = BFS(callNodes.get(i), callNodes.get(i+1), verbose);
			System.out.println("BFS Path from root to goal: " + getPath(bfsoutput) + "\n\n");
			Node avoidbfsoutput = avoidingStatesBFS(callNodes.get(i), callNodes.get(i+1), verbose);
			System.out.println("Avoiding States BFS Path from root to goal: " + getPath(avoidbfsoutput) + "\n\n");
			Node avoidoutput = allowingRepeatedDFS(callNodes.get(i), callNodes.get(i+1), verbose);
			System.out.println("DFS Path from root to goal: " + getPath(avoidoutput) + "\n\n");
			Node output = DFS(callNodes.get(i), callNodes.get(i+1), verbose);
			System.out.println("Avoiding States DFS Path from root to goal: " + getPath(output) + "\n\n");
			Node aStaroutput = ASTAR(callNodes.get(i), callNodes.get(i+1), verbose);
			System.out.println("ASTAR Path from root to goal: " + getPath(aStaroutput) + "\n\n");
		}
		//verbose = true;
		//System.out.println(init.getKids());
		//Node goals = BFS(init, goalNode);
		//System.out.println(goals.getParent());
		//System.out.println("Path from root to goal: " + getPath(goals));
		
		//System.out.println("\n");
		//Node secGoals = DFS(init, goalNode);
		//System.out.println("Path from root to goal: " + getPath(secGoals));
		
		//Node thirdGoals = allowingRepeatedDFS(init, goalNode);
		//System.out.println("\nPath from root to goal: " + getPath(thirdGoals));
		

		
	}
	
	/**
	 * 
	 * @param inital Intial Node that represents the start state of the searching algorithm
	 * @param goal	Goal Node that represents the goal state of our seraching algorithm
	 * @param verbose Boolean to check whether verbose mode is active or not
	 * @return The goal node with attatched refrences to path from start state
	 */
	public static Node BFS(Node inital, Node goal, boolean verbose) {
		
		Queue<Node> que = new LinkedList<Node>();
		HashSet<Node> explored = new HashSet<Node>();
		que.add(inital);
		//explored.add(inital);
		
		int expandedNodes = 0;
		
		//int currDepth = 0;
		//int depth = 0;
		while (que.size() > 0) {
			Node currState = que.poll();
			expandedNodes++;//when removed you are expanded
			//System.out.println(currState);
			explored.add(currState);
			
			if (verbose) {
				System.out.println(currState.toString());
			}
			
			
			if (currState.isGoal(goal.getState())) {
				System.out.println("Nodes left on fringe: " + que.size());
				System.out.println("Num of expanded nodes: " + expandedNodes);
				System.out.println("Maximum nodes stored in memory: " + (que.size() + explored.size()));//parentheses matter
				return currState;
			}
			if(currState.getDepth() <= 10){
				currState.generateKids(goal);
				//System.out.println(currState.getKids());
				for(int i = 0; i < currState.getKids().size(); i++) {
					//if (!currState.getKids().contains(que.get(i))&& currState.getKids().contains(explored.)) {
					//if (!que.contains(currState.getKids().get(i)) && !explored.contains(currState.getKids().get(i))) {
					que.add(currState.getKids().get(i));
					//explored.add(currState.getKids().get(i));// you are explored when you are removed from the queue
					//expandedNodes++;
					
					//System.out.println(que);
				//}
				}
			//currDepth+=1;
			}
			}
		return null;
	}
	
/**
 * 
 * @param inital Intial Node that represents the start state of the searching algorithm
 * @param goal	Goal Node that represents the goal state of our seraching algorithm
 * @param verbose Boolean to check whether verbose mode is active or not
 * @return The goal node with attatched refrences to path from start state
 */
public static Node avoidingStatesBFS(Node inital, Node goal, boolean verbose) {
		
		Queue<Node> que = new LinkedList<Node>();
		HashSet<Node> explored = new HashSet<Node>();
		que.add(inital);
		
		int expandedNodes = 0;
		
		while (que.size() > 0) {
			Node currState = que.poll();
			expandedNodes++;//when removed you are expanded
			//System.out.println(currState);
			explored.add(currState);
			
			if (verbose) {
				System.out.println(currState.toString());
			}
			
			
			if (currState.isGoal(goal.getState())) {
				System.out.println("Nodes left on fringe: " + que.size());
				System.out.println("Num of expanded nodes: " + expandedNodes);
				System.out.println("Maximum nodes stored in memory: " + (que.size() + explored.size())); //parentheses matter
				return currState;
			}
			if(currState.getDepth() <= 10){
				currState.generateKids(goal);
				//System.out.println(currState.getKids());
				for(int i = 0; i < currState.getKids().size(); i++) {
					  
					if (!explored.contains(currState.getKids().get(i))) {
						que.add(currState.getKids().get(i));
					}
				}
			//currDepth+=1;
			}
			}
		return null;
	}
	
/**
 * 
 * @param init Intial Node that represents the start state of the searching algorithm
 * @param goal	Goal Node that represents the goal state of our seraching algorithm
 * @param verbose Boolean to check whether verbose mode is active or not
 * @return The goal node with attatched refrences to path from start state
 */
	public static Node DFS(Node init, Node goal, boolean verbose) {
		
		Stack<Node> stack = new Stack<Node>();
		HashSet<Node> explored = new HashSet<Node>();
		
		stack.add(init);
	
		int expandedNodes = 0;
		//max depth of goal braching factor*maxdepth
		while(!stack.isEmpty()) {
			//if(depth <= limit) {
				Node state = stack.pop();
				explored.add(state);
				expandedNodes++;
				
				if (verbose) {
					System.out.println(state.toString());
				}
				
				if (state.isGoal(goal.getState())) {
					System.out.println("Nodes left on fringe: " + stack.size());
					System.out.println("Num of expanded nodes: " + expandedNodes);
					System.out.println("Maximum nodes stored in memory: " + (stack.size() + explored.size()));
					return state;
				}
				if (state.getDepth() <= 10) {
				//System.out.println("Looking");
				state.generateKids(goal);
				
				for (int i = 0; i < state.getKids().size(); i++) {
					if (!stack.contains(state.getKids().get(i)) && !explored.contains(state.getKids().get(i))){
						//expandedNodes++;
						stack.push(state.getKids().get(i));
						
					}
				}
				//depth++;
				}
				
		//}
			//else {
				//System.out.println("Goal Node not found in depth limit!");
			//}
		}
		return null;
	}
	
	/**
	 * 
	 * @param init Intial Node that represents the start state of the searching algorithm
	 * @param goal	Goal Node that represents the goal state of our seraching algorithm
	 * @param verbose Boolean to check whether verbose mode is active or not
	 * @return The goal node with attatched refrences to path from start state
	 */
public static Node allowingRepeatedDFS(Node init, Node goal, boolean verbose) {
		
		Stack<Node> stack = new Stack<Node>();
		HashSet<Node> explored = new HashSet<Node>();
		
		stack.add(init);
	
		int expandedNodes = 0;
		//max depth of goal braching factor*maxdepth
		while(!stack.isEmpty()) {
			//if(depth <= limit) {
				Node state = stack.pop();
				explored.add(state);
				expandedNodes++;
				if (verbose == true) {
					System.out.println(state.toString());
				}
				
				
				if (state.isGoal(goal.getState())) {
					System.out.println("Nodes left on fringe: " + stack.size());
					System.out.println("Num of expanded nodes: " + expandedNodes);
					System.out.println("Maximum nodes stored in memory: " + (stack.size() + explored.size()));
					return state;
				}
				if (state.getDepth() <= 10) {
				//System.out.println("Looking");
				state.generateKids(goal);
				
				for (int i = 0; i < state.getKids().size(); i++) {
					//if (!stack.contains(state.getKids().get(i)) && !explored.contains(state.getKids().get(i))){
						
						stack.push(state.getKids().get(i));
						
					//}
				}

				}
				
		//}
			//else {
				//System.out.println("Goal Node not found in depth limit!");
			//}
		}
		return null;
	}

/**
 * 
 * @param init Intial Node that represents the start state of the searching algorithm
 * @param goal	Goal Node that represents the goal state of our seraching algorithm
 * @param verbose Boolean to check whether verbose mode is active or not
 * @return The goal node with attatched refrences to path from start state
 */
	public static Node ASTAR(Node init, Node goal, boolean verbose) {
		
		MinHeap<Node> heap = new MinHeap<Node>();
		//PriorityQueue<Node> heap = new PriorityQueue<Node>();
		HashSet<Node> explored = new HashSet<Node>();
		heap.insert(init);
		int expandedNodes = 0;
		explored.add(init);
		//System.out.println(heap);
		
		while (!heap.isEmpty()) {
			
			Node state = heap.removemin();
			expandedNodes++;
			
			explored.add(state);
			
			if (verbose) {
				System.out.println(state.toString());
			}
			
			if (state.isGoal(goal.getState())) {
				System.out.println("Nodes left on fringe: " + heap.heapsize());
				System.out.println("Num of expanded nodes: " + expandedNodes);
				System.out.println("Maximum nodes stored in memory: " + (heap.heapsize() + explored.size()));
				return state;
			}
			
			state.generateKids(goal);
			
			if (state.getDepth() <= 10) {
			for (int i = 0; i < state.getKids().size(); i++) {
				
				int cost = (state.getKids().get(i).misplacedTiles(goal.getState()) + state.getKids().get(i).getDepth());
				state.getKids().get(i).setEvalResult(cost);
				heap.insert(state.getKids().get(i));
				explored.add(state.getKids().get(i));
			}
			}
		}
		
		return goal;
		
	}
	
	/**
	 * 
	 * @param goal Goal node of current searching path used to trace back steps
	 * @return String path of given start to finish node path
	 */
	public static String getPath(Node goal) {
		
		//String move = goal.getState().remove(goal.getState().size()-1).toString();
		//String retString = goal.getState().toString();
		try {
		String path = "";
		while(goal.getParent() != null) {
			path = goal.getMove()+ " " + path;
			goal = goal.getParent();
			}
		return path;
		}
		catch (NullPointerException e) {
			return "Invalid Entry";
		}
			//goal.getState().remove(currArray.size()-1);
			/*int temp = goal.getState().get(9);
			String iString = "";
			if (temp == 0) {
				iString = goal.toString();
			}
			else if (temp == 10) {
				iString = "U";
			}
			else if (temp == 11)
				iString = "R";
			else if (temp==12)
				iString = "D";
			else
				iString = "L";
			
			retString = iString + ", " + retString;*/
	}
	
	
		
}
