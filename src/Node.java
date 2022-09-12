
import java.util.ArrayList;

public class Node implements Comparable<Node> {

	
	private Node parent;
	private ArrayList<Integer> state; // = new ArrayList<Integer>(10);
	private int depth;
	private int evalResult;
	private ArrayList<Node> kids;
	
	/**
	 * Constructor for Node
	 * @param cState Current State of a node
	 * @param parNode Parent node to be refrenced
	 * @param goal Goal array to be initalized
	 */
	public Node(ArrayList<Integer> cState, Node parNode, ArrayList<Integer> goal) {
		this.parent = parNode;
		this.state = new ArrayList<Integer>(10);
		this.state = cState;
		if (parNode == null) {
		this.depth = 0;
		}
		else {
			this.depth = parent.getDepth() +1;
		}
		this.evalResult = misplacedTiles(goal)+depth;
		this.kids = new ArrayList<Node>();
	}
	
	/**
	 * Used to compare nodes in A* search
	 */
	public int compareTo(Node other){
		if(this.getEvalResult() < other.getEvalResult()){
			return -1;
			}
		else if (this.getEvalResult() == other.getEvalResult()){
			return 0;
			}
		else{
		return 1;
		}
	}
		

	/**
	 * @return String representation of a node
	 */
	public String toString() {
		String retString = "";
		for (int i = 0; i < this.state.size()-1; i++) {
			if (i<8) {
				retString = retString + this.state.get(i) + " ";
				if (i==2 || i==5)
					retString = retString + "\n";
			}
			else {
				retString = retString + this.state.get(i);
			}
		}
		return retString + "\n";
	}
	//isGoal int array
	
	/**
	 * 
	 * @param check Integer array to be comparead with current node
	 * @return Whether or not node is at goal state
	 */
	public boolean isGoal(ArrayList<Integer> check) {
		boolean stillTrue = true;//System.out.println(goals);
		for (int i = 0; i < check.size()-1; i++) {
			if (check.get(i)==this.state.get(i)) {
				stillTrue = true;
			}
			else {
				return false;
			}
		}
		return stillTrue;
	}
	
	
	//int findBlankMethod - walks state and returns index of 0
	/**
	 * Finds the blank spot in 8 tile puzzle
	 * @param state State to find blank spot
	 * @return integer location
	 */
	public int findBlankPos(ArrayList<Integer> state) {
		for(int i = 0; i < state.size(); i++) {
			if(state.get(i)==0)
				return i;
		}
		return -1;
	}
	
