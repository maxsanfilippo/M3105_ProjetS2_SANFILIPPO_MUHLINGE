package fallen_heroes;

public class Invocation extends Card
{
	
	private int attackPoints;

	private final int defensePoints;

	private int currentDefensePoints;

	private boolean doNotDoItsFirstTurn;

	private boolean hasAttacked;

	private String capacity;

	private int criDeGuerreEffect;

	private String criDeGuerreTarget;

	private String conferationEffect;

	private boolean hasUsedItsSecondAttack_Enchainement;

	public Invocation(String p_name, String p_imagePath, String p_description, int p_manaCost, 
						int p_attackPoints, int p_defensePoints, String p_capacity, int p_criDeGuerreEffect,
						String p_criDeGuerreTarget, String p_conferationEffect)
	{
		super(p_name, p_imagePath, p_description, p_manaCost);
		this.attackPoints = p_attackPoints;
		this.defensePoints = p_defensePoints;
		this.currentDefensePoints = p_defensePoints;
		this.doNotDoItsFirstTurn = false;
		this.hasAttacked = false;
		this.capacity = p_capacity;
		this.criDeGuerreEffect = p_criDeGuerreEffect;
		this.criDeGuerreTarget = p_criDeGuerreTarget;
		this.conferationEffect = p_conferationEffect;
		
		this.hasUsedItsSecondAttack_Enchainement = false;
	}
	

	public void doItsFirstTurn()
	{
		this.doNotDoItsFirstTurn = false;
	}

	public void makeOperational()
	{
		this.doNotDoItsFirstTurn = true;
		this.hasAttacked = false;
		this.hasUsedItsSecondAttack_Enchainement = false;
	}

	public void makeInactive()
	{
		this.hasAttacked = true;
	}

	public int getCurrentDefensePoints()
	{
		return this.currentDefensePoints;
	}

	public int getAttackPoints()
	{
		return this.attackPoints;
	}

	public String getCapacity()
	{
		return this.capacity;
	}

	public void updateLifePoints(int deltaLifePoints)
	{
		this.currentDefensePoints -= deltaLifePoints;
	}

	public boolean doNotDoItsFirstTurn()
	{
		return this.doNotDoItsFirstTurn;
	}
	
	public int changeLife(int p_deltaLife)
	{
		this.currentDefensePoints -= p_deltaLife;
		
		return this.currentDefensePoints;
	}

	public void changeAttack(int p_deltaAttack)
	{
		this.attackPoints += p_deltaAttack;
	}

	public boolean hasAttacked()
	{
		return this.hasAttacked;
	}

	public String returnCapacity()
	{
		return this.capacity;
	}

	public String returnConferationCapacity()
	{
		return this.conferationEffect;
	}

	public void changeHasUsedItsSecondAttack_Enchainement(boolean p_value)
	{
		this.hasUsedItsSecondAttack_Enchainement = p_value;
	}

	public boolean returnHasUsedItsSecondAttack_Enchainement()
	{
		return this.hasUsedItsSecondAttack_Enchainement;
	}
	
	public void affectTheCapacity(String p_capacity)
	{
		this.capacity = p_capacity;
	}
	
	public int returnCriDeGuerreEffect()
	{
		return this.criDeGuerreEffect;
	}
	
	public String returnCriDeGuerreTarget()
	{
		return this.criDeGuerreTarget;
	}
	
	public String getCardRepresentation(boolean isInTheHand)
	{
		String writeCapacity = "";
		if (this.capacity.equals("null")) writeCapacity = "aucune";
		else writeCapacity = this.capacity;
			
		if (isInTheHand) return super.getCardRepresentation(isInTheHand)  + "\n +\tAttaque : " + this.attackPoints + "\n +\tVie : " + this.currentDefensePoints + "\n +\tCout en mana : " + this.getManaCost() + "\n +\tCapacité spéciale : " + writeCapacity + "\n +---------------------------------+";
		return super.getCardRepresentation(isInTheHand)  + "\n +\tAttaque : " + this.attackPoints + "\n +\tVie : " + this.currentDefensePoints + "\n +\tDisponible pour attaquer : " + this.doNotDoItsFirstTurn + "\n +\tCapacité spéciale : " + writeCapacity + "\n +---------------------------------+";
	}



}
