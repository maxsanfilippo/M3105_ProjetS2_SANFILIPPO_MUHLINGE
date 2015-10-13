package fallen_heroes;
import java.util.LinkedList;

public class Hand
{

	public final static int MAXIMAL_CARDS_NUMBER_IN_HAND = 5;
	
	private int cardsNumberInHand;

	private LinkedList<Card> hand;

	public Hand()
	{
		this.hand = new LinkedList<Card>();
		this.cardsNumberInHand = 0;
	}

	public void addACardInHand(Card cardToAdd)
	{
	
		if (this.cardsNumberInHand < MAXIMAL_CARDS_NUMBER_IN_HAND)
		{
			this.hand.add(cardToAdd);
			this.cardsNumberInHand++;
		}
		
	}

	public void removeCard(Card cardToRemove)
	{
		this.hand.remove(cardToRemove);
		this.cardsNumberInHand--;
	}

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