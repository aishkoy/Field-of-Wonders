import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Ведите количество игроков: ");
        int numPlayers = sc.nextInt();
        sc.nextLine();

        String[] players = new String[numPlayers];
        for (int i = 0; i < numPlayers; i++) {
            System.out.printf("Игрок №%d введите имя: ", (i + 1));
            players[i] = sc.next();
        }

        System.out.println(Arrays.toString(players));

        Random rand = new Random();
        int randWord = rand.nextInt(10);

        System.out.println(getWordForRound(randWord));
        System.out.println(getRiddleForRound(randWord));
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
        return wordRiddles[randWord];
    }
}