	/**
	 * Generates all subsequent children of called node
	 * @param goal Goal Node of current iteration used to calculate evalution results for A*
	 */
	public void generateKids(Node goal){
		//ArrayList<ArrayList<Integer>> kids = new ArrayList<>();
		
		
		int pos = findBlankPos(this.state);
		
		if (pos == 0) {
			
			ArrayList<Integer> R = new ArrayList<Integer>(10);
			R = (ArrayList<Integer>) this.state.clone();
			R.set(9, 0);
			
			ArrayList<Integer> D = new ArrayList<Integer>(10);
			D = (ArrayList<Integer>) this.state.clone();
			D.set(9, 0);
			
			R = swap(state, 0, 1, 11);
			Node right = new Node(R, this, goal.getState());
			//right.setDepth(0);
			//right.setParent(this);
			//right.setEvalResult(right.getDepth() + misplacedTiles(goal.getState()));
			
			D = swap(state, 0, 3, 12);
			Node down = new Node(D,this, goal.getState());
			//down.setDepth(1);
			//down.setParent(this);
			//down.setEvalResult(down.getDepth() + misplacedTiles(goal.getState()));
			
			kids.add(right);
			kids.add(down);
			}
		
		else if (pos == 1) {
			
			ArrayList<Integer> R = new ArrayList<Integer>(10);
			R = (ArrayList<Integer>) this.state.clone();
			R.set(9, 0);
			
			ArrayList<Integer> L = new ArrayList<Integer>(10);
			L = (ArrayList<Integer>) this.state.clone();
			L.set(9, 0);
			
			ArrayList<Integer> D = new ArrayList<Integer>(10);
			D = (ArrayList<Integer>) this.state.clone();
			D.set(9, 0);
			
			R = swap(state, 1, 2, 11);
			Node right = new Node(R,this, goal.getState());
			//right.setEvalResult(right.getDepth() + misplacedTiles(goal.getState()));
			
			L = swap(state, 1, 0, 13);
			Node left = new Node(L,this, goal.getState());
			//left.setEvalResult(left.getDepth() + misplacedTiles(goal.getState()));
			
			
			D = swap(state, 1, 4, 12);
			Node down = new Node(D,this, goal.getState());
			//down.setEvalResult(down.getDepth() + misplacedTiles(goal.getState()));
			
			kids.add(right);			
			kids.add(down);
			kids.add(left);
			
		}
		
		else if (pos == 2) {
			
			ArrayList<Integer> L = new ArrayList<Integer>(10);
			L = (ArrayList<Integer>) this.state.clone();
			L.set(9, 0);
			
			ArrayList<Integer> D = new ArrayList<Integer>(10);
			D = (ArrayList<Integer>) this.state.clone();
			D.set(9, 0);
			
			L = swap(state, 2, 1, 13);
			Node left = new Node(L,this, goal.getState());
			//left.setEvalResult(left.getDepth() + misplacedTiles(goal.getState()));
			
			D = swap(state, 2, 5, 12);
			Node down = new Node(D,this, goal.getState());
			//down.setEvalResult(down.getDepth() + misplacedTiles(goal.getState()));
			
			
			
			kids.add(down);
			kids.add(left);
		}
		
		else if (pos ==3) {
			ArrayList<Integer> R = new ArrayList<Integer>(10);
			R = (ArrayList<Integer>) this.state.clone();
			R.set(9, 0);
			
			ArrayList<Integer> D = new ArrayList<Integer>(10);
			D = (ArrayList<Integer>) this.state.clone();
			D.set(9, 0);
			
			ArrayList<Integer> U = new ArrayList<Integer>(10);
			U = (ArrayList<Integer>) this.state.clone();
			U.set(9, 0);
			
			R = swap(state, 3, 4, 11);
			Node right = new Node(R,this, goal.getState());
			//right.setEvalResult(right.getDepth() + misplacedTiles(goal.getState()));
			
			D = swap(state, 3, 6, 12);
			Node down = new Node(D,this, goal.getState());
			//down.setEvalResult(down.getDepth() + misplacedTiles(goal.getState()));
			
			
			U = swap(state, 3, 0, 10);
			Node up = new Node(U,this, goal.getState());
			//up.setEvalResult(up.getDepth() + misplacedTiles(goal.getState()));
			
			kids.add(up);
			kids.add(right);
			kids.add(down);
		
		}
		else if(pos==4) {
			ArrayList<Integer> R = new ArrayList<Integer>(10);
			R = (ArrayList<Integer>) this.state.clone();
			R.set(9, 0);
			
			ArrayList<Integer> L = new ArrayList<Integer>(10);
			L = (ArrayList<Integer>) this.state.clone();
			L.set(9, 0);
			
			ArrayList<Integer> D = new ArrayList<Integer>(10);
			D = (ArrayList<Integer>) this.state.clone();
			D.set(9, 0);
			
			ArrayList<Integer> U = new ArrayList<Integer>(10);
			U = (ArrayList<Integer>) this.state.clone();
			U.set(9, 0);
			
			R = swap(state, 4, 5, 11);
			Node right = new Node(R,this, goal.getState());
			//right.setEvalResult(right.getDepth() + misplacedTiles(goal.getState()));
			
			
			L = swap(state, 4, 3, 13);
			Node left = new Node(L,this, goal.getState());
			//left.setEvalResult(left.getDepth() + misplacedTiles(goal.getState()));
			
			D = swap(state, 4, 7, 12);
			Node down = new Node(D,this, goal.getState());
			//down.setEvalResult(down.getDepth() + misplacedTiles(goal.getState()));
			
			
			U = swap(state, 4, 1, 10);
			Node up = new Node(U,this, goal.getState());
			//up.setEvalResult(up.getDepth() + misplacedTiles(goal.getState()));
			
			
			this.kids.add(up);
			this.kids.add(right);
			this.kids.add(down);
			this.kids.add(left);
		}
		else if (pos == 5) {
			
			ArrayList<Integer> L = new ArrayList<Integer>(10);
			L = (ArrayList<Integer>) this.state.clone();
			L.set(9, 0);
			
			ArrayList<Integer> D = new ArrayList<Integer>(10);
			D = (ArrayList<Integer>) this.state.clone();
			D.set(9, 0);
			
			ArrayList<Integer> U = new ArrayList<Integer>(10);
			U = (ArrayList<Integer>) this.state.clone();
			U.set(9, 0);
			
			L = swap(state, 5, 4, 13);
			Node left = new Node(L,this, goal.getState());
			//left.setEvalResult(left.getDepth() + misplacedTiles(goal.getState()));
			
			
			D = swap(state, 5, 8, 12);
			Node down = new Node(D,this, goal.getState());
			//down.setEvalResult(down.getDepth() + misplacedTiles(goal.getState()));
			
			
			U = swap(state, 5, 2, 10);
			Node up = new Node(U,this, goal.getState());
			//up.setEvalResult(up.getDepth() + misplacedTiles(goal.getState()));
			
			
			kids.add(up);
			kids.add(down);
			kids.add(left);
		}
		else if (pos == 6) {
			ArrayList<Integer> R = new ArrayList<Integer>(10);
			R = (ArrayList<Integer>) this.state.clone();
			R.set(9, 0);
			
			
			ArrayList<Integer> U = new ArrayList<Integer>(10);
			U = (ArrayList<Integer>) this.state.clone();
			U.set(9, 0);
			
			R = swap(state, 6, 7, 11);
			Node right = new Node(R,this, goal.getState());
			//right.setEvalResult(right.getDepth() + misplacedTiles(goal.getState()));
			
			
			
			U = swap(state, 6, 3, 10);
			Node up = new Node(U,this, goal.getState());
			//up.setEvalResult(up.getDepth() + misplacedTiles(goal.getState()));
			
			
			kids.add(up);
			kids.add(right);
			
		}
		else if (pos == 7) {
			ArrayList<Integer> R = new ArrayList<Integer>(10);
			R = (ArrayList<Integer>) this.state.clone();
			R.set(9, 0);
			
			ArrayList<Integer> L = new ArrayList<Integer>(10);
			L = (ArrayList<Integer>) this.state.clone();
			L.set(9, 0);
			
			ArrayList<Integer> U = new ArrayList<Integer>(10);
			U = (ArrayList<Integer>) this.state.clone();
			U.set(9, 0);
			
			R = swap(state, 7, 8, 11);
			Node right = new Node(R,this, goal.getState());
			//right.setEvalResult(right.getDepth() + misplacedTiles(goal.getState()));
			
			
			L = swap(state, 7, 6, 13);
			Node left = new Node(L,this, goal.getState());
			//left.setEvalResult(left.getDepth() + misplacedTiles(goal.getState()));
			
			
			U = swap(state, 7, 4, 10);
			Node up = new Node(U,this, goal.getState());
			//up.setEvalResult(up.getDepth() + misplacedTiles(goal.getState()));
			
			
			kids.add(up);
			kids.add(right);
			kids.add(left);
			
		}
		else {
			
			ArrayList<Integer> L = new ArrayList<Integer>(10);
			L = (ArrayList<Integer>) this.state.clone();
			L.set(9, 0);
			
			ArrayList<Integer> U = new ArrayList<Integer>(10);
			U = (ArrayList<Integer>) this.state.clone();
			U.set(9, 0);
			
			L = swap(state, 8, 7, 13);
			Node left = new Node(L,this, goal.getState());
			//left.setEvalResult(left.getDepth() + misplacedTiles(goal.getState()));
			

			U = swap(state, 8, 5, 10);
			Node up = new Node(U,this, goal.getState());
			//up.setEvalResult(up.getDepth() + misplacedTiles(goal.getState()));
			
			
			kids.add(up);
			kids.add(left);
			
		}
		
		}

