import java.util.Comparator;
import java.util.List;

import javax.swing.text.html.HTMLDocument.Iterator;



public class Astar implements Search {
	//Astar class that creates the Astar search
	private BoardNode initialNode;
	private int i;
	private int expandedNode;
	
	public Astar(BoardNode node, int i) {
		this.initialNode = node; 
		this.i = i; // this int value helps determine which heuristic will be used
	}

	
public boolean search() {

	Comparator<BoardNode> f1Comparator = new Comparator<BoardNode>() {
		@Override
		public int compare(BoardNode a, BoardNode b) {
			return (a.getMaxCost() + numCorPos(a)) - (b.getMaxCost()+numCorPos(b));
		}
	};

	Comparator<BoardNode> f2Comparator = new Comparator<BoardNode>() {
		@Override
		public int compare(BoardNode a, BoardNode b) {
			return (a.getMaxCost() + manhattan(a)) - (b.getMaxCost()+ manhattan(b));
		}
	};

		//Astar search which creates a priority queue which sorts according to h(n)
				Info info = new Info();
				if(this.i==1) {
					info.makePQueue(f1Comparator);
				}
				else {
					info.makePQueue(f2Comparator);
				}
				 //making a priority queue with one of the heuristics determine the Comparator
				BoardNode node = initialNode;
				info.pQueue.add(node);
				
				while(!(info.pQueue.isEmpty())) {
					node = info.pQueue.poll();
					info.incTime();
				//	info.visited.put(node.hashCode(), node);
					info.visited.add(node.getString());
					if(node.isGaol()) {
						PathActions p = new PathActions(initialNode,node,info,expandedNode); // class that creates a path from goal to start Node if goal is reached.
						p.printPath(); // the path is then printed
						return true;
					}
					
					Successor s = new Successor(); // Successor class created to provide next possible moves from current node
					List<BoardNode> list = s.successor(node); // list of potential children


					for(BoardNode temp: list) {
						boolean ans = info.visited.contains(temp.getString()); //Uses temporary node's hashCode to check if it has been expanded or not.
						if(ans==false) { //if it hasn't been expanded then we can now check if there is a node in the Priority Queue with a higher Cost
							if(!(info.pQueue.contains(temp))){
								info.pQueue.add(temp);
								info.pQueueSize();
								expandedNode++;
							}
						}
					}
				}
				return false;
			}

	//First Heuristic which tells us how many tiles are in an incorrect position

	public int numCorPos(BoardNode node) {
		int [][] goal ={{1,2,3,4},{12,13,14,5},{11,0,15,6},{10,9,8,7}};
		int result = 0;
		int [][] state = node.getMatrix();
		for(int i=0; i<state.length; i++) {
			for(int j=0; j<state.length; j++) {
				if(goal[i][j]!=state[i][j]) {
					result += 1;
				}
			}
		}
		return result;
	}

	public int manhattan(BoardNode node) {   //second heuristic which uses a goal state to help determined how far argument node tiles are from desired position
//		int [][]goal = {{1,2,3,4},{12,13,14,5},{11,0,15,6},{10,9,8,7}};
		int result = 0;
		int [][]state = node.getMatrix();
		for(int i=0; i<state.length; i++) {
			for(int j=0; j<state.length; j++) {
				int value = state[i][j];
				int maxValue = Math.max(Math.abs(i - node.getRow(value)), Math.abs(j - node.getCol(value)));
				result += maxValue;
			}
		}
		return result;
	}
}
