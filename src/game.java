import java.util.List;
import java.util.Scanner;

public class game {
    private Board board = new Board(4);
    public int startPlayer;
    public int startTile;

    public game() {
        initializeBoard(4);
    }

    public boolean gameOver() {
        // two conditions, a player has no more tiles or no player can play
        for (int i = 0; i < board.numPlayers; i++) {
            Player p = board.players.get(i);
            if (p.getHand().isEmpty()) {
                System.out.println("Player " + i + " has no hand");
                return true;
            }
            if (!p.possibleMoves(board).isEmpty()) {
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
        Scanner scanner = new Scanner(System.in);
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
            Player current = game.board.players.get(playerTurn);
            System.out.println("Board: " + game.board.board);
            System.out.println("Current player: " + playerTurn);
            if (playerTurn == 0) {
                // User's turn

                while (true) {
                    if (current.possibleMoves(game.board).size() == 0) {
                        System.out.println("you have no move");
                        break;
                    }
                    System.out.println("hand: " + current);
                    System.out.println("Enter index: ");
                    int index = scanner.nextInt();
                    System.out.println("Enter side (0 is left, 1 is right): ");
                    int side = scanner.nextInt();
                    Move m = new Move(current.getHand().get(index), side);
                    if (!game.board.isValid(m)) {
                        System.out.println("Invalid move");
                    } else {
                        if (m.side == 0) {
                            if (m.T.getLeft() == game.board.Left()) {
                                game.board.board.addFirst(m.T.flipped());
                            } else {
                                game.board.board.addFirst(m.T);
                            }
                            current.removeTile(m.T);
                            break;
                        } else if (m.side == 1) {
                            if (m.T.getRight() == game.board.Right()) {
                                game.board.board.add(m.T.flipped());
                            } else {
                                game.board.board.add(m.T);
                            }
                            current.removeTile(m.T);
                            break;
                        } else {
                            //invalid side/move
                            continue;
                        }
                    }
                }

                playerTurn ++ ;
            } else {
                System.out.println("hand: " + current);
                Move m = current.firstMove(game.board);
                if (m == null) {
                    System.out.println("Bot has no moves");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
//                    playerTurn++;
//                    if (playerTurn == game.board.numPlayers) {
//                        playerTurn = 0;
//                    }
//                    continue;
                } else {
                    System.out.println("Bot has a move");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
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
