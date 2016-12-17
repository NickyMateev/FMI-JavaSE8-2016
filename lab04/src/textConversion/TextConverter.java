package textConversion;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.Map.Entry;

// TODO: uppercase letters don't get translated

public class TextConverter {

	public static final String FINAL_WORD = "kraj";
	public static final String FINAL_PATTERN = "(.*\\s+)?kraj[,!@#$%^&*()\\s]?.*";
	public static final String ALPHABET_PATH = "alphabet.properties";
	public static final String DESTINATION_PATH = "result.txt";
	public static final int TOP_LETTER_COUNT = 7;

	public String takeUserInput() {

		Scanner scanner = new Scanner(System.in);
		StringBuilder sb = new StringBuilder();
		String lastInput = "";

		System.out.println("Enter your text:");
		while (!lastInput.matches(FINAL_PATTERN)) {
			lastInput = scanner.nextLine();
			sb.append(lastInput);
			sb.append(System.lineSeparator());
		}
		System.out.println("\nInput taken.");

		String result;
		int finalWordIndex = sb.indexOf(FINAL_WORD);
		if (finalWordIndex != -1) {
			result = sb.substring(0, finalWordIndex);
		} else {
			result = sb.toString();
		}

		return result;
	}

	public String translateText(String text) throws FileNotFoundException, IOException {

		Properties props = this.getProperties(ALPHABET_PATH);

		StringBuilder result = new StringBuilder();
		String currString;
		for (char c : text.toCharArray()) {
			if (props.containsKey(c + "")) {
				result.append(props.get(c + ""));
			} else {
				result.append(c + "");
			}
		}

		return result.toString();
	}

	private Properties getProperties(String propertiesFile) throws FileNotFoundException, IOException {
		Properties props = new Properties();
		props.load(new FileInputStream(propertiesFile));

		return props;
	}

	public void saveTranslationToFile(String text) throws IOException {
		this.saveTranslationToFile(text, DESTINATION_PATH);
	}

	public void saveTranslationToFile(String text, String destinationPath) throws IOException {
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(destinationPath))) {
			writer.write(text);
		}
	}

	public int getWordCount(String sourcePath) throws IOException {
		StringBuilder stringBuilder = new StringBuilder();
		try (BufferedReader reader = Files.newBufferedReader(Paths.get(sourcePath))) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line);
				stringBuilder.append(' ');
			}
		}

		String[] elements = stringBuilder.toString().split("\\s+");

		return elements.length;
	}

	public Map<String, Integer> getFrequencyMap(String text) throws FileNotFoundException, IOException {
		Map<String, Integer> map = new HashMap<>();
		this.loadLetters(map);

		for (char c : text.toCharArray()) {
			if (map.containsKey(c + "")) {
				map.put(c + "", map.get(c + "") + 1);
			}
		}

		return map;
	}

	private void loadLetters(Map<String, Integer> map) throws FileNotFoundException, IOException {
		Properties properties = this.getProperties(ALPHABET_PATH);
		for (Object elem : properties.values()) {
			map.put(elem.toString(), 0);
		}
	}

	public Map<String, Integer> getMostFrequentLetters(Map<String, Integer> letterMap) throws FileNotFoundException, IOException {
		return this.getMostFrequentLetters(letterMap, TOP_LETTER_COUNT);
	}

	public Map<String, Integer> getMostFrequentLetters(Map<String, Integer> letterMap, int count) throws FileNotFoundException, IOException {
		
		if(count > this.getPropertiesCount(ALPHABET_PATH)){
			count = TOP_LETTER_COUNT; 
		}

		List<Map.Entry<String, Integer>> entryList = new LinkedList<>(letterMap.entrySet());
		Collections.sort(entryList, new Comparator<Map.Entry<String, Integer>>() {

			@Override
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
				return (o2.getValue().compareTo(o1.getValue()));
			}
		});

		Map<String, Integer> result = new LinkedHashMap<>();
		Iterator<Entry<String, Integer>> it = entryList.iterator();
		for (int i = 0; i < count; i++) {
			Entry<String, Integer> entry = it.next();
		    result.put(entry.getKey(), entry.getValue());
		}

		return result;
	}
	
	private int getPropertiesCount(String propertiesFile) throws FileNotFoundException, IOException{
		 Properties props = this.getProperties(propertiesFile); 
		 return props.size();
	}

}