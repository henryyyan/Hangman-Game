package aoa.choosers;

import edu.princeton.cs.algs4.StdRandom;
import aoa.utils.FileUtils;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class RandomChooser implements Chooser {
    private final String chosenWord;
    private String pattern = "";

    public RandomChooser(int wordLength, String dictionaryFile) {
        if (wordLength == 0 || wordLength == Integer.MAX_VALUE) {
            throw new IllegalStateException();
        }
        else if (wordLength < 0) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < wordLength; i++) {
            pattern += "-";
        }
        List<String> words1 = FileUtils.readWords(dictionaryFile);
        List<String> words = new ArrayList<>();
        for (String word : words1) {
            if (word.length() == wordLength) {
                words.add(word);
            }
        }
        if (words.size() == 0) {
            throw new IllegalStateException();
        }
        int numWords = words.size();
        int randomlyChosenWordNumber = StdRandom.uniform(numWords);
        chosenWord = words.get(randomlyChosenWordNumber);
    }

    @Override
    public int makeGuess(char letter) {
        int count = 0;
        String patternNew = "";
        for (int i = 0; i < chosenWord.length(); i++) {
            if (chosenWord.charAt(i) == letter) {
                patternNew += letter;
                count++;
            }
            else if (pattern.charAt(i) != '-') {
                patternNew += pattern.charAt(i);
            }
            else {
                patternNew += "-";
            }
        }
        pattern = patternNew;
        return count;
    }

    @Override
    public String getPattern() {
        return pattern;
    }

    @Override
    public String getWord() {
        return chosenWord;
    }
}
