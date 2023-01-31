package stacs.wordle;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class WordleAppTest
{
    public final String testFilePath = "src\\test\\resources\\wordlist-test.txt";
    public final String wordFilePath = "src\\main\\resources\\wordlist.txt";
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
}
