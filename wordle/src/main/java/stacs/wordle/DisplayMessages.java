package stacs.wordle;

import javax.swing.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class DisplayMessages {
    /**
     * @param trial - number of tries the user took to guess the word
     * @return - winner addressing text
     */
    public String winnerAddress(int trial) {
        switch (trial) {
            case 1:
                return "Splendid!";
            case 2:
                return "That's Admirable!";
            case 3:
                return "Three cheers!";
            case 4:
                return "Hip, hip Hooray!";
            case 5:
                return "Way to Go!";
            case 6:
                return "Congratulations!";
        }
        return "Congrats";
    }

    /**
     * THe following method gives the user the trial number for input
     *
     * @param trial - number of tries at which the user is in at the current time.
     * @return - correcting trial number in text
     */
    public String trialInputMessage(int trial) {
        switch (trial) {
            case 1:
                return "first";
            case 2:
                return "second";
            case 3:
                return "third";
            case 4:
                return "fourth";
            case 5:
                return "fifth";
            case 6:
                return "last";
        }
        return "first";
    }


    /**
     * This method displays the previous trial result to the user
     *
     * @param guesses - dictionary of user input and guess result
     */
    public void printPreviousTrialResult(Map<String, String> guesses) {
        System.out.println("\n*** Previous Guess ***");
        for (int index = 0; index < guesses.size(); index++) {
            System.out.print(index + 1 + ".");
            int charIndex = 0;
            for (char g : (String.valueOf(guesses.values().toArray()[index])).toCharArray()) {
                switch (g) {
                    case 'g':
                        System.out.print("\t" + ConsoleTextColor.GREEN + String.valueOf(guesses.keySet().toArray()[index]).charAt(charIndex) + ConsoleTextColor.RESET);
                        break;
                    case 'r':
                        System.out.print("\t" + ConsoleTextColor.RED + String.valueOf(guesses.keySet().toArray()[index]).charAt(charIndex) + ConsoleTextColor.RESET);
                        break;
                    case 'y':
                        System.out.print("\t" + ConsoleTextColor.YELLOW + String.valueOf(guesses.keySet().toArray()[index]).charAt(charIndex) + ConsoleTextColor.RESET);
                        break;
                    default:
                        break;
                }
                charIndex++;
            }
            System.out.println();
        }
    }

    /**
     * This method is used to display the result of word matches to user.
     *
     * @param input - user entered input.
     * @param matchResult - result of matched characters
     */
    public void printWordMatchResult(String input, String matchResult) {
        char[] inputCharacters = input.toUpperCase().toCharArray();
        for (int index = 0; index < inputCharacters.length; index++) {
            if (matchResult.charAt(index) == 'g') {
                System.out.println(ConsoleTextColor.GREEN + inputCharacters[index] + ConsoleTextColor.RESET + " : " + ConsoleTextColor.GREEN_BACKGROUND + "Correct" + ConsoleTextColor.RESET);
            } else if (matchResult.charAt(index) == 'y') {
                System.out.println(ConsoleTextColor.YELLOW + inputCharacters[index] + ConsoleTextColor.RESET + " : " + ConsoleTextColor.YELLOW_BACKGROUND + "Present" + ConsoleTextColor.RESET);
            } else {
                System.out.println(ConsoleTextColor.RED + inputCharacters[index] + ConsoleTextColor.RESET + " : " + ConsoleTextColor.RED_BACKGROUND + "Incorrect" + ConsoleTextColor.RESET);
            }
        }
    }


    /**
     * The following method displays rules of the game to the user.
     */
    public void gameRules() {
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
     *
     * @param guesses - dictionary of user input and guess result.
     * @param score   - score obtained in the game.
     */
    public void gameStats(Map<String, String> guesses, int score) {
        System.out.println("\n\tSTATISTICS");
        System.out.println("\twin: " + score + "%");
        System.out.println("Guess Distribution");
        int index = 1;
        for (String guess : guesses.values()) {
            System.out.print(index + ": ");
            char[] wordGuess = guess.toCharArray();
            for (int wordIndex = 0; wordIndex < guess.length(); wordIndex++) {
                System.out.print(wordGuess[wordIndex] == 'g' ? ConsoleTextColor.WHITE + "|" + ConsoleTextColor.GREEN_BACKGROUND_BRIGHT + "  " + ConsoleTextColor.WHITE + "|" :
                        wordGuess[wordIndex] == 'y' ? ConsoleTextColor.WHITE + "|" + ConsoleTextColor.YELLOW_BACKGROUND_BRIGHT + "  " + ConsoleTextColor.WHITE + "|" :
                                ConsoleTextColor.WHITE + "|" + ConsoleTextColor.RED_BACKGROUND_BRIGHT + "  " + ConsoleTextColor.WHITE + "|");
            }
            System.out.println("\n" + ConsoleTextColor.RESET);
            index++;
        }
    }


    /**
     * Function that displays the rules of the game before start of game.
     */
    public void gameRulesGui() {
        String rules = "<html><font size='5' color=purple>HOW TO PLAY</font>" +
                "\nGuess the wordle in 6 tries\n" +
                "\n• Each guess must be a valid 5-letter word." +
                "\n• The color of the letters will change to show how close your guess was to the word.\n" +
                "\nExample\n" +
                "<html><font color=green>h</font> : <font color=green>Correct</font></html>, " +
                "\n<html><font color=orange>y</font> : <font color=orange>Present</font></html>, " +
                "\n<html><font color=green>p</font> : <font color=green>Correct</font></html>, " +
                "\n<html><font color=green>p</font> : <font color=green>Correct</font></html>, " +
                "\n<html><font color=red>y</font> : <font color=red>Incorrect</font></html>" +
                "\n<html>Letters <font color=green>h, p, p</font> are in correct spot, and letter<font color=orange> y </font>is in the word but in wrong position and letter <font color=red> e </font> is not in any spot </html>, "
                +
                "\n<html>The Correct word is HAPPY" + "</html>";

        JOptionPane.showOptionDialog(null, rules, "Game Rules", JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE, null, null, null);
    }


    /**
     * The following function displays the statistics for the played game.
     * @param score - computed score from all words
     * @param guessResult - result of all guesses made by the user
     * @param wordForTheDay - the chosen random word for the day
     */
    public void gameStatsGui(int score, LinkedHashMap<String, String> guessResult,String wordForTheDay) {
        StringBuilder stats = new StringBuilder("<html><font size='5' color=purple>Statistics</font></html>");
        stats.append("\n<html><font size='3' >Win: ").append(score).append("%</font></html>");
        stats.append("\n<html><font size='3' >Guess Distribution: ").append("</font></html>");
        for (String guess : guessResult.values()) {
            stats.append("\n<html><font style=\"background-color:").append(getColorText(guess.charAt(0))).append(";color:").append(getColorText(guess.charAt(0)))
                    .append("\">.....</font>  ").append("<font style=\"background-color:").append(getColorText(guess.charAt(1)))
                    .append(";color:").append(getColorText(guess.charAt(1))).append("\">.....</font>  ")
                    .append("<font style=\"background-color:").append(getColorText(guess.charAt(2))).append(";color:").append(getColorText(guess.charAt(2)))
                    .append("\">.....</font>  ").append("<font style=\"background-color:").append(getColorText(guess.charAt(3)))
                    .append(";color:").append(getColorText(guess.charAt(3))).append("\">.....</font>  ")
                    .append("<font style=\"background-color:").append(getColorText(guess.charAt(4))).append(";color:").append(getColorText(guess.charAt(4)))
                    .append("\">.....</font> \n");
        }
        stats.append("\nWord for the day is : '").append(wordForTheDay).append("'");
        JOptionPane.showOptionDialog(null, stats.toString(), "Game stats", JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE, null, null, null);
    }


    public String getColorText(char colorCode) {
        switch (colorCode){
            case 'g' : return "green";
            case 'y' : return "yellow";
            default: return "red";
        }
    }

    /**
     * This method is used to display the word match result on the GUI.
     *
     * @param userInput - user guess word
     * @param guessResult - result of the guesses word when compared to word for the day
     * @return text to be displayed on gui
     */
    public String textResultGui(String userInput, String guessResult){
        char[] result = guessResult.toCharArray();
        return "<html><font size='5' color=" + getColorText(result[0]) + ">  " + userInput.toUpperCase().charAt(0)
                + " </font> <font            " +
                "<html><font size='5' color=" + getColorText(result[1]) + ">  " + userInput.toUpperCase().charAt(1)
                + " </font> <font            " +
                "<html><font size='5' color=" + getColorText(result[2]) + ">  " + userInput.toUpperCase().charAt(2)
                + " </font> <font            " +
                "<html><font size='5' color=" + getColorText(result[3]) + ">  " + userInput.toUpperCase().charAt(3)
                + " </font> <font            " +
                "<html><font size='5' color=" + getColorText(result[4]) + ">  " + userInput.toUpperCase().charAt(4)
                + " </font> <font            ";
    }
}

