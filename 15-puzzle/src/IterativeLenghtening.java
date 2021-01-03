import java.util.*;

public class IterativeLenghtening {
    private BoardNode initialNode;
    private int limitCost = 1;
    boolean StartFromInitialNode=false;

    private Info info = new Info();


    public IterativeLenghtening(BoardNode node) {
        this.initialNode = node;
    }

    private class gComparator implements Comparator<BoardNode>{  //implemented compare method for priority queue

        public int compare(BoardNode a, BoardNode b) {
            return a.getMaxCost() - b.getMaxCost();
        }
    }

    public boolean search() {
        info.time++;
        info.makePQueue(new gComparator()); //making a priority queue with gComparator
        BoardNode node = initialNode;
        info.pQueue.add(node);    //add first child to the priority queue
        info.tempQueue.put(node.getString(), node);  //add node to the temporary queue which checks whether there is node with bigger cost than current node

        while(!(info.pQueue.isEmpty())) { //iterate pQueue until it's empty

            if (info.time % 5000 == 0) //print current # of expanded node
                System.out.println("Current # of expanded nodes : " + info.time + "queue size: " + info.getSpace()) ;

            if(!StartFromInitialNode ){
                node = info.pQueue.poll();   //poll first element from the queue
                info.tempQueue.remove(node.getString());
                info.time++;
                if(node.getMaxCost() == limitCost) { //if we reached to new cost depth, we increase limit
                    limitCost++;
                }
                else{
                    BoardNode node2 = info.pQueue.poll();
                    if(node2!=null)  {

                        if( node2.getMaxCost() == limitCost){
                            StartFromInitialNode= true;
                            if(!node.isGoal())
                              continue;
                        }
                        info.pQueue.add(node2);

                    }

                }
            }
            else{
                node = initialNode;
                info.pQueue.clear();
                info.tempQueue.clear();
                info.pQueue.add(node);    //add first child to the priority queue
                info.tempQueue.put(node.getString(), node);  //add node to the temporary queue which checks whether there is node with bigger cost than current node
                info.visited.clear();
                StartFromInitialNode=false;
                limitCost++;
                continue;
            }

            info.visited.put(node.getString(), node);  //add polled node to the visited queue

            if(node.isGoal()) {   // check to see if goal is reached
                BoardActions p = new BoardActions(initialNode,node,info); // class that creates a path from goal to start Node if goal is reached.
                p.printPath(); // the path is then printed
                return true;
            }
            else{
                if(StartFromInitialNode)
                    continue;
            }

            Controller s = new Controller();    // controller class created to provide next possible moves from current node
            List<BoardNode> list = s.controller(node); // list of potential children

//            int controlStartFromInitialNode=0;
            for(BoardNode temp: list) {
                if(!info.visited.containsKey(temp.getString())) { // if node is not visited before

                    if(temp.getMaxCost() <= limitCost) {// check if the path cost exceeds the limit cost
                        if (info.tempQueue.containsKey(temp.getString()) ) {  //check if the node is included in the Priority Queue
                            BoardNode tempNode = (BoardNode) info.tempQueue.get(temp.getString());
                            if (temp.getMaxCost() < tempNode.getMaxCost()) {
                                info.pQueue.remove(tempNode);   // remove temporary node from temporary queue if current node has smaller cost
                                info.pQueue.add(temp);          // add current node to the priority queue
                                info.tempQueue.put(temp.getString(), temp);  //add new node to the temporary queue
                            }

                        } else { //node is not included in the Priority Queue
                            info.pQueue.add(temp); // add it to the pQueue
                            info.pQueueSize();
                            info.tempQueue.put(temp.getString(), temp); // add it to the tempQueue

                        }
                    }

                }

            }

//            limitCost++; // increment limit cost
        }

        return false;
    }
}