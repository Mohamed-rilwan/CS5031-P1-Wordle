package stacs.wordle;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.*;
import java.io.FileNotFoundException;

/**
 * This is the Graphical interface for the wordle app
 * The game rules and functions remain the same as the console application
 * @author - matriculation Id - 220032472
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
    public static LinkedHashMap<String, String> guessesWithResult = new LinkedHashMap<>();
    static WordleService wordleService = new WordleService();

    static {
        try {
            words = WordleService.loadWordlist(WordleApp.filePath);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Invalid file path for word list");
        }
    }

    static int tries = 1;

    static String wordForTheDay;

    static DisplayMessages messages = new DisplayMessages();


    static {
        try {
            wordForTheDay = WordleApp.randomWordSelector(WordleApp.filePath);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Invalid file path for word list");
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
        messages.gameRulesGui();
        StartWordle();
        JOptionPane optionPane = new JOptionPane();
        panel.add(optionPane);
    }

    public static void StartWordle() {
        System.out.println("Welcome to CS5031 - Wordle");
        messages.gameRules();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        EnterWord();
    }

    /**
     * Check if the word is valid and perform word match
     */
    public void EnterWord() {
        if (guessesWithResult.containsKey(guess.getText())) {
            JOptionPane.showMessageDialog(frame, "Word already entered", "Invalid Word", JOptionPane.ERROR_MESSAGE);
            appTitle = new JLabel("Word already entered");
            System.out.println("Word already entered");
        } else {
            if (WordleApp.isValidWord(guess.getText(), words)) {
                ButtonPressed();
            } else {
                JOptionPane.showMessageDialog(frame, "Not a valid English word\nPlease enter a valid 5-letter word",
                        "Invalid Word", JOptionPane.ERROR_MESSAGE);
                appTitle = new JLabel("Not a valid English word");
                System.out.println("Not a valid English word");
            }
        }
    }

    /**
     * This method is used to compute the user input and check its correctness
     * against the word of the day
     */
    public void ButtonPressed() {
        guess.setBounds(60, 50 + ((tries) * 25), 75, 25);
        String userInput = guess.getText().toLowerCase();
        String result = wordleService.matchWord(userInput, wordForTheDay);
        score += wordleService.scoreCalculator(result, maxTries - tries);
        guessesWithResult.put(userInput, result);
        String finalString = messages.textResultGui(userInput, result);
        setNextLabel(finalString);
        guess.setText("");
        tries++;
        if (result.equals("ggggg") || tries > maxTries) {
            EndWordle();
        }

    }

    /**
     * This method ends the wordle game and displays game statistics
     */
    public static void EndWordle() {
        guess.setEnabled(false);
        guess.setVisible(false);
        String result = (tries > maxTries  ? "Better luck next time! " : messages.winnerAddress(tries - 1 ));
        System.out.println(result);
        if (tries > maxTries) {
            helperTitle.setBounds(45, 220, 180, 20);
            helperTitle.setText("<html><font size='3' color=red>" + "Better luck next time!");
        } else {
            helperTitle.setBounds(60, 220, 180, 20);
            helperTitle.setText("<html><font size='3' color=green>" + messages.winnerAddress(tries - 1));
        }
        messages.gameStatsGui(score, guessesWithResult,wordForTheDay);
    }

    /**
     * Method to update the user guesses into correct position
     * @param string append word to the particular guess
     */
    public static void setNextLabel(String string) {
        guesses[tries - 1].setText(string);
    }
}