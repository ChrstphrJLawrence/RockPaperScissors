import processing.core.PApplet;
import processing.core.PFont;


public class MainApp extends PApplet{
    PFont f;
    MenuState menuState;
    GameState gameState;
    OnePlayerState onePlayerState;
    FightState fightState;
    Player player1;
    Player player2;
    Player computerPlayer;
    static int tieMonsterPoints;

    public static void main(String[] args) {
        PApplet.main("MainApp", args);
    }

    public void settings() {

        size(400, 500);
    }

    public void setup() {
        background(255);
        f = createFont("Arial",28,true);
        textAlign(CENTER);
        gameState = GameState.MENU;
        menuState = MenuState.ONEPLAYER;
        onePlayerState = OnePlayerState.ROCK;
        fightState = FightState.MAINMENU;
        player1 = new Player(false);
        player2 = new Player(false);
        computerPlayer = new Player(true);
        computerPlayer.setChoice();
    }

    public void draw() {
        if (gameState.equals(GameState.MENU)) {
            background(255);
            textFont(f, 28);
            fill(0);
            text("Rock | Paper | Scissors", width/2, 50);


            if (menuState.equals(MenuState.ONEPLAYER)) {
                menuButton("1 Player Game", 100, true);
                menuButton("2 Player Game", 200, false);
                menuButton("Quit", 300, false);
            }
            else if (menuState.equals(MenuState.TWOPLAYERS)) {
                menuButton("1 Player Game", 100, false);
                menuButton("2 Player Game", 200, true);
                menuButton("Quit", 300, false);
            }
            else if (menuState.equals(MenuState.QUIT)){
                menuButton("1 Player Game", 100, false);
                menuButton("2 Player Game", 200, false);
                menuButton("Quit", 300, true);
            }
        }
        else if (gameState.equals(GameState.ONEPLAYER)) {
            textFont(f, 28);
            fill(0);
            background(0, 0, 255);
            text("BATTLE VS. COMPUTER", width/2, 50);

            if (onePlayerState.equals(OnePlayerState.ROCK)) {
                gameButton("ROCK", 100, true, true);
                gameButton("PAPER", 200, false, true);
                gameButton("SCISSORS", 300, false, true);
                player1.setChoice(Choice.ROCK);
            }
            else if (onePlayerState.equals(OnePlayerState.PAPER)) {
                gameButton("ROCK", 100, false, true);
                gameButton("PAPER", 200, true, true);
                gameButton("SCISSORS", 300, false, true);
                player1.setChoice(Choice.PAPER);
            }
            else if (onePlayerState.equals(OnePlayerState.SCISSORS)) {
                gameButton("ROCK", 100, false, true);
                gameButton("PAPER", 200, false, true);
                gameButton("SCISSORS", 300, true, true);
                player1.setChoice(Choice.SCISSORS);
            }
            else if (onePlayerState.equals(OnePlayerState.FIGHT)) {
                background(0, 0, 255);
                text("FIGHT!", width/2, 50);
                String fightResult = "";
                if (player1.getChoice().equals(Choice.ROCK) && computerPlayer.getChoice().equals(Choice.SCISSORS) ||
                        computerPlayer.getChoice().equals(Choice.ROCK) && player1.getChoice().equals(Choice.SCISSORS)) {
                    fightResult = "ROCK SMASHES SCISSORS";
                }
                else if (player1.getChoice().equals(Choice.PAPER) && computerPlayer.getChoice().equals(Choice.ROCK) ||
                        computerPlayer.getChoice().equals(Choice.PAPER) && player1.getChoice().equals(Choice.ROCK)) {
                    fightResult = "PAPER COVERS ROCK";
                }
                else if (player1.getChoice().equals(Choice.SCISSORS) && computerPlayer.getChoice().equals(Choice.PAPER) ||
                        computerPlayer.getChoice().equals(Choice.SCISSORS) && player1.getChoice().equals(Choice.PAPER)) {
                    fightResult = "SCISSORS CUT PAPER";
                }
                text(fightResult, width/2, 100);
                text(determineWinner(player1, computerPlayer), width/2, 200);
                if (fightState.equals(FightState.MAINMENU)) {
                    menuButton("Play Again", 300, false);
                    menuButton("Main Menu", 400, true);
                }
                else if (fightState.equals(FightState.PLAYAGAIN)) {
                    menuButton("Play Again", 300, true);
                    menuButton("Main Menu", 400, false);
                }
            }
        }
        else if (gameState.equals(GameState.TWOPLAYERS)) {
            background(0, 0, 255);
            text("BATTLE VS. HUMAN", width/2, 50);
        }
        else if (gameState.equals(GameState.QUIT)) {
            exit();
        }
    }

