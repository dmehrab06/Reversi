
/**
 * Example MiniMax agent extending Agent class.
 * Here, for simplicity of understanding min and max functions are written separately. One single function can do the task. 
 * @author Azad
 *
 */
public class MinimaxTTTAgent extends Agent
{
	int maxdepth;
	public MinimaxTTTAgent(String name, int mx) {
		super(name);
		maxdepth=mx;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void makeMove(Game game) {
		// TODO Auto-generated method stub
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Reversi revGame = (Reversi) game;
		CellValueTuple best = max(revGame,0);
		if(best.col!=-1)
		{
			revGame.updatestate(best.row,best.col,role);
		}
		
	}
	
	private CellValueTuple max(Reversi game, int depth)
	{	
		CellValueTuple maxCVT = new CellValueTuple();
		maxCVT.utility = -100000000;
		


		int winner = game.checkForWin();
		
		//terminal check
		if(winner == role)
		{
			maxCVT.utility = 1000000; //this agent wins
			return maxCVT;
		}
		else if(winner==(1-role)) 
		{
			maxCVT.utility = -1000000; //opponent wins
			return maxCVT;  
		}
		else if (game.isBoardFull() || depth==maxdepth)
		{
			int cc = game.countrole(role);
			maxCVT.utility=cc;
			return maxCVT;  
		}

		
		for (int i = 0; i < game.boardsize; i++) 
		{
			for (int j = 0; j < game.boardsize;j++)
			{
				if(!isValidCell(i,j,role)) continue; //eikhane valid kina dekhte hobe
				
				Reversi tmp = new Reversi();
				tmp.reversicpy(game);
				game.updatestate(i,j,role); //temporarily making a move 
				//eikhane huge change
				int v = min(game,depth+1).utility;
				if(v>maxCVT.utility)
				{
					maxCVT.utility=v;
					maxCVT.row = i;
					maxCVT.col = j;
				}
				game.reversicpy(tmp); // reverting back to original state
				//eiikhaneo huge change
				
			}
		}
		return maxCVT;
			
	}
	
	private CellValueTuple min(Reversi game, int depth)
	{	
		CellValueTuple minCVT = new CellValueTuple();
		minCVT.utility = 100000000;
		
		int winner = game.checkForWin();
		
		//terminal check
		if(winner == role)
		{
			minCVT.utility = 1000000; //max wins
			return minCVT;
		}
		else if(winner==(1-role)) 
		{
			minCVT.utility = -1000000; //min wins
			return minCVT;  
		}
		else if (game.isBoardFull() || depth==maxdepth)
		{
			int cc = game.countrole(role);
			minCVT.utility=cc;
			return minCVT;  
		}
		for (int i = 0; i < game.boardsize; i++) 
		{
			for (int j = 0; j < game.boardsize ;j++)
			{
				if(!isValidCell(i,j,1-role)) continue; //change again
				
				Reversi tmp = new Reversi();
				tmp.reversicpy(game);
				game.updatestate(i,j,1-role);
				//huge change

				int v = max(game,depth+1).utility;
				if(v<minCVT.utility)
				{
					minCVT.utility=v;
					minCVT.row = i;
					minCVT.col = j;
				}
				game.reversicpy(tmp);
				//again huge change
			}
		}
		return minCVT;
			
	}
	
	private int minRole()
	{
		if(role==0)return 1;
		else return 0;
	}

	class CellValueTuple
	{
		int row,col, utility;
		public CellValueTuple() {
			// TODO Auto-generated constructor stub
			row =-1;
			col =-1;
		}
	}

}
