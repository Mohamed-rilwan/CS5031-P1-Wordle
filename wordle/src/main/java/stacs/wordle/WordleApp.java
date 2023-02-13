package stacs.wordle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Random;
import java.util.Scanner;

/**
 * This is the command line interface for the wordle app
 * The game rules and functions are displayed at the start of the game
 * @author - matriculation Id - 220032472
 */
public class WordleApp {
    public static final String filePath = "src\\main\\resources\\wordlist.txt";
    public static final int maxTries = 6;
    public static int score = 0;
    //Since only unique words are allowed, the words and the result can be added to a map list as a key value pair
    public static LinkedHashMap<String, String> guesses = new LinkedHashMap<>();
    public static int trial = 1;
    static WordleService wordleservice = new WordleService();
    static DisplayMessages messages = new DisplayMessages();

    public static void setScore(int score) {
        WordleApp.score = score;
    }

    public static void setTrial(int trialNumber) {
        WordleApp.trial = trialNumber;
    }

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Welcome to CS5031 - Wordle");
        messages.gameRules();
        wordleGame();
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
     * entered user word is a valid 5-letter english word
     *
     * @param input      - guesses from the user
     * @param validWords - list of all valid 5-letter words
     * @return - true if the word is valid
     */
    public static boolean isValidWord(String input, ArrayList<String> validWords) {
        return validWords.contains(input);
    }

    /**
     * The following method initializes wordle game,
     * by checking the user input and matching with the word for the day
     *
     * @throws FileNotFoundException - when the given file path doesn't exist
     */
    public static void wordleGame() throws FileNotFoundException {
        ArrayList<String> wordList = loadWordlist(filePath);
        String wordOfTheDay = randomWordSelector(filePath);
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        String matchResult = null;
        do {
            if (guesses.size() > 0) {
                messages.printPreviousTrialResult(guesses);
            }
            System.out.println("Enter your " + messages.trialInputMessage(trial) + " guess");
            String userGuess = scanner.nextLine().trim();
            if (userGuess.length() != 5) {
                System.out.println("Please enter a valid 5-letter word");
            } else if (!wordList.contains(userGuess.toLowerCase())) {
                System.out.println("Not a valid English word");
            } else if (guesses.containsKey(userGuess)) {
                System.out.println("Word already entered");
            } else {
                matchResult = wordleservice.matchWord(userGuess, wordOfTheDay);
                setScore(score + wordleservice.scoreCalculator(matchResult, maxTries - trial));
                messages.printWordMatchResult(userGuess, matchResult);
                guesses.put(userGuess, matchResult);
                if (matchResult.equals("ggggg")) {
                    System.out.println("\n" + messages.winnerAddress(trial) + " You won in " + trial + (trial == 1 ? " try" : " tries"));
                    System.out.println("Word of the day : " + wordOfTheDay);
                    break;
                }
                setTrial(trial + 1);
            }
        }
        while (trial < maxTries + 1);
        assert matchResult != null;
        System.out.println(!matchResult.equals("ggggg") ? "\nBetter luck next time!" + "\n" + "Word of the day : " + wordOfTheDay : "");
        messages.gameStats(guesses, score);
    }
}
