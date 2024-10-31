import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String[] players = PlayerManager.getPlayers();
        showDecoration();

        int randIndex = getRandomInt();

        System.out.println(getRiddleForRound(randIndex));
        String word = getWordForRound(randIndex);

        String[] guessedWord = new String[word.length()];
        Arrays.fill(guessedWord, "[ ]");
        System.out.print("Слово:   ");
        for (String s : guessedWord) {
            System.out.print(s + " ");
        }
        System.out.println();

        boolean isGameWon = false;
        while (!isGameWon) {
            Scanner scanner = new Scanner(System.in);

            String playerInput = "";
            for (String player : players) {
                System.out.printf("Игрок %s, введите букву или слово: ", player);
                boolean hasExtraTurn = false;

                playerInput = scanner.nextLine().strip().toLowerCase();

                if(playerInput.length() > 1){
                    if(playerInput.equalsIgnoreCase(word)){
                        System.out.printf("Игрок %s победил!", player);
                        isGameWon = true;
                    } else{
                        System.out.printf("Игрок %s ввел неверное слово!", player);
                    }
                } else {
                    boolean isLetterFoound = false;
                    char letter = playerInput.charAt(0);
                    for(int i = 0; i < word.length(); i++){
                        if(word.charAt(i) == letter){
                            guessedWord[i] = String.format("%s",letter);
                            isLetterFoound = true;
                            hasExtraTurn = true;
                        }
                    }
                    if(isLetterFoound) {
                        System.out.println("Такая буква есть!");
                    } else{
                        System.out.println("Такой буквы нет...");
                        hasExtraTurn = false;
                    }
                    System.out.println(Arrays.toString(guessedWord));

                }
            }
        }
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
}