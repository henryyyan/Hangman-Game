package aoa.guessers;

import aoa.utils.FileUtils;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class PatternAwareLetterFreqGuesser implements Guesser {
    private final List<String> words;

    public PatternAwareLetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);
    }

    @Override
    /** Returns the most common letter in the set of valid words based on the current
     *  PATTERN. */

    public char getGuess(String pattern, List<Character> guesses) {
        Map<Character, Integer> freq = this.getFreqMap(pattern);
        Map<Character, Integer> freq2 = this.getFreqMap(pattern);
        for (char x : freq2.keySet()) {
            if (guesses.contains(x)) {
                freq.remove(x);
            }
        }
        int max = 0;
        char letter = 0;
        for (Map.Entry<Character, Integer> element : freq.entrySet()) {
            int num = element.getValue();
            if (num > max) {
                max = num;
                letter = element.getKey();
            }
        }
        return letter;
    }

    public List<String> OnlyWordsThatMatchPattern(String pattern) {
        List<String> NewWords = new ArrayList<>(words);
        int length = pattern.length();
        for (String word : words) {
            if (word.length() == length) {
                for (int i = 0; i < length; i++) {
                    if (pattern.charAt(i) != '-' && pattern.charAt(i) != word.charAt(i)) {
                        NewWords.remove(word);
                    }
                }
            }
            else {
                NewWords.remove(word);
            }
        }
        return NewWords;
    }

    public Map<Character, Integer> getFreqMap(String pattern) {
        Map<Character, Integer> map = new TreeMap<>();
        List<String> newWords = OnlyWordsThatMatchPattern(pattern);
        for (String word : newWords) {
            for (int j = 0; j < word.length(); j++) {
                if (map.containsKey(word.charAt(j))) {
                    int x = map.get(word.charAt(j));
                    map.remove(word.charAt(j));
                    map.put(word.charAt(j), x + 1);
                }
                else {
                    map.put(word.charAt(j), 1);
                }
            }
        }
        return map;
    }



    public static void main(String[] args) {
        PatternAwareLetterFreqGuesser palfg = new PatternAwareLetterFreqGuesser("data/example.txt");
        System.out.println(palfg.getGuess("-e--", List.of('e')));
    }
}