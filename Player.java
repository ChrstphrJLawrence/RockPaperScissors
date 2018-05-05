import java.util.Random;

public class Player {
    private int points;
    private Choice choice;
    private boolean computer;

    Player(boolean computer) {
        this.computer = computer;
        this.choice = Choice.ROCK;   //TODO: maybe don't do this
    }

    public Choice getChoice() {
        return choice;
    }

    public void setChoice(Choice choice) {
        if (choice.equals(Choice.ROCK)) {
            this.choice = Choice.ROCK;
        }
        else if (choice.equals(Choice.PAPER)) {
            this.choice = Choice.PAPER;
        }
        else if (choice.equals(Choice.SCISSORS)) {
            this.choice = Choice.SCISSORS;
        }
        else {
            System.out.println("WTF");   //TODO: Handle
        }
    }

    public void setChoice() {
        if (computer) {
            Random random = new Random();
            int selection = random.nextInt(3);
            if (selection == 0) {
                this.choice = Choice.ROCK;
            }
            else if (selection == 1) {
                this.choice = Choice.PAPER;
            }
            else if (selection == 2) {
                this.choice = Choice.SCISSORS;
            }
            else {
                System.out.println("Something went wrong."); //TODO: Improve
            }
        }
        else {
            System.out.println("Must send input to set choice for non-computer player.");  //TODO: Improve
        }
    }

    public int getPoints() {
        return points;
    }

    public void addPoint() {
        this.points += 1;
    }
}
