import java.util.Scanner;
import java.math.*;
public class Game {
    //rules of the game:
    //rock beats scissors
    //paper beats rock
    //scissors beats paper
    public static void main(String[] args) {
        //take input from user - user choice
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter your move (rock, paper, or scissors): ");
        String userChoice = sc.nextLine();

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

    } //this closes the main method
} //this closes the class
