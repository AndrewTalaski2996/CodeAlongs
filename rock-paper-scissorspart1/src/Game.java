import java.util.Scanner;
import java.math.*;
public class Game {
    //rules of the game:
    //rock beats scissors
    //paper beats rock
    //scissors beats paper
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String userChoice = null;
        boolean exit = false;

        do{
            //variable for our validChoice
            boolean validChoice = false;
            do{
                System.out.println("Please enter your move (rock, paper, or scissors): ");
                userChoice = sc.nextLine();
                if (userChoice.equalsIgnoreCase("rock") || userChoice.equalsIgnoreCase("paper") || userChoice.equalsIgnoreCase("scissors")) {
                    validChoice = true;
                } else {
                    System.out.println("Error: invalid choice.");
                }
            }while(!validChoice); //loop will run as long as validChoice is false



            //randomly generate a choice - computer choice
            int random = (int)Math.floor(Math.random() * 3);
            String computerChoice = null;

            switch(random) {
                case 0:
                    computerChoice = "rock";
                    break;
                case 1:
                    computerChoice = "paper";
                    break;
                case 2:
                    computerChoice = "scissors";
                    break;
                default: //shouldn't happen
                    computerChoice = null;
            }

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

            //Give user a way to exit game
            System.out.println("Would you like to play again? [y/n]?: ");
            String playAgain = sc.nextLine();

            if (playAgain.equalsIgnoreCase("n")) {
                System.out.println("Goodbye!");
                exit = true;
            } else {
                exit = false;
            }
        } while (!exit);

    } //this closes the main method
} //this closes the class
