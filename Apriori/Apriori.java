import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Apriori {
	public static List<ItemSet> getFrequentItemSets(int minSupport, List<AttributeSet> attributeSets) {
		List<ItemSet> frequentItemSet = getFirstFrequentItemSet(minSupport, attributeSets);
		List<ItemSet> kthFrequentItemSet = frequentItemSet;
		do {
			kthFrequentItemSet = getKthFrequentItemSet(minSupport, kthFrequentItemSet, attributeSets);
			frequentItemSet.addAll(kthFrequentItemSet);
		} while ( kthFrequentItemSet != null && !kthFrequentItemSet.isEmpty() );
		
		return frequentItemSet;
	}
	
	private static List<ItemSet> getFirstFrequentItemSet(int minSupport, List<AttributeSet> attributeSets) {
		List<ItemSet> frequentItemSet = new ArrayList<ItemSet>();
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
		for ( Entry<Attribute, Integer> e : attrCounter.entrySet() ) {
			if ( e.getValue() >= minSupport ) {
				frequentItemSet.add(new ItemSet(e.getKey()));
			}
		}
		return frequentItemSet;
	}
	
	private static List<ItemSet> getKthFrequentItemSet(int minSupport, 
			List<ItemSet> pfrequentItemSet, List<AttributeSet> attributeSets) {
		List<ItemSet> frequentItemSet = new ArrayList<ItemSet>();
		Map<ItemSet, Integer> itemSetCounter = new HashMap<ItemSet, Integer>();
		
		for ( int i = 0; i < pfrequentItemSet.size(); ++ i ) {
			for ( int j = i + 1; j < pfrequentItemSet.size(); ++ j ) {
				ItemSet kthFrequentItem = ItemSet.mergeItemSet(pfrequentItemSet.get(i), pfrequentItemSet.get(j));
				if ( kthFrequentItem != null ) {
					itemSetCounter.put(kthFrequentItem, 0);
				}
			}
		}
		// Calculate the support
		for ( Entry<ItemSet, Integer> e : itemSetCounter.entrySet() ) {
			ItemSet is = e.getKey();
			for ( AttributeSet as : attributeSets ) {
				if ( as.hasItemSet(is) ) {
					Integer count = e.getValue();
					e.setValue(count + 1);
				}
			}
		}
		
		for ( Entry<ItemSet, Integer> e : itemSetCounter.entrySet() ) {
			ItemSet is = e.getKey();
			Integer count = e.getValue();
			if ( count >= minSupport ) {
				frequentItemSet.add(is);
			}
		}
		return frequentItemSet;
	}
}
