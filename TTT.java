package Package;

import java.util.*;
import java.lang.*;

public class TTT 
{
	//Printing the board
	private static void printBoard(char[] board)
	{
		System.out.println(board[0] + "|" + board[1] + "|" + board[2]);
		
		System.out.println("-----");
		
		System.out.println(board[3] + "|" + board[4] + "|" + board[5]);
		
		System.out.println("-----");
		
		System.out.println(board[6] + "|" + board[7] + "|" + board[8]);
	}
	
	//Placing the move of player and computer
	private static void placeMove(char[] board,int position,char symbol)
	{
		board[position-1]=symbol;
	}
	
	//Checking if the position is valid or not
	private static Boolean isValidMove(char[] board,int position)
	{
		if(position>=1 && position<=9)
			return(board[position-1]==' ');
		
		return false;
		
	}
	
	//Finding the best move for computer(maximizer)
	private static int findBestMove(char[] board)
	{
		int bestVal=Integer.MIN_VALUE;
		int bestMove=0;
		
		int i;
		
		for(i=1;i<=9;i++)
		{
			if(isValidMove(board,i))
			{
				board[i-1]='0';
				
				int moveVal=minimax(board,0,false);
				
				board[i-1]=' ';
				
				if(moveVal>bestVal)
				{
					bestVal=moveVal;
					bestMove=i;
				}
			}
		}	
		
		return bestMove;
	}
	
	//MiniMax algorithm to consider all the possible ways and give the best value for that move
	private static int minimax(char[] board , int depth , boolean isMax)
	{
		int score=evaluate(board);
		
		if(score==10 || score==-10)
			return score;
		
		if (isMovesLeft(board) == false)
	        return 0;
		
		if(isMax)
		{
			int best=Integer.MIN_VALUE;
			
			for(int i=1;i<=9;i++)
			{
				if(isValidMove(board,i))
				{
					board[i-1]='0';
					
					best=Math.max(best, minimax(board,depth+1,false));
					
					board[i-1]=' ';
				}
			}
			return best;
		}
		else
		{
            int best=Integer.MAX_VALUE;
			
			for(int i=1;i<=9;i++)
			{
				if(isValidMove(board,i))
				{
					board[i-1]='X';
					
					best=Math.min(best, minimax(board,depth+1,true));
					
					board[i-1]=' ';
				}
			}
			return best;
		}
		
	}
	
	//Evaluating score of the board
	private static int evaluate(char[] board)
	{
	
			if(board[0]==board[1] && board[1]==board[2])
			{
				if(board[0]=='0')
					return 10;
				if(board[0]=='X')
					return -10;
			}
			if(board[3]==board[4] && board[4]==board[5])
			{
				if(board[3]=='0')
					return 10;
				if(board[3]=='X')
					return -10;
			}
			if(board[6]==board[7] && board[7]==board[8])
			{
				if(board[6]=='0')
					return 10;
				if(board[6]=='X')
					return -10;
			}if(board[0]==board[3] && board[3]==board[6])
			{
				if(board[0]=='0')
					return 10;
				if(board[0]=='X')
					return -10;
			}if(board[1]==board[4] && board[4]==board[7])
			{
				if(board[1]=='0')
					return 10;
				if(board[1]=='X')
					return -10;
			}if(board[2]==board[5] && board[5]==board[8])
			{
				if(board[2]=='0')
					return 10;
				if(board[2]=='X')
					return -10;
			}
			if(board[0]==board[4] && board[4]==board[8])
			{
				if(board[0]=='0')
					return 10;
				if(board[0]=='X')
					return -10;
			}
			if(board[2]==board[4] && board[4]==board[6])
			{
				if(board[2]=='0')
					return 10;
				if(board[2]=='X')
					return -10;
			}
			
			// Else if none of them have won
			return 0;
	    
	}
	
	//Checking for any move left or not for minimax to move further after 1 try move
	static Boolean isMovesLeft(char board[])
	{
	    for (int i = 0; i <9; i++)
	            if (board[i] == ' ')
	                return true;
	    
	    return false;
	}
	
	//Getting a position from player and placing it after validation
	private static void playerTurn(char[] board,Scanner inp)
	{
		while(true)
		{
			System.out.println("\nEnter your move: ");
			
			int position=inp.nextInt();
			
			if(isValidMove(board,position))
			{
				placeMove(board,position,'X');
				printBoard(board);
				break;
			}
			else
			{
				System.out.println("\nInvalid move!");
			}
		}
	}
	
	//Randomly choosing a position for computer's move and placing it after validation
	private static void computerTurn(char[] board)
	{
		/*
		Random rand=new Random();
		
		while(true)
		{
		  int position=rand.nextInt(9)+1;
		  
		  if(isValidMove(board,position))
		  {
			  System.out.println("Computer choose: " + position);
			  
			  placeMove(board,position,'0');
			  printBoard(board);
			  break;
		  }
		}	
		*/
		
		int bestMove= findBestMove(board);
		
		System.out.println("\nComputer choose: " + bestMove);
		
		placeMove(board,bestMove,'0');
		printBoard(board);
	}
	
	//Game ends with either a win or if no empty position is left
	private static Boolean isGameFinished(char[] board)
	{
		//Checking if player wins or not
		if(hasContestantWon(board,'X'))
		{
			System.out.println("\nPlayer wins");
			return true;
		}
		
		//Checking if computer wins or not
		if(hasContestantWon(board,'0'))
		{
			System.out.println("\nComputer wins");
			return true;
		}
		
		//Checking if there is an empty position left or not
		for(int i=0;i<board.length;i++)
		{
			if(board[i]==' ')  //another move can be made
			  return false;
		}
		
		//Otherwise
		//Its a tie
		System.out.println("\nGame ended in a tie");
		return true;
			
	}
	
	private static Boolean hasContestantWon(char[] board, char symbol)
	{
		if(board[0]==symbol && board[1]==symbol && board[2]==symbol)
			return true;
		if(board[3]==symbol && board[4]==symbol && board[5]==symbol)
			return true;
		if(board[6]==symbol && board[7]==symbol && board[8]==symbol)
			return true;
		if(board[0]==symbol && board[4]==symbol && board[8]==symbol)
			return true;
		if(board[2]==symbol && board[4]==symbol && board[6]==symbol)
			return true;
		if(board[0]==symbol && board[3]==symbol && board[6]==symbol)
			return true;
		if(board[1]==symbol && board[4]==symbol && board[7]==symbol)
			return true;
		if(board[2]==symbol && board[5]==symbol && board[8]==symbol)
			return true;
		
		return false;
		
	}
	
	public static void main(String[] args)
	{
		System.out.println("Let's play\n");
		System.out.println("TIC TAC TOE");
		
		char[] board= {' ',' ',' ',' ',' ',' ',' ',' ',' '};
		Scanner inp=new Scanner(System.in);
		
		printBoard(board);
		
		while(true)
		{
			if(!isGameFinished(board))
			    playerTurn(board,inp);
			else
				break;
			
			if(!isGameFinished(board))
				computerTurn(board);
			else
				break;
		}
		
		inp.close();
	}
}
