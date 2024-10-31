import java.util.Scanner;

public class Player {
    private final String name;
    private int wins;
    private int points;

    public Player(String name) {
        this.name = name;
        this.wins = 0;
        this.points = 0;
    }

    public String getName() {
        return name;
    }

    public int getWins() {
        return wins;
    }


    public int getPoints() {
        return points;
    }

    public void incrementPoints() {
        points += 100;
    }

    public void incrementWins() {
        wins++;
    }
}


class PlayerManager {
    public static Scanner scanner = new Scanner(System.in);
    public static Player[] getPlayers() {
        int numPlayers = 0;

        boolean validInput = false;
        Game.showDecoration();
        do {
            System.out.print("Введите количество игроков: ");
            String numPlayersStr = scanner.nextLine().strip();
            if (numPlayersStr.matches("^[1-9]\\d*$")) {
                numPlayers = Integer.parseInt(numPlayersStr);
                validInput = true;
            } else {
                Game.showDecoration();
                System.out.println("Ошибка: Введите положительное целое число.");
            }
        }
        while (!validInput);

        Player[] players = new Player[numPlayers];
        for (int i = 0; i < numPlayers; i++) {
            System.out.printf("Игрок №%d, введите имя: ", (i + 1));
            String name = scanner.nextLine();
            while (name.isBlank()) {
                System.out.printf("Игрок №%d, Вы не ввели имя! Попробуйте еще раз: ", (i + 1));
                name = scanner.nextLine();
            }
            players[i] = new Player(name);
        }
        return players;
    }
}

