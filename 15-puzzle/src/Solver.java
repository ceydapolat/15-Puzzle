import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.event.ListSelectionEvent;

public class Solver {

	public static void main(String[] args) {
		
		// All the boards.

		// search waiting to be initialized
		Search search = null;
		
		// Simple UI which prompts the User for an algorithm and difficulty level
		boolean con = true;
		while(con==true)	{  //the loop keeps going till User says no
			System.out.println();
			System.out.println("Welcome to 8 puzzle");    //Below are the options asking User for which search and what difficulty to pick
			System.out.println("Please chose an Algorithm below:");
			System.out.println();
			System.out.println();

			System.out.println("1. UniformCost");
			System.out.println("2. A*");
			System.out.println("3. Iterative Lenghtening");
			System.out.println();
			Scanner scanner = new Scanner(System.in);
			int input = scanner.nextInt();

			System.out.println("Choose depth(2, 4, 6,..., 26, 28)");

			System.out.println();

			int optimumDepth = scanner.nextInt();
//			int [][] easy = {{1, 3, 14, 5}, {12, 15, 2, 4}, {11, 13, 7, 6}, {10, 9, 8, 0}};   //10

			int [][] easy = {{1, 2, 13, 4}, {12, 0, 14, 3}, {11, 15, 5, 6}, {10, 9, 8, 7}};   //4

//			int [][] easy = {{1, 2, 0, 4}, {13, 3, 14, 5}, {11, 12, 8, 6}, {15, 10, 9, 7}};   //4


	//		int [][] easy = initialStateBuilder(optimumDepth);
			BoardNode node = new BoardNode(easy);


			switch(input) {    //switch is used to determine what search and difficulty to use

			case 1:
				search = new UniformCost(node);
				break;
			case 2:  //final case for A* which provides options for both Heuristics
				System.out.println();
				System.out.println("Please pick a heuristic: ");
				System.out.println();
				System.out.println("1. Misplaced Tiles");
				System.out.println("2. Manhattan");
				System.out.println("3. New Heuristic Function");
				System.out.println();
				int input3 = scanner.nextInt();

				switch(input3){
					case 1:
						search = new Astar(node,1);
						break;
					case 2:
						search = new Astar(node,2);
						break;
					case 3:
						search = new Astar(node,3);
						break;

				}

				case 3:
					search = new IterativeLenghtening(node);
					break;
			}

			System.out.println("The search will begin: ");
			search.search(); //the search starts
			System.out.println("Do you want to continue?");
			System.out.println();
			System.out.println("1. Yes");
			System.out.println("2. No");
			int input5 = scanner.nextInt();
			if(input5==2) {
				con = false;
			}
		}
	}


	public static int[][] initialStateBuilder(int actionNumber){
		int state[][] = {{1,2,3,4},{12,13,14,5},{11,0,15,6},{10,9,8,7}};

		int col = 1;
		int row = 2;

		int randomNum;
		int previousDir = 0;
		boolean firstAction = true;
		int temp;

		for(int i = 0; i < actionNumber; i++){
			randomNum = ThreadLocalRandom.current().nextInt(0, 8);
			System.out.println(randomNum);

			if(row != 0 && randomNum == 0) {  //UP
				if(previousDir != 1){
					if(firstAction)
						firstAction = false;
					previousDir = randomNum;
					temp = state[row-1][col];
					state[row-1][col] = 0;
					state[row][col] = temp;
					row = row - 1;
				}
				else{
					i--;
				}
			}

			else if(row != 3 && randomNum == 1) {  //DOWN
				if(previousDir != 0 || firstAction){
					firstAction = false;
					previousDir = randomNum;
					temp = state[row+1][col];
					state[row+1][col] = 0;
					state[row][col] = temp;
					row = row + 1;
				}
				else{
					i--;
				}
			}

			else if(col != 3 && randomNum == 2) {  //RIGHT
				if(previousDir != 3){
					if(firstAction)
						firstAction = false;
					previousDir = randomNum;
					temp = state[row][col+1];
					state[row][col+1] = 0;
					state[row][col] = temp;
					col = col + 1;
				}
				else{
					i--;
				}
			}

			else if(col != 0 && randomNum == 3) {  //LEFT
				if(previousDir != 2){
					if(firstAction)
						firstAction = false;
					previousDir = randomNum;
					temp = state[row][col-1];
					state[row][col-1] = 0;
					state[row][col] = temp;
					col = col - 1;
				}
				else{
					i--;
				}
			}

			else if(row != 0 && col != 3 && randomNum == 4) {  //UPPER RIGHT
				if(previousDir != 7){
					if(firstAction)
						firstAction = false;
					previousDir = randomNum;
					temp = state[row-1][col+1];
					state[row-1][col+1] = 0;
					state[row][col] = temp;
					row = row - 1;
					col = col + 1;
				}
				else{
					i--;
				}
			}

			else if(row != 0 && col != 0 && randomNum == 5) {  //UPPER LEFT
				if(previousDir != 6){
					if(firstAction)
						firstAction = false;
					previousDir = randomNum;
					temp = state[row-1][col-1];
					state[row-1][col-1] = 0;
					state[row][col] = temp;
					row = row - 1;
					col = col - 1;
				}
				else{
					i--;
				}
			}

			else if(row != 3 && col != 3 && randomNum == 6) {  //LOWER RIGHT
				if(previousDir != 5){
					if(firstAction)
						firstAction = false;
					previousDir = randomNum;
					temp = state[row+1][col+1];
					state[row+1][col+1] = 0;
					state[row][col] = temp;
					row = row + 1;
					col = col + 1;
				}
				else{
					i--;
				}
			}

			else if(row != 3 && col != 0 && randomNum == 7) {  //LOWER LEFT
				if(previousDir != 4){
					if(firstAction)
						firstAction = false;
					previousDir = randomNum;
					temp = state[row+1][col-1];
					state[row+1][col-1] = 0;
					state[row][col] = temp;
					row = row + 1;
					col = col - 1;
				}
				else{
					i--;
				}
			}
			else{
				i--;
			}

		}

			for(int y = 0; y < state.length; y++){
				System.out.print(Arrays.toString(state[y]));
				System.out.println();
			}

		return state;
	}

	

	
}

	




