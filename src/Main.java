import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String[] players = PlayerManager.getPlayers();
        showDecoration();

        int randIndex = getRandomInt();
        System.out.println(getRiddleForRound(randIndex));
        showGuessedWord(getWordForRound(randIndex));
        getPlayerInput(players);
    }

    public static class PlayerManager {
        public static String[] getPlayers() {
            Scanner sc = new Scanner(System.in);
            System.out.print("Введите количество игроков: ");
            int numPlayers = sc.nextInt();
            sc.nextLine();

            String[] players = new String[numPlayers];
            for (int i = 0; i < numPlayers; i++) {
                System.out.printf("Игрок №%d, введите имя: ", (i + 1));
                players[i] = sc.nextLine();
                while(players[i].isBlank()){
                    System.out.print("Вы не ввели имя! Попробуйте еще раз: ");
                    players[i] = sc.nextLine();
                }
            }
            return players;
        }
    }

    public static int getRandomInt(){
        Random rand = new Random();
        return rand.nextInt(10);
    }

    public static String getWordForRound(int randWord){
        String[] words = {"Компас", "Мираж", "Сфинкс", "Рубин", "Барометр", "Оазис", "Альбатрос", "Ковчег", "Лабиринт", "Парус"};
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

    public static void showGuessedWord(String getWordForRound){
        String[] guessedWord = new String[getWordForRound.length()];
        Arrays.fill(guessedWord, "[ ]");
        System.out.print("Слово:   ");
        for (String s : guessedWord) {
            System.out.print(s + " ");
        }
        System.out.println();
    }

    public static String getPlayerInput(String[] players){
        Scanner scanner = new Scanner(System.in);

        String input = "";
        for (String player : players) {
            System.out.printf("Игрок %s, введите букву или слово: ", player);
            input = scanner.nextLine().trim().toLowerCase();
        }
        return input;
    }

    public static boolean areWordsEqual(String getPlayerInput, String getWordForRound){
        char[] word = getWordForRound.toCharArray();
        char[] playerInput = getPlayerInput.toCharArray();
        return Arrays.equals(word, playerInput);
    }
}