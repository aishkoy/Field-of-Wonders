import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Stream;


class Game{
    public static final Scanner scanner = new Scanner(System.in);
    static String floor = "_";
    public String word;
    private final String[] wordLetters;
    public String[] guessedWord;
    public StringBuilder guessedLetters = new StringBuilder();
    public StringBuilder inputLetters = new StringBuilder();
    public Player[] players;
    public int randIndex = getRandomInt();

    public Game(Player[] players){
        this.players = players;
        this.word = getWordForRound(randIndex);
        this.wordLetters = new String[word.length()];
        for (int i = 0; i < word.length(); i++) {
            wordLetters[i] = String.valueOf(word.charAt(i));
        }
        this.guessedWord = Stream.generate(() -> " ").limit(word.length()).toArray(String[]::new);
    }

    public void start(){
        int maxScore = 0;
        String playerGetMaxPoints = "";
        boolean isGameWon = false;
        showGameInfo(randIndex, inputLetters.toString(), guessedWord);

        while (!isGameWon) {
            AnyoneGet600:
            for (Player player : players) {
                boolean hasExtraTurn = false;
                do {
                    System.out.printf("Игрок %s, введите букву или слово: ", player.getName());
                    String playerInput = getValidInput(randIndex, guessedWord, inputLetters, player, guessedLetters);

                    if (playerInput.isBlank()) {
                        continue;
                    }

                    if (playerInput.length() > 1) {
                        if (playerInput.equalsIgnoreCase(word)) {
                            showGameInfo(randIndex, inputLetters.toString(), guessedWord);
                            System.out.printf("Игрок %s победил!", player.getName());
                            player.incrementWins();
                            isGameWon = true;
                            break;
                        } else {
                            showGameInfo(randIndex, inputLetters.toString(), guessedWord);
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

                        inputLetters.append(playerInput);
                        if (isLetterFound) {
                            guessedLetters.append(playerInput);
                            player.incrementPoints();
                            showGameInfo(randIndex, inputLetters.toString(), guessedWord);
                            System.out.println("Такая буква есть!");
                        } else {
                            showGameInfo(randIndex, inputLetters.toString(), guessedWord);
                            System.out.println("Такой буквы нет...");
                            hasExtraTurn = false;
                        }

                    }

                    if (Arrays.equals(guessedWord, wordLetters)) {
                        showDecoration();
                        System.out.printf("\nИгрок %s нашел слово!", player.getName());
                        player.incrementWins();
                        isGameWon = true;
                        break;
                    }

                    if (player.getPoints() == 600 && players.length > 1) {
                        playerGetMaxPoints = player.getName();
                        maxScore = player.getPoints();
                        break AnyoneGet600;
                    }

                } while (hasExtraTurn);

                if (isGameWon) break;
            }

            if(!isGameWon && maxScore == 600){
                showGameInfo(randIndex, inputLetters.toString(), guessedWord);
                System.out.printf("\nИгрок %s набрал %d очков, что является наивысшим результатом в этой игре. \nТеперь, другие игроки могут попытаться угадать слово. Если игрок назовет слово неправильно, он проиграет. \nЕсли же кто-то назовет слово правильно, то он станет победителем! \nА если никто не угадает, то победителем является %s!", playerGetMaxPoints, maxScore, playerGetMaxPoints);
                for(Player player : players){
                    if(player.getPoints() == maxScore){
                        continue;
                    }
                    System.out.printf("\n\nИгрок %s попытайся угадать слово: ", player.getName());
                    String playerInput = scanner.nextLine().strip().toLowerCase();

                    if(playerInput.isBlank() || !playerInput.equalsIgnoreCase(word)){
                        showDecoration();
                        System.out.println("К сожалению, это неправильное слово.");

                    } else {
                        showDecoration();
                        System.out.printf("Слово угадано верно!! Игрок %s победил!", player.getName());
                        player.incrementWins();
                        isGameWon = true;
                        break;
                    }
                }

                if(!isGameWon){
                    System.out.printf("\nТак как никто не угадал слово. Игрок %s становится победителем. Поздравляем!", playerGetMaxPoints);
                    for(Player player : players){
                        if(player.getPoints() == maxScore){
                            player.incrementWins();
                            isGameWon = true;
                        }
                    }
                }
            }
        }

        endGame(word, players);
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
        String riddle = "Загадка: \n" + Riddles[randWord];
        return riddle + "\n" + floor.repeat(93);
    }

    public static void showDecoration(){
        cleanScreen();
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
        System.out.print("\nСостояние слова:   ");
        for (String letters : guessedWord) {
            System.out.printf("[%s]", letters);
        }
        System.out.print("\n" + floor.repeat(93));
    }

    public static boolean isLetterEntered(String guessedLetters, String playerInput) {
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
    }

    public static void cleanScreen() {
        for (int i = 0; i < 10; i++) {
            System.out.println();
        }
    }

    public static void showGameInfo(int randIndex, String inputLetters, String[] guessedWord) {
        cleanScreen();
        showDecoration();
        System.out.println(getRiddleForRound(randIndex));
        showRemainingLetters(String.valueOf(inputLetters));
        showGuessProgress(guessedWord);
        System.out.println("\n");
    }

    public static String getValidInput(int randIndex, String[] guessedWord, StringBuilder inputLetters, Player player, StringBuilder guessedLetters) {
        String playerInput;
        do{
            playerInput = scanner.nextLine().strip().toLowerCase();
            if(!playerInput.matches("[а-яА-ЯёЁ]+") || playerInput.isBlank()){
                showGameInfo(randIndex, inputLetters.toString(), guessedWord);
                System.out.printf("\nНеверный ввод. Игрок %s, попробуйте еще раз: ", player.getName());
                playerInput = "";
            } else if (isLetterEntered(guessedLetters.toString(), playerInput)) {
                showGameInfo(randIndex, inputLetters.toString(), guessedWord);
                System.out.println("\nЭта буква уже была введена, введите другую.");
                playerInput = "";
            }
        } while(playerInput.isBlank());

        return playerInput;
    }

    public static void endGame(String word, Player[] players) {
        showDecoration();
        System.out.println("\nУгадываемое слово: " + word);
        System.out.println("\nСпасибо вам за игру! До свиданья!");
        displayLeaderboard(players);
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
}