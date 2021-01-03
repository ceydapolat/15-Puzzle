import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BoardActions {

	private List<BoardNode> path; // path according to actions taken
	private Info info; //info object is used in order to print details about space and time

	public BoardActions(BoardNode initialNode, BoardNode goalNode, Info info) {
		path = this.getInitialPath(initialNode, goalNode);
		this.info = info;
	}


	
	private List<BoardNode> getInitialPath(BoardNode initialNode, BoardNode goalNode) {  //using the parent, the board was enabled to roll back from goal state to initial state
		BoardNode tempNode = goalNode;
		List<BoardNode> list = new ArrayList<BoardNode>();
		
		while(!(tempNode.equals(initialNode))) {
			list.add(tempNode);
			tempNode = tempNode.getParent();
			
		}
		list.add(initialNode);
		return list;
	}
	
	
	public void printPath() {   //print paths after actions done with details
		int size = path.size();

		for(int i= size-1;i>=0;i--) {
			System.out.println();
			System.out.println();
			System.out.println("Direction Moved: " + path.get(i).getDir());
			System.out.println("Depth: " + (path.get(i).getDepth()-1));
			System.out.println("Cost: " + path.get(i).getCost());
			System.out.println("MaxCost: " + path.get(i).getMaxCost());
			System.out.println();
			System.out.println("Current Node: \n");
			System.out.println(Arrays.deepToString(path.get(i).getMatrix()).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
			System.out.println();
		}
		System.out.println("Time Complexity: " + info.getTime());
		System.out.println("Space Complexity: " + info.getSpace());
	}
}
