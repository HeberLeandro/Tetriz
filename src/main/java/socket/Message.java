package socket;

import model.Piece;



public class Message {

    private ConnectionType type;
    String[][] board;
    private Piece[] pieces;
    private Piece piece;

    public Message() {

    }

    public Message(ConnectionType type) {
        this.type = type;
        //this.turn = turn;
    }

    Message(ConnectionType connectionType, String[][] board, Piece[] pieces) {
        this.pieces = pieces;
        this.board = board;
        this.type = connectionType;
    }

    public ConnectionType getType() {
        return type;
    }

    public void setType(ConnectionType type) {
        this.type = type;
    }

    public String[][] getBoard() {
        return board;
    }

    public void setBoard(String[][] board) {
        this.board = board;
    }

    public Piece[] getPieces() {
        return pieces;
    }

    public void setPieces(Piece[] pieces) {
        this.pieces = pieces;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

}
