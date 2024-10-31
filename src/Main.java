import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Player[] players = PlayerManager.getPlayers();
        showDecoration();

        int randIndex = getRandomInt();

        System.out.println(getRiddleForRound(randIndex));
        String word = getWordForRound(randIndex);

        String[] guessedWord = new String[word.length()];
        Arrays.fill(guessedWord, " ");
        System.out.print("Слово:   ");
        for (String s : guessedWord) {
            System.out.printf("[%s]", s);
        }
        System.out.println();

        String[] wordLetters = new String[word.length()];
        for (int i = 0; i < word.length(); i++) {
            wordLetters[i] = String.valueOf(word.charAt(i));
        }

        boolean isGameWon = false;
        StringBuilder guessedLetters = new StringBuilder();


        while (!isGameWon) {
            for (Player player : players) {
                showRemainingLetters(String.valueOf(guessedLetters));
                boolean hasExtraTurn = false;
                do {
                    System.out.printf("\nИгрок %s, введите букву или слово: ", player.getName());
                    String playerInput = scanner.nextLine().strip().toLowerCase();

                    while (playerInput.isBlank()) {
                        System.out.printf("\nНеверный ввод. Игрок %s, попробуйте еще раз: ", player.getName());
                        playerInput = scanner.nextLine().strip().toLowerCase();
                    }

                    if (isLetterGuessed(guessedLetters.toString(), playerInput)) {
                        System.out.println("\nЭта буква уже была введена, введите другую.");
                        continue;
                    }

                    if (playerInput.length() > 1) {
                        if (playerInput.equalsIgnoreCase(word)) {
                            System.out.printf("Игрок %s победил!", player.getName());
                            player.incrementWins();
                            isGameWon = true;
                            break;
                        } else {
                            System.out.printf("Игрок %s ввел неверное слово!", player.getName());
                            showGuessProgress(guessedWord);
                            hasExtraTurn = false;
                        }

                    } else {
                        boolean isLetterFound = false;
                        char letter = playerInput.charAt(0);
                        for (int j = 0; j < word.length(); j++) {
                            if (letter == word.charAt(j)) {
                                guessedWord[j] = String.valueOf(letter);
                                isLetterFound = true;
                                hasExtraTurn = true;
                            }
                        }

                        if (isLetterFound) {
                            guessedLetters.append(playerInput);
                            player.incrementPoints();
                            System.out.println("Такая буква есть!");
                        } else {
                            System.out.println("Такой буквы нет...");
                            hasExtraTurn = false;
                        }

                        showGuessProgress(guessedWord);
                    }

                    if (Arrays.equals(guessedWord, wordLetters)) {
                        System.out.printf("\nИгрок %s нашел слово!", player.getName());
                        player.incrementWins();
                        isGameWon = true;
                        break;
                    }
                } while (hasExtraTurn);

                if (isGameWon) break;
            }
        }

        displayLeaderboard(players);
    }


    public static class PlayerManager {
        public static Player[] getPlayers() {
            Scanner sc = new Scanner(System.in);
            System.out.print("Введите количество игроков: ");
            int numPlayers = sc.nextInt();
            sc.nextLine();

            Player[] players = new Player[numPlayers];
            for (int i = 0; i < numPlayers; i++) {
                System.out.printf("Игрок №%d, введите имя: ", (i + 1));
                String name = sc.nextLine();
                while (name.isBlank()) {
                    System.out.print("Вы не ввели имя! Попробуйте еще раз: ");
                    name = sc.nextLine();
                }
                players[i] = new Player(name);
            }
            return players;
        }
    }

    public static class Player {
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

    public static void sortPlayersByWins(Player[] players) {
        int n = players.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (players[j].getWins() < players[j + 1].getWins()) {
                    Player temp = players[j];
                    players[j] = players[j + 1];
                    players[j + 1] = temp;
                }
            }
        }
    }

    public static void displayLeaderboard(Player[] players) {
        sortPlayersByWins(players);
        System.out.println("\nТаблица лидеров:");
        for (Player player : players) {
            System.out.println(player.getName() + " - Победы: " + player.getWins() + ", Очки: " + player.getPoints());
        }
    }

    public static int getRandomInt(){
        Random rand = new Random();
        return rand.nextInt(10);
    }

    public static String getWordForRound(int randWord){
        String[] words = {"компас", "мираж", "сфинкс", "рубин", "барометр", "оазис", "альбатрос", "ковчег", "лабиринт", "парус"};
        return words[randWord];
    }

    public static String getRiddleForRound(int randWord){
        String[] wordRiddles = {"Он укажет, где восток, и не заблудится моряк.",
                "В пустыне он как светлый сон, но это только обман.",
                "Египетский хранитель песков, что стоит молча, но хранит тайны тысячелетий.",
                "Красный камень, что дороже золота, но огня не имеет.",
                "Измеряет то, что невидимо, но важно для погоды.",
                "В центре пустыни он как зелёный остров спасения.",
                "Птица океанов, что крыльями не касается моря, но знает его просторы.",
                "Судно, что несло спасение всем, кто был на борту.",
                "Ходы и выходы запутаны, и один из них ведет к спасению.",
                "Он гонит корабль вперёд, но сам неподвижен."};
        return "Загадка: " + wordRiddles[randWord];
    }

    public static void showDecoration(){
        for(int i = 0; i < 31; i++){
            System.out.print("===");
        }
        System.out.println();
        for(int i = 0; i < 35; i++){
            System.out.print(" ");
        }
        System.out.println("ИГРА 'ПОЛЕ ЧУДЕС'");
        for(int i = 0; i < 31; i++){
            System.out.print("===");
        }
        System.out.println();
    }

    public static void showGuessProgress(String[] guessedWord){
        System.out.println("Состояние слова: ");
        for (String letters : guessedWord) {
            System.out.printf("[%s]", letters);
        }
    }

    public static boolean isLetterGuessed(String guessedLetters, String playerInput) {
        return guessedLetters.contains(playerInput);
    }

    public static void showRemainingLetters(String guessedLetters) {
        String[] allLetters = {
                "а", "б", "в", "г", "д", "е", "ё", "ж", "з", "и",
                "й", "к", "л", "м", "н", "о", "п", "р", "с", "т",
                "у", "ф", "х", "ц", "ч", "ш", "щ", "ъ", "ы", "ь",
                "э", "ю", "я"
        };
        String[] remainingLetters = Arrays.stream(allLetters)
                .filter(letter -> !guessedLetters.contains(letter))
                .toArray(String[]::new);

        System.out.print("\nОставшиеся буквы: ");
        for(String letter : remainingLetters) {
            System.out.print(letter + " ");
        }
    }

}