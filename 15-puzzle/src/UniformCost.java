import java.util.*;

public class UniformCost implements Search {
	private BoardNode initialNode;
	Info info = new Info();

	public UniformCost(BoardNode node) {
		this.initialNode = node;
	}

	private class gComparator implements Comparator<BoardNode>{

		public int compare(BoardNode a, BoardNode b) {
			return a.getMaxCost() - b.getMaxCost();
		}
	}

	public boolean search() {
		//UniformCost search which creates a priority queue which sorts according to g(n)
		info.makePQueue(new gComparator()); //making a priority queue with gComparator
		BoardNode node = initialNode;
		info.pQueue.add(node);

		while(!(info.pQueue.isEmpty())) {

			node = info.pQueue.poll();
			info.incTime();
			info.visited.put(node.getString(), node.getMaxCost());

			if(node.isGaol()) {
				PathActions p = new PathActions(initialNode,node,info); // class that creates a path from goal to start Node if goal is reached.
				p.printPath(); // the path is then printed
				return true;
			}

			Successor s = new Successor(); // Successor class created to provide next possible moves from current node
			List<BoardNode> list = s.successor(node); // list of potential children

			for(BoardNode temp: list) {

				boolean ans= true;
				if(info.visited.containsKey(temp.getString())){
					if(temp.getMaxCost() >= (int)info.visited.get(temp.getString()))
						ans=false;
				}
				if(ans) { //if it hasn't been expanded then we can now check if there is a node in the Priority Queue with a higher Cost
					if(!(info.pQueue.contains(temp))){
						info.pQueue.add(temp);
						info.pQueueSize();
					}
				}
			}
		}
		return false;
	}



}