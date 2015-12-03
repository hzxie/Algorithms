import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ItemSet {
	// Default Constructor used internally
	private ItemSet() { }
	
	public ItemSet(Attribute attr) {
		String attributeName = attr.attributeName;
		attrs.put(attributeName, attr);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		int hashCode = 0;
		for ( Entry<String, Attribute> e : attrs.entrySet() ) {
			hashCode += e.getValue().hashCode();
		}
		return hashCode;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		if ( o instanceof ItemSet ) {
			return o.hashCode() == this.hashCode();
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ItemSet: {");
		for ( Entry<String, Attribute> attr : attrs.entrySet() ) {
			sb.append(attr + " ");
		}
		sb.append("}");
		return sb.toString();
	}
	
	public int size() {
		return attrs.size();
	}
	
	public void put(Attribute attr) {
		String attributeName = attr.attributeName;
		attrs.put(attributeName, attr);
	}
	
	public List<Attribute> getAttributes() {
		List<Attribute> attrList = new ArrayList<Attribute>();
		for ( Entry<String, Attribute> e : attrs.entrySet() ) {
			attrList.add(e.getValue());
		}
		return attrList;
	}
	
	public static ItemSet mergeItemSet(ItemSet is1, ItemSet is2) {
		if ( is1.size() != is2.size() ) {
			return null;
		}
		Map<String, Attribute> mergedAttrs = new HashMap<String, Attribute>();
		for ( Attribute attr : is1.getAttributes() ) {
			String attributeName = attr.attributeName;
			mergedAttrs.put(attributeName, attr);
		}
		for ( Attribute attr : is2.getAttributes() ) {
			String attributeName = attr.attributeName;
			if ( mergedAttrs.containsKey(attributeName) &&
				!mergedAttrs.get(attributeName).equals(attr) ) {
				// The same attribute that has different value
				return null;
			}
			mergedAttrs.put(attributeName, attr);
		}
		
		if ( mergedAttrs.size() != is1.size() + 1 ) {
			return null;
		}
		ItemSet is = new ItemSet();
		for ( Entry<String, Attribute> e : mergedAttrs.entrySet() ) {
			is.put(e.getValue());
		}
		return is;
	}
	
	private Map<String, Attribute> attrs = new HashMap<String, Attribute>();
}
