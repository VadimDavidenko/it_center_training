package words_freq;

import java.util.Set;

/**
 * Created by v.davidenko on 22.12.2015.
 * Runs class Freq
 */
public class Start {

    public static void main(String[] args) {
        Set<String> wordSet;
        Freq freq = new Freq();

        freq.setText(freq.generateRandomText(200));
        System.out.println(freq.getText() + "\n");

        int frequency = 4;

        wordSet = freq.getWordsByFrequencyLessThan(frequency);
        if (wordSet != null) System.out.println(wordSet.toString() + "\n");

        wordSet = freq.getWordsByFrequency(frequency);
        if (wordSet != null) System.out.println(wordSet.toString() + "\n");

        wordSet = freq.getWordsByFrequencyMoreThan(frequency);
        if (wordSet != null) System.out.println(wordSet.toString() + "\n");

        freq.printAsc();

        freq.printDesc();


    }

}
