import java.util.Random;

public class Player {
    private int points;
    private Choice choice;
    private boolean computer;
    private String name;

    public Player(String name, boolean computer) {
        this.name = name;
        this.computer = computer;
        this.choice = Choice.ROCK;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
            throw new IllegalArgumentException("Supported Choices: Paper, Rock, Scissors.");
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
            else {
                this.choice = Choice.SCISSORS;
            }
        }
        else {
            throw new IllegalStateException("Must send input to set choice for non-computer player.");
        }
    }

    public int getPoints() {
        return points;
    }

    public void addPoint() {
        this.points += 1;
    }
}
