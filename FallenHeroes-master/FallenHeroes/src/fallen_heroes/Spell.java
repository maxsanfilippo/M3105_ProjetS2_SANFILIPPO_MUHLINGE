package fallen_heroes;

public class Spell extends Card
{

	private int effect;

	private String spellTarget;

	public Spell(String p_name, String p_imagePath, String p_description, int p_manaCost,int p_effect, String p_spellTarget)
	{
		super(p_name, p_imagePath, p_description, p_manaCost);
		this.effect = p_effect;
		this.spellTarget = p_spellTarget;
	}

	public String returnSpellTarget()
	{
		return this.spellTarget;
	}

	public int returnEffect()
	{
		return this.effect;
	}

	public String getCardRepresentation(boolean isInTheHand)
	{
		return  super.getCardRepresentation(true) + "\n +\tCout en mana : " + this.getManaCost()  + "\n +\tCible : " + this.spellTarget + "\n +\tEffet : " + this.effect + "\n +---------------------------------+";
	}

}