	/**
	 * Swaps position in node
	 * @param state curren state of node
	 * @param pos1 Pos to be swapped
	 * @param pos2 Pos to be swapped
	 * @param move What direction node state is being changed
	 * @return Integer array of newly swapped avlues
	 */
	public ArrayList<Integer> swap(ArrayList<Integer> state, int pos1, int pos2, int move){
		  ArrayList<Integer> newArray = (ArrayList<Integer>) this.state.clone();
		  int temp = newArray.get(pos1);
		  newArray.set(pos1, newArray.get(pos2));
		  newArray.set(pos2, temp);
		  newArray.set(9, move);// 10=U 11=R 12=D 13=L
		  return newArray;
		 
	 }
	
	/**
	 * Finds amount of misplaced tiles
	 * @param goal Goal state used to see amount of misplaced tiles
	 * @return Amount of misplaced tiles
	 */
	public int misplacedTiles(ArrayList<Integer> goal){
		int mpt = 0;
		for(int i = 0; i < goal.size()-1; i++) {
			if(this.state.get(i) != goal.get(i)) {
				mpt++;
			}
		}
		return mpt;
	}

	public Node getParent() {
		return parent;
	}
	
	public void setParent(Node parent) {
		this.parent = parent;
	}
	
	public ArrayList<Integer> getState() {
		return state;
	}
	
	public void setState(ArrayList<Integer> state) {
		this.state = state;
	}
	
	public int getDepth() {
		return depth;
	}
	
	public void setDepth(int depth) {
		this.depth = depth;
	}
	
	public int getEvalResult() {
		return evalResult;
	}
	
	public void setEvalResult(int evalResult) {
		this.evalResult = evalResult;
	}
	
	public ArrayList<Node> getKids() {
		return kids;
	}
	
	public void setKids(ArrayList<Node> kids) {
		this.kids = kids;
	}
	
	/**
	 * Gets which direction preivous node moved
	 * @return Char representing the direction moved
	 */
	public char getMove(){// 10=U 11=R 12=D 13=L
		if(state.get(state.size()-1) == 10){
			return 'U';
		}
		else if(state.get(state.size()-1) == 11){
			return 'R';
		}
		else if(state.get(state.size()-1) == 12){
			return 'D';
		}
		else if(state.get(state.size()-1) == 13){
			return 'L';
		}
		else{
			return '*';//dummy
		}
	}
	
	//f(n) = g(n) + h(n)
	//		depth + mpt

}