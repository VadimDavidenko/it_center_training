package words_freq;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * Created by v.davidenko on 21.12.2015.
 * Class counts how often the same words are presented in some text
 */
public class Freq {
    private String text;

    // Allows entering some text line from Console
    public String setTextFromConsole() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter some words via spaces below:");
        return scanner.nextLine();
    }

    // Reads some text from a file
    public String setTextFromFile(String fileName) {
        File file = new File (fileName);
        StringBuilder sb = new StringBuilder();
        int b;
        FileInputStream is = null;
        try{
            is = new FileInputStream(file);
            while((b = is.read()) != -1) {
                sb.append((char)b);
            }
        } catch(IOException e) {
            System.err.println("File reading error: " + e );
        } finally{
            try{
                if(is != null) {
                    is.close();
                }
            } catch(IOException e) {
                System.err.println("Stream closing error: " + e);
            }
        }
        return sb.toString();
    }

    // Generates random text contained words separated by spaces
    public String generateRandomText(int textLength) {
        final Integer CHAR_A = 65;
        final Integer CHAR_Z = 90;
        final Integer MAX_WORD_LENGTH = 7;
        final Integer MIN_WORD_LENGTH = 2;
        final Integer MAX_WORD_REPEATS = 5;

        StringBuilder text = new StringBuilder();
        Random random = new Random();
        int b;
        while(text.length() < textLength) {
            // generate a random word
            int wordLength = random.nextInt(MAX_WORD_LENGTH - MIN_WORD_LENGTH) + MIN_WORD_LENGTH;
            StringBuilder word = new StringBuilder();
            for (int i=1; i <= wordLength; i++) {
                b = random.nextInt(CHAR_Z - CHAR_A) + CHAR_A;
                word.append((char) b);
            }
            // random repeating of word adding
            int repeatsCounter = MAX_WORD_REPEATS;
            for (int i = random.nextInt(repeatsCounter); i < repeatsCounter; i++) {
                text.append(word.toString());
                text.append(" ");
            }
        }
        return text.toString().substring(0, textLength - 1);
    }

    // Words frequencies calculation
    private Map<String, Integer> getWordsFrequency() {
        Map<String, Integer> wordMap = new HashMap<String, Integer>();

        if (text != null) {
            String[] words = text.split(" ");
            for (String word : words) {
                if (wordMap.containsKey(word)) {
                    wordMap.put(word, wordMap.get(word) + 1);
                } else {
                    wordMap.put(word, 1);
                }
            }
        }
        return wordMap;
    }

    // Getting the words by predefined frequency
    public Set<String> getWordsByFrequency(int frequency) {
        Set<String> wordSet = new HashSet<String>();
        Map<String, Integer> wordMap = getWordsFrequency();

        if (wordMap != null) {
            Set<Map.Entry<String, Integer>> entries = wordMap.entrySet();
            for (Map.Entry entry : entries) {
                if (entry.getValue().equals(frequency)) {
                    wordSet.add((String) entry.getKey());
                }
            }
        }
        return wordSet;
    }

    // Getting the words less then by predefined frequency
    public Set<String> getWordsByFrequencyLessThan(int frequency) {
        Set<String> wordSet = new HashSet<String>();

        Map<String, Integer> wordMap = getWordsFrequency();
        if (wordMap != null) {
            Set<Map.Entry<String, Integer>> entries = wordMap.entrySet();
            for (Map.Entry entry : entries) {
                if ((Integer) entry.getValue() < frequency) {
                    wordSet.add((String) entry.getKey());
                }
            }
        }
        return wordSet;
    }

    // Getting the words more then by predefined frequency
    public Set<String> getWordsByFrequencyMoreThan(int frequency) {
        Set<String> wordSet = new HashSet<String>();

        Map<String, Integer> wordMap = getWordsFrequency();
        if (wordMap != null) {
            Set<Map.Entry<String, Integer>> entries = wordMap.entrySet();
            for (Map.Entry entry : entries) {
                if ((Integer) entry.getValue() > frequency) {
                    wordSet.add((String) entry.getKey());
                }
            }
        }
        return wordSet;
    }

    // Prints all words + their frequencies by ascending
    public void printAsc() {
        Map<String, Integer> wordMap = getWordsFrequency();

        if (wordMap != null) {
            Comparator<String> comparator = new ValueComparator(wordMap, true);
            Map<String, Integer> sortedMap = new TreeMap<String, Integer>(comparator);
            sortedMap.putAll(wordMap);

            Set<Map.Entry<String, Integer>> entries = sortedMap.entrySet();
            for (Map.Entry entry : entries) {
                System.out.println(entry.getKey() + " -> " + entry.getValue());
            }
            System.out.println();
        }
    }

    // Prints all words + their frequencies by descending
    public void printDesc() {
        Map<String, Integer> wordMap = getWordsFrequency();

        if (wordMap != null) {
            Comparator<String> comparator = new ValueComparator(wordMap, false);
            Map<String, Integer> sortedMap = new TreeMap<String, Integer>(comparator);
            sortedMap.putAll(wordMap);

            Set<Map.Entry<String, Integer>> entries = sortedMap.entrySet();
            for (Map.Entry entry : entries) {
                System.out.println(entry.getKey() + " -> " + entry.getValue());
            }
            System.out.println();
        }
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}


// Custom comparator, compare map entities by values (not by keys)
// Compares values ascending (orderAsc = true) and descending (orderAsc = false)
class ValueComparator implements Comparator<String> {
    private Map<String, Integer> base;
    boolean orderAsc;

    public ValueComparator(Map<String, Integer> base, boolean orderAsc) {
        this.base = base;
        this.orderAsc = orderAsc;
    }

    @Override
    public int compare(String s1, String s2) {
        if (orderAsc) {     // ascending
            if (base.get(s1).compareTo(base.get(s2)) < 0) {
                return -1;
            } else {
                return 1;
            }
        } else {        // descending
            if (base.get(s1).compareTo(base.get(s2)) > 0) {
                return -1;
            } else {
                return 1;
            }
        }
    }
}
