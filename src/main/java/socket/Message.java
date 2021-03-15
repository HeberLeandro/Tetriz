package socket;

import java.util.List;
import model.Piece;

public class Message {

    private ConnectionType type;
    String[][] myBoard;
    String[][] enemyBoard;
    private List<Piece> myPiecesqueue;
    private List<Piece> enemyPiecesqueue;

    public Message() {

    }

    public Message(ConnectionType type) {
        this.type = type;
        //this.turn = turn;
    }
    
    //Primeias peças
    Message(ConnectionType connectionType, String[][] myBoard, List<Piece> myPiecesqueue) {
        this.type = connectionType;
        this.enemyPiecesqueue = myPiecesqueue;
        this.myPiecesqueue = myPiecesqueue;
        this.myBoard = myBoard;
        this.enemyBoard = myBoard;
    }
    
    //Nova peça junto a lista de peças e tabuleiro
    Message(ConnectionType connectionType, String[][] myBoard, String[][] enemyBoard, List<Piece> myPiecesqueue, List<Piece> enemyPiecesqueue) {
        this.type = connectionType;
        this.myPiecesqueue = myPiecesqueue;
        this.enemyPiecesqueue = enemyPiecesqueue;
        this.myBoard = myBoard;
        this.enemyBoard = enemyBoard;
    }
    
    //Board do inimigo atulizado
    Message(ConnectionType connectionType,String[][] enemyBoard) {
        this.type = connectionType;
        this.enemyBoard = enemyBoard;
    }

    public ConnectionType getType() {
        return type;
    }

    public void setType(ConnectionType type) {
        this.type = type;
    }

    public String[][] getMyBoard() {
        return myBoard;
    }

    public void setMyBoard(String[][] myBoard) {
        this.myBoard = myBoard;
    }

    public String[][] getEnemyBoard() {
        return enemyBoard;
    }

    public void setEnemyBoard(String[][] enemyBoard) {
        this.enemyBoard = enemyBoard;
    }

    public List<Piece> getMyPiecesqueue() {
        return myPiecesqueue;
    }

    public void setMyPiecesqueue(List<Piece> myPiecesqueue) {
        this.myPiecesqueue = myPiecesqueue;
    }

    public List<Piece> getEnemyPiecesqueue() {
        return enemyPiecesqueue;
    }

    public void setEnemyPiecesqueue(List<Piece> enemyPiecesqueue) {
        this.enemyPiecesqueue = enemyPiecesqueue;
    }   
}
