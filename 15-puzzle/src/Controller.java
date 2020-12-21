import java.util.ArrayList;
import java.util.List;

public class Controller {

	private String[] directions = {"UP", "RIGHT", "LEFT", "DOWN", "UPPER_RIGHT", "UPPER_LEFT", "LOWER_LEFT", "LOWER_RIGHT"}; //directions of actions

	public Controller() {}
	
	public List<BoardNode> controller(BoardNode node) { // controller function which takes a current state and lists the possible states according to actions done

		List<BoardNode> possibleActionList = new ArrayList<BoardNode>();

		int row = node.getRowBlank();
		int col = node.getColBlank();

		// do the movements

		//UP
		if(row != 0) {
			BoardNode upNode = node.createChild(row-1, col,1); // a child is created if the zero tile can move up
			upNode.setDir(directions[0]);
			possibleActionList.add(upNode);

		}

		//DOWN
		if(row != 3) {
			BoardNode downNode = node.createChild(row+1, col,1); // a child is created if the zero tile can move down
			downNode.setDir(directions[3]);
			possibleActionList.add(downNode);
		}

        //RIGHT
        if(col != 3) {
            BoardNode rightNode = node.createChild(row, col+1,1);
            rightNode.setDir(directions[1]);
            possibleActionList.add(rightNode);
        }

		//LEFT
		if(col != 0) {
			BoardNode leftNode = node.createChild(row, col-1, 1); // a child is created if the zero tile can move left
			leftNode.setDir(directions[2]);
			possibleActionList.add(leftNode);
		}


		//UPPER LEFT
		if((row != 0) && (col != 0)) {
			BoardNode rightNode = node.createChild(row-1, col-1,3); // a child is created if the zero tile can move upper left
			rightNode.setDir(directions[5]);
			possibleActionList.add(rightNode);
		}

		//LOWER RIGHT
		if((row != 3) && (col != 3)) {
			BoardNode rightNode = node.createChild(row+1, col+1,3); // a child is created if the zero tile can move lower right
			rightNode.setDir(directions[7]);
			possibleActionList.add(rightNode);
		}

		//LOWER LEFT
		if((row != 3) && (col != 0)) {
			BoardNode rightNode = node.createChild(row+1, col-1,3); // a child is created if the zero tile can move lower left
			rightNode.setDir(directions[6]);
			possibleActionList.add(rightNode);
		}

        //UPPER RIGHT
        if((col != 3) && (row != 0)) {
            BoardNode upNode = node.createChild(row-1, col+1,3); // a child is created if the zero tile can move upper right
            upNode.setDir(directions[4]);
            possibleActionList.add(upNode);
        }
		return possibleActionList;  // a list of children is returned
	}

}
