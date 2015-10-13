package fallen_heroes;
import java.io.IOException;



/**
 * A player which have is own hand, board, and deck, and who will play the game
 * @author chamboug
 *
 */
public class Player
{


	
	/**
	 * Initial life points of the beginning of the game
	 */
	public final static int INITIAL_LIFE_POINT = 20;
	/**
	 * Initial mana points of the beginning of the game
	 */
	public final static int MANA_INITIAL_NUMBER = 5;
	
	public static final int MAXIMAL_MANA_NUMBER = 10;
	

	/**
	 * Initial number of cards of each player when the game starts
	 */
	public final static int INITIAL_CARD_NUMBER = 3;
	
	
	/**
	 * Counter of players
	 */
	public static int NUMBER_OF_CREATED_PLAYER = 0;
	

	/**
	 * Current health points of the player
	 */
	private int healthPoint;

	/**
	 * Current state of mana
	 */
	private int mana;

	
	
	
	/**
	 * Deck of the player
	 */
	private Deck deck;
	/**
	 * Hand of the player
	 */
	private Hand hand;
	/**
	 * Board of the player
	 */
	private Board board;
	
	/**
	 * Opponent of the current player
	 */
	private Player opponent;
	

	/**
	 * Unique number associated to each player
	 */
	private int playerNumber;
	
	
	private PlayerEntryInterface playerEntryInterface;
	
	private PlayerDisplayInterface playerDisplayInterface;

	
	/**
	 * Initialize the player with all constants about initial state and with the parameters p_classes and p_isHuman
	 * @param p_classes : class (light or dark)
	 * @param playerDisplayInterface 
	 * @param playerEntryInterface 
	 * @param p_isHuman : define if the player is real or a computer
	 */
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
		
