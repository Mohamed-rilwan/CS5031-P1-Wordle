package stacs.wordle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class WordleAppTest
{
    public final String testFilePath = "src\\test\\resources\\wordlist-test.txt";
    public final String wordFilePath = "src\\main\\resources\\wordlist.txt";
    private WordleService wordleService;

    @BeforeEach
    public void setUp() {
        wordleService = new WordleService();
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
        String matchResult = wordleService.matchWord(wordOfTheDay, userInput);
        assertEquals( matchResult,"ggggg");

        String wordOfTheDay1 = "fetch";
        String userInput1 = "feSeh";
        String matchResult1 = wordleService.matchWord(wordOfTheDay1, userInput1);
        assertNotEquals( matchResult1,"ggggg");
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


    /**
     * The following test checks if a character is available in only one position as expected
     * The earlier scenarios would have produced a result where the presence of character 'e' in the first place as a partial guess
     * and the presence of character 'e' in the last place as an exact guess
     * But the required result will be to identify the first postiion of 'e' as invalid and the last as valid position
     */
    @Test
    public void twoIncorrectLetter() {
        String testWord = "exile";
        String wordOfTheWord = "cache";

        String matchResult = wordleService.matchWord(wordOfTheWord, testWord);

        //Previous check result
        assertNotEquals("yrrrg", matchResult);

        //New expected result
        assertEquals(matchResult,"rrrrg");

    }

    /**
     * The following test validated the scoring strategy
     */
    @Test
    public void validateScoring() {

        String guessResult = "ggggg";
        String guessResult1 = "gryrg";
        String guessResult2 = "yryry";


        int score = wordleService.scoreCalculator(guessResult, 1);
        int score1 = wordleService.scoreCalculator(guessResult1, 1);
        int score2 = wordleService.scoreCalculator(guessResult2, 1);
        int score3 = wordleService.scoreCalculator(guessResult, 3);

        int expectedScoreResult = 20;
        int expectedScoreResult1 = 10;
        int expectedScoreResult2 = 6;
        int expectedScoreResult3 = 60;

        assertEquals(score, expectedScoreResult);
        assertEquals(score1, expectedScoreResult1);
        assertEquals(score2, expectedScoreResult2);
        assertEquals(score3, expectedScoreResult3);


        assertNotEquals(score,expectedScoreResult1);

    }
}
