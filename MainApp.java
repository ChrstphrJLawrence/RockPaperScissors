import State.*;
import processing.core.PApplet;
import processing.core.PFont;


public class MainApp extends PApplet {
    private PFont lazer84;
    private MenuState menuState;
    private GameState gameState;
    private OnePlayerState onePlayerState;
    private TwoPlayerState twoPlayerState;
    private FightState fightState;
    private Player player1;
    private Player player2;
    private Player computerPlayer;
    private int tieMonsterPoints;
    private int onePlayerCounter, twoPlayerCounter;

    public static void main(String[] args) {
        PApplet.main("MainApp", args);
    }

    public void settings() {

        size(400, 500);
    }

    private void background() {
        background(255);
    }

    private void pink() {
        fill(240, 144, 192);
    }

    private void brightBlue() {
        fill(24, 216, 240);
    }

    private void red() {
        fill(237, 20, 92);
    }

    public void setup() {
        background();
        lazer84 = createFont("Lazer84.ttf", 62, true);
        textAlign(CENTER);
        gameState = GameState.MENU;
        menuState = MenuState.ONE_PLAYER;
        onePlayerState = OnePlayerState.ROCK;
        twoPlayerState = TwoPlayerState.WAIT;
        fightState = FightState.PLAY_AGAIN;
        player1 = new Player("Player 1", false);
        player2 = new Player("Player 2", false);
        computerPlayer = new Player("COM", true);
        computerPlayer.setChoice();
        onePlayerCounter = 0;
        twoPlayerCounter = 0;
    }

    public void draw() {
        if (gameState.equals(GameState.MENU)) {
            menuScreen();
        } else if (gameState.equals(GameState.ONE_PLAYER)) {
            onePlayerGame();
        } else if (gameState.equals(GameState.TWO_PLAYERS)) {
            twoPlayerGame();
        } else if (gameState.equals(GameState.QUIT)) {
            exit();
        }
    }

    public void keyPressed() {
        if (gameState.equals(GameState.MENU)) {
            menuControls();
        }
        else if (!gameState.equals(GameState.MENU) &&
                onePlayerState.equals(OnePlayerState.FIGHT) || twoPlayerState.equals(TwoPlayerState.FIGHT)) {
            fightControls();
        }
        else if (gameState.equals(GameState.ONE_PLAYER)) {
            onePlayerControls();
        }
        else if (gameState.equals(GameState.TWO_PLAYERS)) {
            twoPlayerControls();
        }
    }

    private void menuScreen() {
        background();
        textFont(lazer84, 62);

        fill(0);
        text("Rock", width / 2 + 5, 70 + 5);
        text("Paper", width / 2 + 5, 160 + 5);
        text("Scissors", width / 2 + 5, 250 + 5);

        brightBlue();
        text("Rock", width / 2, 70);
        text("Paper", width / 2, 160);
        text("Scissors", width / 2, 250);


        textFont(lazer84, 32);
        if (menuState.equals(MenuState.ONE_PLAYER)) {
            menuButton("1 Player Game", 290, true);
            menuButton("2 Player Game", 360, false);
            menuButton("Quit", 430, false);
        } else if (menuState.equals(MenuState.TWO_PLAYERS)) {
            menuButton("1 Player Game", 290, false);
            menuButton("2 Player Game", 360, true);
            menuButton("Quit", 430, false);
        } else if (menuState.equals(MenuState.QUIT)) {
            menuButton("1 Player Game", 290, false);
            menuButton("2 Player Game", 360, false);
            menuButton("Quit", 430, true);
        }
    }


    private void gameScreen(Player first, Player second) {
        background();
        textFont(lazer84, 100);
        textAlign(CENTER);

        fill(0);
        text("VS.", width / 2 + 5 + 5, 185 + 5);
        pink();
        text("VS.", width / 2 + 5, 185);

        textFont(lazer84, 78);
        fill(0);
        text("BATTLE!", width / 2 + 5, 75 + 5);
        brightBlue();
        text("BATTLE!", width / 2, 75);

        textFont(lazer84, 28);
        textAlign(LEFT);
        fill(0);
        text(first.getName(), 20 + 3, 155 + 3);
        brightBlue();
        text(first.getName(), 20, 155);
        textAlign(RIGHT);
        fill(0);
        text(second.getName(), width - 20 + 3, 155 + 3);
        brightBlue();
        text(second.getName(), width - 20, 155);
        textAlign(CENTER);
    }

