/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author eletr
 */
public class Piece {
    private List<int[][]> type;
    private String color;
    private int pieceRotation;
    
    private int[][] activePiece;
    
    private int x, y;

    public Piece(List<int[][]> type, String color) {
        this.type = type;
        this.color = color;
        
        this.pieceRotation = 0;
        this.activePiece = type.get(pieceRotation);
        this.x = 3;
        this.y = -2;
    }

    public List<int[][]> getType() {
        return type;
    }

    public void setType(List<int[][]> type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getPieceRotation() {
        return pieceRotation;
    }

    public void setPieceRotation(int pieceRotation) {
        this.pieceRotation = pieceRotation;
    }

    public int[][] getActivePiece() {
        return activePiece;
    }

    public void setActivePiece(int[][] activePiece) {
        this.activePiece = activePiece;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
