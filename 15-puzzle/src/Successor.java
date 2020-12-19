import java.util.ArrayList;
import java.util.List;

public class Successor {

	String[] directions = {"UP", "RIGHT", "LEFT", "DOWN", "UPPER_RIGHT", "UPPER_LEFT", "LOWER_LEFT", "LOWER_RIGHT"};

	public Successor() {}
	
	public List<BoardNode> successor(BoardNode node) {
		//successor function that takes a state and returns a list of possible states that can be reached
		
		List<BoardNode> list = new ArrayList<BoardNode>();

		int row = node.getRowBlank();
		int col = node.getColBlank();

		//up

		if(row != 0) {  //uses information about the nature of 2d arrays to dictate the zero-tile's movement.
			BoardNode upNode = node.createChild(row-1, col,1);
			upNode.setDir(directions[0]); //UP
			list.add(upNode);

		}

		//left
		if(col != 0) {
			BoardNode leftNode = node.createChild(row, col-1, 1); // a child is created if the zero tile can move left.
			leftNode.setDir(directions[2]); //LEFT
			list.add(leftNode);
		}
		
		//down
		
		if(row != 3) {
			BoardNode downNode = node.createChild(row+1, col,1);
			downNode.setDir(directions[3]); //DOWN
			list.add(downNode);
		}
		
		//right
		
		if(col != 3) {
			BoardNode rightNode = node.createChild(row, col+1,1);
			rightNode.setDir(directions[1]); //RIGHT
			list.add(rightNode);
		}

		//upper right cross
		if((col != 3) && (row != 0)) {  //uses information about the nature of 2d arrays to dictate the zero-tile's movement.
			BoardNode upNode = node.createChild(row-1, col+1,3);
			upNode.setDir(directions[4]); //UPPER RIGHT
			list.add(upNode);
		}

		//upper left cross
		if((row != 0) && (col != 0)) {
			BoardNode rightNode = node.createChild(row-1, col-1,3);
			rightNode.setDir(directions[5]); //UPPER LEFT
			list.add(rightNode);
		}

		//lower left cross
		if((row != 3) && (col != 0)) {
			BoardNode rightNode = node.createChild(row+1, col-1,3);
			rightNode.setDir(directions[6]); //LOWER LEFT
			list.add(rightNode);
		}

		//lower right cross
		if((row != 3) && (col != 3)) {
			BoardNode rightNode = node.createChild(row+1, col+1,3);
			rightNode.setDir(directions[7]); //LOWER RIGHT
			list.add(rightNode);
		}
		
		return list;  // a list of children is returned.
		 
	}

}