    private void twoPlayerGame() {
        gameScreen(player1, player2);

        twoPlayerGameButton("ROCK", 200, true, 'Q');
        twoPlayerGameButton("PAPER", 300, true, 'A');
        twoPlayerGameButton("SCISSORS", 400, true, 'Z');
        twoPlayerGameButton("ROCK", 200, false, 'O');
        twoPlayerGameButton("PAPER", 300, false, 'K');
        twoPlayerGameButton("SCISSORS", 400, false, 'M');

        switch(twoPlayerState) {
            case WAIT:
                brightBlue();
                rect(width / 2 - 15, 260, 28, 28, 5);
                fill(255);
                rect(width / 2 + 2 - 15, 260 + 2, 24, 24, 5);
                red();
                textAlign(RIGHT);
                textFont(lazer84, 16);
                text("S", width / 2 + 2, 260 + 20);

                textFont(lazer84, 18);
                textAlign(CENTER);
                fill(0);
                text("PRESS", width / 2 + 2, 246 + 2);
                brightBlue();
                text("PRESS", width / 2, 246);

                fill(0);
                text("TO\nSTART", width / 2 + 2, 310 + 2);
                brightBlue();
                text("TO\nSTART", width / 2, 310);
                break;

            case THREE:
                textFont(lazer84, 82);
                fill(0);
                text("3", width / 2 + 20 + 5, 300 + 5);
                brightBlue();
                text("3", width / 2 + 20, 300);
                break;

            case TWO:
                textFont(lazer84, 82);
                fill(0);
                text("2", width / 2 + 20 + 5, 300 + 5);
                brightBlue();
                text("2", width / 2 + 20, 300);
                break;

            case ONE:
                textFont(lazer84, 82);
                fill(0);
                text("1", width / 2 + 20 + 5, 300 + 5);
                brightBlue();
                text("1", width / 2 + 20, 300);
                break;

            case FIGHT:
                resultScreen(player1, player2);
        }
        int speed = 60;
        if (twoPlayerCounter == 0) {
            twoPlayerState = TwoPlayerState.WAIT;
        }
        else if (twoPlayerCounter < speed && twoPlayerCounter > 0) {
            twoPlayerState = TwoPlayerState.THREE;
        }
        else if (twoPlayerCounter < speed * 2) {
            twoPlayerState = TwoPlayerState.TWO;
        }
        else if (twoPlayerCounter < speed * 3) {
            twoPlayerState = TwoPlayerState.ONE;
        }
        else if (twoPlayerCounter < speed * 4) {
            twoPlayerState = TwoPlayerState.FIGHT;
        }
        if (!twoPlayerState.equals(TwoPlayerState.WAIT) && !twoPlayerState.equals(TwoPlayerState.FIGHT)) {
            twoPlayerCounter++;
        }
    }


    private void onePlayerGame() {
        gameScreen(player1, computerPlayer);

        if (onePlayerState.equals(OnePlayerState.ROCK)) {
            gameButton("ROCK", 240, true, true);
            gameButton("PAPER", 320, false, true);
            gameButton("SCISSORS", 400, false, true);
            player1.setChoice(Choice.ROCK);
        }
        else if (onePlayerState.equals(OnePlayerState.PAPER)) {
            gameButton("ROCK", 240, false, true);
            gameButton("PAPER", 320, true, true);
            gameButton("SCISSORS", 400, false, true);
            player1.setChoice(Choice.PAPER);
        }
        else if (onePlayerState.equals(OnePlayerState.SCISSORS)) {
            gameButton("ROCK", 240, false, true);
            gameButton("PAPER", 320, false, true);
            gameButton("SCISSORS", 400, true, true);
            player1.setChoice(Choice.SCISSORS);
        }
        else if (onePlayerState.equals(OnePlayerState.FIGHT)) {
            resultScreen(player1, computerPlayer);
        }


        if (!onePlayerState.equals(OnePlayerState.FIGHT)) {
            if (computerPlayer.getChoice().equals(Choice.ROCK)) {
                gameButton("ROCK", 240, true, false);
                gameButton("PAPER", 320, false, false);
                gameButton("SCISSORS", 400, false, false);
                computerPlayer.setChoice(Choice.ROCK);
            }
            else if (computerPlayer.getChoice().equals(Choice.PAPER)) {
                gameButton("ROCK", 240, false, false);
                gameButton("PAPER", 320, true, false);
                gameButton("SCISSORS", 400, false, false);
                computerPlayer.setChoice(Choice.PAPER);
            }
            else if (computerPlayer.getChoice().equals(Choice.SCISSORS)) {
                gameButton("ROCK", 240, false, false);
                gameButton("PAPER", 320, false, false);
                gameButton("SCISSORS", 400, true, false);
                computerPlayer.setChoice(Choice.SCISSORS);
            }
            if (onePlayerCounter > 35) {
                computerPlayer.setChoice();
                onePlayerCounter = 0;
            }
            onePlayerCounter++;
        }
    }

