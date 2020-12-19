import java.util.*;


public class Info {
	//info class that is used in every search
	public java.util.PriorityQueue<BoardNode> pQueue;
	public int time;
	private int maxQueueSize;
	HashMap visited = new HashMap();

	public Info() {
		pQueue = new java.util.PriorityQueue<BoardNode>();
		time = 0;
		maxQueueSize = 0;
	}
	
	public void  makePQueue(Comparator c) {   //creates a priority queue with a comparator as an argument to decide the order in which the queue will organize elements
		pQueue = new java.util.PriorityQueue<BoardNode>(c);
	}
	
	public void incTime() {  //timer method that begins timer
		time += 1;
	}

	public void pQueueSize() {  //behaves similar to queueSize() but for priority queue
		if(pQueue.size()>maxQueueSize) {
			maxQueueSize = pQueue.size();
		}
	}
	
	public int getTime() { //time is returned
		return time;
	}
	
	public int getSpace() {  //space is returned
		return maxQueueSize;
	}
	
}
