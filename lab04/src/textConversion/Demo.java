package textConversion;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

public class Demo {

	public static void main(String[] args) throws IOException {

		TextConverter converter = new TextConverter();
		String userInput = converter.takeUserInput();
		String translatedText = converter.translateText(userInput);

		System.out.println("Translated text:");
		System.out.println(translatedText);
		converter.saveTranslationToFile(translatedText);

		System.out.println(
				System.lineSeparator() + "Word count: " + converter.getWordCount(TextConverter.DESTINATION_PATH));

		Map<String, Integer> letterMap = converter.getFrequencyMap(translatedText);
		Map<String, Integer> topLetters = converter.getMostFrequentLetters(letterMap);
		System.out.println(System.lineSeparator() + "Letter frequency map(top " + TextConverter.TOP_LETTER_COUNT + "):");
		for (Entry<String, Integer> entry : topLetters.entrySet()) {
			System.out.println(entry.getKey() + " - " + entry.getValue());
		}

	}

}