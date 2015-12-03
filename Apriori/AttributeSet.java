import java.util.List;

public interface AttributeSet {
	/**
	 * Get all Attribute objects in the class.
	 * @return all Attribute objects with in a list
	 */
	public List<Attribute> getAtrtibutes();
	
	/**
	 * Get all continuous Attribute objects in the class.
	 * @return all continuous Attribute objects with in a list
	 */
	public List<Attribute> getContinuousAtrtibutes();
	
	/**
	 * Check if the object has the ItemSet.
	 * @param is - the ItemSet need to check
	 * @return if the object has the ItemSet
	 */
	public boolean hasItemSet(ItemSet is);
	
	/**
	 * Check if the object has the Attribute.
	 * @param attr - the Attribute need to check
	 * @return if the object has the Attribute
	 */
	public boolean hasAttribute(Attribute attr);
}
