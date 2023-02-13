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


     java -cp target/wordle-1.0-SNAPSHOT.jar stacs.wordle.WordleApp
     java -cp target/wordle-1.0-SNAPSHOT.jar stacs.wordle.WordleGui

The first command runs the game on command line where as the second code runs the application on a graphical interface

To run the test cases run the following command
-   `mvn test -Dtest=WordleAppTest`


# What is additonal from NYTimes wordle?
The game doesn't allow the user to enter same guess twice, improving their chances at guessing better.

## Code explanation
- `WordleApp.java` contains the code for command line execution of wordle
- `WordleGui.java` Contains the code needed for graphical interface. 
- `WordleService.java` has the logic for comparing the words against the selected random words for the day and scoring strategy.
- `DisplayMessages.java` This class contains all the methods needed for displaying text to commandline or GUI.
- `ConsoleTextColor.java` contains the color codes neeeded for displaying in command line .
- `WordList.txt` is the text file containing all logical 5 letter words in English.

