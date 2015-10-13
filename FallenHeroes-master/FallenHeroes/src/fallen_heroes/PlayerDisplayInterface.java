package fallen_heroes;

public interface PlayerDisplayInterface
{
	public void displayLogo();
	
	public void displayBoards(int p_player1Life, int p_player2Life, int p_player1Mana, int p_player2Mana, String p_boardPlayer1, String p_boardPlayer2);
	
	public void displayNextTurnMessage(int p_playerNumber);
	
	public void displayChoices();
	
	public void displayNotEnoughManaMessage();
	
	public void displayConferationMessage(String p_capacity);
	
	public void displayNotValidCard();
	
	public void displayCriDeGuerreMessage(int p_criDeGuerreEffect);
	
	public void displayNoOtherTargetThanOpponentForCriDeGuerreCapacity();
	
	public void displayOpponentAsATarget();
	
	public void displayCurrentPlayerAsATarget();
	
	public void displayHand(Hand p_hand);
	
	public void displayBoard(Board p_board, boolean p_displayOutlines);
	
	public void displayWaitingForAChoice();
	
	public void displaySpellMessage(int p_spellEffect, String p_spellTarget);
	
	public void displayNoOtherTargetThanCurrentPlayerForThisSpell();
	
	public void displayNoTargetAvailableForThisSpell();
	
	public void displayTheirIsAnInvocationWithAvantGardeInOpponentBoard();
	
	public void displayHasAlreadyAttacked();
	
	public void displayCantAttackFirstTurn();
	
	public void displayPLayerState(int p_life, int p_mana);
	
	public void displayNoOtherTargetThanOpponentForThisSpell();
	
	public void displayPossibleDeckChoices(int p_playerNumber);
	
	public void refreshFullDisplay();
	
	public void displayToMuchInvocationOnTheBoard();
	
	public void displayVictoryMessage();
	
}
