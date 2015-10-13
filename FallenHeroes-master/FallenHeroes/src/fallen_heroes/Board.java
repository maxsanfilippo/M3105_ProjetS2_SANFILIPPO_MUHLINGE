package fallen_heroes;
import java.util.LinkedList;

/**
 * The class that designates the place where we put cards we play
 * @author Group 6
 */
public class Board
{
	/**
	 * Maximum number of invocations
	 */
	public final static int MAXIMAL_INVOCATION_NUMBER = 5;
	
	/**
	 * Number of laid invocations
	 */
	private int numberOfLaidInvocations;
	
	
	/**
	 * The board is a linked list composed of invocations
	 */
	private LinkedList<Invocation> boardCards;
	
	/**
	 * Initializes the board
	 */
	public Board()
	{
		this.numberOfLaidInvocations = 0;

		
		this.boardCards = new LinkedList<Invocation>();
	}
	
	/**
	 * Get the laid invocations number
	 * @return the number of laid invocations
	 */
	public int getInvocationsNumber()
	{
		return this.numberOfLaidInvocations;
	}
	
	public int getInvocationNumber()
	{
		return this.numberOfLaidInvocations;
	}
	
	/**
	 * Returns the selected invocation with its index returned in parameter
	 * @param p_invocationIndex Index of the invocation
	 * @return if there is an invocation it return it, else it return nothing
	 */
	public Invocation getInvocation(int p_invocationIndex)
	{
		if (p_invocationIndex >= 0 && p_invocationIndex < this.numberOfLaidInvocations)	return this.boardCards.get(p_invocationIndex);
		
		return null;

	}
	
	/**
	 * Add the selected invocation on the board if there is enough space
	 * @param invocationToPlay Selected invocation to add it to the board
	 */
	public void addInvocation(Invocation invocationToPlay)
	{
		if (this.numberOfLaidInvocations < MAXIMAL_INVOCATION_NUMBER)
		{
			this.boardCards.add(invocationToPlay);
			this.numberOfLaidInvocations++;
		}
	}
	
	/**
	 * Remove the selected invocation of the board
	 * @param p_invocationToRemove The invocation we selected to remove it
	 */
	public void removeInvocation(Invocation p_invocationToRemove)
	{
		this.boardCards.remove(p_invocationToRemove);
		this.numberOfLaidInvocations--;
	}
	
	
	/**
	 * there is at least one "Avant-garde" invocation on the board or not
	 * @return true if there is at least on "Avant-garde" invocation on the board, false if not
	 */
	public boolean thereIsAtLeastOneAvantGardeInvocationOnTheBoard()
	{
		
		for(Invocation myCard : this.boardCards)
		{
			if (myCard.returnCapacity().equals("avant-garde"))
			{
				return true;
			}
		}
		
		return false;
	}
	
	
	
	/**
	 * Returns a text representation of the board
	 * @param displayOutline Designates if we want outline or not
	 * @return The text representation selected
	 */
	public String returnBoard(boolean afficherOutline)
	{
		String Returning = "";
		if (afficherOutline) Returning = "************************************* Le terrain *************************************\n";
		
		int cardNumber = 1;
		for(Invocation myCard : this.boardCards)
		{
			Returning += "\tCarte numéro : " + cardNumber + "\n" + myCard.getCardRepresentation(false) + "\n";
			cardNumber++;
		}


		
		if (afficherOutline) Returning += "**************************************************************************************\n";
		return Returning;

	}
	
}
