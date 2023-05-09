import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import javax.swing.plaf.synth.Region;

import java.util.List;

public class Hangman {

    /* #region static variables */
    public static char[] placeholders;
    public static List<Character> guessedLetters = new ArrayList<Character>();
    public static char guess;

    public static String[] words = { "ant", "baboon", "badger", "bat", "bear", "beaver", "camel",
            "cat", "clam", "cobra", "cougar", "coyote", "crow", "deer", "dog", "donkey", "duck", "eagle",
            "ferret", "fox", "frog", "goat", "goose", "hawk", "lion", "lizard", "llama", "mole", "monkey",
            "moose", "mouse", "mule", "newt", "otter", "owl", "panda", "parrot", "pigeon", "python",
            "rabbit", "ram", "rat", "raven", "rhino", "salmon", "seal", "shark", "sheep", "skunk", "sloth",
            "snake", "spider", "stork", "swan", "tiger", "toad", "trout", "turkey", "turtle", "weasel",
            "whale", "wolf", "wombat", "zebra" };

    public static String[] gallows = {
            "+---+\n" +
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
                    "/|\\  |\n" +
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
                    " =========\n"
    };
    /* #endregion */

    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String word = RandomWord();
        char[] wordLetters = WordLetters(word);
        placeholders = InitializePlaceholders(word.length());
        char[] wrongGuesses = new char[6];
        int noGuessesLeft = 6;

        while (noGuessesLeft != 0) {
            PrintGame(noGuessesLeft, wrongGuesses);
            Guess(scanner);

            if (CheckGuess(wordLetters))
                UpdatePlaceholders(word);
            else {
                wrongGuesses[6 - noGuessesLeft] = guess;
                noGuessesLeft--;
            }

            if (AllLettersGuessed(wordLetters, gallows, noGuessesLeft))
                break;
        }

        if (noGuessesLeft == 0) {
            System.out.print(gallows[6]);
            System.out.print("\n RIP YOU LOST!\n");
            System.out.print("The answer was:   " + word + "\n");
        }
        scanner.close();
    }

    // Returns a random word from the words array
    public static String RandomWord() 
    {
        Random random = new Random();
        return words[random.nextInt(words.length)];
    }

    // Returns a char array that contains the letters of the given word
    public static char[] WordLetters(String _word) { return _word.toCharArray(); }

    // Initializes placeholders based on number of letters
    public static char[] InitializePlaceholders(int _noLetters) 
    {
        char[] placeholders = new char[_noLetters];
        for (int i = 0; i < placeholders.length; i++)
            placeholders[i] = '_';
        return placeholders;
    }

    // Handles the printing of guess and takes the char input from the user
    public static void Guess(Scanner _scanner) {
        System.out.print("Guess:    ");
        guess = _scanner.next().charAt(0);
        System.out.print("\n");
    }

    // Checks whether or not the guess was correct and if it was guessed before
    public static boolean CheckGuess(char[] _wordLetters) {
        for (char c : _wordLetters) {
            if (guess == c) {
                if (guessedLetters.contains(guess))
                    System.out.print("\nYOU ALREADY CORRECTLY GUESSED THIS LETTER!\n");
                else {
                    guessedLetters.add(guess);
                    System.out.print("\nYOU GUESSED A NEW LETTER!\n");
                }
                return true;
            }
        }
        System.out.print("\nYOU GUESSED WRONG!\n");
        return false;
    }

    // Checks if all letters are guessed
    public static boolean AllLettersGuessed(char[] _wordLetters, String[] _gallows, int _noGuessesLeft) {
        if (Arrays.equals(placeholders, _wordLetters)) {
            System.out.print(_gallows[6 - _noGuessesLeft] + "\n");
            PrintPlaceholders();
            System.out.print("YAAYY YOU DID IT! \n");
            return true;
        }
        return false;
    }

    // Updates the placeholders
    public static void UpdatePlaceholders(String _word) {
        for (int i = 0; i < _word.length(); i++) {
            if (_word.charAt(i) == guess)
            placeholders[i] = guess;
        }
    }
    
    // Prints most the game in the terminal, which contains the current gallow, the
    // word placeholders and the wrong guesses
    public static void PrintGame(int _noGuessesLeft, char[] _wrongGuesses) {
        System.out.print(gallows[6 - _noGuessesLeft] + "\n");
        PrintPlaceholders();
        PrintWrongGuesses(_wrongGuesses);
    }

    // Prints the placeholders array seperated by spaces
    public static void PrintPlaceholders() {
        System.out.print("Word:   ");

        for (int i = 0; i < placeholders.length; i++)
            System.out.print(" " + placeholders[i]);

        System.out.print("\n");
    }

    // Prints the wrong guesses array seperated by spaces
    public static void PrintWrongGuesses(char[] _wrongGuesses) {
        System.out.print("Misses:   ");

        for (int i = 0; i < _wrongGuesses.length; i++)
            System.out.print(_wrongGuesses[i] + " ");

        System.out.print("\n");
    }
}
