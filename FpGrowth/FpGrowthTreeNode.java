import java.util.HashMap;
import java.util.Map;

public class FpGrowthTreeNode {
	public FpGrowthTreeNode(Attribute attribute, FpGrowthTreeNode parent) {
		this.attribute = attribute;
		this.parent = parent;
		this.children = new HashMap<Attribute, FpGrowthTreeNode>();
		this.nextSiblings = null;
		this.support = 1;
	}
	
	public boolean hasAttribute(Attribute attr) {
		return children.containsKey(attr);
	}
	
	public Attribute attribute;
	
	public FpGrowthTreeNode parent;
	
	public Map<Attribute, FpGrowthTreeNode> children;
	
	public FpGrowthTreeNode nextSiblings;
	
	public int support;
}
