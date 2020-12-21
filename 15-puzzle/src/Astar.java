import java.util.Comparator;
import java.util.List;

public class Astar {
	private BoardNode initialNode;
	private int heuristic;

	private Info info = new Info(); //information about priority queue for a*

	public Astar(BoardNode node, int heuristic) {
		this.initialNode = node; 
		this.heuristic = heuristic; // this int value helps determine which heuristic will be used
	}
	
    public boolean search() {

        Comparator<BoardNode> f1Comparator = new Comparator<BoardNode>() { // implemented compare again for heuristic 1
            @Override
            public int compare(BoardNode a, BoardNode b) {
                return (a.getMaxCost() + misplacedTiles(a)) - (b.getMaxCost()+ misplacedTiles(b));
            }
        };

        Comparator<BoardNode> f2Comparator = new Comparator<BoardNode>() { // implemented compare again for heuristic 2
            @Override
            public int compare(BoardNode a, BoardNode b) {
                return (a.getMaxCost() + manhattan(a)) - (b.getMaxCost()+ manhattan(b));
            }
        };


        Comparator<BoardNode> f3Comparator = new Comparator<BoardNode>() { // implemented compare again for heuristic 3
            @Override
            public int compare(BoardNode a, BoardNode b) {
                return (a.getMaxCost() + (manhattan(a) * manhattan(a)) - b.getMaxCost() - (manhattan(b) * manhattan(b)) );
            }
        };

        // create priority queue according to selected heuristic function
        if(this.heuristic ==1)
            info.makePQueue(f1Comparator);
        else if(this.heuristic ==2)
            info.makePQueue(f2Comparator);
        else
            info.makePQueue(f3Comparator);

        //making a priority queue with one of the heuristics
        BoardNode node = initialNode;
        info.pQueue.add(node);   //add first child to the priority queue
        info.tempQueue.put(node.getString(), node);  //add node to the temporary queue which checks whether there is node with bigger cost than current node

        while(!(info.pQueue.isEmpty())) { //iterate pQueue until it's empty

            if (info.time % 5000 == 0)  //print current # of expanded node
                System.out.println("Current # of expanded nodes : " + info.time);

            node = info.pQueue.poll();   //poll first element from the queue
            info.tempQueue.remove(node);
            info.time++;  //increment time to calculate time complexity
            info.visited.put(node.getString(), node.getMaxCost());   //add polled node to the visited queue

            if(node.isGoal()) {   // check to see if goal is reached
                BoardActions p = new BoardActions(initialNode,node, info); // class that creates a path from goal to start Node if goal is reached.
                p.printPath(); // the path is then printed
                return true;
            }

            Controller s = new Controller();    // controller class created to provide next possible moves from current node
            List<BoardNode> list = s.controller(node); // list of potential children


            for(BoardNode temp: list) {
                if(!info.visited.containsKey(temp.getString())) {  //check if the node has been visited before
                    if (info.tempQueue.containsKey(temp.getString())) {
                        BoardNode tempNode = (BoardNode) info.tempQueue.get(temp.getString());
                        if (temp.getMaxCost() < tempNode.getMaxCost()) {
                            info.pQueue.remove(tempNode);   // remove temporary node from temporary queue if current node has smaller cost
                            info.pQueue.add(temp);          // add current node to the priority queue
                            info.tempQueue.put(temp.getString(), temp);  //add new node to the temporary queue
                        }
                    } else { // if node is not visited before
                        info.pQueue.add(temp); // add it to the pQueue
                        info.pQueueSize();
                        info.tempQueue.put(temp.getString(), temp); // add it to the tempQueue
                    }
                }
            }
        }
        return false;
	}

	private int misplacedTiles(BoardNode node) { 	// heuristic which tells us how many tiles are in an incorrect position
        int [][] goal ={{1,2,3,4},{12,13,14,5},{11,0,15,6},{10,9,8,7}};
		int result = 0;
		int [][] state = node.getMatrix();
		for(int i=0; i<state.length; i++) {
			for(int j=0; j<state.length; j++) {
			    if(state[i][j] == 0)
			        continue;
				if(goal[i][j]!=state[i][j]) {
					result += 1;
				}
			}
		}
		return result;
	}

	private int manhattan(BoardNode node) {   //heuristic which uses a goal state to help determined how far argument node tiles are from goal position
		int result = 0;
		int [][]state = node.getMatrix();
		for(int i=0; i<state.length; i++) {
			for(int j=0; j<state.length; j++) {
				int value = state[i][j];
//                if(value == 0)
//                    continue;

				int maxValue = Math.max(Math.abs(i - node.getRow(value)), Math.abs(j - node.getCol(value)));
				result += maxValue;
			}
		}
		return result;
	}
}