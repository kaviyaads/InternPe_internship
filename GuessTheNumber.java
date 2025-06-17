import java.util.*; // Importing Scanner for input and Random for random number generation

public class GuessTheNumber {

    // Shared Scanner and Random objects for use throughout the program
    static Scanner scanner = new Scanner(System.in);
    static Random random = new Random();

    public static void main(String[] args) {
        System.out.println("🎮 Welcome to the Advanced Guess the Number Game!");

        boolean playAgain;
        do {
            // Step 1: Choose difficulty level
            int level = selectDifficulty();

            // Step 2: Determine range and attempts based on difficulty
            int maxNumber = getMaxNumber(level);
            int maxAttempts = getMaxAttempts(level);

            // Step 3: Generate the random number to be guessed
            int numberToGuess = random.nextInt(maxNumber) + 1;

            System.out.println("\nI have selected a number between 1 and " + maxNumber);
            System.out.println("You have " + maxAttempts + " attempts. Good luck!\n");

            int attemptsUsed = 0;
            boolean guessedCorrectly = false;

            // Step 4: Loop through user attempts
            while (attemptsUsed < maxAttempts) {
                int guess = getValidGuess(maxNumber); // Get user's guess
                attemptsUsed++;

                if (guess == numberToGuess) {
                    guessedCorrectly = true;
                    System.out.println("🎉 Congratulations! You guessed the number in " + attemptsUsed + " attempts.");
                    break;
                } else {
                    giveHint(guess, numberToGuess); // Provide hint if guess is wrong
                    System.out.println("Attempts left: " + (maxAttempts - attemptsUsed));
                }
            }

            // Step 5: Show correct number if not guessed
            if (!guessedCorrectly) {
                System.out.println("😢 You've used all attempts! The correct number was: " + numberToGuess);
            }

            // Step 6: Calculate and show score
            int score = calculateScore(guessedCorrectly, maxAttempts, attemptsUsed);
            System.out.println("Your score: " + score + "\n");

            // Step 7: Ask to play again
            playAgain = askToPlayAgain();
        } while (playAgain); // Repeat if player wants to play again

        System.out.println("👋 Thanks for playing. Goodbye!");
    }

    // Allows the user to select a difficulty level
    static int selectDifficulty() {
        System.out.println("Select Difficulty Level:");
        System.out.println("1. Easy   (1-50, 10 attempts)");
        System.out.println("2. Medium (1-100, 7 attempts)");
        System.out.println("3. Hard   (1-200, 5 attempts)");
        System.out.print("Enter choice (1-3): ");

        int level;
        while (true) {
            try {
                level = Integer.parseInt(scanner.nextLine());
                if (level >= 1 && level <= 3) break; // Valid input
                else System.out.print("Invalid input. Choose 1, 2, or 3: ");
            } catch (Exception e) {
                System.out.print("Please enter a valid number: ");
            }
        }
        return level;
    }

    // Returns max number based on difficulty level
    static int getMaxNumber(int level) {
        return switch (level) {
            case 1 -> 50;   // Easy
            case 2 -> 100;  // Medium
            case 3 -> 200;  // Hard
            default -> 100;
        };
    }

    // Returns number of attempts based on difficulty
    static int getMaxAttempts(int level) {
        return switch (level) {
            case 1 -> 10;  // Easy
            case 2 -> 7;   // Medium
            case 3 -> 5;   // Hard
            default -> 7;
        };
    }

    // Gets and validates the user's guess input
    static int getValidGuess(int maxNumber) {
        System.out.print("Enter your guess: ");
        while (true) {
            try {
                int guess = Integer.parseInt(scanner.nextLine());
                if (guess >= 1 && guess <= maxNumber) return guess; // Valid guess
                else System.out.print("Please enter a number between 1 and " + maxNumber + ": ");
            } catch (Exception e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }

    // Gives feedback on how close the guess is
    static void giveHint(int guess, int numberToGuess) {
        int diff = Math.abs(guess - numberToGuess);
        if (diff <= 5) {
            System.out.println("🔥 Very Hot!");
        } else if (diff <= 10) {
            System.out.println("🌡️ Hot");
        } else if (diff <= 20) {
            System.out.println("🌤️ Warm");
        } else {
            System.out.println("❄️ Cold");
        }

        // Provide directional hint
        if (guess < numberToGuess) {
            System.out.println("Hint: Try a higher number.");
        } else {
            System.out.println("Hint: Try a lower number.");
        }
    }

    // Calculates score based on remaining attempts
    static int calculateScore(boolean guessedCorrectly, int maxAttempts, int attemptsUsed) {
        if (!guessedCorrectly) return 0; // No score if failed
        return (maxAttempts - attemptsUsed + 1) * 10; // Higher score for fewer attempts
    }

    // Asks the user whether they want to play again
    static boolean askToPlayAgain() {
        System.out.print("Do you want to play again? (yes/no): ");
        String response = scanner.nextLine().trim().toLowerCase();
        return response.equals("yes") || response.equals("y");
    }
}
