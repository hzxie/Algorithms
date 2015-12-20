import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Weather implements AttributeSet {
	public Weather(Attribute outlook, Attribute temperature, 
			Attribute humidity, Attribute wind) {
		this.outlook = outlook;
		this.temperature = temperature;
		this.humidity = humidity;
		this.wind = wind;
		this.playBall = getPlayBallAttribute("?");
	}
	

	public Weather(Attribute outlook, Attribute temperature, 
			Attribute humidity, Attribute wind, Attribute playBall) {
		this.outlook = outlook;
		this.temperature = temperature;
		this.humidity = humidity;
		this.wind = wind;
		this.playBall = playBall;
	}
	
	@Override
	public Attribute getAttribute(String attributeName) {
		if ( attributeName.equals("Outlook") ) {
			return outlook;
		} else if ( attributeName.equals("Temperature") ) {
			return temperature;
		} else if ( attributeName.equals("Humidity") ) {
			return humidity;
		} else if ( attributeName.equals("Wind") ) {
			return wind;
		} else if ( attributeName.equals("PlayBall") ) {
			return playBall;
		}
		return null;
	}

	@Override
	public Attribute getDecisionAttribute() {
		return playBall;
	}
	
	public List<String> getClassificationAttributeNames() {
		return classificationAttributeNames;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return String.format("Weather[%s, %s, %s, %s, %s]", 
				new Object[]{outlook, temperature, humidity, wind, playBall});
	}
	
	/* Getter and Setter for Outlook */
	public static Attribute getOutlookAttribute(String attributeValue) {
		String attributeName = "Outlook";
		Outlook value = getOutlookEnumValue(attributeValue);
		boolean isContinuous = false;
		boolean isIgnored = attributeValue.equals("?") || value == Outlook.UNKNOWN;

		return new Attribute(attributeName, isContinuous, isIgnored, value);
	}
	
	private static Outlook getOutlookEnumValue(String value) {
		return OUTLOOK_MAP.containsKey(value) ? OUTLOOK_MAP.get(value) : Outlook.UNKNOWN;
	}
	
	/* Getter and Setter for Temperature */
	public static Attribute getTemperatureAttribute(String attributeValue) {
		String attributeName = "Temperature";
		Temperature value = getTemperatureEnumValue(attributeValue);
		boolean isContinuous = false;
		boolean isIgnored = attributeValue.equals("?") || value == Temperature.UNKNOWN;

		return new Attribute(attributeName, isContinuous, isIgnored, value);
	}

	private static Temperature getTemperatureEnumValue(String value) {
		return TEMPERATURE_MAP.containsKey(value) ? TEMPERATURE_MAP.get(value) : Temperature.UNKNOWN;
	}
	
	/* Getter and Setter for Humidity */
	public static Attribute getHumidityAttribute(String attributeValue) {
		String attributeName = "Humidity";
		Humidity value = getHumidityEnumValue(attributeValue);
		boolean isContinuous = false;
		boolean isIgnored = attributeValue.equals("?") || value == Humidity.UNKNOWN;

		return new Attribute(attributeName, isContinuous, isIgnored, value);
	}

	private static Humidity getHumidityEnumValue(String value) {
		return HUMIDITY_MAP.containsKey(value) ? HUMIDITY_MAP.get(value) : Humidity.UNKNOWN;
	}
	
	/* Getter and Setter for Wind */
	public static Attribute getWindAttribute(String attributeValue) {
		String attributeName = "Wind";
		Wind value = getWindEnumValue(attributeValue);
		boolean isContinuous = false;
		boolean isIgnored = attributeValue.equals("?") || value == Wind.UNKNOWN;

		return new Attribute(attributeName, isContinuous, isIgnored, value);
	}

	private static Wind getWindEnumValue(String value) {
		return WIND_MAP.containsKey(value) ? WIND_MAP.get(value) : Wind.UNKNOWN;
	}
	
	/* Getter and Setter for PlayBall */
	public static Attribute getPlayBallAttribute(String attributeValue) {
		String attributeName = "PlayBall";
		PlayBall value = getPlayBallEnumValue(attributeValue);
		boolean isContinuous = false;
		boolean isIgnored = attributeValue.equals("?") || value == PlayBall.UNKNOWN;

		return new Attribute(attributeName, isContinuous, isIgnored, value);
	}

	private static PlayBall getPlayBallEnumValue(String value) {
		return PLAY_BALL_MAP.containsKey(value) ? PLAY_BALL_MAP.get(value) : PlayBall.UNKNOWN;
	}
	
	/* The attributes of Weather */
	public final Attribute outlook;
	public final Attribute temperature;
	public final Attribute humidity;
	public final Attribute wind;
	public final Attribute playBall;
	
	private static final List<String> classificationAttributeNames;
	static {
		classificationAttributeNames = new ArrayList<String>();
		classificationAttributeNames.add("Outlook");
		classificationAttributeNames.add("Temperature");
		classificationAttributeNames.add("Humidity");
		classificationAttributeNames.add("Wind");
	}
	
	private static enum Outlook {
		UNKNOWN, SUNNY, OVERCAST, RAIN
	};
	private static final Map<String, Outlook> OUTLOOK_MAP;
	static {
		OUTLOOK_MAP = new HashMap<String, Outlook>(4, 1);
		OUTLOOK_MAP.put("?", Outlook.UNKNOWN);
		OUTLOOK_MAP.put("Sunny", Outlook.SUNNY);
		OUTLOOK_MAP.put("Overcast", Outlook.OVERCAST);
		OUTLOOK_MAP.put("Rain", Outlook.RAIN);
	}
	
	private static enum Temperature {
		UNKNOWN, HOT, MILD, COOL
	};
	private static final Map<String, Temperature> TEMPERATURE_MAP;
	static {
		TEMPERATURE_MAP = new HashMap<String, Temperature>(4, 1);
		TEMPERATURE_MAP.put("?", Temperature.UNKNOWN);
		TEMPERATURE_MAP.put("Hot", Temperature.HOT);
		TEMPERATURE_MAP.put("Mild", Temperature.MILD);
		TEMPERATURE_MAP.put("Cool", Temperature.COOL);
	}
	
	private static enum Humidity {
		UNKNOWN, HIGH, NORMAL, LOW
	};
	private static final Map<String, Humidity> HUMIDITY_MAP;
	static {
		HUMIDITY_MAP = new HashMap<String, Humidity>(4, 1);
		HUMIDITY_MAP.put("?", Humidity.UNKNOWN);
		HUMIDITY_MAP.put("High", Humidity.HIGH);
		HUMIDITY_MAP.put("Normal", Humidity.NORMAL);
		HUMIDITY_MAP.put("Low", Humidity.LOW);
	}
	
	private static enum Wind {
		UNKNOWN, WEAK, STRONG
	};
	private static final Map<String, Wind> WIND_MAP;
	static {
		WIND_MAP = new HashMap<String, Wind>(3, 1);
		WIND_MAP.put("?", Wind.UNKNOWN);
		WIND_MAP.put("Weak", Wind.WEAK);
		WIND_MAP.put("Strong", Wind.STRONG);
	}
	
	private static enum PlayBall {
		UNKNOWN, YES, NO
	};
	private static final Map<String, PlayBall> PLAY_BALL_MAP;
	static {
		PLAY_BALL_MAP = new HashMap<String, PlayBall>(3, 1);
		PLAY_BALL_MAP.put("?", PlayBall.UNKNOWN);
		PLAY_BALL_MAP.put("Yes", PlayBall.YES);
		PLAY_BALL_MAP.put("No", PlayBall.NO);
	}
}
