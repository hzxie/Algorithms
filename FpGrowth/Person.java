import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Person implements AttributeSet {
	public Person(Attribute age, Attribute workClass, Attribute finalWeight, 
			Attribute education, Attribute educationYears, Attribute maritalStatus, 
			Attribute occupation, Attribute relationship, Attribute race, Attribute sex, 
			Attribute capitalGain, Attribute capitalLoss, Attribute hoursPerWeek, 
			Attribute nativeCountry, Attribute salary) {
		this.age = age;
		this.workClass = workClass;
		this.finalWeight = finalWeight;
		this.education = education;
		this.educationYears = educationYears;
		this.maritalStatus = maritalStatus;
		this.occupation = occupation;
		this.relationship = relationship;
		this.race = race;
		this.sex = sex;
		this.capitalGain = capitalGain;
		this.capitalLoss = capitalLoss;
		this.hoursPerWeek = hoursPerWeek;
		this.nativeCountry = nativeCountry;
		this.salary = salary;
	}

	/* (non-Javadoc)
	 * @see edu.rochester.common.AttributeSet#getAtrtibutes()
	 */
	public List<Attribute> getAtrtibutes() {
		List<Attribute> attrs = getContinuousAtrtibutes();
		attrs.add(workClass);
		attrs.add(education);
		attrs.add(maritalStatus);
		attrs.add(occupation);
		attrs.add(relationship);
		attrs.add(race);
		attrs.add(sex);
		attrs.add(nativeCountry);
		attrs.add(salary);
		return attrs;
	}
	
	/* (non-Javadoc)
	 * @see edu.rochester.common.AttributeSet#getContinuousAtrtibutes()
	 */
	public List<Attribute> getContinuousAtrtibutes() {
		List<Attribute> attrs = new ArrayList<Attribute>();
		attrs.add(age);
		attrs.add(finalWeight);
		attrs.add(educationYears);
		attrs.add(capitalGain);
		attrs.add(capitalLoss);
		attrs.add(hoursPerWeek);
		return attrs;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return String.format("Person: [%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s]", 
				new Object[] { formatAttribute(age), formatAttribute(workClass), formatAttribute(finalWeight), 
						formatAttribute(education), formatAttribute(educationYears), formatAttribute(maritalStatus), 
						formatAttribute(occupation), formatAttribute(relationship), formatAttribute(race), formatAttribute(sex), 
						formatAttribute(capitalGain), formatAttribute(capitalLoss), formatAttribute(hoursPerWeek), 
						formatAttribute(nativeCountry), formatAttribute(salary) });
	}
	
	// Used in FpGrowth Algorithm
	public boolean hasAttribute(Attribute attr) {
		String attributeName = attr.attributeName;
		return getAttribute(attributeName).equals(attr);
	}

	public Attribute getAttribute(String attributeName) {
		if ( attributeName.equals("Age") ) {
			return age;
		} else if ( attributeName.equals("WorkClass") ) {
			return workClass;
		} else if ( attributeName.equals("FinalWeight") ) {
			return finalWeight;
		} else if ( attributeName.equals("Education") ) {
			return education;
		} else if ( attributeName.equals("EducationYears") ) {
			return educationYears;
		} else if ( attributeName.equals("MaritalStatus") ) {
			return maritalStatus;
		} else if ( attributeName.equals("Occupation") ) {
			return occupation;
		} else if ( attributeName.equals("Relationship") ) {
			return relationship;
		} else if ( attributeName.equals("Race") ) {
			return race;
		} else if ( attributeName.equals("Sex") ) {
			return sex;
		} else if ( attributeName.equals("CapitalGain") ) {
			return capitalGain;
		} else if ( attributeName.equals("CapitalLoss") ) {
			return capitalLoss;
		} else if ( attributeName.equals("HoursPerWeek") ) {
			return hoursPerWeek;
		} else if ( attributeName.equals("NativeCountry") ) {
			return nativeCountry;
		} else if ( attributeName.equals("Salary") ) {
			return salary;
		} 
		return null;
	}

	public static String formatAttribute(Attribute attr) {
		String attributeName = attr.attributeName;

		if ( attributeName.equals("Age") ) {
			return formatAgeAttribute(attr);
		} else if ( attributeName.equals("WorkClass") ) {
			return formatWorkClassAttribute(attr);
		} else if ( attributeName.equals("FinalWeight") ) {
			return formatFinalWeightAttribute(attr);
		} else if ( attributeName.equals("Education") ) {
			return formatEducationAttribute(attr);
		} else if ( attributeName.equals("EducationYears") ) {
			return formatEducationYearsAttribute(attr);
		} else if ( attributeName.equals("MaritalStatus") ) {
			return formatMaritalStatusAttribute(attr);
		} else if ( attributeName.equals("Occupation") ) {
			return formatOccupationAttribute(attr);
		} else if ( attributeName.equals("Relationship") ) {
			return formatRelationshipAttribute(attr);
		} else if ( attributeName.equals("Race") ) {
			return formatRaceAttribute(attr);
		} else if ( attributeName.equals("Sex") ) {
			return formatSexAttribute(attr);
		} else if ( attributeName.equals("CapitalGain") ) {
			return formatCapitalGainAttribute(attr);
		} else if ( attributeName.equals("CapitalLoss") ) {
			return formatCapitalLossAttribute(attr);
		} else if ( attributeName.equals("HoursPerWeek") ) {
			return formatHoursPerWeekAttribute(attr);
		} else if ( attributeName.equals("NativeCountry") ) {
			return formatNativeCountryAttribute(attr);
		} else if ( attributeName.equals("Salary") ) {
			return formatSalaryAttribute(attr);
		} 
		return "";
	}

	/* Getter and Setter for Age */
	public static Attribute getAgeAttribute(String attributeValue) {
		String attributeName = "Age";
		Integer value = Integer.parseInt(attributeValue);
		boolean isContinuous = true;
		boolean isIgnored = attributeValue.equals("?");

		return new Attribute(attributeName, isContinuous, isIgnored, value);
	}

	private static String formatAgeAttribute(Attribute attr) {
		return String.format("[Age: %s-%s]", 
				new Object[] { attr.lowerValue, attr.upperValue });
	}

	/* Getter and Setter for WorkClass */
	public static Attribute getWorkClassAttribute(String attributeValue) {
		String attributeName = "WorkClass";
		WorkClass value = getWorkClassEnumValue(attributeValue);
		boolean isContinuous = false;
		boolean isIgnored = attributeValue.equals("?") || value == WorkClass.UNKNOWN;

		return new Attribute(attributeName, isContinuous, isIgnored, value);
	}

	private static WorkClass getWorkClassEnumValue(String value) {
		return WORK_CLASS_MAP.containsKey(value) ? WORK_CLASS_MAP.get(value) : WorkClass.UNKNOWN;
	}

	private static String formatWorkClassAttribute(Attribute attr) {
		String value = getKeyFromValue(WORK_CLASS_MAP, attr.attributeValue);
		return String.format("[WorkClass: %s]", value);
	}
	
	/* Getter and Setter for FinalWeight */
	public static Attribute getFinalWeightAttribute(String attributeValue) {
		String attributeName = "FinalWeight";
		Integer value = Integer.parseInt(attributeValue);
		boolean isContinuous = true;
		boolean isIgnored = attributeValue.equals("?");

		return new Attribute(attributeName, isContinuous, isIgnored, value);
	}

	private static String formatFinalWeightAttribute(Attribute attr) {
		return String.format("[FinalWeight: %s-%s]", 
				new Object[] { attr.lowerValue, attr.upperValue });
	}
	
	/* Getter and Setter for Education */
	public static Attribute getEducationAttribute(String attributeValue) {
		String attributeName = "Education";
		Education value = getEducationEnumValue(attributeValue);
		boolean isContinuous = false;
		boolean isIgnored = attributeValue.equals("?") || value == Education.UNKNOWN;

		return new Attribute(attributeName, isContinuous, isIgnored, value);
	}

	private static Education getEducationEnumValue(String value) {
		return EDUCATION_MAP.containsKey(value) ? EDUCATION_MAP.get(value) : Education.UNKNOWN;
	}

	private static String formatEducationAttribute(Attribute attr) {
		String value = getKeyFromValue(EDUCATION_MAP, attr.attributeValue);
		return String.format("[Education: %s]", value);
	}
	
	/* Getter and Setter for EducationYears */
	public static Attribute getEducationYearsAttribute(String attributeValue) {
		String attributeName = "EducationYears";
		Integer value = Integer.parseInt(attributeValue);
		boolean isContinuous = true;
		boolean isIgnored = attributeValue.equals("?");

		return new Attribute(attributeName, isContinuous, isIgnored, value);
	}

	private static String formatEducationYearsAttribute(Attribute attr) {
		return String.format("[EducationYears: %s-%s]", 
				new Object[] { attr.lowerValue, attr.upperValue });
	}
	
	/* Getter and Setter for MaritalStatus */
	public static Attribute getMaritalStatusAttribute(String attributeValue) {
		String attributeName = "MaritalStatus";
		MaritalStatus value = getMaritalStatusEnumValue(attributeValue);
		boolean isContinuous = false;
		boolean isIgnored = attributeValue.equals("?") || value == MaritalStatus.UNKNOWN;

		return new Attribute(attributeName, isContinuous, isIgnored, value);
	}

	private static MaritalStatus getMaritalStatusEnumValue(String value) {
		return MARITAL_STATUS_MAP.containsKey(value) ? MARITAL_STATUS_MAP.get(value) : MaritalStatus.UNKNOWN;
	}

	private static String formatMaritalStatusAttribute(Attribute attr) {
		String value = getKeyFromValue(MARITAL_STATUS_MAP, attr.attributeValue);
		return String.format("[MaritalStatus: %s]", value);
	}
	
	/* Getter and Setter for Occupation */
	public static Attribute getOccupationAttribute(String attributeValue) {
		String attributeName = "Occupation";
		Occupation value = getOccupationEnumValue(attributeValue);
		boolean isContinuous = false;
		boolean isIgnored = attributeValue.equals("?") || value == Occupation.UNKNOWN;

		return new Attribute(attributeName, isContinuous, isIgnored, value);
	}

	private static Occupation getOccupationEnumValue(String value) {
		return OCCUPATION_MAP.containsKey(value) ? OCCUPATION_MAP.get(value) : Occupation.UNKNOWN;
	}

	private static String formatOccupationAttribute(Attribute attr) {
		String value = getKeyFromValue(OCCUPATION_MAP, attr.attributeValue);
		return String.format("[Occupation: %s]", value);
	}
	
	/* Getter and Setter for Relationship */
	public static Attribute getRelationshipAttribute(String attributeValue) {
		String attributeName = "Relationship";
		Relationship value = getRelationshipEnumValue(attributeValue);
		boolean isContinuous = false;
		boolean isIgnored = attributeValue.equals("?") || value == Relationship.UNKNOWN;

		return new Attribute(attributeName, isContinuous, isIgnored, value);
	}

	private static Relationship getRelationshipEnumValue(String value) {
		return RELATIONSHIP_MAP.containsKey(value) ? RELATIONSHIP_MAP.get(value) : Relationship.UNKNOWN;
	}

	private static String formatRelationshipAttribute(Attribute attr) {
		String value = getKeyFromValue(RELATIONSHIP_MAP, attr.attributeValue);
		return String.format("[Relationship: %s]", value);
	}
	
	/* Getter and Setter for Race */
	public static Attribute getRaceAttribute(String attributeValue) {
		String attributeName = "Race";
		Race value = getRaceEnumValue(attributeValue);
		boolean isContinuous = false;
		boolean isIgnored = attributeValue.equals("?") || value == Race.UNKNOWN;

		return new Attribute(attributeName, isContinuous, isIgnored, value);
	}

	private static Race getRaceEnumValue(String value) {
		return RACE_MAP.containsKey(value) ? RACE_MAP.get(value) : Race.UNKNOWN;
	}

	private static String formatRaceAttribute(Attribute attr) {
		String value = getKeyFromValue(RACE_MAP, attr.attributeValue);
		return String.format("[Race: %s]", value);
	}
	
	/* Getter and Setter for Sex */
	public static Attribute getSexAttribute(String attributeValue) {
		String attributeName = "Sex";
		Sex value = getSexEnumValue(attributeValue);
		boolean isContinuous = false;
		boolean isIgnored = attributeValue.equals("?") || value == Sex.UNKNOWN;

		return new Attribute(attributeName, isContinuous, isIgnored, value);
	}

	private static Sex getSexEnumValue(String value) {
		return SEX_MAP.containsKey(value) ? SEX_MAP.get(value) : Sex.UNKNOWN;
	}

	private static String formatSexAttribute(Attribute attr) {
		String value = getKeyFromValue(SEX_MAP, attr.attributeValue);
		return String.format("[Sex: %s]", value);
	}
	
	/* Getter and Setter for CapitalGain */
	public static Attribute getCapitalGainAttribute(String attributeValue) {
		String attributeName = "CapitalGain";
		Integer value = Integer.parseInt(attributeValue);
		boolean isContinuous = true;
		boolean isIgnored = attributeValue.equals("?");

		return new Attribute(attributeName, isContinuous, isIgnored, value);
	}

	private static String formatCapitalGainAttribute(Attribute attr) {
		return String.format("[CapitalGain: %s-%s]", 
				new Object[] { attr.lowerValue, attr.upperValue });
	}
	
	/* Getter and Setter for CapitalLoss */
	public static Attribute getCapitalLossAttribute(String attributeValue) {
		String attributeName = "CapitalLoss";
		Integer value = Integer.parseInt(attributeValue);
		boolean isContinuous = true;
		boolean isIgnored = attributeValue.equals("?");

		return new Attribute(attributeName, isContinuous, isIgnored, value);
	}

	private static String formatCapitalLossAttribute(Attribute attr) {
		return String.format("[CapitalLoss: %s-%s]", 
				new Object[] { attr.lowerValue, attr.upperValue });
	}
	
	/* Getter and Setter for HoursPerWeek */
	public static Attribute getHoursPerWeekAttribute(String attributeValue) {
		String attributeName = "HoursPerWeek";
		Integer value = Integer.parseInt(attributeValue);
		boolean isContinuous = true;
		boolean isIgnored = attributeValue.equals("?");

		return new Attribute(attributeName, isContinuous, isIgnored, value);
	}

	private static String formatHoursPerWeekAttribute(Attribute attr) {
		return String.format("[HoursPerWeek: %s-%s]", 
				new Object[] { attr.lowerValue, attr.upperValue });
	}
	
	/* Getter and Setter for NativeCountry */
	public static Attribute getNativeCountryAttribute(String attributeValue) {
		String attributeName = "NativeCountry";
		NativeCountry value = getNativeCountryEnumValue(attributeValue);
		boolean isContinuous = false;
		boolean isIgnored = attributeValue.equals("?") || value == NativeCountry.UNKNOWN;

		return new Attribute(attributeName, isContinuous, isIgnored, value);
	}

	private static NativeCountry getNativeCountryEnumValue(String value) {
		return NATIVE_COUNTRY_MAP.containsKey(value) ? NATIVE_COUNTRY_MAP.get(value) : NativeCountry.UNKNOWN;
	}

	private static String formatNativeCountryAttribute(Attribute attr) {
		String value = getKeyFromValue(NATIVE_COUNTRY_MAP, attr.attributeValue);
		return String.format("[NativeCountry: %s]", value);
	}
	
	/* Getter and Setter for Salary */
	public static Attribute getSalaryAttribute(String attributeValue) {
		String attributeName = "Salary";
		Salary value = getSalaryEnumValue(attributeValue);
		boolean isContinuous = false;
		boolean isIgnored = attributeValue.equals("?") || value == Salary.UNKNOWN;

		return new Attribute(attributeName, isContinuous, isIgnored, value);
	}

	private static Salary getSalaryEnumValue(String value) {
		return SALARY_MAP.containsKey(value) ? SALARY_MAP.get(value) : Salary.UNKNOWN;
	}

	private static String formatSalaryAttribute(Attribute attr) {
		String value = getKeyFromValue(SALARY_MAP, attr.attributeValue);
		return String.format("[Salary: %s]", value);
	}

	/* Common methods for the class */
	private static String getKeyFromValue(Map<String, ?> targetMap, Object attributeValue) {
		for ( Entry<String, ?> e : targetMap.entrySet() ) {
			if ( e.getValue().equals(attributeValue) ) {
				return e.getKey();
			}
		}
		return "";
	}

	/* The attributes of the person */
	public final Attribute age;
	public final Attribute workClass;
	public final Attribute finalWeight;
	public final Attribute education;
	public final Attribute educationYears;
	public final Attribute maritalStatus;
	public final Attribute occupation;
	public final Attribute relationship;
	public final Attribute race;
	public final Attribute sex;
	public final Attribute capitalGain;
	public final Attribute capitalLoss;
	public final Attribute hoursPerWeek;
	public final Attribute nativeCountry;
	public final Attribute salary;

	private static enum WorkClass {
		UNKNOWN, PRIVATE, SELF_EMP_NOT_INC, SELF_EMP_INC, FEDERAL_GOV, LOCAL_GOV, STATE_GOV, WITHOUT_PAY, NEVER_WORKED
	};
	private static final Map<String, WorkClass> WORK_CLASS_MAP;
	static {
		WORK_CLASS_MAP = new HashMap<String, WorkClass>(8, 1);
		WORK_CLASS_MAP.put("Private", WorkClass.PRIVATE);
		WORK_CLASS_MAP.put("Self-emp-not-inc", WorkClass.SELF_EMP_NOT_INC);
		WORK_CLASS_MAP.put("Self-emp-inc", WorkClass.SELF_EMP_INC);
		WORK_CLASS_MAP.put("Federal-gov", WorkClass.FEDERAL_GOV);
		WORK_CLASS_MAP.put("Local-gov", WorkClass.LOCAL_GOV);
		WORK_CLASS_MAP.put("State-gov", WorkClass.STATE_GOV);
		WORK_CLASS_MAP.put("Without-pay", WorkClass.WITHOUT_PAY);
		WORK_CLASS_MAP.put("Never-worked", WorkClass.NEVER_WORKED);
	}
	
	private static enum Education {
		UNKNOWN, BACHELORS, SOME_COLLEGE, ELEVENTH, HS_GRAD, PROF_SCHOOL, ASSOC_ACDM, ASSOC_VOC, NINTH, SEVENTH_EIGHTH, TWELVETH, MASTERS, FIRST_FOURTH, TENTH, DOCTORATE, FIFTH_SIXTH, PRESCHOOL
	}
	private static final Map<String, Education> EDUCATION_MAP;
	static {
		EDUCATION_MAP = new HashMap<String, Education>(16, 1);
		EDUCATION_MAP.put("Bachelors", Education.BACHELORS);
		EDUCATION_MAP.put("Some-college", Education.SOME_COLLEGE);
		EDUCATION_MAP.put("11th", Education.ELEVENTH);
		EDUCATION_MAP.put("HS-grad", Education.HS_GRAD);
		EDUCATION_MAP.put("Prof-school", Education.PROF_SCHOOL);
		EDUCATION_MAP.put("Assoc-acdm", Education.ASSOC_ACDM);
		EDUCATION_MAP.put("Assoc-voc", Education.ASSOC_VOC);
		EDUCATION_MAP.put("9th", Education.NINTH);
		EDUCATION_MAP.put("7th-8th", Education.SEVENTH_EIGHTH);
		EDUCATION_MAP.put("12th", Education.TWELVETH);
		EDUCATION_MAP.put("Masters", Education.MASTERS);
		EDUCATION_MAP.put("1st-4th", Education.FIRST_FOURTH);
		EDUCATION_MAP.put("10th", Education.TENTH);
		EDUCATION_MAP.put("Doctorate", Education.DOCTORATE);
		EDUCATION_MAP.put("5th-6th", Education.FIFTH_SIXTH);
		EDUCATION_MAP.put("Preschool", Education.PRESCHOOL);
	}
	
	private static enum MaritalStatus {
		UNKNOWN, MARRIED_CIV_SPOUSE, DIVORCED, NEVER_MARRIED, SEPARATED, WIDOWED, MARRIED_SPOUSE_ABSENT, MARRIED_AF_SPOUSE
	};
	private static final Map<String, MaritalStatus> MARITAL_STATUS_MAP;
	static {
		MARITAL_STATUS_MAP = new HashMap<String, MaritalStatus>();
		MARITAL_STATUS_MAP.put("Married-civ-spouse", MaritalStatus.MARRIED_CIV_SPOUSE);
		MARITAL_STATUS_MAP.put("Divorced", MaritalStatus.DIVORCED);
		MARITAL_STATUS_MAP.put("Never-married", MaritalStatus.NEVER_MARRIED);
		MARITAL_STATUS_MAP.put("Separated", MaritalStatus.SEPARATED);
		MARITAL_STATUS_MAP.put("Widowed", MaritalStatus.WIDOWED);
		MARITAL_STATUS_MAP.put("Married-spouse-absent", MaritalStatus.MARRIED_SPOUSE_ABSENT);
		MARITAL_STATUS_MAP.put("Married-AF-spouse", MaritalStatus.MARRIED_AF_SPOUSE);
	}
	
	private static enum Occupation {
		UNKNOWN, TECH_SUPPORT, CRAFT_REPAIR, OTHER_SERVICE, SALES, EXEC_MANAGERIAL, PROF_SPECIALTY, HANDLERS_CLEANERS, MACHINE_OP_INSPCT, ADM_CLERICAL, FARMING_FISHING, TRANSPORT_MOVING, PRIV_HOUSE_SERV, PROTECTIVE_SERV, ARMED_FORCES
	};
	private static final Map<String, Occupation> OCCUPATION_MAP;
	static {
		OCCUPATION_MAP = new HashMap<String, Occupation>(14, 1);
		OCCUPATION_MAP.put("Tech-support", Occupation.TECH_SUPPORT);
		OCCUPATION_MAP.put("Craft-repair", Occupation.CRAFT_REPAIR);
		OCCUPATION_MAP.put("Other-service", Occupation.OTHER_SERVICE);
		OCCUPATION_MAP.put("Sales", Occupation.SALES);
		OCCUPATION_MAP.put("Exec-managerial", Occupation.EXEC_MANAGERIAL);
		OCCUPATION_MAP.put("Prof-specialty", Occupation.PROF_SPECIALTY);
		OCCUPATION_MAP.put("Handlers-cleaners", Occupation.HANDLERS_CLEANERS);
		OCCUPATION_MAP.put("Machine-op-inspct", Occupation.MACHINE_OP_INSPCT);
		OCCUPATION_MAP.put("Adm-clerical", Occupation.ADM_CLERICAL);
		OCCUPATION_MAP.put("Farming-fishing", Occupation.FARMING_FISHING);
		OCCUPATION_MAP.put("Transport-moving", Occupation.TRANSPORT_MOVING);
		OCCUPATION_MAP.put("Priv-house-serv", Occupation.PRIV_HOUSE_SERV);
		OCCUPATION_MAP.put("Protective-serv", Occupation.PROTECTIVE_SERV);
		OCCUPATION_MAP.put("Armed-Forces", Occupation.ARMED_FORCES);
	}
	
	private static enum Relationship {
		UNKNOWN, WIFE, OWN_CHILD, HUSBAND, NOT_IN_FAMILY, OTHER_RELATIVE, UNMARRIED
	};
	private static final Map<String, Relationship> RELATIONSHIP_MAP;
	static {
		RELATIONSHIP_MAP = new HashMap<String, Relationship>(6, 1);
		RELATIONSHIP_MAP.put("Wife", Relationship.WIFE);
		RELATIONSHIP_MAP.put("Own-child", Relationship.OWN_CHILD);
		RELATIONSHIP_MAP.put("Husband", Relationship.HUSBAND);
		RELATIONSHIP_MAP.put("Not-in-family", Relationship.NOT_IN_FAMILY);
		RELATIONSHIP_MAP.put("Other-relative", Relationship.OTHER_RELATIVE);
		RELATIONSHIP_MAP.put("Unmarried", Relationship.UNMARRIED);
	}
	
	private static enum Race {
		UNKNOWN, WHITE, ASIAN_PAC_ISLANDER, AMER_INDIAN_ESKIMO, OTHER, BLACK
	};
	private static final Map<String, Race> RACE_MAP;
	static {
		RACE_MAP = new HashMap<String, Race>(5, 1);
		RACE_MAP.put("White", Race.WHITE);
		RACE_MAP.put("Asian-Pac-Islander", Race.ASIAN_PAC_ISLANDER);
		RACE_MAP.put("Amer-Indian-Eskimo", Race.AMER_INDIAN_ESKIMO);
		RACE_MAP.put("Other", Race.OTHER);
		RACE_MAP.put("Black", Race.BLACK);
	}
	
	private static enum Sex {
		UNKNOWN, FEMALE, MALE
	};
	private static final Map<String, Sex> SEX_MAP;
	static {
		SEX_MAP = new HashMap<String, Sex>(2, 1);
		SEX_MAP.put("Female", Sex.FEMALE);
		SEX_MAP.put("Male", Sex.MALE);
	}
	
	private static enum NativeCountry {
		UNKNOWN, UNITED_STATES, CAMBODIA, ENGLAND, PUERTO_RICO, CANADA, GERMANY, OUTLYING_US_GUAM_USVI_ETC, INDIA, JAPAN, GREECE, SOUTH, CHINA, CUBA, IRAN, HONDURAS, PHILIPPINES, ITALY, POLAND, JAMAICA, VIETNAM, MEXICO, PORTUGAL, IRELAND, FRANCE, DOMINICAN_REPUBLIC, LAOS, ECUADOR, TAIWAN, HAITI, COLUMBIA, HUNGARY, GUATEMALA, NICARAGUA, SCOTLAND, THAILAND, YUGOSLAVIA, EL_SALVADOR, TRINADAD_TOBAGO, PERU, HONG, HOLAND_NETHERLANDS
	};
	private static final Map<String, NativeCountry> NATIVE_COUNTRY_MAP;
	static {
		NATIVE_COUNTRY_MAP = new HashMap<String, NativeCountry>(41, 1);
		NATIVE_COUNTRY_MAP.put("United-States", NativeCountry.UNITED_STATES);
		NATIVE_COUNTRY_MAP.put("Cambodia", NativeCountry.CAMBODIA);
		NATIVE_COUNTRY_MAP.put("England", NativeCountry.ENGLAND);
		NATIVE_COUNTRY_MAP.put("Puerto-Rico", NativeCountry.PUERTO_RICO);
		NATIVE_COUNTRY_MAP.put("Canada", NativeCountry.CANADA);
		NATIVE_COUNTRY_MAP.put("Germany", NativeCountry.GERMANY);
		NATIVE_COUNTRY_MAP.put("Outlying-US(Guam-USVI-etc)", NativeCountry.OUTLYING_US_GUAM_USVI_ETC);
		NATIVE_COUNTRY_MAP.put("India", NativeCountry.INDIA);
		NATIVE_COUNTRY_MAP.put("Japan", NativeCountry.JAPAN);
		NATIVE_COUNTRY_MAP.put("Greece", NativeCountry.GREECE);
		NATIVE_COUNTRY_MAP.put("South", NativeCountry.SOUTH);
		NATIVE_COUNTRY_MAP.put("China", NativeCountry.CHINA);
		NATIVE_COUNTRY_MAP.put("Cuba", NativeCountry.CUBA);
		NATIVE_COUNTRY_MAP.put("Iran", NativeCountry.IRAN);
		NATIVE_COUNTRY_MAP.put("Honduras", NativeCountry.HONDURAS);
		NATIVE_COUNTRY_MAP.put("Philippines", NativeCountry.PHILIPPINES);
		NATIVE_COUNTRY_MAP.put("Italy", NativeCountry.ITALY);
		NATIVE_COUNTRY_MAP.put("Poland", NativeCountry.POLAND);
		NATIVE_COUNTRY_MAP.put("Jamaica", NativeCountry.JAMAICA);
		NATIVE_COUNTRY_MAP.put("Vietnam", NativeCountry.VIETNAM);
		NATIVE_COUNTRY_MAP.put("Mexico", NativeCountry.MEXICO);
		NATIVE_COUNTRY_MAP.put("Portugal", NativeCountry.PORTUGAL);
		NATIVE_COUNTRY_MAP.put("Ireland", NativeCountry.IRELAND);
		NATIVE_COUNTRY_MAP.put("France", NativeCountry.FRANCE);
		NATIVE_COUNTRY_MAP.put("Dominican-Republic", NativeCountry.DOMINICAN_REPUBLIC);
		NATIVE_COUNTRY_MAP.put("Laos", NativeCountry.LAOS);
		NATIVE_COUNTRY_MAP.put("Ecuador", NativeCountry.ECUADOR);
		NATIVE_COUNTRY_MAP.put("Taiwan", NativeCountry.TAIWAN);
		NATIVE_COUNTRY_MAP.put("Haiti", NativeCountry.HAITI);
		NATIVE_COUNTRY_MAP.put("Columbia", NativeCountry.COLUMBIA);
		NATIVE_COUNTRY_MAP.put("Hungary", NativeCountry.HUNGARY);
		NATIVE_COUNTRY_MAP.put("Guatemala", NativeCountry.GUATEMALA);
		NATIVE_COUNTRY_MAP.put("Nicaragua", NativeCountry.NICARAGUA);
		NATIVE_COUNTRY_MAP.put("Scotland", NativeCountry.SCOTLAND);
		NATIVE_COUNTRY_MAP.put("Thailand", NativeCountry.THAILAND);
		NATIVE_COUNTRY_MAP.put("Yugoslavia", NativeCountry.YUGOSLAVIA);
		NATIVE_COUNTRY_MAP.put("El-Salvador", NativeCountry.EL_SALVADOR);
		NATIVE_COUNTRY_MAP.put("Trinadad&Tobago", NativeCountry.TRINADAD_TOBAGO);
		NATIVE_COUNTRY_MAP.put("Peru", NativeCountry.PERU);
		NATIVE_COUNTRY_MAP.put("Hong", NativeCountry.HONG);
		NATIVE_COUNTRY_MAP.put("Holand-Netherlands", NativeCountry.HOLAND_NETHERLANDS);
	}
	
	private static enum Salary {
		UNKNOWN, LESS_OR_EQUAL_TO_50K, GREATER_THAN_50K
	};
	private static final Map<String, Salary> SALARY_MAP;
	static {
		SALARY_MAP = new HashMap<String, Salary>(2, 1);
		SALARY_MAP.put("<=50K", Salary.LESS_OR_EQUAL_TO_50K);
		SALARY_MAP.put(">50K", Salary.GREATER_THAN_50K);
	}
}
