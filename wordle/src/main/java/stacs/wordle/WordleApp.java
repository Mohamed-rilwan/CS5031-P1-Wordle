package stacs.wordle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.*;
import java.nio.file.Path;

public class WordleApp {
    public static final String filePath = "src\\test\\resources\\wordlist-test.txt";
    public static int score = 0;

    //Since only unique words are allowed, the words and the result can be added to a map list as a key value pair
    public static Map<String,String> guesses = new HashMap<>();
    public static ArrayList<String> guessesResult = new ArrayList<>();
    public static ArrayList<String> userGuesses = new ArrayList<>();
    public static final int maxTries = 6;

    public static int trial = 1 ;

    public static void setTrial(int trialNumber) {
        WordleApp.trial = trialNumber;
    }

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Welcome to CS5031 - Wordle");
        gameRules();
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
     * selected word for the day matches with the
     * given user input
     *
     * @param input         - guesses from the user
     * @param wordForTheDay - the word that is selected for the day
     * @return - true if the complete word matches
     */
    public static Boolean matchUserInput(String wordForTheDay, String input, int trialNumber) {
        int guessScore = 0;
        boolean status = true;
        StringBuilder guess = new StringBuilder();
        String[] inputCharacters = input.toLowerCase().split("");
        String[] guessCharacters = wordForTheDay.toLowerCase().split("");
        List<String> wordForTheDayList = Arrays.asList(wordForTheDay.split(""));
        for (int index = 0; index < inputCharacters.length; index++) {
            if (inputCharacters[index].equalsIgnoreCase(wordForTheDayList.get(index))) {
                System.out.println(ConsoleTextColor.GREEN + inputCharacters[index] + ConsoleTextColor.RESET + " : " + ConsoleTextColor.GREEN_BACKGROUND + "Correct" + ConsoleTextColor.RESET);
                //User gets 10 points for each accurate guess
                guessScore += 10;
                guess.append("g");
            } else if ((!inputCharacters[index].equals(guessCharacters[index])) && wordForTheDayList.contains(inputCharacters[index])) {
                System.out.println(ConsoleTextColor.YELLOW + inputCharacters[index] + ConsoleTextColor.RESET + " : " + ConsoleTextColor.YELLOW_BACKGROUND + "Present" + ConsoleTextColor.RESET);
                //User gets 5 points for each partial guess
                guessScore += 5;
                status = false;
                guess.append("y");
            } else {
                System.out.println(ConsoleTextColor.RED + inputCharacters[index] + ConsoleTextColor.RESET + " : " + ConsoleTextColor.RED_BACKGROUND + "Incorrect" + ConsoleTextColor.RESET);
                status = false;
                guess.append("r");
            }
        }
        /*
        Score is calculated for each number of tries and correct letter guess in each.
        Scoring strategy
        1. All green (10 points each) - Each try can fetch total of 50 points if all letters are correct
        2. All yellow (5 points each) - Each try can fetch total of 25 points
        3. All tries have a total of 20% weight overall multiplied by the descending number of tries that they take to guess.
        4. Example - if a user gets all partial (yellow) in first try and guesses all in second then total score:
                     1st try - 25 points -> 10%
                     2nd try - 50 points -> 80%
                     Total score - 90%
        */
        score += (guessScore == 50 ? trialNumber : 1) * ((guessScore * 20) / 50);
        guessesResult.add(guess.toString());
        guesses.put(input, guess.toString());
        return status;
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
//        Map<String, String> guessResult =  new HashMap<>();
        boolean status = false;
        do {
            if(userGuesses.size() > 0) { printPreviousTrialResult(); }
            System.out.println("Enter your " + (trial == 1 ? "first" : trial == 2 ? "second" : trial == 3 ? "third" : trial == 4 ? "fourth" : trial == 5 ? "fifth" : "last") + " guess");
            String userGuess = scanner.nextLine().trim();
            if (userGuess.length() != 5) {
                System.out.println("Please enter a valid 5-letter word");
            } else if (!wordList.contains(userGuess.toLowerCase())) {
                System.out.println("Not a valid English word");
            } else if (userGuesses.contains(userGuess)){
                System.out.println("Word already entered");
            }            else {
                userGuesses.add(userGuess);
                status = matchUserInput(wordOfTheDay, userGuess, maxTries - trial);
                setTrial(trial+1);
                if (status) {
                    System.out.println("\nCongratulations! Word of the day : " + wordOfTheDay);
                    break;
                }
            }
        }
        while (trial < maxTries + 1);
        System.out.println(!status ? "\nBetter luck next time!" + "\n" + "Word of the day : " + wordOfTheDay : "");
        gameStats();
    }

