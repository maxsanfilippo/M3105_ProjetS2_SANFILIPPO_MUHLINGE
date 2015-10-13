package fallen_heroes;


public abstract class Card
{

	private String name;

	private String imagePath;

	private String description;

	private int manaCost;
	
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
	
	public int getManaCost()
	{
		return this.manaCost;
	}

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
