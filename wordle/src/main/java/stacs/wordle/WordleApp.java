package stacs.wordle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.nio.file.Path;

public class WordleApp
{
    public static void main( String[] args )
    {
        System.out.println("Welcome to CS5031 - Wordle");
    }

    /**
     * This method is used read a file using a file path
     * and return a list of words in it as an array
     *
     * @return list of all words in the given file.
     * @throws FileNotFoundException when the file is not found in the given
     *                               location.
     */
     protected static ArrayList<String> loadWordlist(String wordlistPath) throws FileNotFoundException
    {
        ArrayList<String> words = new ArrayList<>();
        File textFile = new File(wordlistPath);
        try {
            Scanner fileReader = new Scanner(textFile);
            while(fileReader.hasNextLine()) {
                words.add(fileReader.nextLine());
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            Path path = Paths.get(wordlistPath);
            Path fileName = path.getFileName();
            throw new FileNotFoundException("File not found: " + fileName.toString());
        }
        return words;
    }

    /**
     * This method is used read a file using a given file path and return a word at random
     * from that given file path.
     * @param filePath - indicates the file path for all possible five-letter words
     * @return random selected  word from list of pre-defined words.
     * @throws FileNotFoundException when the file is not found in the given
     *                               location.
     */
    public static String randomWordSelector(String filePath) throws FileNotFoundException {
        String wordForTheDay = "";
        ArrayList<String> words = loadWordlist(filePath);
        return words.get(new Random().nextInt(words.size()));
    }
}
