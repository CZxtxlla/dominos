import java.util.List;
import java.util.Scanner;

public class game {
    private Board board;
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

    private void resetBoard() {
    }

    private void play() {
        Scanner scanner = new Scanner(System.in);
        int playerTurn = this.startPlayer;
        tile t = this.board.players.get(playerTurn).getHand().get(this.startTile);
        this.board.board.add(t);
        this.board.players.get(playerTurn).removeTile(t);
        playerTurn++;
        if (playerTurn == this.board.numPlayers) {
            playerTurn = 0;
        }
        while (!this.gameOver()) {
            Player current = this.board.players.get(playerTurn);
            System.out.println("Board: " + this.board.board);
            System.out.println("Current player: " + playerTurn);
            if (playerTurn == 0) {
                // User's turn

                while (true) {
                    if (current.possibleMoves(this.board).size() == 0) {
                        System.out.println("you have no move");
                        break;
                    }
                    System.out.println("hand: " + current);
                    System.out.println("Enter index: ");
                    int index = scanner.nextInt();
                    System.out.println("Enter side (0 is left, 1 is right): ");
                    int side = scanner.nextInt();
                    Move m = new Move(current.getHand().get(index), side);
                    if (!this.board.isValid(m)) {
                        System.out.println("Invalid move");
                    } else {
                        if (m.side == 0) {
                            if (m.T.getLeft() == this.board.Left()) {
                                this.board.board.addFirst(m.T.flipped());
                            } else {
                                this.board.board.addFirst(m.T);
                            }
                            current.removeTile(m.T);
                            break;
                        } else if (m.side == 1) {
                            if (m.T.getRight() == this.board.Right()) {
                                this.board.board.add(m.T.flipped());
                            } else {
                                this.board.board.add(m.T);
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
                Move m = current.firstMove(this.board);
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
                        this.board.board.addFirst(m.T);
                        current.removeTile(m.T);
                    } else if (m.side == 1) {
                        this.board.board.add(m.T);
                        current.removeTile(m.T);
                    }
                }
                playerTurn++;
                if (playerTurn == this.board.numPlayers) {
                    playerTurn = 0;
                }
            }
        }
        for (Player p : this.board.players) {
            System.out.println("Player hand " + p);
        }
    }

    public static void main(String[] args) {
        game game = new game();
        game.play();
    }
}
