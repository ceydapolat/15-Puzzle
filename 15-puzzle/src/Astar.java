import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Astar implements Search {
	//Astar class that creates the Astar search
	private BoardNode initialNode;
	private int i;

	Info info = new Info();

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


	Comparator<BoardNode> f3Comparator = new Comparator<BoardNode>() {
		@Override
		public int compare(BoardNode a, BoardNode b) {
			return (a.getMaxCost() + (manhattan(a) * manhattan(a)) - b.getMaxCost() - (manhattan(b) * manhattan(b)) );
		}
	};

		//Astar search which creates a priority queue which sorts according to h(n)
				if(this.i==1) {
					info.makePQueue(f1Comparator);
				}
				else if(this.i==2) {
					info.makePQueue(f2Comparator);
				}
				else{
					info.makePQueue(f3Comparator);

				}
				 //making a priority queue with one of the heuristics determine the Comparator
				BoardNode node = initialNode;
				info.pQueue.add(node);
				
				while(!(info.pQueue.isEmpty())) {
					node = info.pQueue.poll();
					info.incTime();
				//	info.visited.put(node.hashCode(), node);
					info.visited.put(node.getString(), node.getMaxCost());
					if(node.isGaol()) {
						PathActions p = new PathActions(initialNode,node, info); // class that creates a path from goal to start Node if goal is reached.
						p.printPath(); // the path is then printed
						return true;
					}
					
					Successor s = new Successor(); // Successor class created to provide next possible moves from current node
					List<BoardNode> list = s.successor(node); // list of potential children


					for(BoardNode temp: list) {
                        boolean ans= true;
                        if(info.visited.containsKey(temp.getString())){
                                ans=false;
                        }
                        if(ans) { //if it hasn't been expanded then we can now check if there is a node in the Priority Queue with a higher Cost

                            pqueueControl(temp);
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

    public  void pqueueControl(BoardNode o) {

        if (o != null) {
            List es = new ArrayList(info.pQueue);

            int currentmaxCost= o.getMaxCost();
            int n=info.pQueue.size();
            int i = n-1;
            boolean control =true;

            for(; i >0; i--) {

                BoardNode temp= (BoardNode) es.get(i);
                if(currentmaxCost-1 == temp.getMaxCost())
                    break;

                if(o.getString().equals(temp.getString())){
                    control=false;

                    if(temp.getMaxCost() > o.getMaxCost()){
                        info.pQueue.remove(temp);
                        info.pQueue.add(o);
                    }
                    break;
                }

            }
            if(control){
                info.pQueue.add(o);
                info.pQueueSize();

            }
        }
    }
}
