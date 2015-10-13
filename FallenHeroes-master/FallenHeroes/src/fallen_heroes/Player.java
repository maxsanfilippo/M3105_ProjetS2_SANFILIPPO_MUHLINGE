package fallen_heroes;
import java.io.IOException;

public class Player
{

	public final static int INITIAL_LIFE_POINT = 20;

	public final static int MANA_INITIAL_NUMBER = 5;
	
	public static final int MAXIMAL_MANA_NUMBER = 10;

	public final static int INITIAL_CARD_NUMBER = 3;

	public static int NUMBER_OF_CREATED_PLAYER = 0;

	private int healthPoint;

	private int mana;


	private Deck deck;

	private Hand hand;

	private Board board;

	private Player opponentPlayer;

	private int playerNumber;
	
	private PlayerEntryInterface playerEntryInterface;
	
	private PlayerDisplayInterface playerDisplayInterface;

	public Player(boolean p_class, PlayerEntryInterface p_playerEntryInterface, PlayerDisplayInterface p_playerDisplayInterface)
	{
		this.healthPoint = INITIAL_LIFE_POINT;
		this.mana = MANA_INITIAL_NUMBER;

		this.playerEntryInterface = p_playerEntryInterface;
		this.playerDisplayInterface = p_playerDisplayInterface;
		
		try
		{
			this.deck = new Deck(new CardFileReader(p_class).returnCardList());
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.hand = new Hand();
		this.board = new Board();
		
		for (int numberOfCard = 1; numberOfCard <= INITIAL_CARD_NUMBER; numberOfCard++)
		{
			this.drawACard();
		}
		
		NUMBER_OF_CREATED_PLAYER++;
		this.playerNumber = NUMBER_OF_CREATED_PLAYER;
		
		this.opponentPlayer = null;
	}
	

	public void setMana(int p_mana)
	{
		this.mana = p_mana;
	}

	public int getLife()
	{
		return this.healthPoint;
	}

	public void selectAction()
	{

			while (true)
			{
				this.playerDisplayInterface.displayPLayerState(this.healthPoint, this.mana);
				this.playerDisplayInterface.refreshFullDisplay();
				this.opponentPlayer.playerDisplayInterface.refreshFullDisplay();
				
				this.playerDisplayInterface.displayChoices();
				int choice = this.playerEntryInterface.selectAction();
				

				if (choice == 1)
				{
					this.playerDisplayInterface.displayHand(this.hand);
				}
				else if (choice == 2)
				{
					this.playerDisplayInterface.displayBoard(this.opponentPlayer.board, true);
					this.playerDisplayInterface.displayBoard(this.board, true);
				}
				else if (choice == 3)
				{
					this.playerDisplayInterface.displayWaitingForAChoice();
					this.poseInvocationOnBoard(this.playerEntryInterface.choseInvocationInHand(this.hand));
				}
				else if (choice == 4)
				{
					this.playerDisplayInterface.displayWaitingForAChoice();
					this.useSpell(this.playerEntryInterface.choseSpellInHand(this.hand));
				}
				else if (choice == 5)
				{
					this.playerDisplayInterface.displayWaitingForAChoice();
					this.attackWithAnInvocationOnTheBoard(this.playerEntryInterface.choseInvocationInCurrentPlayerBoardToAttack(this.board));
				}
				else
				{
					break;
				}

			}
	}
	
	public void drawACard()
	{
		Card drawedCard = this.deck.retrieveHeadCardOfTheDeck();
		
		if (drawedCard != null)
			this.hand.addACardInHand(drawedCard);
	}
	

	private void poseInvocationOnBoard(Invocation p_invocationToPose)
	{
		
		if (p_invocationToPose != null)
		{
			if (this.mana >= p_invocationToPose.getManaCost())
			{
				
				if (this.getNumberOfCardOnTheBoard() != Board.MAXIMAL_INVOCATION_NUMBER)
				{
					this.mana -= p_invocationToPose.getManaCost();
					
					this.board.addInvocationOnBoard(p_invocationToPose);
					p_invocationToPose.doItsFirstTurn();


					if (p_invocationToPose.returnCapacity().equals("conferation"))
					{
						this.hand.removeCard(p_invocationToPose);
						
						this.playerDisplayInterface.refreshFullDisplay();
						
						boolean capacityWasAffected = false;
						do
						{
							this.playerDisplayInterface.displayConferationMessage(p_invocationToPose.returnConferationCapacity());
							// TODO éventuellement afficher le board entre temps
							Invocation invocationToAffect = this.playerEntryInterface.choseInvocationInCurrentPlayerBoard(this.board);
							
							if (invocationToAffect != null)
							{
								invocationToAffect.affectTheCapacity(p_invocationToPose.returnConferationCapacity());
								capacityWasAffected = true;
							}
							else
							{
								this.playerDisplayInterface.displayNotValidCard();
							}
							
							
						} while (!capacityWasAffected);
												
					}
					else if (p_invocationToPose.returnCapacity().equals("cri de guerre"))
					{

						this.hand.removeCard(p_invocationToPose);
						
						this.playerDisplayInterface.refreshFullDisplay();
						
						int CriDeGuerreEffect = p_invocationToPose.returnCriDeGuerreEffect() * (-1);
						
						this.playerDisplayInterface.displayCriDeGuerreMessage(CriDeGuerreEffect);
						
						boolean criDeGuerreWasUsed = false;
						do 
						{
							if (this.opponentPlayer.getNumberOfCardOnTheBoard() == 0)
							{
								this.playerDisplayInterface.displayNoOtherTargetThanOpponentForCriDeGuerreCapacity();
								this.changeOpponentLife(CriDeGuerreEffect);
								break;
							}
							
							this.playerDisplayInterface.displayOpponentAsATarget();
							this.playerDisplayInterface.displayBoard(this.opponentPlayer.board,false);
							
							this.playerDisplayInterface.displayWaitingForAChoice();
							
							Invocation invocationToFocus = this.playerEntryInterface.choseInvocationInOpponentBoardOrOpponentHimself(this.opponentPlayer.board);

								
							if (invocationToFocus == null)
							{
								this.changeOpponentLife(CriDeGuerreEffect);
								criDeGuerreWasUsed = true;
							}
							else
							{
								int targetLife = invocationToFocus.changeLife(CriDeGuerreEffect);
								
								if (targetLife <= 0)
								{
									this.opponentPlayer.board.removeInvocationFromBoard(invocationToFocus);
								}
									
								criDeGuerreWasUsed = true;

							}
							
						} while (!criDeGuerreWasUsed);
					}
					else
					{
						this.hand.removeCard(p_invocationToPose);
						
						this.playerDisplayInterface.refreshFullDisplay();
					}
				}
				else
				{
					this.playerDisplayInterface.displayToMuchInvocationOnTheBoard();
				}

			}
			else
			{
				this.playerDisplayInterface.displayNotEnoughManaMessage();
			}
			
		}
		else
		{
			this.playerDisplayInterface.displayNotValidCard();
		}
		
	}
	
	private void useSpell(Spell p_spell)
	{

		if (p_spell != null)
		{
			if (this.mana >= p_spell.getManaCost())
			{
				if (p_spell.returnSpellTarget().equals("Soi-même (joueur)"))
				{

					this.playerDisplayInterface.displaySpellMessage(p_spell.returnEffect(), p_spell.returnSpellTarget());
					this.addLifePoints(p_spell.returnEffect());

				}
				else if (p_spell.returnSpellTarget().equals("Joueur ennemi"))
				{
					
					this.playerDisplayInterface.displaySpellMessage(p_spell.returnEffect(), p_spell.returnSpellTarget());
					this.changeOpponentLife(p_spell.returnEffect());
				}
				else if (p_spell.returnSpellTarget().equals("Soi-même (joueur ou invocation)"))
				{
					this.playerDisplayInterface.displaySpellMessage(p_spell.returnEffect(), p_spell.returnSpellTarget());
					
					if (this.board.getInvocationNumber() != 0)
					{
						this.playerDisplayInterface.displayCurrentPlayerAsATarget();
						this.playerDisplayInterface.displayBoard(this.board,false);
						
						boolean isAttribute = false;
						do 
						{
							this.playerDisplayInterface.displayWaitingForAChoice();
							
							Invocation invocationToFocus = this.playerEntryInterface.choseInvocationInCurrentPlayerBoardOrCurrentPlayerHimself(this.board);
							
							if (invocationToFocus != null)
							{
								int targetLife = invocationToFocus.changeLife(p_spell.returnEffect() * (-1));
								isAttribute = true;
							}
							else
							{
								this.addLifePoints(p_spell.returnEffect());
								isAttribute = true;
							}							
							
						} while (!isAttribute);
					
					}
					else
					{
						this.playerDisplayInterface.displayNoOtherTargetThanCurrentPlayerForThisSpell();
						this.addLifePoints(p_spell.returnEffect());
					}				
					
				}
				else if (p_spell.returnSpellTarget().equals("Invocation ennemie"))
				{
					this.playerDisplayInterface.displaySpellMessage(p_spell.returnEffect(), p_spell.returnSpellTarget());
					
					if (this.opponentPlayer.board.getInvocationNumber() == 0)
					{
						this.playerDisplayInterface.displayNoTargetAvailableForThisSpell();
					}
					else
					{
						this.playerDisplayInterface.displayBoard(this.opponentPlayer.board,false);
						
						boolean isAttribute = false;
						do 
						{
							this.playerDisplayInterface.displayWaitingForAChoice();
							Invocation invocationToFocus = this.playerEntryInterface.choseInvocationInOpponentBoard(this.opponentPlayer.board);
							
							if (invocationToFocus != null)
							{
								
								int targetLife = invocationToFocus.changeLife(p_spell.returnEffect());
								if (targetLife <= 0)
								{
									this.opponentPlayer.board.removeInvocationFromBoard(invocationToFocus);
								}
								
								isAttribute = true;
							}
							else
							{
								this.playerDisplayInterface.displayNotValidCard();
							}
							
							
						}  while (!isAttribute);
					}
					
				}
				else if (p_spell.returnSpellTarget().equals("Cible ennemie"))
				{
					this.playerDisplayInterface.displaySpellMessage(p_spell.returnEffect(), p_spell.returnSpellTarget());
					
					if (this.opponentPlayer.board.getInvocationNumber() == 0)
					{
						this.playerDisplayInterface.displayNoOtherTargetThanOpponentForThisSpell();
						this.changeOpponentLife(p_spell.returnEffect());
					}
					else
					{
						this.playerDisplayInterface.displayOpponentAsATarget();
						this.playerDisplayInterface.displayBoard(this.opponentPlayer.board,false);
						
						boolean isAttribute = false;
						do 
						{
							this.playerDisplayInterface.displayWaitingForAChoice();
							
							Invocation invocationToFocus = this.playerEntryInterface.choseInvocationInOpponentBoardOrOpponentHimself(this.opponentPlayer.board);
							
							if (invocationToFocus == null)
							{
								this.changeOpponentLife(p_spell.returnEffect());
								isAttribute = true;
							}
							else
							{
								int targetLife = invocationToFocus.changeLife(p_spell.returnEffect());
								
								if (targetLife <= 0)
								{
									this.opponentPlayer.board.removeInvocationFromBoard(invocationToFocus);
								}
								
								isAttribute = true;
							}
							
						} while(!isAttribute);
					}
				}
				
				
				this.mana -= p_spell.getManaCost();
				this.hand.removeCard(p_spell);
				//this.playerDisplayInterface.displayHand(this.hand);
				new Audio(3).start();
				
			}
			else
			{
				this.playerDisplayInterface.displayNotEnoughManaMessage();
			}
			
		}
		else
		{
			this.playerDisplayInterface.displayNotValidCard();
		}
	
	}
	
	
	public void addLifePoints(int p_pointsToAdd)
	{
		if (this.healthPoint + p_pointsToAdd > Player.INITIAL_LIFE_POINT)
		{
			this.healthPoint = Player.INITIAL_LIFE_POINT;
		}
		else
		{
			this.healthPoint += p_pointsToAdd;
		}
	}

	public void defineOpponent(Player p_opponent)
	{
		this.opponentPlayer = p_opponent;
	}

	public void attackWithAnInvocationOnTheBoard(Invocation p_invocation)
	{
		if (p_invocation != null)
		{
			if (p_invocation.doNotDoItsFirstTurn() || p_invocation.getCapacity().equals("charge"))
			{
				if (!(p_invocation.hasAttacked()) || ((p_invocation.returnCapacity().equals("enchainement")) && !(p_invocation.returnHasUsedItsSecondAttack_Enchainement())))
				{
					this.playerDisplayInterface.displayOpponentAsATarget();
					this.playerDisplayInterface.displayBoard(this.opponentPlayer.board,false);
					
					this.playerDisplayInterface.displayWaitingForAChoice();
					
					
					Invocation targetInvocation = this.playerEntryInterface.choseInvocationInOpponentBoardOrOpponentHimself(this.opponentPlayer.board);
					
					
					if (targetInvocation == null)
					{
						if (!(this.opponentPlayer.board.thereIsAtLeastOneAvantGardeInvocationOnTheBoard()))
						{
							this.changeOpponentLife(p_invocation.getAttackPoints());
							new Audio(2).start();
							
							if (p_invocation.hasAttacked())
							{
								p_invocation.changeHasUsedItsSecondAttack_Enchainement(true);
							}

							p_invocation.makeInactive();
							this.playerDisplayInterface.refreshFullDisplay();
						}
						else
						{
							this.playerDisplayInterface.displayTheirIsAnInvocationWithAvantGardeInOpponentBoard();
						}
						
					}
					else
					{
						if (!(!(targetInvocation.returnCapacity().equals("avant-garde")) && this.opponentPlayer.board.thereIsAtLeastOneAvantGardeInvocationOnTheBoard()) )
						{
							int targetCardLife = targetInvocation.changeLife(p_invocation.getAttackPoints());
							int playerCardLife = p_invocation.changeLife(targetInvocation.getAttackPoints());
							new Audio(2).start();
							if (targetCardLife <= 0)
							{
								this.opponentPlayer.board.removeInvocationFromBoard(targetInvocation);
							}
							if (playerCardLife <= 0)
							{
								this.board.removeInvocationFromBoard(p_invocation);
							}
								
							if (p_invocation.hasAttacked())
							{
								p_invocation.changeHasUsedItsSecondAttack_Enchainement(true);
							}
								
								
							this.playerDisplayInterface.refreshFullDisplay();
							p_invocation.makeInactive();
							
						}
						else
						{
							this.playerDisplayInterface.displayTheirIsAnInvocationWithAvantGardeInOpponentBoard();
						}
						
					}
					
				}
				else
				{
					this.playerDisplayInterface.displayHasAlreadyAttacked();
				}
			}
			else
			{
				this.playerDisplayInterface.displayCantAttackFirstTurn();
			}
		}
		
	}
	
	
	public int getMana()
	{
		return this.mana;
	}

	public void updateInvocationStates()
	{
		for (int currentInvocation = 0; currentInvocation < this.board.getInvocationNumber(); currentInvocation++)
		{
			Invocation invocationToChange = this.board.getInvocation(currentInvocation);
			if (invocationToChange != null)
			{
				invocationToChange.makeOperational();
			}
		}
	}
	

	public void applyPacteDiabolique()
	{

		for (int currentInvocation = 0; currentInvocation < this.board.getInvocationNumber(); currentInvocation++)
		{
			Invocation invocationToChange = this.board.getInvocation(currentInvocation);
			if (invocationToChange != null)
			{

				if (invocationToChange.getCapacity().equals("pacte diabolique"))
				{
					int vieCarte = invocationToChange.changeLife(1);
					invocationToChange.changeAttack(1);
					
					if (vieCarte <= 0)
					{
						this.board.removeInvocationFromBoard(invocationToChange);
					}
				}
			}
			
			
		}
		
		
	}
	
	public Card getCardInHand(int index)
	{
		return this.hand.getCard(index);
	}
	
	public Card getCardOnBoard(int index)
	{
		return this.board.getInvocation(index);
	}
	
	public int getNumberOfCardsInTheHand()
	{
		return this.hand.getNumberOfCardsInHand();
	}
	
	public int getNumberOfCardOnTheBoard()
	{
		return this.board.getInvocationsNumber();
	}
	
	public Player getOpponent()
	{
		return this.opponentPlayer;
	}

	public int getPlayerNumber()
	{
		return this.playerNumber;
	}
	
	public void changeOpponentLife(int p_pointsToRemove)
	{
		if (this.opponentPlayer.healthPoint - p_pointsToRemove <= 0)
		{
			this.opponentPlayer.healthPoint = 0;
			new Audio(5).run();
			this.opponentPlayer.playerDisplayInterface.displayVictoryMessage();
			new Audio(5).start();
			System.exit(0);
		}
		else
		{
			this.opponentPlayer.healthPoint -= p_pointsToRemove;
		}
	}
	

	public String toString()
	{
		return this.hand.toString();
	}



}
