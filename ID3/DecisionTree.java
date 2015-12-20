public class DecisionTree {
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return root.toString();
	}
	
	public Attribute getDecision(AttributeSet as) {
		if ( root == null ) {
			return null;
		}
		DecisionTreeNode current = root;
		while ( current != null ) {
			String attributeName = current.attributeName;
			
			Attribute attr = as.getAttribute(attributeName);
			current = current.children.get(attr);
			
			if ( current instanceof DecisionTreeLeafNode ) {
				return ((DecisionTreeLeafNode)current).decisionAttribute;
			}
		}
		return null;
	}
	
	public DecisionTreeNode root = null;
}
