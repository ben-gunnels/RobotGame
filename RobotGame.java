import java.util.Arrays;
import java.util.Scanner;

/*
Game Rules:
The player exists as a robot in the center of the board.
The player can choose to move either right, left, up, or down.
The goal is to travel on 0's (Walkway) to collect 1's (Scrap Metal) and avoid 2's (Bombs).
The player starts out with a limited amount of health depending on the difficulty.
If a player consumes metal his score increases by one.
If a player consumes a bomb his health decreases by one.
If the player's health drops to 0 he is out of the game.
If the player collects enough Scrap Metal he wins the game.

bomb = 2
scrap metal = 1
walkway = 0
*/


class Robot {
    private int health;
    private int score = 0;

    public Robot(String difficulty) {
        switch (difficulty) {
        case "easy":
            this.health = 20;
            break;
        case "medium":
            this.health = 10;
            break;
        case "hard":
            this.health = 5;
            break;
        }
    }

    public void increaseScore() {
        this.score++;
    }

    public void decreaseHealth() {
        this.health--;
    }

    public int getHealth() {
        return this.health;
    }

    public int getScore() {
        return this.score;
    }
};

class Settings {
    private String difficulty;
    private int maxScore;

    public Settings(String d, int maxScore) {
        this.difficulty = d;
        this.maxScore = maxScore;
    }
    
    public String getDifficulty() {
        return this.difficulty;
    }

    public int getMaxScore() {
        return this.maxScore;
    }
};


class Map {
    public int[][] gameBoard = new int[7][7];
    public int boardLength = gameBoard.length;
    private static int[] center = {3, 3};

    public Map() {
        for (int r = 0; r < boardLength; r++) {
            randomizeRowOrColumn(false, true, r);
        }
    }

    public void shiftMap(String dir) {
        switch (dir) {
        case "right":
            shiftArrayRight();
            randomizeRowOrColumn(false, true, 0);
            break;
        case "left":
            shiftArrayLeft();
            randomizeRowOrColumn(false, true, boardLength-1);
            break;
        case "up":
            shiftArrayUp();
            randomizeRowOrColumn(true, false, boardLength-1);
            break;
        case "down":
            shiftArrayDown();
            randomizeRowOrColumn(true, false, 0);
            break;
        }
    }

    public void consumeMetal() {
        if (gameBoard[center[0]][center[0]] == 1) {
            gameBoard[center[0]][center[0]] = 0;
        }
    }

    
    private void shiftArrayRight() {
        for (int r = 0; r < boardLength; r++) {
            for (int c = 0; c < boardLength-1; c++) {
                gameBoard[r][c] = gameBoard[r][c+1];
            }
        }
    }

    private void shiftArrayLeft() {
        for (int r = 0; r < boardLength; r++) {
            for (int c = boardLength-1; c >= 1; c--) {
                gameBoard[r][c] = gameBoard[r][c-1];
            }
        }
    }

    private void shiftArrayUp() {
        for (int r = boardLength-1; r >= 1; r--) {
            for (int c = 0; c < boardLength; c++) {
                gameBoard[r][c] = gameBoard[r-1][c];
            }
        }
    }

    private void shiftArrayDown() {
        for (int r = 0; r < boardLength-1; r++) {
            for (int c = 0; c < boardLength; c++) {
                gameBoard[r][c] = gameBoard[r+1][c];
            }
        }

    }

    private void randomizeRowOrColumn(boolean hor, boolean vert, int dir) {
        // randomize objects coming onto the board
        if (hor) {
            for (int r = 0; r < boardLength; r++) {
                gameBoard[r][dir] = randomObject();
            }
        } else if (vert) {
            for (int c = 0; c < gameBoard[0].length; c++) {
                gameBoard[dir][c] = randomObject();
            }
        }
    }

    private int randomObject() {
        double randomNum = Math.random();
        if (randomNum < 0.2) {
            return 2;
        } else if (randomNum >= 0.2 && randomNum < 0.4) {
            return 1;
        } else {
            return 0;
        }
    }

    public void printGameBoard() {
        for (int r = 0; r < boardLength; r++) {
            System.out.println(Arrays.toString(gameBoard[r]));
        }
    }

    public int getBoardLastIndex() {
        return this.boardLength - 1;
    }
 
};

public class RobotGame {
    private static int[] center = {3, 3};
    
    public static void handleCollision(Map map, Robot robot) {
        if (map.gameBoard[center[0]][center[1]] == 2) {
            // handle obstacle collision
            robot.decreaseHealth();
        } else if (map.gameBoard[center[0]][center[1]] == 1) {
            // handle score collision
            robot.increaseScore();
            map.consumeMetal();
        }
    }

    public static void handleMove(Map map, String direction) {
        int boardLength = map.getBoardLastIndex();
        switch (direction) {
            case "right":
                // move right
                map.shiftMap("right");
                break;
            case "down":
                // move down
                map.shiftMap("down");
                break;
            case "left":
                // move left
                map.shiftMap("left");
                break;
            case "up":
                // move up
                map.shiftMap("up");
                break;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your desired difficulty (easy, medium, hard): ");
        String difficulty = scanner.nextLine();

        System.out.print("Enter your desired maximum score (<= 25): ");
        int desiredScore = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        Settings settings = new Settings(difficulty, desiredScore);
        Robot robot = new Robot(settings.getDifficulty());
        int startingHealth = robot.getHealth();

        int maxScore = settings.getMaxScore();

        System.out.println("Game Initializing...");
        Map map = new Map();

        while (robot.getScore() < maxScore && robot.getHealth() > 0) {
            System.out.println("Score: " + String.valueOf(robot.getScore()));
            System.out.println("Health: " + String.valueOf(robot.getHealth()));
            map.printGameBoard();
            System.out.print("Enter move (right, left, down, up): ");
            String direction = scanner.nextLine();
            switch (direction) {
                case "right":
                    handleMove(map, "right");
                    break;
                case "down":
                    handleMove(map, "down");
                    break;
                case "left":
                    handleMove(map, "left");
                    break;
                case "up":
                    handleMove(map, "up");
                    break;
                default:
                    System.out.println("Invalid move. Please try again.");
                    continue;
            }
            handleCollision(map, robot);
        }

        System.out.println("The Game has Ended!");
        System.out.println("Final Score: " + String.valueOf(robot.getScore()));
        System.out.println("Health Lost: " + String.valueOf(startingHealth - robot.getHealth()));
        if (robot.getScore() >= maxScore) {
            System.out.println("Congratulations! You are a great scrap collector!");
        } else {
            System.out.println("Im Sorry. You weren't scrappy enough! Back to the drawing board!");
        }
    }
};