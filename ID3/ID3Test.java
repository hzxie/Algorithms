import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class ID3Test {
	public static void main(String[] args) {
		final String FILE_PATH = "ID3-TestCase.csv";

		ID3 id3 = new ID3();
		List<AttributeSet> weather = null;
		try {
			weather = IOUtils.getWeatherRecords(FILE_PATH);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Scanner in = new Scanner(System.in);
		String[] attributes = new String[4];
		System.out.println("Input Outlook: (Sunny/Overcast/Rain)");
		attributes[0] = in.nextLine();
		System.out.println("Input Temperature: (Hot/Mild/Cool)");
		attributes[1] = in.nextLine();
		System.out.println("Input Humidity: (Normal/High)");
		attributes[2] = in.nextLine();
		System.out.println("Input Wind: (Strong/Weak)");
		attributes[3] = in.nextLine();
		in.close();
		
		AttributeSet as = new Weather(
				Weather.getOutlookAttribute(attributes[0]),
				Weather.getTemperatureAttribute(attributes[1]),
				Weather.getHumidityAttribute(attributes[2]),
				Weather.getWindAttribute(attributes[3]));
		DecisionTree dt = id3.getDecisionTree(weather);
		System.out.println("\nDecision: " + dt.getDecision(as));
	}
}