    private void resultScreen(Player first, Player second) {
        background();

        String winner = determineWinner(first, second);

        textFont(lazer84, 82);
        textAlign(CENTER);
        fill(0);
        text("WINS!", width/2 + 5, 300 + 5);
        brightBlue();
        text("WINS!", width/2, 300);

        textFont(lazer84, 48);
        fill(0);
        text(winner, width/2 + 5, 220 + 5);
        pink();
        text(winner, width/2, 220);


        String winnerChoice = "";
        String verb = "";
        String loserChoice = "";
        if (first.getChoice().equals(Choice.ROCK) && second.getChoice().equals(Choice.SCISSORS) ||
                second.getChoice().equals(Choice.ROCK) && first.getChoice().equals(Choice.SCISSORS)) {
            winnerChoice = "ROCK";
            verb = "SMASHES";
            loserChoice = "SCISSORS";
        }
        else if (first.getChoice().equals(Choice.PAPER) && second.getChoice().equals(Choice.ROCK) ||
                second.getChoice().equals(Choice.PAPER) && first.getChoice().equals(Choice.ROCK)) {
            winnerChoice = "PAPER";
            verb = "COVERS";
            loserChoice = "ROCK";
        }
        else if (first.getChoice().equals(Choice.SCISSORS) && second.getChoice().equals(Choice.PAPER) ||
                second.getChoice().equals(Choice.SCISSORS) && first.getChoice().equals(Choice.PAPER)) {
            winnerChoice = "SCISSORS";
            verb = "CUT";
            loserChoice = "PAPER";
        }
        else {
            winnerChoice = first.getChoice().name();
            verb = "TIES";
            loserChoice = second.getChoice().name();
        }

        textFont(lazer84, 42);
        textAlign(LEFT);
        fill(0);
        text(winnerChoice, 25 + 5, 50 + 5);
        brightBlue();
        text(winnerChoice, 25, 50);

        textAlign(CENTER);
        fill(0);
        text(verb, width / 2 + 5, 100 + 5);
        brightBlue();
        text(verb, width / 2, 100);

        textAlign(RIGHT);
        fill(0);
        text(loserChoice, width - 25 + 5, 150 + 5);
        brightBlue();
        text(loserChoice, width - 25, 150);


        if (fightState.equals(FightState.MAIN_MENU)) {
            menuButton("Play Again", 350, false);
            menuButton("Main Menu", 420, true);
        }
        else if (fightState.equals(FightState.PLAY_AGAIN)) {
            menuButton("Play Again", 350, true);
            menuButton("Main Menu", 420, false);
        }
    }

    private String determineWinner(Player player1, Player player2) {
        if (player1.getChoice().equals(player2.getChoice())) {
            tieMonsterPoints++;
            return "Tie Monster";
        }
        if (player1.getChoice().equals(Choice.PAPER) && player2.getChoice().equals(Choice.ROCK) ||
                player1.getChoice().equals(Choice.ROCK) && player2.getChoice().equals(Choice.SCISSORS) ||
                player1.getChoice().equals(Choice.SCISSORS) && player2.getChoice().equals(Choice.PAPER)) {
            player1.addPoint();
            return player1.getName();
        }
        else {
            player2.addPoint();
            return player2.getName();
        }
    }

    private void menuControls() {
        if (key == CODED && keyCode == UP) {
            switch (menuState) {
                case ONE_PLAYER:
                    menuState = MenuState.QUIT;
                    break;
                case TWO_PLAYERS:
                    menuState = MenuState.ONE_PLAYER;
                    break;
                case QUIT:
                    menuState = MenuState.TWO_PLAYERS;
                    break;
            }
        }
        if (key == CODED && keyCode == DOWN) {
            switch (menuState) {
                case ONE_PLAYER:
                    menuState = MenuState.TWO_PLAYERS;
                    break;
                case TWO_PLAYERS:
                    menuState = MenuState.QUIT;
                    break;
                case QUIT:
                    menuState = MenuState.ONE_PLAYER;
                    break;
            }
        }

        if ((key == CODED && keyCode == RIGHT) || key == ENTER || key == RETURN) {
            switch(menuState) {
                case ONE_PLAYER: gameState = GameState.ONE_PLAYER;
                    break;
                case TWO_PLAYERS: gameState = GameState.TWO_PLAYERS;
                    break;
                case QUIT: gameState = GameState.QUIT;
                    break;
            }
        }
    }

