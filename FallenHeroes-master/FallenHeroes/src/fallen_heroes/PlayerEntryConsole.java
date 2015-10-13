package fallen_heroes;

import java.util.Scanner;

public class PlayerEntryConsole implements PlayerEntryInterface
{


	public Invocation choseInvocationInHand(Hand p_hand)
	{

		String choix = this.readKeyboardEntry();
		
		
		
		if (entryIsNumeric(choix))
		{
			Card toPlay = p_hand.getCard(Integer.parseInt(choix));
			if (toPlay != null)
			{
				if (toPlay instanceof Invocation)
				{
					return (Invocation)toPlay;
				}
			}
		}
		
		
		return null;
	}
	
	
	public String readKeyboardEntry()
	{
		Scanner sc = new Scanner(System.in);
		String choice = sc.nextLine();
		return choice;
	}

	public boolean entryIsNumeric(String p_entry)
	{
		try
		{
			int test_entree = Integer.parseInt(p_entry);
		}
		catch (NumberFormatException e)
		{
			return false;
		}
		
		return true;
	}


	public Invocation choseInvocationInYourBoard(Board p_board)
	{
		String invocationChoice = this.readKeyboardEntry();
		
		if (entryIsNumeric(invocationChoice))
		{
			Invocation invocationToAffect = p_board.getInvocation(Integer.parseInt(invocationChoice) - 1);
			
			return invocationToAffect;
		}
		
		
		return null;
	}
	
	public Invocation choseInvocationInOpponentBoard(Board p_board)
	{
		String invocationChoice = this.readKeyboardEntry();
		
		if (entryIsNumeric(invocationChoice))
		{
			Invocation invocationToAffect = p_board.getInvocation(Integer.parseInt(invocationChoice) - 1);
			
			return invocationToAffect;
		}
		
		
		return null;
	}



	public Spell choseSpellInHand(Hand p_hand)
	{
		
		String choix = this.readKeyboardEntry();
		
		
		
		if (entryIsNumeric(choix))
		{
			Card toPlay = p_hand.getCard(Integer.parseInt(choix));
			if (toPlay != null)
			{
				if (toPlay instanceof Spell)
				{
					return (Spell)toPlay;
				}
			}
		}
				
		
		
		return null;
	}
	
	public int selectAction()
	{
		
		String choix = this.readKeyboardEntry();
		
		
		
		if (entryIsNumeric(choix))
		{
			return Integer.parseInt(choix);
		}
		
		
		return 0;
	}
	
	
	public int selectDeck()
	{
		String choix = this.readKeyboardEntry();
		
		if (choix.equals("1"))
			return 1;
		else if (choix.equals("2"))
			return 2;
		
		return 0;
	}


	@Override
	public void setCurrentPlayerAndRefresh(Player p_player)
	{
		// TODO Auto-generated method stub
		
	}


	@Override
	public Invocation choseInvocationInCurrentPlayerBoard(Board p_board)
	{
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Invocation choseInvocationInOpponentBoardOrOpponentHimself(
			Board p_board)
	{
		String invocationChoice = this.readKeyboardEntry();
		
		if (entryIsNumeric(invocationChoice))
		{
			Invocation invocationToAffect = p_board.getInvocation(Integer.parseInt(invocationChoice) - 1);
			
			return invocationToAffect;
		}
		
		
		return null;
	}


	@Override
	public Invocation choseInvocationInCurrentPlayerBoardToAttack(Board p_board)
	{
		String invocationChoice = this.readKeyboardEntry();
		
		if (entryIsNumeric(invocationChoice))
		{
			Invocation invocationToAffect = p_board.getInvocation(Integer.parseInt(invocationChoice) - 1);
			
			return invocationToAffect;
		}
		
		
		return null;
	}


	@Override
	public Invocation choseInvocationInCurrentPlayerBoardOrCurrentPlayerHimself(
			Board p_board)
	{
		String invocationChoice = this.readKeyboardEntry();
		
		if (entryIsNumeric(invocationChoice))
		{
			Invocation invocationToAffect = p_board.getInvocation(Integer.parseInt(invocationChoice) - 1);
			
			return invocationToAffect;
		}
		
		
		return null;
	}
	

}
