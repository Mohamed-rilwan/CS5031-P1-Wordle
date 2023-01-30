package stacs.wordle;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class WordleAppTest
{
    @Test
    public void shouldLoadWordlist() throws FileNotFoundException
    {
        ArrayList<String> wordlist = WordleApp.loadWordlist("D:\\University_Of_St_Andrews\\Semester 2\\CS5031 - Software Engineering Practice\\Coursework\\P1-Wordle\\CS5031-P1-Wordle\\wordle\\src\\test\\resources\\wordlist-test.txt");
        // test wordlist only contains 3 words, so wordlist should have the size of 3
        assertEquals(3, wordlist.size());
    }

    @Test
    public void fetchRandomWord() throws FileNotFoundException
    {
        String randomWord1 = WordleApp.randomWordSelector("D:\\University_Of_St_Andrews\\Semester 2\\CS5031 - Software Engineering Practice\\Coursework\\P1-Wordle\\CS5031-P1-Wordle\\wordle\\src\\test\\resources\\wordlist-test.txt");
        assertNotNull(randomWord1);

        String randomWord2 = WordleApp.randomWordSelector("D:\\University_Of_St_Andrews\\Semester 2\\CS5031 - Software Engineering Practice\\Coursework\\P1-Wordle\\CS5031-P1-Wordle\\wordle\\src\\test\\resources\\wordlist-test.txt");
        assertNotNull(randomWord2);

    }

    //Test if the given two words are matching character by character
    @Test
    public void matchTestWord(){
        String wordOfTheDay = "fetch";
        String userInput = "fetch";
        boolean isMatch = WordleApp.matchUserInput(wordOfTheDay, userInput);
        assertTrue(isMatch);
    }
}