    /**
     * The following method displays rules of the game to the user.
     */
    public static void gameRules() {
        System.out.println("\nHOW TO PLAY\nGuess the wordle in 6 tries \n");
        System.out.println("• Each guess must be a valid 5-letter word.\n• The color of the letters will change to show how close your guess was to the word.\n");
        System.out.println("Example");
        System.out.println(ConsoleTextColor.GREEN + "h " + ConsoleTextColor.RESET + " : " + ConsoleTextColor.GREEN_BACKGROUND + "Correct" + ConsoleTextColor.RESET);
        System.out.println(ConsoleTextColor.YELLOW + "y " + ConsoleTextColor.RESET + " : " + ConsoleTextColor.YELLOW_BACKGROUND + "Present" + ConsoleTextColor.RESET);
        System.out.println(ConsoleTextColor.GREEN + "p " + ConsoleTextColor.RESET + " : " + ConsoleTextColor.GREEN_BACKGROUND + "Correct" + ConsoleTextColor.RESET);
        System.out.println(ConsoleTextColor.GREEN + "p " + ConsoleTextColor.RESET + " : " + ConsoleTextColor.GREEN_BACKGROUND + "Correct" + ConsoleTextColor.RESET);
        System.out.println(ConsoleTextColor.RED + "e " + ConsoleTextColor.RESET + " : " + ConsoleTextColor.RED_BACKGROUND + "Incorrect" + ConsoleTextColor.RESET + "\n");
        System.out.println("Letters " + ConsoleTextColor.GREEN + "h, p ,p" + ConsoleTextColor.RESET + " are in correct spot, and letter "
                + ConsoleTextColor.YELLOW + "y" + ConsoleTextColor.RESET + " is in the word but in wrong position and letter "
                + ConsoleTextColor.RED + "e" + ConsoleTextColor.RESET + " is not in any spot ");
        System.out.println("The Correct word is HAPPY\n");
    }

    /**
     * The following method displays final statistics of the game to the user.
     */
    public static void gameStats() {
        System.out.println("\n\tSTATISTICS");
        System.out.println("\twin: " + score + "%");
        System.out.println("Guess Distribution");
        int index = 1;
        for (String guess : guessesResult) {
            System.out.print(index + ": ");
            char[] wordGuess = guess.toCharArray();
            for (int wordIndex = 0; wordIndex < guess.length(); wordIndex++) {
                System.out.print(wordGuess[wordIndex] == 'g' ? ConsoleTextColor.WHITE + "|" + ConsoleTextColor.GREEN_BACKGROUND_BRIGHT + "  " +  ConsoleTextColor.WHITE + "|" :
                        wordGuess[wordIndex] == 'y' ? ConsoleTextColor.WHITE + "|" +  ConsoleTextColor.YELLOW_BACKGROUND_BRIGHT + "  " + ConsoleTextColor.WHITE + "|"  :
                                ConsoleTextColor.WHITE + "|" + ConsoleTextColor.RED_BACKGROUND_BRIGHT + "  " + ConsoleTextColor.WHITE + "|" );
            }

            System.out.println("\n" + ConsoleTextColor.RESET);
            index++;
        }
    }

    /**
     * This method displays the previous trial result to the user
     */
    public static void printPreviousTrialResult(){
        System.out.println("\nPrevious Guess");
        for(int index = 0; index < userGuesses.size(); index++ ){
            int charIndex = 0;
            for (char g : guessesResult.get(index).toCharArray()) {
                switch (g) {
                    case 'g':
                    System.out.print("\t" + ConsoleTextColor.GREEN + userGuesses.get(index).charAt(charIndex) + ConsoleTextColor.RESET );
                        break;
                    case 'r':
                        System.out.print("\t" +ConsoleTextColor.RED + userGuesses.get(index).charAt(charIndex) + ConsoleTextColor.RESET);
                        break;
                    case 'y':
                        System.out.print("\t" +ConsoleTextColor.YELLOW + userGuesses.get(index).charAt(charIndex) + ConsoleTextColor.RESET);
                        break;
                    default: break;
                }
                charIndex++;
            }
            System.out.println();
        }
    }
}