    private void fightControls() {
        if (key == CODED && keyCode == UP || keyCode == DOWN) {
            switch (fightState) {
                case PLAY_AGAIN:
                    fightState = FightState.MAIN_MENU;
                    break;
                case MAIN_MENU:
                    fightState = FightState.PLAY_AGAIN;
                    break;
            }
        }
        if ((key == CODED && keyCode == RIGHT) || key == ENTER || key == RETURN) {
            switch (fightState) {
                case PLAY_AGAIN:
                    if (gameState.equals(GameState.ONE_PLAYER)) {
                        gameState = GameState.ONE_PLAYER;
                        onePlayerState = OnePlayerState.ROCK;
                        computerPlayer.setChoice();
                        onePlayerCounter = 0;
                    }
                    else if (gameState.equals(GameState.TWO_PLAYERS)) {
                        gameState = GameState.TWO_PLAYERS;
                        twoPlayerCounter = 0;
                        twoPlayerState = TwoPlayerState.WAIT;
                        onePlayerState = OnePlayerState.ROCK;
                    }
                    break;
                case MAIN_MENU:
                    setup();
                    break;
            }
        }
    }

    private void onePlayerControls() {
        if (key == CODED && keyCode == UP) {
            switch (onePlayerState) {
                case ROCK:
                    onePlayerState = OnePlayerState.SCISSORS;
                    break;
                case PAPER:
                    onePlayerState = OnePlayerState.ROCK;
                    break;
                case SCISSORS:
                    onePlayerState = OnePlayerState.PAPER;
                    break;
            }
        }
        if (key == CODED && keyCode == DOWN) {
            switch (onePlayerState) {
                case ROCK:
                    onePlayerState = OnePlayerState.PAPER;
                    break;
                case PAPER:
                    onePlayerState = OnePlayerState.SCISSORS;
                    break;
                case SCISSORS:
                    onePlayerState = OnePlayerState.ROCK;
                    break;
            }
        }
        if ((key == CODED && keyCode == RIGHT) || key == ENTER || key == RETURN) {
            onePlayerState = OnePlayerState.FIGHT;
        }
    }

    private void twoPlayerControls() {
        if (key == 'S' || key == 's') {
            twoPlayerCounter++;
        }
        if (key == 'Q' || key == 'q') {
            player1.setChoice(Choice.ROCK);
        }
        if (key == 'A' || key == 'a') {
            player1.setChoice(Choice.PAPER);
        }
        if (key == 'Z' || key == 'z') {
            player1.setChoice(Choice.SCISSORS);
        }
        if (key == 'O' || key == 'o') {
            player2.setChoice(Choice.ROCK);
        }
        if (key == 'K' || key == 'k') {
            player2.setChoice(Choice.PAPER);
        }
        if (key == 'M' || key == 'm') {
            player2.setChoice(Choice.SCISSORS);
        }
    }

    private void menuButton(String label, int y, boolean selected) {
        gameButton(label, y, selected, ButtonPosition.CENTER);
    }

    private void gameButton(String label, int y, boolean selected, boolean left) {
        gameButton(label, y, selected, left ? ButtonPosition.LEFT : ButtonPosition.RIGHT);
    }

    private void gameButton(String label, int y, boolean selected, ButtonPosition buttonPosition) {
        textAlign(CENTER);
        textFont(lazer84, 28);
        int x = 0;
        switch (buttonPosition) {
            case LEFT:
                x = 50;
                break;
            case RIGHT:
                x = width - 120;
                break;
            case CENTER:
                x = 158;
                break;
        }
        if (selected) {
            red();
            triangle(x - 25, y + 40, x + /*70*/ 50 + label.length() * 4, y, x + /*65*/45 + label.length() * 4, y + 35);
        }
        brightBlue();
        triangle(x - 25, y, x + 3, y + 53, x + 65 + label.length() * 5, y + 18);
        fill(0);
        text(label, x + 30 + 3, y + 30 + 3);
        pink();
        text(label, x + 30, y + 30);
    }

    private void twoPlayerGameButton(String label, int y, boolean left, char key) {
        gameButton(label, y, false, left);
        textFont(lazer84, 16);
        int x = left ? 100 : width - 60;
        brightBlue();
        rect(x, y + 40, 28, 28, 5);
        fill(255);
        rect(x + 2, y + 40 + 2, 24, 24, 5);
        red();
        textAlign(RIGHT);
        text(key, x + 20, y + 60);
    }

}
