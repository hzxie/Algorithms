public class Attribute {
	public Attribute(String attributeName, boolean isContinuous, boolean isIgnored, Object attributeValue) {
		this.attributeName = attributeName;
		this.isContinuous = isContinuous;
		this.isIgnored = isIgnored;
		this.attributeValue = attributeValue;
	}
	
	public Attribute(String attributeName, boolean isContinuous, boolean isIgnored, Number attributeValue, Number lowerValue, Number upperValue) {
		this.attributeName = attributeName;
		this.isContinuous = isContinuous;
		this.isIgnored = isIgnored;
		this.attributeValue = attributeValue;
		this.lowerValue = lowerValue;
		this.upperValue = upperValue;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		int hashCode = attributeName.hashCode();
		
		if ( isContinuous ) {
			hashCode +=  lowerValue.hashCode() + upperValue.hashCode();
		} else {
			hashCode +=  attributeValue.hashCode();
		}
		return hashCode;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		if ( o instanceof Attribute ) {
			return o.hashCode() == this.hashCode();
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String description = "";
		
		if ( isIgnored ) {
			return description;
		}
		if ( isContinuous ) {
			description = String.format("Attribute: [AttrName = %s, AttrValue = [%s, %s)]", 
							new Object[] { attributeName, lowerValue, upperValue });
		} else {
			description = String.format("Attribute: [AttrName = %s, AttrValue = %s]", 
					new Object[] { attributeName, attributeValue });
		}
		return description;
	}
	
	public final String attributeName;
	
	public final boolean isContinuous;
	
	public final boolean isIgnored;
	
	/**
	 * For non-continuous value.
	 */
	public final Object attributeValue;
	
	/**
	 * For continuous value.
	 */
	public Number lowerValue;
	public Number upperValue;
	
}
