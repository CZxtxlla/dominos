import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
    public List<tile> tiles;
    public List<tile> board;
    public List<Player> players;
    public int numPlayers;

    public Board(int numPlayers) {
        this.numPlayers = numPlayers;
        this.players = new ArrayList<>();
        this.tiles = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            for (int j = i; j < 7; j++) {
                tiles.add(new tile(i, j));
            }
        }
        List<tile> tilescopy = new ArrayList<>(tiles);
        board = new ArrayList<>();
        Random rand = new Random();
        for (int i = 1; i <= numPlayers; i++) {
            Player p = new Player("Player " + i);
            for (int j = 0; j < 7; j++) {
                int index = rand.nextInt(tilescopy.size());
                p.addTile(tilescopy.get(index));
                tilescopy.remove(index);
            }
            this.players.add(p);
        }
    }

    public int Left() {
        return (board.get(0)).getLeft();
    }

    public int Right() {
        return (board.get(board.size() - 1)).getRight();
    }

    public boolean isValid(Move move) {
        if (move.side == 1) {
            return move.T.getLeft() == this.Right() || move.T.getRight() == this.Right();
        } else {
            return move.T.getRight() == this.Left() || move.T.getLeft() == this.Left();
        }
    }

    public static void main(String[] args) {
        Board b = new Board(2);
        System.out.println(b.tiles.size());
        System.out.println(b.tiles);
        System.out.println(b.players);
        System.out.println(b.board);
        tile highestDouble = new tile(0, 0);

        int startPlayer = 0;
        int startTile = 0;
        for (int i = 0; i < b.numPlayers; i++) {
            List<tile> hand = b.players.get(i).getHand();
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
        System.out.println("Start player: " + startPlayer + ", Start tile index: " + startTile);

    }

}
