package stacs.wordle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.*;
import java.nio.file.Path;

public class WordleApp {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Welcome to CS5031 - Wordle");
        int maxTries = 6;
        int trial = 1;
        String filePath = "src\\main\\resources\\wordlist.txt";
        ArrayList<String> wordList = loadWordlist(filePath);
        String wordOfTheDay = randomWordSelector(filePath);
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        boolean status = false;
        do {
            System.out.println("Enter your " + (trial == 1 ? "first" : trial == 2 ? "second" : trial == 3 ? "third" : trial == 4 ? "fourth" : trial == 5 ? "fifth" : "sixth") + " guess");
            String userGuess = scanner.nextLine().trim();
            if (userGuess.length() != 5) {
                System.out.println("Please enter a valid 5-letter word");
            }
            else if(!wordList.contains(userGuess)){
                System.out.println("Not a valid english word");
            }
            else {
                status = matchUserInput(wordOfTheDay, userGuess);
                trial++;
                if (status) {
                    System.out.println("Congratulations! Word of the day : " + wordOfTheDay);
                    break;
                }
            }
        }
        while (trial < maxTries + 1);
        System.out.println(!status ? "Better luck next time!" + "\n" + "Word of the day : " + wordOfTheDay : "");

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
    public static boolean matchUserInput(String wordForTheDay, String input) {
        boolean status = true;
        String[] inputCharacters = input.toLowerCase().split("");
        String[] guessCharacters = wordForTheDay.toLowerCase().split("");
        List<String> vowelsList = Arrays.asList(wordForTheDay.split(""));
        for (int index = 0; index < inputCharacters.length; index++) {
            if (inputCharacters[index].equalsIgnoreCase(vowelsList.get(index))) {
                System.out.println(ConsoleColor.GREEN + inputCharacters[index] + ConsoleColor.RESET + " : " + ConsoleColor.GREEN_BACKGROUND + "Correct" + ConsoleColor.RESET);
            } else if ((!inputCharacters[index].equals(guessCharacters[index])) && vowelsList.contains(inputCharacters[index])) {
                System.out.println(ConsoleColor.YELLOW + inputCharacters[index] + ConsoleColor.RESET + " : " + ConsoleColor.YELLOW_BACKGROUND + "Present" + ConsoleColor.RESET);
                status = false;
            } else {
                System.out.println(ConsoleColor.RED + inputCharacters[index] + ConsoleColor.RESET + " : " + ConsoleColor.RED_BACKGROUND + "Incorrect" + ConsoleColor.RESET);
                status = false;
            }
        }
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
}
