import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int randIndex = getRandomInt();
        String word = getWordForRound(randIndex);
        String[] wordLetters = new String[word.length()];
        for (int i = 0; i < word.length(); i++) {
            wordLetters[i] = String.valueOf(word.charAt(i));
        }
        String[] guessedWord = Stream.generate(() -> " ").limit(word.length()).toArray(String[]::new);

        StringBuilder guessedLetters = new StringBuilder();
        StringBuilder inputLetters = new StringBuilder();

        int maxScore = 0;
        String playerGetMaxPoints = "";


        showDecoration();
        Player[] players = PlayerManager.getPlayers();
        System.out.println();
        cleanScreen();
        showGameInfo(randIndex, String.valueOf(inputLetters), guessedWord);

        boolean isGameWon = false;
        while (!isGameWon) {
            AnyOneGet600:
            for (Player player : players) {
                boolean hasExtraTurn = false;
                do {
                    System.out.printf("\nИгрок %s, введите букву или слово: ", player.getName());
                    String playerInput = getValidInput(randIndex, guessedWord, inputLetters, player);

                    if (isLetterGuessed(guessedLetters.toString(), playerInput)) {
                        cleanScreen();
                        showGameInfo(randIndex, String.valueOf(inputLetters), guessedWord);
                        System.out.println("\nЭта буква уже была введена, введите другую.");
                        continue;
                    }

                    if (playerInput.length() > 1) {
                        if (playerInput.equalsIgnoreCase(word)) {
                            cleanScreen();
                            showGameInfo(randIndex, String.valueOf(inputLetters), guessedWord);
                            System.out.printf("Игрок %s победил!", player.getName());
                            player.incrementWins();
                            isGameWon = true;
                            break;
                        } else {
                            cleanScreen();
                            showGameInfo(randIndex, String.valueOf(inputLetters), guessedWord);
                            System.out.printf("Игрок %s ввел неверное слово!", player.getName());
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

                        cleanScreen();
                        if (isLetterFound) {
                            guessedLetters.append(playerInput);
                            player.incrementPoints();
                            showGameInfo(randIndex, String.valueOf(inputLetters), guessedWord);
                            System.out.println("Такая буква есть!");
                        } else {
                            showGameInfo(randIndex, String.valueOf(inputLetters), guessedWord);
                            System.out.println("Такой буквы нет...");
                            hasExtraTurn = false;
                        }
                        inputLetters.append(playerInput);
                    }

                    if (Arrays.equals(guessedWord, wordLetters)) {
                        cleanScreen();
                        showDecoration();
                        System.out.printf("\nИгрок %s нашел слово!", player.getName());
                        player.incrementWins();
                        isGameWon = true;
                        break;
                    }

                    if (player.getPoints() == 600) {
                        playerGetMaxPoints = player.getName();
                        maxScore = player.getPoints();
                        break AnyOneGet600;
                    }

                } while (hasExtraTurn);

                if (isGameWon) break;
            }

            if(!isGameWon && maxScore == 600){
                cleanScreen();
                showGameInfo(randIndex, String.valueOf(inputLetters), guessedWord);
                System.out.printf("\nИгрок %s набрал %d очков, что является наивысшим результатом в этой игре. \nТеперь, другие игроки могут попытаться угадать слово. Если игрок назовет слово неправильно, он проиграет. \nЕсли же кто-то назовет слово правильно, то он станет победителем! \nА если никто не угадает, то победителем является %s!", playerGetMaxPoints, maxScore, playerGetMaxPoints);
                for(Player player : players){
                    if(player.getPoints() == maxScore){
                        continue;
                    }
                    System.out.printf("\n\nИгрок %s попытайся угадать слово: ", player.getName());
                    String playerInput = scanner.nextLine().strip().toLowerCase();

                    if(playerInput.isBlank() || !playerInput.equalsIgnoreCase(word)){
                        cleanScreen();
                        showGameInfo(randIndex, String.valueOf(inputLetters), guessedWord);
                        System.out.println("К сожалению, это неправильное слово.");

                    } else {
                        cleanScreen();
                        showDecoration();
                        System.out.printf("Слово угадано верно!! Игрок %s победил!", player.getName());
                        player.incrementWins();
                        isGameWon = true;
                        break;
                    }
                }

                if(!isGameWon){
                    System.out.printf("Так как никто не угадал слово. Игрок %s становится победителем. Поздравляем!", playerGetMaxPoints);
                    for(Player player : players){
                        if(player.getPoints() == maxScore){
                            player.incrementWins();
                            isGameWon = true;
                        }
                    }
                }
            }
        }

        System.out.println("\nУгадываемое слово: " + word);
        System.out.println("\nСпасибо вам за игру! До свиданья!");
        displayLeaderboard(players);
    }

    public static class PlayerManager {

        public static Player[] getPlayers() {
            Scanner sc = new Scanner(System.in);
            int numPlayers = 0;

            boolean validInput = false;
            while (!validInput) {
                System.out.print("Введите количество игроков: ");
                String numPlayersStr = sc.nextLine().strip();

                if (numPlayersStr.matches("^[1-9]\\d*$")) {
                    numPlayers = Integer.parseInt(numPlayersStr);
                    validInput = true;
                } else {
                    System.out.println("Ошибка: Введите положительное целое число.");
                }
            }

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

    public static String getWordForRound(int randWord) {
        String[] words = {"консолидация", "реинтеграция", "электродинамика", "протоколизация", "фотоэлектроника", "интернационал", "антропогенез", "гелиофизика", "иммунология", "фотодокументация"};
        return words[randWord];
    }

    public static String getRiddleForRound(int randWord) {
        String[] Riddles = {
                "Процесс объединения всех вместе,\nКогда разногласия отходят на место.\nДля группы, компании, народа\nВажна эта связь как основы природа.",
                "Процесс возвращения в круг свой родной,\nКогда отделённый становится свой.\nКогда, возвращаясь в общество снова,\nОн часть единого снова готова.",
                "Изучает токи, волны и свет,\nСвязь зарядов ищет ответ.\nВ этой науке формулы правят,\nЗнания точные силу направят.",
                "Превращает слова и дела в формальности,\nГде каждый шаг записан и в деталях ясности.\nДля договоров и встреч — это правило строгим,\nЧтобы каждый момент был известен многим.",
                "Наука о том, как свет реагирует,\nЗаряд электрона при этом фигурирует.\nПри солнечном свете заряды бегут,\nУчёные в этом процесс поддадут.",
                "Когда народы разные вместе живут,\nВсе культуры свою ценность ведут.\nТак называют единство народов,\nЦель мира для всех нации в этом подходе.",
                "Откуда мы взялись, как стали людьми,\nПроцесс превращения и мысли и умы.\nНаука, что корни нашего вида,\nИсторию нашу с вниманием исследует всегда.",
                "Солнце и звёзды в её фокусе взгляд,\nСвет и движение ищет подряд.\nНаука о звёздах и свете их жарком,\nКаждый ответ найдет в этом порядке ярком.",
                "Охрана от болезней, защита организма,\nСистема работает точно и без компромисса.\nОна распознает врагов без урона,\nИ защитит нас от любого угона.",
                "Снимок на память, в истории след,\nДокумент важный и фото ответ.\nВ этой науке визуальная грань,\nВажнейший источник — источник знаний и дань."
        };
        return "Загадка: \n" + Riddles[randWord];
    }

    public static void showDecoration(){
        System.out.println();
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
        System.out.print("Состояние слова: ");
        for (String letters : guessedWord) {
            System.out.printf("[%s]", letters);
        }
    }

    public static boolean isLetterGuessed(String guessedLetters, String playerInput) {
        return guessedLetters.contains(playerInput);
    }

    public static void showRemainingLetters(String inputLetters) {
        String[] allLetters = {
                "а", "б", "в", "г", "д", "е", "ё", "ж", "з", "и",
                "й", "к", "л", "м", "н", "о", "п", "р", "с", "т",
                "у", "ф", "х", "ц", "ч", "ш", "щ", "ъ", "ы", "ь",
                "э", "ю", "я"
        };
        String[] remainingLetters = Arrays.stream(allLetters)
                .filter(letter -> !inputLetters.contains(letter))
                .toArray(String[]::new);

        System.out.print("\nОставшиеся буквы: ");
        for(String letter : remainingLetters) {
            System.out.print(letter.toUpperCase() + " ");
        }
        System.out.println();
    }

    public static void cleanScreen() {
        for (int i = 0; i < 30; i++) {
            System.out.println();
        }
    }

    public static void showGameInfo(int randIndex, String inputLetters, String[] guessedWord) {
        showDecoration();
        System.out.println(getRiddleForRound(randIndex));
        showRemainingLetters(String.valueOf(inputLetters));
        showGuessProgress(guessedWord);
        System.out.println("\n");
    }

    public static String getValidInput(int randIndex, String[] guessedWord, StringBuilder inputLetters, Player player) {
        Scanner scanner = new Scanner(System.in);
        String playerInput = scanner.nextLine().strip().toLowerCase();

        while (playerInput.isBlank() || !playerInput.matches("[а-яА-ЯёЁ]+")) {
            cleanScreen();
            showGameInfo(randIndex, String.valueOf(inputLetters), guessedWord);
            System.out.printf("\nНеверный ввод. Игрок %s, попробуйте еще раз: ", player.getName());
            playerInput = scanner.nextLine().strip().toLowerCase();
        }

        return playerInput;
    }
}