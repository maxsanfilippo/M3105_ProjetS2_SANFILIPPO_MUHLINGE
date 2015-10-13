package fallen_heroes;

public class Game
{
	private Player player1;

	private Player player2;
	
	private PlayerEntryInterface playerEntryInterface1;
	
	private PlayerDisplayInterface playerDisplayInterface1;
	
	private PlayerEntryInterface playerEntryInterface2;
	
	private PlayerDisplayInterface playerDisplayInterface2;
	
	private int turnsNumber;

	public Game(PlayerEntryInterface p_playerEntryInterface1, PlayerDisplayInterface p_playerDisplayInterface1, 
			    PlayerEntryInterface p_playerEntryInterface2, PlayerDisplayInterface p_playerDisplayInterface2)
	{
		this.playerEntryInterface1 = p_playerEntryInterface1;
		this.playerDisplayInterface1 = p_playerDisplayInterface1;
		this.playerEntryInterface2 = p_playerEntryInterface2;
		this.playerDisplayInterface2 = p_playerDisplayInterface2;

		this.playerDisplayInterface1.displayLogo();
		
		this.player1 = new Player(this.chooseDeck(p_playerEntryInterface1, p_playerDisplayInterface1, 1), playerEntryInterface1, playerDisplayInterface1);
		this.player2 = new Player(this.chooseDeck(p_playerEntryInterface2, p_playerDisplayInterface2, 2), playerEntryInterface2, playerDisplayInterface2);
		
		this.player1.defineOpponent(this.player2);
		this.player2.defineOpponent(this.player1);

		p_playerEntryInterface1.setCurrentPlayerAndRefresh(player1);

		this.turnsNumber = 1;

	}
	
	
	public boolean chooseDeck(PlayerEntryInterface p_playerEntryInterface, PlayerDisplayInterface p_playerDisplayInterface, int p_playerNumber)
	{
		int choice;
		
		do
		{
			p_playerDisplayInterface.displayPossibleDeckChoices(p_playerNumber);
			choice = p_playerEntryInterface.selectDeck();
		} while (choice != 1 && choice != 2);
		
		
		if (choice == 1)
			return true;
		
		return false;
	}
	
	public void play()
	{

		while (true)
		{
			this.playerDisplayInterface1.displayNextTurnMessage(1);
			this.nextTurn(this.player1);
			new Audio(4).start();

			this.playerDisplayInterface2.displayNextTurnMessage(2);
			this.nextTurn(this.player2);
			new Audio(4).start();

			this.turnsNumber++;
		}

	}
	
	public void nextTurn(Player p_player)
	{
		p_player.updateInvocationStates();

		if (this.turnsNumber != 1)
		{
			p_player.drawACard();
			manaIncrement(p_player);
			p_player.applyPacteDiabolique();
		}
		
		if (p_player.equals(this.player1))
		{
			this.playerEntryInterface1.setCurrentPlayerAndRefresh(this.player1);
			//this.playerEntryInterface2.setCurrentPlayer(this.player1);
		}
		else
		{
			//this.playerEntryInterface1.setCurrentPlayer(this.player2);
			this.playerEntryInterface2.setCurrentPlayerAndRefresh(this.player2);
		}

		p_player.selectAction();
	}
	
	private void manaIncrement(Player p_player)
	{
	
		int manaMultiplier = (int)((this.turnsNumber / 5)+1);
	
		if (manaMultiplier > 3)
		{
			manaMultiplier = 3;
		}
	
		int newMana = p_player.getMana() + (1 * manaMultiplier);
		if (newMana > 10)
			newMana = 10;
		
		p_player.setMana(newMana);
	}

	public Player searchVictoriousPlayer()
	{
		if (this.player1.getLife() == 0) return this.player2;
		return this.player1;
	}
	

}
