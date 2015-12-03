import java.io.IOException;
import java.util.List;

public class AprioriTest {
	public static void main(String[] args) {
		final String FILE_PATH = "adult.txt";
		final int DISCRETIZATION_LEVELS = 10;
		final double MIN_SUPPORT_RATIO = 0.5;
		
		List<AttributeSet> people = null;
		try {
			people = IOUtils.getPeopleRecords(FILE_PATH);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		DiscretizationUtils.discretization(people, DISCRETIZATION_LEVELS);
		
		int totalRecords = people.size();
		int minSupport = (int)Math.floor(totalRecords * MIN_SUPPORT_RATIO);
		List<ItemSet> frequentItemSets = Apriori.getFrequentItemSets(minSupport, people);
		for ( ItemSet is : frequentItemSets ) {
			System.out.println(is);
		}
	}
}
