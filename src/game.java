import java.util.List;

public class game {
    private Board board = new Board(2);
    public int startPlayer;
    public int startTile;

    public game() {
        initializeBoard(2);
    }

    public boolean gameOver() {
        // two conditions, a player has no more tiles or no player can play
        for (int i = 0; i < board.numPlayers; i++) {
            Player p = board.players.get(i);
            if (p.getHand().size() == 0) {
                System.out.println("Player " + i + " has no hand");
                return true;
            }
            if (p.possibleMoves(board).size() != 0) {
                //System.out.println(p.possibleMoves(board));
                return false;
            }
        }

        System.out.println("Nobody has a move");
        return true;
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
        tile t = game.board.players.get(playerTurn).getHand().get(game.startTile);
        game.board.board.add(t);
        game.board.players.get(playerTurn).removeTile(t);
        playerTurn++;
        if (playerTurn == game.board.numPlayers) {
            playerTurn = 0;
        }
        while (!game.gameOver()) {
            if (playerTurn == 5) {
                // User's turn
                System.out.println("hello");
                playerTurn ++ ;
            } else {
                System.out.println(game.board.board);
                Player current = game.board.players.get(playerTurn);
                System.out.println("Current player: " + current);
                Move m = current.firstMove(game.board);
                if (m == null) {
                    playerTurn++;
                    if (playerTurn == game.board.numPlayers) {
                        playerTurn = 0;
                    }
                    continue;
                } else {
                    if (m.side == 0) {
                        game.board.board.addFirst(m.T);
                        current.removeTile(m.T);
                    } else if (m.side == 1) {
                        game.board.board.add(m.T);
                        current.removeTile(m.T);
                    }
                }
                playerTurn++;
                if (playerTurn == game.board.numPlayers) {
                    playerTurn = 0;
                }
            }
        }
        for (Player p : game.board.players) {
            System.out.println("Player hand " + p);
        }

    }
}
