------

## A Wordle game built in Maven Java and Swing for GUI. 

------

# How does it work?
The wordle game takes upto six inputs from users and matches with the selected word for the day. The list of all recognized words are placed in the wordlist.txt file.
The game can be run using command ine and grapical user interface. 

# Installation

The game can be run using the following commands.

1. The needs to be packaged using 
    `java clean package`

2. This creates the executable JAR file which can be run using the following commands 

 - Game over the command line 
    ```` java -cp target/wordle-1.0-SNAPSHOT.jar stacs.wordle.WordleApp````
 - Game over Graphical Interface
    ```` java -cp target/wordle-1.0-SNAPSHOT.jar stacs.wordle.WordleGui ````

To run the test cases run the following command
-   `mvn test -Dtest=WordleAppTest`

##Scoring Strategy

Since every word is unique, the scoring is based on the number of correct letters guessed and the attempts taken to guess the correct word.
The score is calculated for each number of tries and correct letters guessed in each.

1. All green (10 points each) - Each try can fetch a total of 50 points if all letters are correct
2. All yellow (5 points each) - Each try can fetch a total of 25 points
3. All tries have a total of 20% weight overall multiplied by the descending number of tries that they take to guess.

Example - If a user gets all partial (yellow) on the first try and guesses all in the second then the total score:
1st try - 25 points -> 10%
2nd try - 50 points -> 80%
Total score percentage (score) - 90%

![image](https://github.com/Mohamed-rilwan/Word-of-Wordle/assets/44545353/abd030a9-943a-44c8-ab5e-ef7bdab16d67)
The figure shows game statistics after all guesses


# What is additional from the NYTimes Wordle?
The system does not allow the user to enter a guess word twice. This is currently allowed in Wordle, but in the implementation for this coursework, it is prevented, as removing this would allow the user to make a better guess, given there are only six tries.

#Edge case
1. If the character is already in the other position and we entered it twice, what must happen
Example: In the following case the letter “A” was used in multiple places, but was considered to be partial only in one position and not in both as the final word has only one “A”
![image](https://github.com/Mohamed-rilwan/Word-of-Wordle/assets/44545353/087e26f4-d309-4fae-ab3e-24fadba20b7e)
This has been tackled after successive iterations of the logic refactoring.

## Code explanation
- `WordleApp.java` contains the code for the command line execution of Wordle
- `WordleGui.java` Contains the code needed for a graphical interface. 
- `WordleService.java` has the logic for comparing the words against the selected random words for the day and scoring strategy.
- `DisplayMessages.java` This class contains all the methods needed for displaying text to the command line or GUI.
- `ConsoleTextColor.java` contains the color codes needed for displaying in the command line.
- `WordList.txt` is the text file containing all logical 5-letter words in English.

  ## Game Screenshots
![image](https://github.com/Mohamed-rilwan/Word-of-Wordle/assets/44545353/f57d842f-800c-4fc3-ba89-aa5fde99a514)

  

