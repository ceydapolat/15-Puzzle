import java.util.ArrayList;
import java.util.List;

public class BoardNode {
		
	private  int[][]  state;
	private List<BoardNode> children;
	private BoardNode parent;
	private int depth;
	private int blankRow;
	private int blankCol;
	private String direction;
	private String stringState;
	private int cost;
	private int maxCost;
	
	public BoardNode(int [][] state) {
		this.state = state;
		this.depth = 1;
		this.children = new ArrayList<BoardNode>(); //the children of the node
		this.parent = null;
		this.cost = 0;
		this.maxCost = 0;
		this.stringState = createStringStateBoard(state);
		this.direction = null;
		for(int i=0; i<=3; i++) {
			for(int j=0; j<=3; j++) {
				if(state[i][j]==0) {
					this.blankRow = i;
					this.blankCol = j;
					break;
				}
			}
		}
	}
	
	private String createStringStateBoard(int[][]  state) {   //method that returns a String version of the board
		StringBuilder sb = new StringBuilder();
		for (int i =0; i<state.length; i++) {
			for(int j = 0; j<state[i].length;j++ ) {
				sb.append(state[i][j]);
				sb.append("-");
			}
		}
		return sb.toString();
	}
	
	private void addChild(BoardNode child) { //adding a Child to the node
		child.setParent(this); //set parent of the child as this node
		child.setDepth(this.getDepth()+1); //increment the depth
		child.setPathCost(child.getCost());
		this.children.add(child);
	
	}
	
	private void setParent(BoardNode parent) {
		this.parent = parent;
	}
	
	private void setDepth(int depth) {
		this.depth = depth;
	}
	public int getDepth() {
		return depth;
	}
	
	public BoardNode getParent() {
		return parent;
	}
	
	public int getRowBlank() {
		return blankRow;
	}
	
	public int getColBlank() {
		return blankCol;
	}
	
	public int [][] getMatrix(){
		return state;
	}
	
	public int getCost() {
		return this.cost;
	}
	
	public BoardNode createChild(int a, int b, int dirCost) { //creating the child or possible states from current node
		int[][] temp = new int[state.length][state.length];

		for(int i=0; i<state.length; i++)
			System.arraycopy(state[i], 0, temp[i], 0, state[i].length);

		temp[blankRow][blankCol] = temp[a][b];
		int cost = dirCost;
		temp[a][b] = 0;
		BoardNode child = new BoardNode(temp); //create child as a node object
		child.setCost(cost);
		addChild(child); // add child to the parent
		return child;
	}
	
	public void setDir(String d) {				//setting the Direction moved
		this.direction = d;
	}

	public String getDir() {				//getting the direction moved
		return direction;
	}
	
	public boolean isGoal() { // Check if goal state has been reached
		boolean result;
		int [][] goal = {{1,2,3,4},{12,13,14,5},{11,0,15,6},{10,9,8,7}}; //goal state which we want to reach

		result = this.getString().equals(createStringStateBoard(goal));

		return result;
	}
	
	public String getString() {			//getting String version of Board
		return stringState;
	}
	
	private void setCost(int i) {
		this.cost = i;
	}

	private void setPathCost(int i) { //set path cost of the current child node
		this.maxCost = this.getParent().getMaxCost() + i;
	}
	
	public int getMaxCost() { //getting the current MaxCode to get to current Node
		return maxCost;
	}
	
	public int getRow(int value) {		// get row value of the given node value
		int row = 0;
		int [][] goal = {{1,2,3,4},{12,13,14,5},{11,0,15,6},{10,9,8,7}};
		for(int i=0; i<=3; i++) {
			for(int j=0; j<=3; j++) {
				if(goal[i][j]==value) {
					row = i;
				}
			}
		}
			return row;
	}

	public int getCol(int value) {		// get the column value of the given node value
		int col = 0;
		int [][] goal = {{1,2,3,4},{12,13,14,5},{11,0,15,6},{10,9,8,7}};
		for(int i=0; i<=3; i++) {
			for(int j=0; j<=3; j++) {
				if(goal[i][j]==value) {
					col = j;
				}
			}
		}
			return col;
	}

	
	
}
