/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;
import socket.ConnectionType;

/**
 *
 * @author eletr
 */
public class FrontMessage {
    ConnectionType type;
    String[][] board;
    Piece piece;
    List<Piece> queue;
        
    public FrontMessage() {
    }
    
    public FrontMessage(String[][] board,Piece piece, ConnectionType type, List<Piece> queue) {
        this.board = board;
        this.piece = piece;
        this.type = type;
        this.queue = queue;
    }

    public List<Piece> getQueue() {
        return queue;
    }

    public void setQueue(List<Piece> queue) {
        this.queue = queue;
    }
    
    
    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
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
    
}
