import java.util.*;

public class UniformCost implements Search {
	private BoardNode initialNode;
	private Info info = new Info(); //information about priority queue for uniform cost search

	public UniformCost(BoardNode node) {
		this.initialNode = node;
	}

	private class gComparator implements Comparator<BoardNode>{ //implemented compare method for priority queue

		public int compare(BoardNode a, BoardNode b) {
			return a.getMaxCost() - b.getMaxCost();
		}
	}

	public boolean search() {
		info.makePQueue(new gComparator()); //making a priority queue with gComparator
		BoardNode node = initialNode;
		info.pQueue.add(node);  //add first child to the priority queue
		info.tempQueue.put(node.getString(), node);  //add node to the temporary queue which checks whether there is node with bigger cost than current node

		while(!(info.pQueue.isEmpty())) { //iterate pQueue until it's empty

			if (info.time % 5000 == 0) //print current # of expanded node
				System.out.println("Current # of expanded nodes : " + info.time + "queue size: " + info.getSpace()) ;

			node = info.pQueue.poll(); //poll first element from the queue
			info.tempQueue.remove(node);
			info.time++; //increment time to calculate time complexity
			info.visited.put(node.getString(), node.getMaxCost()); //add polled node to the visited queue

			if(node.isGoal()) { // check to see if goal is reached
				BoardActions p = new BoardActions(initialNode,node,info); // class that creates a path from goal to start node
				p.printPath();  // the path is then printed
				return true;
			}

			Controller s = new Controller(); // controller class created to provide next possible moves from current node
			List<BoardNode> list = s.controller(node); // list of potential children

			for(BoardNode boardNode: list) {

				if(!info.visited.containsKey(boardNode.getString())){ //check if the node has been visited before
					if(info.tempQueue.containsKey(boardNode.getString())){
						BoardNode tempNode = (BoardNode) info.tempQueue.get(boardNode.getString());
						if(boardNode.getMaxCost() < tempNode.getMaxCost()){
							info.pQueue.remove(tempNode); // remove temporary node from temporary queue if current node has smaller cost
							info.pQueue.add(boardNode);   // add current node to the priority queue
							info.tempQueue.put(boardNode.getString(), boardNode); //add new node to the temporary queue
						}
					}
					else{ // if node is not visited before
						info.pQueue.add(boardNode); // add it to the pQueue
						info.pQueueSize();
						info.tempQueue.put(boardNode.getString(), boardNode); // add it to the tempQueue
					}
				}
			}
		}
		return false;
	}
}