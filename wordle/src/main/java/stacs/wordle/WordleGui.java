package stacs.wordle;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.ArrayList;
import java.io.FileNotFoundException;

/**
 * The following class builds the GUI interface for Wordle game
 */
public class WordleGui extends JFrame implements ActionListener {
    public static int maxTries = 6;
    private static JFrame frame;
    private static JLabel appTitle;
    private static JLabel helperTitle;
    private static JTextField guess;
    private static JLabel[] guesses;
    static ArrayList<String> words;
    static int score = 0;
    public static ArrayList<String[]> finalGuesses = new ArrayList<>();

    static {
        try {
            words = WordleApp.loadWordlist(WordleApp.filePath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    static int tries;
    static char[] input;
    static char[] answer;
    static boolean status;
    static String wordForTheDay;

    static {
        try {
            wordForTheDay = WordleApp.randomWordSelector(WordleApp.filePath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        JPanel panel = new JPanel();
        frame = new JFrame();
        frame.setSize(220, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.add(panel);
        frame.getContentPane().setBackground(Color.BLACK);
        panel.setBackground(Color.BLACK);

        panel.setLayout(null);
        appTitle = new JLabel("<html><font size='5' color=white>WORDLE</font> <font");
        appTitle.setBounds(60, 10, 180, 20);
        panel.add(appTitle);

        helperTitle = new JLabel();
        helperTitle.setBounds(40, 220, 180, 20);
        panel.add(helperTitle);

        guess = new JTextField();
        guess.addActionListener(new WordleGui());
        guess.setBounds(60, 50, 75, 25);
        panel.add(guess);

        guesses = new JLabel[6];
        for (int i = 0; i < 6; i++) {
            guesses[i] = new JLabel("<html><font size='5' color=white> _ _ _ _ _ </font> <font");
            guesses[i].setBounds(60, 50 + (i * 25), 100, 25);
            panel.add(guesses[i]);
        }
        frame.setVisible(true);
        gameRules();
        StartWordle();
        JOptionPane optionPane = new JOptionPane();
        panel.add(optionPane);
    }

    public static void StartWordle() {
        tries = 0;
        System.out.println("Welcome to CS5031 - Wordle");
        WordleApp.gameRules();
        answer = new char[5];
        for (int index = 0; index < maxTries - 1; index++) answer[index] = wordForTheDay.charAt(index);
        input = new char[5];
    }

    public static void EndWordle() {
        guess.setEnabled(false);
        guess.setVisible(false);
        String result = (!status || tries > maxTries - 1 ? "Better luck next time! " : "Congratulations!");
        System.out.println(result);
        if (!status || tries > maxTries - 1) {
            helperTitle.setBounds(45, 220, 180, 20);
            helperTitle.setText("<html><font size='3' color=red>" + "Better luck next time!");
        } else {
            helperTitle.setBounds(60, 220, 180, 20);
            helperTitle.setText("<html><font size='3' color=green>" + "Congratulations!");
        }

        gameStats();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        EnterWord();
    }

    public static void EnterWord() {
        if (WordleApp.isValidWord(guess.getText(), words)) ButtonPressed();
        else {
            JOptionPane.showMessageDialog(frame, "Not a valid English word\nPlease enter a valid 5-letter word", "Invalid Word", JOptionPane.ERROR_MESSAGE);
            appTitle = new JLabel("Not a valid English word");
            System.out.println("Not a valid English word");
        }
    }

    public static void ButtonPressed() {
        guess.setBounds(60, 50 + ((tries + 1) * 25), 75, 25);
        String userInput = guess.getText().toLowerCase();
        String[] letterColor = playWordle(userInput);
        status = true;
        for (String indexValue : letterColor) {
            if (!indexValue.equals("green")) {
                status = false;
                break;
            }
        }
        finalGuesses.add(letterColor);
        if (status || tries > maxTries - 1) EndWordle();

        String finalString = (
                "<html><font size='5' color=" + letterColor[0] + ">  " + userInput.toUpperCase().charAt(0) + " </font> <font            " +
                        "<html><font size='5' color=" + letterColor[1] + ">  " + userInput.toUpperCase().charAt(1) + " </font> <font            " +
                        "<html><font size='5' color=" + letterColor[2] + ">  " + userInput.toUpperCase().charAt(2) + " </font> <font            " +
                        "<html><font size='5' color=" + letterColor[3] + ">  " + userInput.toUpperCase().charAt(3) + " </font> <font            " +
                        "<html><font size='5' color=" + letterColor[4] + ">  " + userInput.toUpperCase().charAt(4) + " </font> <font            ");
        setNextLabel(finalString);
        guess.setText("");
    }

    /**
     * Methods to check if each of the entered input is valid
     * @param InputWordleWord  - users guesses during the game
     * @return array of strings that states the correct position of letters
     */
    public static String[] playWordle(String InputWordleWord) {
        status = false;
        tries++;
        String R1 = InputWordleWord.toLowerCase();

        for (int i = 0; i < 5; i++) {
            input[i] = R1.charAt(i);
        }
        for (int i = 0; i < 5; i++) answer[i] = wordForTheDay.charAt(i);
        return ReturnColorOfLetters(input, answer,maxTries - tries);
    }

    public static void setNextLabel(String string) {
        guesses[tries - 1].setText(string);
    }

    public static String[] ReturnColorOfLetters(char[] inputWord, char[] correctWord,int trialNumber) {
        int guessScore = 0;
        String[] colorForLetter = {"red", "red", "red", "red", "red"};

        for (int i = 0; i < 5; i++) {
            if (inputWord[i] == correctWord[i]) {
                correctWord[i] = '-';
                colorForLetter[i] = "green";
                guessScore += 10;
            }
        }

        for (int j = 0; j < 5; j++) {
            for (int k = 0; k < 5; k++) {
                if (inputWord[j] == correctWord[k] && !colorForLetter[j].equals("green")) {
                    colorForLetter[j] = "yellow";
                    correctWord[k] = '-';
                    guessScore += 5;
                }
            }
        }

        WordleApp.matchUserInput(wordForTheDay, String.valueOf(inputWord), tries);
        System.out.println();
        score += (guessScore == 50 ? trialNumber : 1) * ((guessScore * 20) / 50);
        return colorForLetter;
    }

    /**
     * Function that displays the rules of the game before start of game
     */
    public static void gameRules() {
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
                "\n<html>Letters <font color=green>h, p, p</font> are in correct spot, and letter<font color=orange> y </font>is in the word but in wrong position and letter <font color=red> e </font> is not in any spot </html>, " +
                "\n<html>The Correct word is HAPPY" + "</html>";

        JOptionPane.showOptionDialog(null, rules,"Game Rules",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,null,null,null);
    }

    /**
     * The following function displays the statistics for the played game
     */
    public static void gameStats() {
        StringBuilder stats = new StringBuilder("<html><font size='5' color=purple>Statistics</font></html>");
               stats.append("\n<html><font size='3' >Win: ").append(score).append("%</font></html>");
        for (String[] guess : finalGuesses) {
            stats.append("\n<html><font style=\"background-color:").append(guess[0]).append(";color:").append(guess[0]).append("\">.....</font>  ").append("<font style=\"background-color:").append(guess[1]).append(";color:").append(guess[1]).append("\">.....</font>  ").append("<font style=\"background-color:").append(guess[2]).append(";color:").append(guess[2]).append("\">.....</font>  ").append("<font style=\"background-color:").append(guess[3]).append(";color:").append(guess[3]).append("\">.....</font>  ").append("<font style=\"background-color:").append(guess[4]).append(";color:").append(guess[4]).append("\">.....</font>  \n");
        }

        JOptionPane.showOptionDialog(null, stats.toString(),"Game stats",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,null,null,null);
    }

}