		this.opponent = null;
	}
	
	/**
	 * Change the value of the mana
	 * @param p_mana : the new quantity of mana
	 */
	public void defineMana(int p_mana)
	{
		this.mana = p_mana;
	}
	
	/**
	 * Get the current life for the player
	 * @return this.healthPoint : current life for the player
	 */
	public int getLife()
	{
		return this.healthPoint;
	}
	
	/**
	 * The interface to allow the player to choose an action
	 */
	public void selectAction()
	{

			while (true)
			{
				this.playerDisplayInterface.displayPLayerState(this.healthPoint, this.mana);
				this.playerDisplayInterface.refreshFullDisplay();
				this.opponent.playerDisplayInterface.refreshFullDisplay();
				
				this.playerDisplayInterface.displayChoices();
				int choice = this.playerEntryInterface.selectAction();
				

				if (choice == 1)
				{
					this.playerDisplayInterface.displayHand(this.hand);
				}
				else if (choice == 2)
				{
					this.playerDisplayInterface.displayBoard(this.opponent.board, true);
					this.playerDisplayInterface.displayBoard(this.board, true);
				}
				else if (choice == 3)
				{
					this.playerDisplayInterface.displayWaitingForAChoice();
					this.poseInvocation(this.playerEntryInterface.choseInvocationInHand(this.hand));
				}
				else if (choice == 4)
				{
					this.playerDisplayInterface.displayWaitingForAChoice();
					this.useSpell(this.playerEntryInterface.choseSpellInHand(this.hand));
				}
				else if (choice == 5)
				{
					this.playerDisplayInterface.displayWaitingForAChoice();
					this.attack(this.playerEntryInterface.choseInvocationInCurrentPlayerBoardToAttack(this.board));
				}
				else
				{
					break;
				}

			}



	}
	
	/**
	 * The action of the player of picking the next card of his deck
	 */
	public void drawACard()
	{
		Card drawedCard = this.deck.retrieveHeadCard();
		
		if (drawedCard != null)
			this.hand.addACard(drawedCard);
	}
	

	
	
	/**
	 * Allow the player to pose an invocation on the board. It contains :
	 * - a choice of the card with checking if it is exists in the hand and if it is an instance of Invocation
	 * - a choice of the creature with checking mana state
	 * - it check if the Invocation to pose have a special capacity like "confération" or "cri de guerre", and if needed, apply the special capacity
	 * - pose the creature on the player's board
	 */
	private void poseInvocation(Invocation p_invocationToPose)
	{
		
		if (p_invocationToPose != null)
		{
			if (this.mana >= p_invocationToPose.getManaCost())
			{

				
				
				if (this.getNumberOfCardOnTheBoard() != Board.MAXIMAL_INVOCATION_NUMBER)
				{
					this.mana -= p_invocationToPose.getManaCost();
					
					this.board.addInvocation(p_invocationToPose);
					p_invocationToPose.doHisFirstTurn();


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
								invocationToAffect.affectTheSpecialCapacity(p_invocationToPose.returnConferationCapacity());
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
							if (this.opponent.getNumberOfCardOnTheBoard() == 0)
							{
								this.playerDisplayInterface.displayNoOtherTargetThanOpponentForCriDeGuerreCapacity();
								this.changeOpponentLife(CriDeGuerreEffect);
								break;
							}
							
							this.playerDisplayInterface.displayOpponentAsATarget();
							this.playerDisplayInterface.displayBoard(this.opponent.board,false);
							
							this.playerDisplayInterface.displayWaitingForAChoice();
							
							Invocation invocationToFocus = this.playerEntryInterface.choseInvocationInOpponentBoardOrOpponentHimself(this.opponent.board);

								
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
									this.opponent.board.removeInvocation(invocationToFocus);
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
	
	/**
	 * Allow the player to play a spell. It contains :
	 * - a choice of the card with checking if it is exists in the hand and if it is an instance of Spell
	 * - a choice of the spell to play with checking mana state
	 * - Apply the effect of the spell
	 */
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
					// TODO finir partie
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
					
					if (this.opponent.board.getInvocationNumber() == 0)
					{
						this.playerDisplayInterface.displayNoTargetAvailableForThisSpell();
					}
					else
					{
						this.playerDisplayInterface.displayBoard(this.opponent.board,false);
						
						boolean isAttribute = false;
						do 
						{
							this.playerDisplayInterface.displayWaitingForAChoice();
							Invocation invocationToFocus = this.playerEntryInterface.choseInvocationInOpponentBoard(this.opponent.board);
							
							if (invocationToFocus != null)
							{
								
								int targetLife = invocationToFocus.changeLife(p_spell.returnEffect());
								if (targetLife <= 0)
								{
									this.opponent.board.removeInvocation(invocationToFocus);
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
					
					if (this.opponent.board.getInvocationNumber() == 0)
					{
						this.playerDisplayInterface.displayNoOtherTargetThanOpponentForThisSpell();
						this.changeOpponentLife(p_spell.returnEffect());
					}
					else
					{
						this.playerDisplayInterface.displayOpponentAsATarget();
						this.playerDisplayInterface.displayBoard(this.opponent.board,false);
						
						boolean isAttribute = false;
						do 
						{
							this.playerDisplayInterface.displayWaitingForAChoice();
							
							Invocation invocationToFocus = this.playerEntryInterface.choseInvocationInOpponentBoardOrOpponentHimself(this.opponent.board);
							
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
									this.opponent.board.removeInvocation(invocationToFocus);
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
	
	
	/**
	 * Define the opponent of the current player
	 * @param p_opponent : the opponent to associate
	 */
	public void defineOpponent(Player p_opponent)
	{
		this.opponent = p_opponent;
	}
	
	/**
	 * Allow the player to attack with an Invocation posed on the board. It checks :
	 * - if the keyboard entry is a number
	 * - if the keyboard entry is an existing card in the board of the player or not
	 * - if the second keyboard entry is a number
	 * - if the second keyboard entry is an existing card in the board of the player's opponent or not
	 * - if there is a card on the player's opponent board with the special capacity "avant-garde", and if needed, applies it
	 * - if the invocation which attack has a special capacity in : "charge", "enchainement"
	 */
	public void attack(Invocation p_invocation)
	{
		if (p_invocation != null)
		{
			if (p_invocation.doNotDoHisFirstTurn() || p_invocation.getCapacity().equals("charge"))
			{
				if (!(p_invocation.haveAttacked()) || ((p_invocation.returnCapacity().equals("enchainement")) && !(p_invocation.returnHasUsedHisSecondAttack_Enchainement())))
				{
					this.playerDisplayInterface.displayOpponentAsATarget();
					this.playerDisplayInterface.displayBoard(this.opponent.board,false);
					
					this.playerDisplayInterface.displayWaitingForAChoice();
					
					
					Invocation targetInvocation = this.playerEntryInterface.choseInvocationInOpponentBoardOrOpponentHimself(this.opponent.board);
					
					
					if (targetInvocation == null)
					{
						if (!(this.opponent.board.thereIsAtLeastOneAvantGardeInvocationOnTheBoard()))
						{
							this.changeOpponentLife(p_invocation.getAttackPoints());
							new Audio(2).start();
							
							if (p_invocation.haveAttacked())
							{
								p_invocation.changeHasUsedHisSecondAttack_Enchainement(true);
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
						if (!(!(targetInvocation.returnCapacity().equals("avant-garde")) && this.opponent.board.thereIsAtLeastOneAvantGardeInvocationOnTheBoard()) )
						{
							int targetCardLife = targetInvocation.changeLife(p_invocation.getAttackPoints());
							int playerCardLife = p_invocation.changeLife(targetInvocation.getAttackPoints());
							new Audio(2).start();
							if (targetCardLife <= 0)
							{
								this.opponent.board.removeInvocation(targetInvocation);
							}
							if (playerCardLife <= 0)
							{
								this.board.removeInvocation(p_invocation);
							}
								
							if (p_invocation.haveAttacked())
							{
								p_invocation.changeHasUsedHisSecondAttack_Enchainement(true);
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
	
	/**
	 * Return the current value of mana of the player
	 * @return this.mana : the current value of mana of the player
	 */
	public int getMana()
	{
		return this.mana;
	}
	

	

	
	/**
	 * Apply the function makeOperational for each Invocation on the player's board
	 */
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
	

	

	
	/**
	 * For each card of the player's board, check if the special capacity is "Pacte diabolique" and, if needed, applies it
	 */
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
						this.board.removeInvocation(invocationToChange);
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
		return this.opponent;
	}
	
	/**
	 * Return the player's number
	 * @return this.playerNumber : the number of the player
	 */
	public int getPlayerNumber()
	{
		return this.playerNumber;
	}
	
	public void changeOpponentLife(int p_pointsToRemove)
	{
		if (this.opponent.healthPoint - p_pointsToRemove <= 0)
		{
			this.opponent.healthPoint = 0;
			new Audio(5).run();
			this.opponent.playerDisplayInterface.displayVictoryMessage();
			new Audio(5).start();
			System.exit(0);
		}
		else
		{
			this.opponent.healthPoint -= p_pointsToRemove;
		}
	}
	

	public String toString()
	{
		return this.hand.toString();
	}



}
