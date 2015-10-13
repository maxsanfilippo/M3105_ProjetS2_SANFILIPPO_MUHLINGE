package fallen_heroes;


/**
 * The class that designates cards we play
 * @author Group 6
 */
public abstract class Card
{
	/**
	 * Name of the card
	 */
	private String name;
	
	/**
	 * Path of the image
	 */
	private String imagePath;
	
	/**
	 * Description of the card
	 */
	private String description;
	
	/**
	 * Mana cost of the card
	 */
	private int manaCost;
	
	
	/**
	 * Initializes the card with all its parameters and initializes its description if there is one
	 * @param p_name Name of the card
	 * @param p_imagePath Path of the image
	 * @param p_description Description of the card
	 * @param p_manaCost Mana cost of the card
	 */
	public Card(String p_name, String p_imagePath, String p_description, int p_manaCost)
	{
		this.name = p_name;
		this.imagePath = p_imagePath;
		
		if (p_description.equals("null")) 
		{
			this.description = "";
		}
		else
		{
			this.description = p_description;
		}
		
		
		this.manaCost = p_manaCost;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public String getImagePath()
	{
		return this.imagePath;
	}
	
	/**
	 * Get the mana cost of the card
	 * @return the mana cost
	 */
	public int getManaCost()
	{
		return this.manaCost;
	}

	/**
	 * Get the representation of the card
	 * @param isInTheHand the card is in the hand or not
	 * @return the representation of the card
	 */
	public String getCardRepresentation(boolean isInTheHand)
	{
		return "+---------------------------------+\n +\tNom : " + this.name;
	}
	
	public boolean equals (Object o)
    {
        if (o == null) return false;
        if (o == this) return true;
         
        if (!(o instanceof Card)) return false;
         
        Card comparing = (Card)o;
        return this.name == comparing.name; 
    }
 
	public int hashCode()
    {
        int hashcode = 0;
         for (int x=0; x<this.name.length(); x++)
             hashcode += (int)this.name.charAt(x);
         
        return hashcode;
    }



}
