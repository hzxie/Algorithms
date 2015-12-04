import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class FpGrowth {
	public static List<ItemSet> getFrequentItemSets(int minSupport, List<AttributeSet> attributeSets) {
		Map<Attribute, FpGrowthTreeNode> headerTable = getHeaderTable(minSupport, attributeSets);
		@SuppressWarnings("unused")
		FpGrowthTreeNode root = buildFpGrowthTree(attributeSets, headerTable);
		
		Set<Entry<Attribute, FpGrowthTreeNode>> entrySet = headerTable.entrySet();
		int entrySetSize = entrySet.size();
		@SuppressWarnings("unchecked")
		Entry<Attribute, FpGrowthTreeNode>[] headerTableNodes = new Entry[entrySetSize];
		entrySet.toArray(headerTableNodes);
		
		List<ItemSet> frequentItemSet = new ArrayList<ItemSet>();
		for ( int i = entrySetSize - 1; i >= 0; -- i ) {
			List<ItemSet> conditionalPatternBase = getConditionalPatternBase(headerTableNodes[i].getValue());
			ItemSet conditionalFpTree = getConditionalFpTree(minSupport, conditionalPatternBase);
			
			if ( conditionalFpTree.size() > 0 ) {
				frequentItemSet.add(conditionalFpTree);
			}
		}
		
		return frequentItemSet;
	}

	private static Map<Attribute, FpGrowthTreeNode> getHeaderTable(
			int minSupport, List<AttributeSet> attributeSets) {
		Map<Attribute, FpGrowthTreeNode> headerTable = new LinkedHashMap<Attribute, FpGrowthTreeNode>();
		Map<Attribute, Integer> attrCounter = new HashMap<Attribute, Integer>();
		
		for ( AttributeSet as : attributeSets ) {
			List<Attribute> attrs = as.getAtrtibutes();
			for ( Attribute attr : attrs ) {
				if ( attr.isIgnored ) {
					continue;
				}
				int count = 0;
				if ( attrCounter.containsKey(attr) ) {
					count = attrCounter.get(attr);
				}
				attrCounter.put(attr, count + 1);
			}
		}
		
		attrCounter = sortByComparator(attrCounter);
		for ( Entry<Attribute, Integer> e : attrCounter.entrySet() ) {
			if ( e.getValue() >= minSupport ) {
				headerTable.put(e.getKey(), null);
			}
		}
		return headerTable;
	}
	
	private static Map<Attribute, Integer> sortByComparator(Map<Attribute, Integer> unsortMap) {
		// Convert Map to List
		List<Map.Entry<Attribute, Integer>> list = 
			new LinkedList<Map.Entry<Attribute, Integer>>(unsortMap.entrySet());

		// Sort list with comparator, to compare the Map values
		Collections.sort(list, new Comparator<Map.Entry<Attribute, Integer>>() {
			public int compare(Map.Entry<Attribute, Integer> o1,
                               Map.Entry<Attribute, Integer> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});

		// Convert sorted map back to a Map
		Map<Attribute, Integer> sortedMap = new LinkedHashMap<Attribute, Integer>();
		for ( Iterator<Map.Entry<Attribute, Integer>> it = list.iterator(); it.hasNext(); ) {
			Map.Entry<Attribute, Integer> entry = it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}

	private static FpGrowthTreeNode buildFpGrowthTree(List<AttributeSet> attributeSets,
			Map<Attribute, FpGrowthTreeNode> headerTable) {
		FpGrowthTreeNode root = new FpGrowthTreeNode(null, null);
		for ( AttributeSet as : attributeSets ) {
			FpGrowthTreeNode currentNode = root;
			for ( Entry<Attribute, FpGrowthTreeNode> e : headerTable.entrySet() ) {
				Attribute currentAttribute = e.getKey();
				if ( !as.hasAttribute(currentAttribute) ) {
					continue;
				}
				
				// Build the tree vertically
				FpGrowthTreeNode fgtn = null;
				if ( currentNode.hasAttribute(currentAttribute) ) {
					fgtn = currentNode.children.get(currentAttribute);
					++ fgtn.support;
				} else {
					fgtn = new FpGrowthTreeNode(currentAttribute, currentNode);
					currentNode.children.put(currentAttribute, fgtn);
				}
				
				// Build the tree horizontally
				Set<FpGrowthTreeNode> previousHeaderTableNodes = new HashSet<FpGrowthTreeNode>();
				FpGrowthTreeNode previousHeaderTableNode = null;
				FpGrowthTreeNode currentHeaderTableNode = e.getValue();
				while ( currentHeaderTableNode != null ) {
					previousHeaderTableNodes.add(currentHeaderTableNode);
					previousHeaderTableNode = currentHeaderTableNode;
					currentHeaderTableNode = currentHeaderTableNode.nextSiblings;
				}
				if ( !previousHeaderTableNodes.contains(fgtn) ) {
					if ( previousHeaderTableNode != null ) {
						previousHeaderTableNode.nextSiblings = fgtn;
					} else {
						e.setValue(fgtn);
					}
				}
				
				currentNode = currentNode.children.get(currentAttribute);
			}
		}
		return root;
	}
	
	private static List<ItemSet> getConditionalPatternBase(FpGrowthTreeNode fgtn) {
		List<ItemSet> conditionalPatternBase = new ArrayList<ItemSet>();
		
		while ( fgtn.nextSiblings != null ) {
			ItemSet is = new ItemSet(fgtn.attribute, fgtn.support);
			FpGrowthTreeNode parent = fgtn.parent;
			while ( parent != null ) {
				is.put(parent.attribute, fgtn.support);
				parent = parent.parent;
			}
			
			conditionalPatternBase.add(is);
			fgtn = fgtn.nextSiblings;
		}
		return conditionalPatternBase;
	}
	
	private static ItemSet getConditionalFpTree(int minSupport, 
			List<ItemSet>conditionalPatternBase) {
		ItemSet conditionalFpTree = new ItemSet();
		Map<Attribute, Integer> attrCounter = new HashMap<Attribute, Integer>();
		
		for ( ItemSet is : conditionalPatternBase ) {
			for ( Entry<Attribute, Integer> e : is.attrs.entrySet() ) {
				Attribute attr = e.getKey();
				int support = e.getValue();
				
				if ( attrCounter.containsKey(attr) ) {
					int currentSupport = attrCounter.get(attr);
					attrCounter.put(attr, support + currentSupport);
				} else {
					attrCounter.put(attr, support);
				}
			}
		}
		for ( Entry<Attribute, Integer> e : attrCounter.entrySet() ) {
			Attribute attr = e.getKey();
			int support = e.getValue();
			
			if ( support >= minSupport ) {
				conditionalFpTree.put(attr, support);
			}
		}
		return conditionalFpTree;
	}
}
