import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class IterativeLenghtening implements Search {
    private BoardNode initialNode;
    Info info = new Info();

    private int limitCost = 0;

    public IterativeLenghtening(BoardNode node) {
        this.initialNode = node;
    }

    private class gComparator implements Comparator<BoardNode>{

        public int compare(BoardNode a, BoardNode b) {
            return a.getMaxCost() - b.getMaxCost();
        }
    }

    public boolean search() {
        //Iterative lengthening search which creates a priority queue which sorts according to g(n)
        Info info = new Info();
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
                if(!info.visited.containsKey(temp.getString())) {//if it hasn't been expanded then we can now check if there is a node in the Priority Queue with a higher Cost

                    if (!(info.pQueue.contains(temp)) && temp.getMaxCost() <= limitCost) {
                        info.pQueue.add(temp);
                        info.pQueueSize();
                    }
                    limitCost++;
                }
            }


        }
        return false;
    }


}
