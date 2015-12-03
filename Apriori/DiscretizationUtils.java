import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiscretizationUtils {
	public static void  discretization(List<AttributeSet> attributeSets, int discretizationLevels) {
		Map<String, Number> maximumAttrValues = new HashMap<String, Number>();
		Map<String, Number> minimumAttrValues = new HashMap<String, Number>();
		
		// get max and min values for continuous values
		for ( AttributeSet as : attributeSets ) {
			List<Attribute> attrs = as.getContinuousAtrtibutes();
			for ( Attribute attr : attrs ) {
				String attributeName = attr.attributeName;
				Number attributeValue = (Number)attr.attributeValue;
				
				if ( !maximumAttrValues.containsKey(attributeName) ) {
					maximumAttrValues.put(attributeName, attributeValue);
				} else if ( maximumAttrValues.get(attributeName).doubleValue() <  attributeValue.doubleValue() ) {
					maximumAttrValues.put(attributeName, attributeValue);
				}
				if ( !minimumAttrValues.containsKey(attributeName) ) {
					minimumAttrValues.put(attributeName, attributeValue);
				} else if ( minimumAttrValues.get(attributeName).doubleValue() >  attributeValue.doubleValue() ) {
					minimumAttrValues.put(attributeName, attributeValue);
				}
			}
		}
		
		// set lower and upper bounds for continuous values
		for ( AttributeSet attributeSet : attributeSets ) {
			List<Attribute> attrs = attributeSet.getContinuousAtrtibutes();
			for ( Attribute attr : attrs ) {
				String attributeName = attr.attributeName;
				double attributeValue = ((Number)attr.attributeValue).doubleValue();
				double maximumValue = maximumAttrValues.get(attributeName).doubleValue();
				double minimumValue = minimumAttrValues.get(attributeName).doubleValue();
				double interval = (maximumValue - minimumValue + 1) / discretizationLevels;
				int baseLevel = (int)Math.floor(attributeValue / interval);
				
				
				attr.lowerValue = baseLevel * interval;
				attr.upperValue = (baseLevel + 1) * interval;
			}
		}
	}
}
