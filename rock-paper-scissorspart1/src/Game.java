import java.util.Scanner;
public class Game {
    static Scanner sc = new Scanner(System.in);
    //rules of the game:
    //rock beats scissors
    //paper beats rock
    //scissors beats paper
    public static void main(String[] args) {
        boolean exit = false;
        //human v. computer
        do{
            determineWinner(generateMove(getUserChoice()), generateMove(getComputerChoice()));
            exit = playAgain();
        } while (!exit);

        //human v. human
        do {
            int userOneMove = getUserChoice();
            int userTwoMove = getUserChoice();
            determineWinner(generateMove(userOneMove), generateMove(userTwoMove));
            exit = playAgain();
        } while (!exit);

        //computer v. computer
        determineWinner(generateMove(getComputerChoice()), generateMove(getComputerChoice()));

    } //this closes the main method

    public static int getUserChoice(){
        boolean validChoice = false;
        int userChoice = 0;

        do{
            System.out.println("Please enter your move (rock, paper, or scissors): ");
            System.out.println("0: Rock");
            System.out.println("1: Paper");
            System.out.println("2: Scissors");
            userChoice = Integer.parseInt(sc.nextLine());
            if (userChoice == 0 || userChoice == 1 || userChoice == 2) {
                validChoice = true;
            } else {
                System.out.println("Error: invalid choice.");
            }
        }while(!validChoice);
        return userChoice;
    }

    public static int getComputerChoice(){
        //randomly generate a choice - computer choice
        return (int)Math.floor(Math.random() * 3);
    }

    public static String generateMove(int playerChoice) {
        String move;
        switch(playerChoice) {
            case 0:
                move = "rock";
                break;
            case 1:
                move = "paper";
                break;
            case 2:
                move = "scissors";
                break;
            default: //shouldn't happen
                move = null;
        }
        return move;
    }

    public static void determineWinner(String userChoice, String computerChoice) {
        //compare choices and determine a winner
        String graphic = "L O A D I N G ";
        for (int i = 0; i < graphic.length(); i++) {
            System.out.println(graphic.charAt(i));
        }

        if (computerChoice.equalsIgnoreCase(userChoice)) {
            System.out.printf("You both picked %s, it's a tie.%n", userChoice);
        } else if (computerChoice.equalsIgnoreCase("rock") && userChoice.equalsIgnoreCase("paper")) {
            System.out.printf("The computer picked %s, and you picked %s. You win!", computerChoice, userChoice);
        } else if (computerChoice.equalsIgnoreCase("paper") && userChoice.equalsIgnoreCase("scissors")) {
            System.out.printf("The computer picked %s, and you picked %s. You win!", computerChoice, userChoice);
        } else if (computerChoice.equalsIgnoreCase("scissors") && userChoice.equalsIgnoreCase("rock")) {
            System.out.printf("The computer picked %s, and you picked %s. You win!", computerChoice, userChoice);
        } else {
            System.out.printf("The computer picked %s, and you picked %s. You lose...", computerChoice, userChoice);
        }
    }

    public static boolean playAgain() {
        //Give user a way to exit game
        System.out.println("Would you like to play again? [y/n]?: ");
        String playAgain = sc.nextLine();

        if (playAgain.equalsIgnoreCase("n")) {
            System.out.println("Goodbye!");
            return true;
        } else {
            return false;
        }
    }

} //this closes the class
