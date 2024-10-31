public class Main {
    public static void main(String[] args) {
        Player[] players = PlayerManager.getPlayers();
        Game game = new Game(players);
        game.start();
    }
}