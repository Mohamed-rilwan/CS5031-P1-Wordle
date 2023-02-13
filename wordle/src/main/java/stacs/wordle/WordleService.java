package stacs.wordle;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class WordleService {

    /**
     * This method is used read a file using a file path
     * and return a list of words in it as an array
     *
     * @return list of all words in the given file.
     * @throws FileNotFoundException when the file is not found in the given
     *                               location.
     */
    public static ArrayList<String> loadWordlist(String wordlistPath) throws FileNotFoundException {
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
     * This method is used to colorize the input guess based on its match against
     * the correct word
     *
     * @param inputWord     - character array of the usr input
     * @param wordForTheDay - Random word chosen for the game
     * @return - colored output based on the user word entry
     */
    public String matchWord(String inputWord, String wordForTheDay) {
        // Instantiating with all red colored letters before starting to check
        char[] guessResult = {'r', 'r', 'r', 'r', 'r'};

        assert inputWord.length() == 5;
        assert wordForTheDay.length() == 5;

        char[] wordForTheDayList = wordForTheDay.toCharArray();
        char[] inputList = inputWord.toCharArray();

        //Checking for a correct input word
        for (int index = 0; index < inputList.length; index++) {
            if (inputList[index] == wordForTheDayList[index]) {
                wordForTheDayList[index] = ' ';
                guessResult[index] = 'g';
            }
        }

        /*
              Checking for a words that are present by in wrong order
              Edge case - if a character is present but is available in multiple positions in a chosen word
              This can cause the basic implementation to give yellow in all the partial position
              This method checks for all position in th random selected word for the day to see if there are
              other possibilities of the character
         */
        if (!String.valueOf(guessResult).equals("ggggg")) {
            for (int inputIndex = 0; inputIndex < 5; inputIndex++) {
                for (int wordIndex = 0; wordIndex < 5; wordIndex++) {
                    if (inputList[inputIndex] == wordForTheDayList[wordIndex] && guessResult[inputIndex] != 'g') {
                        guessResult[inputIndex] = 'y';
                        //Remove the character from the original position when matched
                        wordForTheDayList[wordIndex] = ' ';
                    }
                }
            }
        }
        return String.valueOf(guessResult);
    }

    /**
     *  This method is used to calculate score of the input guess based on its match against
     *  the correct word
     *  Score is calculated for each number of tries and correct letter guess in each.
     * Scoring strategy
     * 1. All green (10 points each) - Each try can fetch total of 50 points if all letters are correct
     * 2. All yellow (5 points each) - Each try can fetch total of 25 points
     * 3. All tries have a total of 20% weight overall multiplied by the descending number of tries that they take to guess.
     * 4. Example - if a user gets all partial (yellow) in first try and guesses all in second then total score:
     *              1st try - 25 points -> 10%
     *              2nd try - 50 points -> 80%
     *              Total score - 90%
     * @param guessResult - colorized input result for the corresponding user input
     * @param trialNumber - trial of the user input
     * @return score based on the input and number of guesses required
     */
    public int scoreCalculator(String guessResult, int trialNumber) {
        int score = 0;
        char[] guessResultCharacters = guessResult.toCharArray();
        for (char result : guessResultCharacters) {
            switch (result) {
                case 'g':
                    score += 10;
                    break;
                case 'y':
                    score += 5;
                    break;
                default:
                    break;
            }
        }
        return (score == 50 ? trialNumber : 1) * ((score * 20) / 50);
    }
}


