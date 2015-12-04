import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class IOUtils {
	public static List<AttributeSet> getPeopleRecords(String filePath) throws IOException {
		FileInputStream fis = new FileInputStream(filePath);
        InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
        BufferedReader br = new BufferedReader(isr);

        String line = "";
        List<AttributeSet> attributeSets = new ArrayList<AttributeSet>(); 
        while ( (line = br.readLine()) != null ) {
        	String[] attributes = line.split(", ");
        	if ( attributes.length != 15 ) {
        		System.out.println("[WARN] Line #" + attributeSets.size() + 1 + " is ignored.");
        		continue;
        	}
        	
        	attributeSets.add(new Person(
        		Person.getAgeAttribute(attributes[0]),
        		Person.getWorkClassAttribute(attributes[1]),
        		Person.getFinalWeightAttribute(attributes[2]),
        		Person.getEducationAttribute(attributes[3]),
        		Person.getEducationYearsAttribute(attributes[4]),
        		Person.getMaritalStatusAttribute(attributes[5]),
        		Person.getOccupationAttribute(attributes[6]),
        		Person.getRelationshipAttribute(attributes[7]),
        		Person.getRaceAttribute(attributes[8]),
        		Person.getSexAttribute(attributes[9]),
        		Person.getCapitalGainAttribute(attributes[10]),
        		Person.getCapitalLossAttribute(attributes[11]),
        		Person.getHoursPerWeekAttribute(attributes[12]),
        		Person.getNativeCountryAttribute(attributes[13]),
        		Person.getSalaryAttribute(attributes[14])
        	));
        }
        br.close();
        isr.close();
        fis.close();
        
        return attributeSets;
	}
}
