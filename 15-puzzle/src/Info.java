import java.util.*;


public class Info {
	public java.util.PriorityQueue<BoardNode> pQueue; // priority queue which holds prioritized nodes
	public int time;  // variable for calculating time complexity
	private int space; //variable for calculating space complexity
	HashMap visited = new HashMap();
	HashMap tempQueue = new HashMap();

	public Info() {
		pQueue = new java.util.PriorityQueue<BoardNode>();
		time = 0;
		space = 0;
	}
	
	public void  makePQueue(Comparator c) {   //creates a priority queue with a comparator as an argument to decide the order in which the queue will organize elements
		pQueue = new java.util.PriorityQueue<BoardNode>(c);
	}
	
	public void pQueueSize() {
		if(pQueue.size()> space) {
			space = pQueue.size();
		}
	}
	
	public int getTime() { //time is returned
		return time;
	}
	
	public int getSpace() {  //space is returned
		return space;
	}
	
}
