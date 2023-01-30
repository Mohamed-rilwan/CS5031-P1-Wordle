package stacs.wordle;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.*;
import java.nio.file.Path;

public class WordleApp {
    public static void main(String[] args) {
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
    protected static ArrayList<String> loadWordlist(String wordlistPath) throws FileNotFoundException {
        ArrayList<String> words = new ArrayList<>();
        File textFile = new File(wordlistPath);
        try {
            Scanner fileReader = new Scanner(textFile);
            while (fileReader.hasNextLine()) {
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
     *
     * @param filePath - indicates the file path for all possible five-letter words
     * @return random selected  word from list of pre-defined words.
     * @throws FileNotFoundException when the file is not found in the given
     *                               location.
     */
    public static String randomWordSelector(String filePath) throws FileNotFoundException {
        ArrayList<String> words = loadWordlist(filePath);
        return words.get(new Random().nextInt(words.size()));
    }


    /**
     * This method is used to check if the
     * selected word for the day matches with the
     * given user input
     *
     * @param input - guesses from the user
     * @param wordForTheDay - the word that is selected for the day
     * @return - true if the complete word matches
     */
    public static boolean matchUserInput(String wordForTheDay, String input) {
        boolean status = false;
        String[] inputCharacters = input.split("");
        String[] guessCharacters = wordForTheDay.split("");
        List<String> vowelsList = Arrays.asList(wordForTheDay.split(""));
        for (int index = 0; index < inputCharacters.length; index++) {
            if (Objects.equals((inputCharacters[index]), vowelsList.get(index))) {
                System.out.println(inputCharacters[index] + " : " + "Correct");
                status = true;
            } else if ((!inputCharacters[index].equals(guessCharacters[index])) && vowelsList.contains(inputCharacters[index])) {
                System.out.println(inputCharacters[index] + " : " + "Present");
                status = false;
            } else {
                System.out.println(inputCharacters[index] + " : " + "Incorrect");
                status = false;
            }
        }
        return status;
    }

}
