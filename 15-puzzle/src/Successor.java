import java.util.ArrayList;
import java.util.List;

public class Successor {

	String[] directions = {"UP", "RIGHT", "LEFT", "DOWN", "UPPER_RIGHT", "UPPER_LEFT", "LOWER_LEFT", "LOWER_RIGHT"};

	public Successor() {}
	
	public List<String []> successor(BoardNode node, int parentCost) {
		//successor function that takes a state and returns a list of possible states that can be reached
		
		List<String[]> list = new ArrayList<>();

		int row = node.getRowBlank();
		int col = node.getColBlank();

		//up

		if(row != 0) {  //uses information about the nature of 2d arrays to dictate the zero-tile's movement.
			String [] upNode = node.createStates(row-1, col,1+parentCost);
//			upNode.setDir(directions[0]); //UP
			list.add(upNode);

		}

		//left
		if(col != 0) {
			String [] leftNode = node.createStates(row, col-1, 1+parentCost); // a child is created if the zero tile can move left.
//			leftNode.setDir(directions[2]); //LEFT
			list.add(leftNode);
		}
		
		//down
		
		if(row != 3) {
			String [] downNode = node.createStates(row+1, col,1+parentCost);
//			downNode.setDir(directions[3]); //DOWN
			list.add(downNode);
		}
		
		//right
		
		if(col != 3) {
			String [] rightNode = node.createStates(row, col+1,1+parentCost);
//			rightNode.setDir(directions[1]); //RIGHT
			list.add(rightNode);
		}

		//upper right cross
		if((col != 3) && (row != 0)) {  //uses information about the nature of 2d arrays to dictate the zero-tile's movement.
			String [] upperRightNode = node.createStates(row-1, col+1,3+parentCost);
//			upNode.setDir(directions[4]); //UPPER RIGHT
			list.add(upperRightNode);
		}

		//upper left cross
		if((row != 0) && (col != 0)) {
			String [] upperLeftNode = node.createStates(row-1, col-1,3+parentCost);
//			rightNode.setDir(directions[5]); //UPPER LEFT
			list.add(upperLeftNode);
		}

		//lower left cross
		if((row != 3) && (col != 0)) {
			String [] lowerLeftNode = node.createStates(row+1, col-1,3+parentCost);
//			rightNode.setDir(directions[6]); //LOWER LEFT
			list.add(lowerLeftNode);
		}

		//lower right cross
		if((row != 3) && (col != 3)) {
			String [] lowerRightNode = node.createStates(row+1, col+1,3+parentCost);
//			rightNode.setDir(directions[7]); //LOWER RIGHT
			list.add(lowerRightNode);
		}
		
		return list;  // a list of children is returned.
		 
	}


}
