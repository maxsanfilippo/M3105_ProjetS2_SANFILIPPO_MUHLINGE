package fallen_heroes;

/**
 * The class that designates cards but precisely the invocation
 * @author Group 6
 */
public class Invocation extends Card
{
	//private int tourAEtePosee;
	
	/**
	 * attack points of the invocation
	 */
	private int attackPoints;
	
	/**
	 * defense points of the invocation
	 */
	private final int defensePoints;
	
	/**
	 * current defense points of the invocation
	 */
	private int currentDefensePoints;
	
	/**
	 * do not do his first turn
	 */
	private boolean doNotDoHisFirstTurn;
	
	/**
	 * it has attacked or not
	 */
	private boolean hasAttacked;
	
	/**
	 * the capacity of the invocation
	 */
	private String capacity;
	
	/**
	 * "cri de guerre" effect
	 */
	private int criDeGuerreEffect;
	
	/**
	 * "cri de guerre" target
	 */
	private String criDeGuerreTarget;
	
	/**
	 * "conferation" effect
	 */
	private String conferationEffect;
	
	/**
	 * it has used his second attack
	 */
	private boolean hasUsedHisSecondAttack_Enchainement;
	

	/**
	 * initializes the invocation 
	 * @param p_name the name
	 * @param p_imagePath the path of the image
	 * @param p_description the description
	 * @param p_manaCost the mana cost 
	 * @param p_attackPoints attack points
	 * @param p_defensePoints defense points
	 * @param p_capacity the capacity
	 * @param p_criDeGuerreEffect the "cri de guerre" effect
	 * @param p_criDeGuerreTarget the "cri de guerre" target
	 * @param p_conferationEffect the "conferation" effect
	 */
	public Invocation(String p_name, String p_imagePath, String p_description, int p_manaCost, 
						int p_attackPoints, int p_defensePoints, String p_capacity, int p_criDeGuerreEffect,
						String p_criDeGuerreTarget, String p_conferationEffect)
	{
		super(p_name, p_imagePath, p_description, p_manaCost);
		this.attackPoints = p_attackPoints;
		this.defensePoints = p_defensePoints;
		this.currentDefensePoints = p_defensePoints;
		this.doNotDoHisFirstTurn = false;
		this.hasAttacked = false;
		this.capacity = p_capacity;
		this.criDeGuerreEffect = p_criDeGuerreEffect;
		this.criDeGuerreTarget = p_criDeGuerreTarget;
		this.conferationEffect = p_conferationEffect;
		
		this.hasUsedHisSecondAttack_Enchainement = false;
	}
	
	/**
	 * do his first turn
	 */
	public void doHisFirstTurn()
	{
		this.doNotDoHisFirstTurn = false;
	}
	
	/**
	 * make an invocation operational
	 */
	public void makeOperational()
	{
		this.doNotDoHisFirstTurn = true;
		this.hasAttacked = false;
		this.hasUsedHisSecondAttack_Enchainement = false;
	}
	
	/**
	 * make an invocation inactive
	 */
	public void makeInactive()
	{
		this.hasAttacked = true;
	}

	/**
	 * get current defense points
	 * @return current defense points
	 */
	public int getCurrentDefensePoints()
	{
		return this.currentDefensePoints;
	}
	
	/**
	 * get attack points of the invocation
	 * @return attack points of the invocation
	 */
	public int getAttackPoints()
	{
		return this.attackPoints;
	}
	
	/**
	 * get the capacity of the invocation
	 * @return the capacity
	 */
	public String getCapacity()
	{
		return this.capacity;
	}
	
	/** 
	 * update life points
	 * @param deltaLifePoints what we want to subtract
	 */
	public void updateLifePoints(int deltaLifePoints)
	{
		this.currentDefensePoints -= deltaLifePoints;
	}
	
	/**
	 * if it do his first turn or not
	 * @return if it do his first turn or not
	 */
	public boolean doNotDoHisFirstTurn()
	{
		return this.doNotDoHisFirstTurn;
	}
	
	/**
	 * change the life points
	 * @param p_deltaLife what we want to subtract
	 * @return current defense points
	 */
	public int changeLife(int p_deltaLife)
	{
		this.currentDefensePoints -= p_deltaLife;
		
		return this.currentDefensePoints;
	}
	
	/**
	 * change the attack points
	 * @param p_deltaAttack what we want to add
	 */
	public void changeAttack(int p_deltaAttack)
	{
		this.attackPoints += p_deltaAttack;
	}
	
	/**
	 * it has attacked or not
	 * @return if it has attacked or not
	 */
	public boolean haveAttacked()
	{
		return this.hasAttacked;
	}
	
	/**
	 * return the capacity
	 * @return the capacity
	 */
	public String returnCapacity()
	{
		return this.capacity;
	}
	
	/**
	 * return the "conferation" capacity
	 * @return the "conferation" capacity
	 */
	public String returnConferationCapacity()
	{
		return this.conferationEffect;
	}
	
	/**
	 * affect false or true if it has used his second attack "enchainement" or not
	 * @param p_value false of true
	 */
	public void changeHasUsedHisSecondAttack_Enchainement(boolean p_value)
	{
		this.hasUsedHisSecondAttack_Enchainement = p_value;
	}
	
	/**
	 * return if it has used his second attack "enchainement" or not
	 * @return if it has used his second attack "enchainement" or not
	 */
	public boolean returnHasUsedHisSecondAttack_Enchainement()
	{
		return this.hasUsedHisSecondAttack_Enchainement;
	}
	
	/**
	 * affect the special capacity
	 * @param p_capacity the capacity
	 */
	public void affectTheSpecialCapacity(String p_capacity)
	{
		this.capacity = p_capacity;
	}
	
	/**
	 * return the "Cri De Guerre" effect
	 * @return the "Cri De Guerre" effect
	 */
	public int returnCriDeGuerreEffect()
	{
		return this.criDeGuerreEffect;
	}
	
	/**
	 * return the "Cri De Guerre" target
	 * @return the "Cri de guerre" target
	 */
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
		return super.getCardRepresentation(isInTheHand)  + "\n +\tAttaque : " + this.attackPoints + "\n +\tVie : " + this.currentDefensePoints + "\n +\tDisponible pour attaquer : " + this.doNotDoHisFirstTurn + "\n +\tCapacité spéciale : " + writeCapacity + "\n +---------------------------------+";
	}



}
