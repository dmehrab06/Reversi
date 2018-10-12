import java.util.Scanner;

/**
 * An example class implementing Agent class for human players. 
 * The implementation is coupled with the actual game (here, TickTackToe) the agent is playing.
 * @author Azad
 *
 */
public class HumanTTTAgent extends Agent
{

	static Scanner in = new Scanner(System.in);
	public HumanTTTAgent(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void makeMove(Game game) {
		// TODO Auto-generated method stub
		
		int row,col;
		TickTackToe revGame = (Reversi) game;
		
		//er kono valid move na thakle pass // include korte hobe

		boolean first = true;
		do
		{
			if(first) 	System.out.println("Insert row and column ([0,2])");
			else System.out.println("Invalid input! Insert row and column ([0,2]) again.");
			row = in.nextInt();
			col = in.nextInt();
			first=false;
		}while(!revGame.isValidCell(row, col,role));
		
		revGame.updatestate(row,col,role);
	}


	

}
