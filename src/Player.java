import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private List<tile> hand = new ArrayList<>();

    public Player(String name) {
        this.name = name;
    }

    public void addTile(tile tile) {
        hand.add(tile);
    }

    public List<tile> getHand() {
        return hand;
    }

    public List<tile> possibleMoves(Board board) {
        List<tile> possibleMoves = new ArrayList<>();
        for (tile T : hand) {
            if (T.getLeft() == board.Left() || T.getLeft() == board.Right() || T.getRight() == board.Left() || T.getRight() == board.Right()) {
                possibleMoves.add(T);
            }
        }
        return possibleMoves;
    }

    @Override
    public String toString() {
        return hand.toString();
    }
}
