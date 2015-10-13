package fallen_heroes;

/**
 * The class that designates cards but precisely the spell
 * @author Group 6
 */
public class Spell extends Card
{
	/**
	 * Effect of the spell
	 */
	private int effect;
	
	/**
	 * Target of the spell
	 */
	private String spellTarget;
	
	/**
	 * Initializes the spell
	 * @param p_name name of the spell
	 * @param p_imagePath path of the image
	 * @param p_description description of the spell
	 * @param p_manaCost mana cost of the spell
	 * @param p_effect effect of the spell
	 * @param p_spellTarget target of the spell
	 */
	public Spell(String p_name, String p_imagePath, String p_description, int p_manaCost,int p_effect, String p_spellTarget)
	{
		super(p_name, p_imagePath, p_description, p_manaCost);
		this.effect = p_effect;
		this.spellTarget = p_spellTarget;
	}
	
	/**
	 * return the target of the spell
	 * @return the spell target
	 */
	public String returnSpellTarget()
	{
		return this.spellTarget;
	}
	
	/**
	 * return the effect of the spell
	 * @return the effect of the spell
	 */
	public int returnEffect()
	{
		return this.effect;
	}
	

	public String getCardRepresentation(boolean isInTheHand)
	{
		return  super.getCardRepresentation(true) + "\n +\tCout en mana : " + this.getManaCost()  + "\n +\tCible : " + this.spellTarget + "\n +\tEffet : " + this.effect + "\n +---------------------------------+";
	}

}
