package stacs.wordle;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class WordleAppTest
{
    public final String testFilePath = "src\\test\\resources\\wordlist-test.txt";
    public final String wordFilePath = "src\\main\\resources\\wordlist.txt";

    private PrintStream console;
    private ByteArrayOutputStream bytes;

    @BeforeEach
    public void setUp() {
        bytes   = new ByteArrayOutputStream();
        console = System.out;
        System.setOut(new PrintStream(bytes));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(console);
    }

    @Test
    public void shouldLoadWordlist() throws FileNotFoundException
    {
        ArrayList<String> wordlist = WordleApp.loadWordlist(testFilePath);
        // test wordlist only contains 3 words, so wordlist should have the size of 3
        assertEquals(3, wordlist.size());
    }

    @Test
    public void fetchRandomWord() throws FileNotFoundException
    {
        String randomWord1 = WordleApp.randomWordSelector(wordFilePath);
        assertNotNull(randomWord1);

        String randomWord2 = WordleApp.randomWordSelector(wordFilePath);
        assertNotNull(randomWord2);

    }

    //Test if the given two words are matching character by character
    @Test
    public void matchTestWord(){
        String wordOfTheDay = "fetch";
        String userInput = "fetch";
        boolean isMatch = WordleApp.matchUserInput(wordOfTheDay, userInput, 5);
        assertTrue(isMatch);

        String wordOfTheDay1 = "fetch";
        String userInput1 = "feSeh";
        boolean isMatch1 = WordleApp.matchUserInput(wordOfTheDay1, userInput1 , 5);
        assertFalse(isMatch1);
    }

    //Test for exception thrown on invalid file path
    @Test
    public void inValidFile() {
        Exception exception = assertThrows(FileNotFoundException.class, () -> WordleApp.loadWordlist("abc.txt"));
        String expectedMessage = "File not found: abc.txt";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    public void isValidWord() throws FileNotFoundException {
        String validWord1 = "fetch";
        String validWord2 = "cuppa";
        String invalidWord = "2";
        ArrayList<String> wordlist = WordleApp.loadWordlist(wordFilePath);
        boolean isMatch1 = WordleApp.isValidWord(validWord1, wordlist);
        assertTrue(isMatch1);

        boolean isMatch2 = WordleApp.isValidWord(validWord2, wordlist);
        assertTrue(isMatch2);

        boolean isMatch3 = WordleApp.isValidWord(invalidWord, wordlist);
        assertFalse(isMatch3);
    }

//    @Test
//    public void twoIncorrectLetter() throws FileNotFoundException {
//        String testWord = "exile";
//        String wordOfTheWord = "cache";
//        //
//        try
//        {
//            WordleApp app = new WordleApp();
//            app.main(new String[] {});
//        }
//        finally {
//            System.setOut(console);
//        }
//
//    }
}
