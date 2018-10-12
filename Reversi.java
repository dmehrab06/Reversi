/**
 * Example class extending Game class
 * @author Azad
 *
 */
public class Reversi extends Game 
{

	/**
	 * The actual game board
	 * -1 empty, 0 -> O, 1 -> X
	 */
	public int[][] board;
	int boardsize;
	/**
	 * First agent starts with O
	 * @param a
	 * @param b
	 */
	public Reversi(){
		boardsize = 0;
		name = "" ;
		agent = new Agent[2];
	}
	public Reversi(Agent a, Agent b, int sz) {
		super(a, b);
		// TODO Auto-generated constructor stub
		
		boardsize=sz;

		a.setRole(0); // The first argument/agent is always assigned O (0)
		b.setRole(1); // The second argument/agent is always assigned X (1)
					  // NOtice that, here first dows not mean that afent a will make the first move of the game.
					  // Here, first means the first argument of the constructor
					  // Which of a and b will actually give the first move is chosen randomly. See Game class
		
		name = "Reversi";
		
		board = new int[boardsize][boardsize];
		
	}

	public void Reversicopy(Reversi game){
		boardsize=game.boardsize;
		board = new int[boardsize][boardsize];
		for(int i = 0; i < boardsize; ++i){
			for(int j = 0 ; j < boardsize ; ++j){
				board[i][j]=game.board[i][j];
			}
		}
		name = game.name;
		agent[0] = game.agent[0];
		agent[1] = game.agent[1];
		name = game.name;
		random = game.random;
		winner = game.winner;
	}

	/**
	 * Called by the play method of Game class. It must update the winner variable. 
	 * In this implementation, it is done inside checkForWin() function.
	 */
	@Override
	boolean isFinished() {
		// TODO Auto-generated method stub
		if(checkForWin() != -1)
			return true;
		else if(!(hasValidMove(0) || hasValidMove(1)))
			return true; //game draw both are stuck
		else return false;
	}

	@Override
	void initialize(boolean fromFile) {
		// TODO Auto-generated method stub
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				board[i][j] = -1;
			}
		}
		board[boardsize/2][boardsize/2] = 0;
		board[boardsize/2-1][boardsize/2] = 1;
		board[boardsize/2][boardsize/2-1] = 1;
		board[boardsize/2-1][boardsize/2-1] = 0;
	}

	/**
	 * Prints the current board (may be replaced/appended with by GUI)
	 */
	@Override
	void showGameState() {
		// TODO Auto-generated method stub
		 
        System.out.println("-------------");
		
        for (int i = 0; i < boardsize; i++) 
        {
            System.out.print("| ");
            for (int j = 0; j < boardsize; j++) 
            {
            	if(board[i][j]==-1)
            		System.out.print("_" + " | ");
            	else if	(board[i][j]==0)
            		System.out.print( "W | ");
            	else
            		System.out.print( "B | ");
            }
            System.out.println();
            System.out.println("-------------");
        }
    }
	
    /** Loop through all cells of the board and if one is found to be empty (contains -1) then return false.
    	Otherwise the board is full.
    */
    public boolean isBoardFull() {

		
        for (int i = 0; i < boardsize; i++) 
        {
            for (int j = 0; j < boardsize; j++) 
            {
                if (board[i][j] == -1) 
                {
                   return false;
                }
            }
        }
		
        return true;
    }
	
	
    /** Returns role of the winner, if no winner/ still game is going on -1.
     * @return role of the winner, if no winner/ still game is going on -1.
     */
    public int checkForWin() 
    {
    	winner = null;
    	int winrole = -1;
    	if(isBoardFull()){
    		int white = 0;
    		int black = 0;
			for(int i = 0; i < boardsize; ++i){
				for(int j = 0; j<boardsize; ++j){
					if(board[i][j] == 0){
						white++;
					}
					else if(board[i][j] == 1){
						black++;
					}
				}
			}
			if(white > black){
				winrole = 0;
				winner = agent[winrole];
			}	
			else if(black > white){
				winrole = 1;
				winner = agent[winrole];
			}
			else{
				winrole=2;
			}
			return winrole;
    	}
    	return -1;
    }
	
    // Check to see if all three values are the same (and not empty) indicating a win.
    private boolean checkRowCol(int c1, int c2, int c3) 
    {
        return ((c1 != -1) && (c1 == c2) && (c2 == c3)); //eida ekhon porjonto kaaj e lage nai
    }
	
    public boolean withinbound(int row, int col){
    	if(row<0 || row>=boardsize || col<0 || col>=boardsize) return false;
    	return true;
    }
    public boolean isValiddir(int row, int col, int role, int dx, int dy){
		boolean nxtrolefound = false;
		int nxtx = row + dx;
		int nxty = col + dy;
		int curdis = 0;
		while(withinbound(nxtx,nxty)){
			if(board[nxtx][nxty] == role){
				nxtrolefound=true;
				break;
			}
			else if(board[nxtx][nxty] == (1-role) ){
				curdis++;
				nxtx += dx;
				nxty += dy;
			}
			else{
				break;
			}
		}
		if(nxtrolefound && (curdis > 0)){
			return true;
		}
		return false;
    }
	public boolean isValidCell(int row, int col, int role)
	{
		if(!withinbound(row,col)) return false;
		
		if(board[row][col]!=-1) return false;

		//ekta direction valid kina seita arek method e nia jaite hobe

		for(int dx = -1; dx <= 1; ++dx){
			for(int dy = -1 ; dy <= 1; ++dy){
				if(dx || dy){
					if(isValiddir(row,col,role,dx,dy)){
						return true;
					}
				} 
			}
		}

		return false;
	}
	public boolean updatestate(int row, int col, int role){
		for(int dx = -1; dx <= 1; ++dx){
			for(int dy = -1 ; dy <= 1; ++dy){
				if(dx || dy){
					if(isValiddir(row,col,role,dx,dy)){
						int nxtx=row+dx;
						int nxty=row+dy;
						while(withinbound(nxtx,nxty) && board[nxtx][nxty]!=role){
							board[nxtx][nxty]=role;
							nxtx+=dx;
							nxty+=dy;
						}
					}
				} 
			}
		}
		board[row][col]=role;
	}
	public boolean hasValidMove(int role){
		//checks whether the man with role role has a valid move
		//boolean fnd = false;
		for(int i = 0; i < boardsize; ++i){
			for(int j = 0; j < boardsize ; ++j){
				//search for an empty place first
				if(board[i][j] == -1){
					if(isValidCell(i,j,role)){
						return true;
					}
				}
			}
		}
		return false;
	}
	public int countrole(int role){
		int cnt=0;
		for(int i = 0; i < boardsize ; ++i){
			for(int j = 0; j < boardsize ; ++j){
				if(board[i][j]==role)cnt++;
			}
		}
		return cnt;
	}
	@Override
	void updateMessage(String msg) {
		// TODO Auto-generated method stub
		System.out.println(msg);
	}
	
}
