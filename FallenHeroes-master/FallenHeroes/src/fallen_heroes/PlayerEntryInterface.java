package fallen_heroes;

public interface PlayerEntryInterface 
{
	public Invocation choseInvocationInHand(Hand p_hand);
	
	public Spell choseSpellInHand(Hand p_hand);
	
	public Invocation choseInvocationInCurrentPlayerBoard(Board p_board);
	
	public Invocation choseInvocationInCurrentPlayerBoardOrCurrentPlayerHimself(Board p_board);
	
	public Invocation choseInvocationInCurrentPlayerBoardToAttack(Board p_board);
	
	public Invocation choseInvocationInOpponentBoard(Board p_board);
	
	public Invocation choseInvocationInOpponentBoardOrOpponentHimself(Board p_board);
	
	public int selectAction();
	
	public int selectDeck();
	
	public void setCurrentPlayer(Player p_player);

}
