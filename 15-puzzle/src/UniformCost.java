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
//		info.tempQueue.put(node.getString(), node);  //temporary HashMap for pqueue


		while(!(info.pQueue.isEmpty())) {

			if (info.time % 5000 == 0)
				System.out.println("Current # of expanded nodes : " + info.time + "queue size: " + info.getSpace()) ;

			node = info.pQueue.poll();
//			info.tempQueue.remove(node);
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
				if(!info.visited.containsKey(temp.getString())){
						info.pQueue.add(temp);
						info.pQueueSize();
//
//					if(info.tempQueue.containsKey(temp.getString())){
//
//						BoardNode tempnode = (BoardNode) info.tempQueue.get(temp.getString());
//						if(temp.getMaxCost() < tempnode.getMaxCost()){
//							info.pQueue.remove(tempnode);
//							info.pQueue.add(temp);
//							info.tempQueue.put(temp.getString(), temp);
//
//						}
//
//
//					}
//					else{
//						info.pQueue.add(temp);
//						info.pQueueSize();
//						info.tempQueue.put(temp.getString(), temp);
//					}

//						pqueueControl(temp);


				}

			}
		}
		return false;
	}

//	public  void pqueueControl(BoardNode o) {
//
//		if (o != null) {
//			List es = new ArrayList(info.pQueue);
//
//			int currentmaxCost= o.getMaxCost();
//			int n=info.pQueue.size();
//			int i = n-1;
//			boolean control =true;
//
//			for(; i >0; i--) {
//
//				BoardNode temp= (BoardNode) es.get(i);
//				if(currentmaxCost-1 == temp.getMaxCost())
//					break;
//
//				if(o.getString().equals(temp.getString())){
//					control=false;
//
//					if(temp.getMaxCost() > o.getMaxCost()){
//						info.pQueue.remove(temp);
//						info.pQueue.add(o);
//					}
//					break;
//				}
//
//			}
//			if(control){
//				info.pQueue.add(o);
//				info.pQueueSize();
//
//			}
//		}
//	}


}