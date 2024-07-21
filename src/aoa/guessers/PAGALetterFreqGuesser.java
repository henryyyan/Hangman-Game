package aoa.guessers;

import aoa.utils.FileUtils;

import java.util.*;

public class PAGALetterFreqGuesser implements Guesser {
    private final List<String> words;

    public PAGALetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);
    }

    @Override
    /** Returns the most common letter in the set of valid words based on the current
     *  PATTERN and the GUESSES that have been made. */

    public char getGuess(String pattern, List<Character> guesses) {
        Map<Character, Integer> freq = this.getFreqMap(pattern, guesses);
        Map<Character, Integer> freq2 = this.getFreqMap(pattern, guesses);
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

    public List<String> OnlyWordsThatMatchPattern(String pattern, List<Character> guesses) {
        List<String> NewWords = new ArrayList<>();
        int length = pattern.length();
        xd:
        for (String word : words) {
            if (word.length() == length) {
                for (int i = 0; i < length; i++) {
                    char pat = pattern.charAt(i);
                    char letter = word.charAt(i);
                    if (!((pat == '-' && !guesses.contains(letter)) || (pat != '-' && pat == letter))) {
                        continue xd;
                    }
                }
                NewWords.add(word);
            }
        }
        return NewWords;
    }

    public Map<Character, Integer> getFreqMap(String pattern, List<Character> guesses) {
        Map<Character, Integer> map = new TreeMap<>();
        List<String> newWords = OnlyWordsThatMatchPattern(pattern, guesses);
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
        PAGALetterFreqGuesser pagalfg = new PAGALetterFreqGuesser("data/example.txt");
        System.out.println(pagalfg.getGuess("----", List.of('e')));
    }
}
