import java.util.List;

public class game {
    private Board board = new Board(4);
    public int startPlayer;
    public int startTile;

    public game() {
        initializeBoard(4);
    }

    public boolean gameOver() {
        return false;
    }

    private void initializeBoard(int players) {
        board = new Board(players);
        tile highestDouble = new tile(0, 0);
        int startPlayer = 0;
        int startTile = 0;
        for (int i = 0; i < board.numPlayers; i++) {
            List<tile> hand = board.players.get(i).getHand();
            for (int j = 0; j < hand.size(); j++) {
                tile t = hand.get(j);
                if (t.isDouble()) {
                    if (t.getLeft() >= highestDouble.getLeft()) {
                        highestDouble = t;
                        startPlayer = i;
                        startTile = j;
                    }
                }
            }
        }
        System.out.println(board.players);

        System.out.println("Start player: " + startPlayer + ", Start tile index: " + startTile);
        this.startPlayer = startPlayer;
        this.startTile = startTile;

    }

    public static void main(String[] args) {
        game game = new game();
        int playerTurn = game.startPlayer;
        while (!game.gameOver()) {
            System.out.println(game.board);
            if (playerTurn == 0) {
                // User's turn
            } else {
                // ai move (for now random)
            }
        }

    }
}
