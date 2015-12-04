import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ItemSet {
	public ItemSet() { }
	
	public ItemSet(Attribute attr, int support) {
		attrs.put(attr, support);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ItemSet: {");
		for ( Entry<Attribute, Integer> attr : attrs.entrySet() ) {
			sb.append(attr + " ");
		}
		sb.append("}");
		return sb.toString();
	}

	public void put(Attribute attr, int support) {
		if ( attr == null ) {
			return;
		}
		attrs.put(attr, support);
	}
	
	public int size() {
		return attrs.size();
	}
	
	public Map<Attribute, Integer> attrs = new HashMap<Attribute, Integer>();
}
