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
    public static ArrayList<String> guesses = new ArrayList<>();
    public static final int maxTries = 6;

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
    public static boolean matchUserInput(String wordForTheDay, String input, int trialNumber) {
        int guessScore = 0;
        boolean status = true;
        StringBuilder guess = new StringBuilder();
        String[] inputCharacters = input.toLowerCase().split("");
        String[] guessCharacters = wordForTheDay.toLowerCase().split("");
        List<String> wordForTheDayList = Arrays.asList(wordForTheDay.split(""));
        for (int index = 0; index < inputCharacters.length; index++) {
            if (inputCharacters[index].equalsIgnoreCase(wordForTheDayList.get(index))) {
                System.out.println(ConsoleColor.GREEN + inputCharacters[index] + ConsoleColor.RESET + " : " + ConsoleColor.GREEN_BACKGROUND + "Correct" + ConsoleColor.RESET);
                guessScore += 10;
                guess.append("g");
            } else if ((!inputCharacters[index].equals(guessCharacters[index])) && wordForTheDayList.contains(inputCharacters[index])) {
                System.out.println(ConsoleColor.YELLOW + inputCharacters[index] + ConsoleColor.RESET + " : " + ConsoleColor.YELLOW_BACKGROUND + "Present" + ConsoleColor.RESET);
                guessScore += 5;
                status = false;
                guess.append("y");
            } else {
                System.out.println(ConsoleColor.RED + inputCharacters[index] + ConsoleColor.RESET + " : " + ConsoleColor.RED_BACKGROUND + "Incorrect" + ConsoleColor.RESET);
                status = false;
                guess.append("r");
            }
        }
        score += (guessScore == 50 ? trialNumber : 1) * ((guessScore * 20) / 50);
        guesses.add(guess.toString());
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
     * @throws FileNotFoundException - when the given file path doesnt exists
     */
    public static void wordleGame() throws FileNotFoundException {
        int trial = 1;
        ArrayList<String> wordList = loadWordlist(filePath);
        String wordOfTheDay = randomWordSelector(filePath);
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        boolean status = false;
        do {
            System.out.println("\nEnter your " + (trial == 1 ? "first" : trial == 2 ? "second" : trial == 3 ? "third" : trial == 4 ? "fourth" : trial == 5 ? "fifth" : "sixth") + " guess");
            String userGuess = scanner.nextLine().trim();
            if (userGuess.length() != 5) {
                System.out.println("Please enter a valid 5-letter word");
            } else if (!wordList.contains(userGuess.toLowerCase())) {
                System.out.println("Not a valid English word");
            } else {
                status = matchUserInput(wordOfTheDay, userGuess, maxTries - trial);
                trial++;
                if (status) {
                    System.out.println("Congratulations! Word of the day : " + wordOfTheDay);
                    break;
                }
            }
        }
        while (trial < maxTries + 1);
        System.out.println(!status ? "Better luck next time!" + "\n" + "Word of the day : " + wordOfTheDay : "");
        gameStats();
    }

    /**
     * The following method displays rules of the game to the user.
     */
    public static void gameRules() {
        System.out.println("\nHOW TO PLAY\nGuess the wordle in 6 tries \n");
        System.out.println("• Each guess must be a valid 5-letter word.\n• The color of the letters will change to show how close your guess was to the word.\n");
        System.out.println("Example");
        System.out.println(ConsoleColor.GREEN + "h " + ConsoleColor.RESET + " : " + ConsoleColor.GREEN_BACKGROUND + "Correct" + ConsoleColor.RESET);
        System.out.println(ConsoleColor.YELLOW + "y " + ConsoleColor.RESET + " : " + ConsoleColor.YELLOW_BACKGROUND + "Present" + ConsoleColor.RESET);
        System.out.println(ConsoleColor.GREEN + "p " + ConsoleColor.RESET + " : " + ConsoleColor.GREEN_BACKGROUND + "Correct" + ConsoleColor.RESET);
        System.out.println(ConsoleColor.GREEN + "p " + ConsoleColor.RESET + " : " + ConsoleColor.GREEN_BACKGROUND + "Correct" + ConsoleColor.RESET);
        System.out.println(ConsoleColor.RED + "e " + ConsoleColor.RESET + " : " + ConsoleColor.RED_BACKGROUND + "Incorrect" + ConsoleColor.RESET + "\n");
        System.out.println("Letters " + ConsoleColor.GREEN + "h, p ,p" + ConsoleColor.RESET + " are in correct spot, and letter "
                + ConsoleColor.YELLOW + "y" + ConsoleColor.RESET + " is in the word but in wrong position and letter "
                + ConsoleColor.RED + "e" + ConsoleColor.RESET + " is not in any spot ");
        System.out.println("The Correct word is HAPPY");
    }

    /**
     * The following method displays final stats of the game to the user.
     */
    public static void gameStats() {
        System.out.println("\t\t STATISTICS");
        System.out.println("\t\twin: " + score + "%");
        System.out.println("Guess Distribution");
        int index = 1;
        for (String guess : guesses) {
            System.out.print(index + ": ");
            char[] wordGuess = guess.toCharArray();
            for (int wordIndex = 0; wordIndex < guess.length(); wordIndex++) {
                System.out.print(wordGuess[wordIndex] == 'g' ? ConsoleColor.WHITE + "|" + ConsoleColor.GREEN_BACKGROUND_BRIGHT + "  " +  ConsoleColor.WHITE + "|" :
                        wordGuess[wordIndex] == 'y' ? ConsoleColor.WHITE + "|" +  ConsoleColor.YELLOW_BACKGROUND_BRIGHT + "  " + ConsoleColor.WHITE + "|"  :
                                ConsoleColor.WHITE + "|" + ConsoleColor.RED_BACKGROUND_BRIGHT + "  " + ConsoleColor.WHITE + "|" );
            }

            System.out.println("\n" + ConsoleColor.RESET);
            index++;
        }

    }
}
