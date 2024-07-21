package aoa.guessers;

import aoa.utils.FileUtils;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class NaiveLetterFreqGuesser implements Guesser {
    private final List<String> words;

    public NaiveLetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);
    }

    @Override
    /** Makes a guess which ignores the given pattern. */
    public char getGuess(String pattern, List<Character> guesses) {
        return getGuess(guesses);
    }

    /** Returns a map from a given letter to its frequency across all words.
     *  This task is similar to something you did in hw0b! */
    public Map<Character, Integer> getFrequencyMap() {
        Map<Character, Integer> map = new TreeMap<>();
        for (String word : words) {
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

    /** Returns the most common letter in WORDS that has not yet been guessed
     *  (and therefore isn't present in GUESSES). */
    public char getGuess(List<Character> guesses) {
        Map<Character, Integer> freq = this.getFrequencyMap();
        Map<Character, Integer> freq2 = this.getFrequencyMap();
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

    public static void main(String[] args) {
        NaiveLetterFreqGuesser nlfg = new NaiveLetterFreqGuesser("data/example.txt");
        System.out.println("list of words: " + nlfg.words);
        System.out.println("frequency map: " + nlfg.getFrequencyMap());

        List<Character> guesses = List.of('e', 'l');
        System.out.println("guess: " + nlfg.getGuess(guesses));
    }
}
