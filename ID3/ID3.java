import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class ID3 {
	public DecisionTree getDecisionTree(List<AttributeSet> attributeSets) {
		DecisionTree dt = new DecisionTree();
		dt.root = new DecisionTreeNode();
		// 待处理的DecisionTreeNode
		Queue<DecisionTreeNode> nodes = new LinkedList<DecisionTreeNode>();
		// 待处理DecisionTreeNode所对应的属性集
		Queue<List<AttributeSet>> filteredAttributeSets = new LinkedList<List<AttributeSet>>();
		
		nodes.offer(dt.root);
		filteredAttributeSets.offer(attributeSets);
		do {
			DecisionTreeNode dtn = nodes.poll();
			List<AttributeSet> correspondingAttributeSet = filteredAttributeSets.poll();
			dtn.attributeName = getAttributeNameWithMaxInformationGain(
					correspondingAttributeSet, dtn.usedAttributeNames);
			dtn.usedAttributeNames.add(dtn.attributeName);
			dtn.children = getAttributesUsingAttributeName(dtn.attributeName, 
					correspondingAttributeSet, dtn.usedAttributeNames);
			
			// 对于最佳分类属性建立孩子节点
			for ( Map.Entry<Attribute, DecisionTreeNode> e : dtn.children.entrySet() ) {
				List<AttributeSet> restAttributeSet = getAttributeSets(e.getKey(), correspondingAttributeSet);
				// 检查是否所有属性集都已被全部分类
				if ( getEntropy(restAttributeSet) == 0 ) {
					DecisionTreeLeafNode dtln = new DecisionTreeLeafNode();
					Attribute decisionAttribute = null;
					if ( restAttributeSet.size() != 0 ) {
						decisionAttribute = restAttributeSet.get(0).getDecisionAttribute();
						dtln.decisionAttribute = decisionAttribute;
					}
					e.setValue(dtln);
				} else {
					nodes.offer(e.getValue());
					filteredAttributeSets.offer(restAttributeSet);
				}
			}
		} while ( !nodes.isEmpty() );
		return dt;
	}
	
	/**
	 * 获取决策树的孩子节点.
	 * @param attributeName 将父节点的属性集分裂的属性名称
	 * @param attributeSets 父节点的属性集
	 * @param usedAttributeNames 已经使用的属性名称
	 * @return
	 */
	private Map<Attribute, DecisionTreeNode> getAttributesUsingAttributeName(
			String attributeName, List<AttributeSet> attributeSets, List<String> usedAttributeNames) {
		Map<Attribute, DecisionTreeNode> decisionTreeNodeChildren = 
				new HashMap<Attribute, DecisionTreeNode>();
		for ( AttributeSet as : attributeSets ) { 
			Attribute attr = as.getAttribute(attributeName);
			if ( !decisionTreeNodeChildren.containsKey(attr) ) {
				DecisionTreeNode dtn = new DecisionTreeNode();
				dtn.usedAttributeNames = new ArrayList<String>(usedAttributeNames);
				decisionTreeNodeChildren.put(attr, dtn);
			}
		}
		return decisionTreeNodeChildren;
	}
	
	/**
	 * 获得最佳(信息增益最大)的分类属性.
	 * @param attributeSets 待分类的属性集
	 * @param usedAttributeNames 已经使用过的分类属性 
	 * @return 最佳分类属性的名称
	 */
	private String getAttributeNameWithMaxInformationGain(List<AttributeSet> attributeSets, List<String> usedAttributeNames) {
		if ( attributeSets.size() == 0 ) {
			return null;
		}
		String bestClassificationAttributeName = null;
		double minEntropy = Double.MAX_VALUE;
		List<String> classificationAttributeNames = attributeSets.get(0).getClassificationAttributeNames();
		for ( String classificationAttributeName : classificationAttributeNames ) {
			if ( usedAttributeNames == null ||
					!usedAttributeNames.contains(classificationAttributeName) ) {
				double entropy = getEntropy(classificationAttributeName, attributeSets);
				if ( entropy < minEntropy ) {
					minEntropy = entropy;
					bestClassificationAttributeName = classificationAttributeName;
				}
			}
		}
		return bestClassificationAttributeName;
	}
	
	/**
	 * 获取满足某个属性值的属性集.
	 * @param classificationAttribute 分类属性
	 * @param attributeSets 待分类的属性集
	 * @return 满足某个属性值的属性集
	 */
	private List<AttributeSet> getAttributeSets(Attribute classificationAttribute, List<AttributeSet> attributeSets) {
		List<AttributeSet> filteredAttributeSets = new ArrayList<AttributeSet>();
		for ( AttributeSet as : attributeSets ) { 
			Attribute attr = as.getAttribute(classificationAttribute.attributeName);
			if ( attr.attributeValue.equals(classificationAttribute.attributeValue) ) {
				filteredAttributeSets.add(as);
			}
		}
		return filteredAttributeSets;
	}

	/**
	 * 获得未分类时的信息熵.
	 * @param attributeSets 待分类的属性集
	 * @return 未分类时的信息熵
	 */
	private double getEntropy(List<AttributeSet> attributeSets) {
		Map<Attribute, Integer> classCounter = new HashMap<Attribute, Integer>();
		
		for ( AttributeSet as : attributeSets ) {
			Attribute attr = as.getDecisionAttribute();
			if ( classCounter.containsKey(attr) ) {
				int count = classCounter.get(attr);
				classCounter.put(attr, count + 1);
			} else {
				classCounter.put(attr, 1);
			}
		}
		int totalCount = attributeSets.size();
		double entropy = 0;
		for ( Map.Entry<Attribute, Integer> e : classCounter.entrySet() ) {
			entropy += getEntropy(e.getValue(), totalCount);
		}
		return entropy;
	}

	/**
	 * 对于某个特定的属性计算信息熵.
	 * @param attributeName 特定的属性(例如: 温度)
	 * @param attributeSets 待分类的属性集
	 * @return 某个特定的属性的信息熵
	 */
	private double getEntropy(String attributeName, List<AttributeSet> attributeSets) {
		Map<Attribute, List<AttributeSet>> attributes = new HashMap<Attribute, List<AttributeSet>>();
		for ( AttributeSet as : attributeSets ) {
			Attribute attr = as.getAttribute(attributeName);
			if ( attributes.containsKey(attr) ) {
				List<AttributeSet> l = attributes.get(attr);
				l.add(as);
			} else {
				List<AttributeSet> l = new ArrayList<AttributeSet>();
				l.add(as);
				attributes.put(attr, l);
			}
		}
		int totalCount = attributeSets.size();
		double entropy = 0;
		for ( Map.Entry<Attribute, List<AttributeSet>> e : attributes.entrySet() ) {
			List<AttributeSet> l = e.getValue();
			entropy += (l.size() * 1.0 / totalCount) * getEntropy(e.getKey(), l);
		}
		return entropy;
	}

	/**
	 * 对于某个特定的属性值计算信息熵.
	 * @param classificationAttribute 特定的属性值(例如: 温度 = 高)
	 * @param attributeSets 待分类的属性集
	 * @return 某个特定的属性值的信息熵
	 */
	private double getEntropy(Attribute classificationAttribute, List<AttributeSet> attributeSets) {
		Map<Attribute, Integer> classCounter = new HashMap<Attribute, Integer>();
		
		for ( AttributeSet as : attributeSets ) {
			Attribute attr = as.getAttribute(classificationAttribute.attributeName);
			if ( attr.attributeValue.equals(classificationAttribute.attributeValue) ) {
				Attribute decisionAttribute = as.getDecisionAttribute();
				if ( classCounter.containsKey(decisionAttribute) ) {
					int count = classCounter.get(decisionAttribute);
					classCounter.put(decisionAttribute, count + 1);
				} else {
					classCounter.put(decisionAttribute, 1);
				}
			}
		}
		int totalCount = attributeSets.size();
		double entropy = 0;
		for ( Map.Entry<Attribute, Integer> e : classCounter.entrySet() ) {
			entropy += getEntropy(e.getValue(), totalCount);
		}
		return entropy;
	}

	/**
	 * 计算信息熵.
	 * @param currentCount 分类后该属性值的实例数量
	 * @param totalCount 分类前该属性的实例数量
	 * @return 该属性值的信息熵
	 */
	private double getEntropy(int currentCount, int totalCount) {
		if ( currentCount == 0 || totalCount == 0 ) {
			return 0;
		}
		double p = currentCount * 1.0 / totalCount;
		return -p * Math.log(p) / Math.log(2);
	}
}
