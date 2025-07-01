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

    public void removeTile(tile tile) {
        hand.remove(tile);
    }

    public List<tile> getHand() {
        return hand;
    }

    public List<Move> possibleMoves(Board board) {
        List<Move> possibleMoves = new ArrayList<>();
        for (tile T : hand) {
            if (T.getLeft() == board.Left()) {
                possibleMoves.add(new Move(T.flipped(), 0)); //0 for left
            } else if (T.getLeft() == board.Right()) {
                possibleMoves.add(new Move(T, 1));
            } else if (T.getRight() == board.Right()) {
                possibleMoves.add(new Move(T.flipped(), 1));
            } else if (T.getRight() == board.Left()) {
                possibleMoves.add(new Move(T, 0));
            }
        }
        return possibleMoves;
    }

    public Move firstMove(Board board) {
        List<Move> possibleMoves = possibleMoves(board);
        if (possibleMoves.isEmpty()) {
            return null;
        } else {
            Move move = possibleMoves.get(0);
            hand.remove(move.T);
            return move;
        }
    }

    @Override
    public String toString() {
        return hand.toString();
    }
}
