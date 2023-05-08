import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import java.util.List;

public class Hangman {

    public static Random random = new Random();
    public static Scanner scanner = new Scanner(System.in);

    public static char[] wordLetters;
    public static char[] placeholders;
    public static List<Character> guessedLetters = new ArrayList<Character>();
    public static List<Character> wrongLetters = new ArrayList<Character>();
    public static int noGuessesLeft = 6;
    public static String word = "";
    public static char guess;

    public static String[] words = {"ant", "baboon", "badger", "bat", "bear", "beaver", "camel",
    "cat", "clam", "cobra", "cougar", "coyote", "crow", "deer",
    "dog", "donkey", "duck", "eagle", "ferret", "fox", "frog", "goat",
    "goose", "hawk", "lion", "lizard", "llama", "mole", "monkey", "moose",
    "mouse", "mule", "newt", "otter", "owl", "panda", "parrot", "pigeon", 
    "python", "rabbit", "ram", "rat", "raven","rhino", "salmon", "seal",
    "shark", "sheep", "skunk", "sloth", "snake", "spider", "stork", "swan",
    "tiger", "toad", "trout", "turkey", "turtle", "weasel", "whale", "wolf",
    "wombat", "zebra"};

    public static String[] gallows = {"+---+\n" +
    "|   |\n" +
    "    |\n" +
    "    |\n" +
    "    |\n" +
    "    |\n" +
    "=========\n",

    "+---+\n" +
    "|   |\n" +
    "O   |\n" +
    "    |\n" +
    "    |\n" +
    "    |\n" +
    "=========\n",

    "+---+\n" +
    "|   |\n" +
    "O   |\n" +
    "|   |\n" +
    "    |\n" +
    "    |\n" +
    "=========\n",

    " +---+\n" +
    " |   |\n" +
    " O   |\n" +
    "/|   |\n" +
    "     |\n" +
    "     |\n" +
    " =========\n",

    " +---+\n" +
    " |   |\n" +
    " O   |\n" +
    "/|\\  |\n" + //if you were wondering, the only way to print '\' is with a trailing escape character, which also happens to be '\'
    "     |\n" +
    "     |\n" +
    " =========\n",

    " +---+\n" +
    " |   |\n" +
    " O   |\n" +
    "/|\\  |\n" +
    "/    |\n" +
    "     |\n" +
    " =========\n",

    " +---+\n" +
    " |   |\n" +
    " O   |\n" +
    "/|\\  |\n" + 
    "/ \\  |\n" +
    "     |\n" +
    " =========\n"};

    public static void main(String[] args) {


        //Initialize the word and wordLetters variables and placeholders
        word = RandomWord();
        wordLetters = WordLetters(word);
        InitializePlaceholders(wordLetters.length);
        
        // //Run the game
        // while(!GameIsOver())
        // {
            
        // }

        //TEST print out the word
        // System.out.println(wordLetters.length);
        System.out.println(word);

        // //TEST printout all the letters of the word
        // for(char c : wordLetters)
        //     System.out.println(c);

        // //TEST INPUT
        // guess = Guess();
        // System.out.println(guess);

        // //TEST CHECK
        //System.out.println("Guess was: " + CheckGuess());

        //TEST CHECK IF GAME IS OVER

        while(!GameIsOver())
        {
            PrintGame();
            UpdatePlaceholders(guess);
        }

        if(GameIsOver() && noGuessesLeft == 0)
        {
            System.out.println("RIP YOU LOST");
            scanner.close();
        }
        else if(GameIsOver() && noGuessesLeft > 0)
        {
            System.out.println("YAY! YOU GUESSED THE WORD CORRECTLY!");
            scanner.close();
        }
    }

    // Return a random word from the words array
    private static String RandomWord() { return words[random.nextInt(words.length)]; }

    // Return a char array that contains the letters of the given word
    private static char[] WordLetters(String _word) { return _word.toCharArray(); } 

    // Handle the system printing guess and takes the char input from the user
    private static char Guess()
    {
        System.out.print("Guess: ");
        String fullInput = scanner.next();

        if(fullInput.length() < 0)
            System.out.println("INVALID: NO LETTER PROVIDED");
        else if(fullInput.length() > 1)
            System.out.println("INVALID: MORE THAN ONE LETTER PROVIDED");
        else
        {
            if(!CheckGuess())
            {
                noGuessesLeft--;
                wrongLetters.add(guess);
            }

            return fullInput.charAt(0);
        }
        
        return '?';
    }

    // Checks whether or not the guess was correct and if it was guessed before
    private static boolean CheckGuess()
    {
        for (char c : wordLetters)
        {
            if(guess == c)
            {
                if(guessedLetters.contains(guess))
                {
                    System.out.println("YOU ALREADY CORRECTLY GUESSED THIS LETTER!");
                }
                else
                {
                    guessedLetters.add(guess);
                    System.out.println("YOU GUESSED A NEW LETTER!");
                    UpdatePlaceholders(guess);
                }
                return true;
            }
        }
        return false;
    }

    // Check if all letters are guessed
    private static boolean AllLettersGuessed()
    {
        for(char c : wordLetters)
        {
            if(!guessedLetters.contains(c))
                return false;
        }
        return true;
    }

    // Check if everything is guessed or if the amount of guesses ran out
    private static boolean GameIsOver()
    {
        if(noGuessesLeft == 0 || AllLettersGuessed())
            return true;
        else
            return false;
    }

    
    // Fill placeholders taking number of letters
    private static void InitializePlaceholders(int _noLetters)
    {
        placeholders = new char[_noLetters];
        for(int i = 0; i < placeholders.length; i++)
        placeholders[i] = '_';
    }
    
    // Print the whole game on the screen
    private static void PrintGame()
    {
        // Print gallow
        System.out.println(gallows[6-noGuessesLeft] + "\n");
        // Print Word: _ _ _
        System.out.println("Word:   " + CurrentWordProgress() + "\n");
        // Print Misses: l, e, t, t, e, r, s
        System.out.println("Misses:   " + WrongGuesses() + "\n");
        // Print Guess : <input>
        guess = Guess();
    }

    // Update the placeholders
    private static void UpdatePlaceholders(char _guess)
    {
        // Check in what index the _guess is supposed to be, could be multiple
        // Replace the placeholders with the _guess char
        for(int i = 0; i < wordLetters.length; i++)
        {
            if(wordLetters[i] == _guess)
            {
                if(i == 0)
                    placeholders[0] = _guess;
                else
                {
                    //TODO BECAUSE THIS DOESN'T MAKE SENSE, SAVE THE INDEX AND USE THAT
                    placeholders[i + 1] = _guess;
                }
            }
        }
    }

    // Turn the placeholders array into an actual displayable string seperated by spaces
    private static String CurrentWordProgress()
    {
        int phIndex = 0;
        char[] tempArray = new char[placeholders.length * 2 + 1];
        for(int i = 0; i < placeholders.length * 2; i+=2)
        {
            tempArray[i] = placeholders[phIndex];

            if(i + 1 > placeholders.length * 2)
                continue;

            tempArray[i + 1] = ' ';
            phIndex++;
        }
        return new String(tempArray);
    }

    private static String WrongGuesses()
    {
        String endString = "";
        for(int i = 0; i < wrongLetters.size(); i++)
            endString += wrongLetters.get(i);

        return endString;
    }

}





