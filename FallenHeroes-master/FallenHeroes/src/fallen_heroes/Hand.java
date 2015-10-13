package fallen_heroes;
import java.util.LinkedList;


/**
 * The class that designates the hand where we have cards
 * @author Group 6
 */
public class Hand
{
	/**
	 * Maximal number of cards in the hand
	 */
	public final static int MAXIMAL_NUMBER_OF_CARDS_IN_HAND = 5;
	
	/**
	 * Number of cards in the hand
	 */
	private int cardsNumberInHand;
	
	/**
	 * The hand is composed by card
	 */
	private LinkedList<Card> hand;
	
	/**
	 * Initializes the hand
	 */
	public Hand()
	{
		this.hand = new LinkedList<Card>();
		this.cardsNumberInHand = 0;
	}
	
	
	
	/**
	 * Add a card in the hand
	 * @param cardToAdd the card to add
	 */
	public void addACard(Card cardToAdd)
	{
	
		if (this.cardsNumberInHand < MAXIMAL_NUMBER_OF_CARDS_IN_HAND)
		{
			this.hand.add(cardToAdd);
			this.cardsNumberInHand++;
		}
		
	}
	
	
	/**
	 * Remove a card from the hand
	 * @param cardToRemove the card to remove
	 */
	public void removeCard(Card cardToRemove)
	{
		this.hand.remove(cardToRemove);
		this.cardsNumberInHand--;
	}
	
	/**
	 * Get the card
	 * @param p_cardToPlayIndex the index of the card
	 * @return the card or null
	 */
	public Card getCard(int p_cardToPlayIndex)
	{/*
		ListIterator<Carte> carteRecherchee;
		carteRecherchee = main.listIterator(p_indiceCarteAJouer - 1);
		Carte aRetourner = carteRecherchee.next();*/
		if (p_cardToPlayIndex > 0 && p_cardToPlayIndex <= this.cardsNumberInHand) return this.hand.get(p_cardToPlayIndex-1);
		
		return null;
	}
	
	public int getNumberOfCardsInHand()
	{
		return this.cardsNumberInHand;
	}
	
	
	public String toString()
	{
		String toReturn = "******* La main *******\n";
		int cardNumber = 1;
		
		for (Card myCard : this.hand)
		{
			toReturn += "Carte numéro : " + cardNumber + "\n " + myCard.getCardRepresentation(true) + "\n";
			cardNumber++;
		}
		
		return toReturn + "***********************";
	}

	
	
	
}