    public static String determineWinner(Player player1, Player player2) {
        if (player1.getChoice().equals(player2.getChoice())) {
            tieMonsterPoints++;
            return "Tie Monster Wins!";
        }
        if (player1.getChoice().equals(Choice.PAPER) && player2.getChoice().equals(Choice.ROCK) ||
                player1.getChoice().equals(Choice.ROCK) && player2.getChoice().equals(Choice.SCISSORS) ||
                player1.getChoice().equals(Choice.SCISSORS) && player2.getChoice().equals(Choice.PAPER)) {
            player1.addPoint();
            return "Player 1 Wins!";
        }
        else {
            player2.addPoint();
            return "Player 2 Wins!";
        }
    }

    public void keyPressed() {
        if (gameState.equals(GameState.MENU)) {
            if (key == CODED && keyCode == UP) {
                switch (menuState) {
                    case ONEPLAYER:
                        menuState = MenuState.QUIT;
                        break;
                    case TWOPLAYERS:
                        menuState = MenuState.ONEPLAYER;
                        break;
                    case QUIT:
                        menuState = MenuState.TWOPLAYERS;
                        break;
                }
            }
            if (key == CODED && keyCode == DOWN) {
                switch (menuState) {
                    case ONEPLAYER:
                        menuState = MenuState.TWOPLAYERS;
                        break;
                    case TWOPLAYERS:
                        menuState = MenuState.QUIT;
                        break;
                    case QUIT:
                        menuState = MenuState.ONEPLAYER;
                        break;
                }
            }

            if (key == CODED && keyCode == RIGHT) { //TODO WHY DOESNT THIS WORK WITH ENTER/RETURN?!?
                switch(menuState) {
                    case ONEPLAYER: gameState = GameState.ONEPLAYER;
                    break;
                    case TWOPLAYERS: gameState = GameState.TWOPLAYERS;
                    break;
                    case QUIT: gameState = GameState.QUIT;
                    break;
                }
            }
        }
        else if (gameState.equals(GameState.ONEPLAYER) && onePlayerState.equals(OnePlayerState.FIGHT)) {
            if (key == CODED && keyCode == UP || keyCode == DOWN) {
                switch (fightState) {
                    case PLAYAGAIN:
                        fightState = FightState.MAINMENU;
                        break;
                    case MAINMENU:
                        fightState = FightState.PLAYAGAIN;
                        break;
                }
            }
            if (key == CODED && keyCode == RIGHT) {
                switch (fightState) {
                    case PLAYAGAIN:
                        gameState = GameState.ONEPLAYER;
                        onePlayerState = OnePlayerState.ROCK;
                        computerPlayer.setChoice();
                        break;
                    case MAINMENU:
                        setup();
                        break;
                }
            }
        }
        else if (gameState.equals(GameState.ONEPLAYER)) {
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
            if (key == CODED && keyCode == RIGHT) {
                onePlayerState = OnePlayerState.FIGHT;
            }
            if (key == CODED && keyCode == LEFT) {  //TODO maybe remove
                setup();
            }
        }
        else if (gameState.equals(GameState.TWOPLAYERS)) {
            if (key == CODED && keyCode == LEFT) {  //TODO maybe remove / duplication
                setup();
            }
        }
    }

    public void menuButton(String label, int y, boolean selected) {
        if (selected) {
            fill(0, 300, 0);
        }
        else {
            fill(300, 0, 0);
        }
        rect( 50, y, 300, 100);
        fill(0);
        text(label,50, y + 25, 300, 100);
    }

    public void gameButton(String label, int y, boolean selected, boolean left) {
        textFont(f, 16);
        fill(0);
        int x = left ? 20 : width - 20;
        fill(255, 0, 0);
        if (selected) {
            rect(x, y, 100, 100);
        }
        fill(0, 255, 0);
        rect( x + 10, y + 10, 80, 80);
        fill(0);
        text(label, x, y + 25, 90, 90);
    }

}
