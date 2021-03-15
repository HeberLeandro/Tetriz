/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author eletr
 */
public class Tetriz {

    private String[][] board1;
    private String[][] board2;
    private List<Piece> player1queuePieces;
    private List<Piece> player2queuePieces;
    private final int row = 20;
    private final int col = 10;
    private final List<List<int[][]>> pieceTypes;
    private List<String> colors;

    Piece lastPieceCoordenate1 = null;
    Piece lastPieceCoordenate2 = null;

    private boolean started = false;

    public Tetriz() {
        this.colors = new ArrayList<>();
        this.pieceTypes = new ArrayList<>();
        this.board1 = new String[row][col];
        this.board2 = new String[row][col];
        this.player1queuePieces = new ArrayList<>();
        this.player2queuePieces = new ArrayList<>();
        this.startBoard();
        setPieceTypes();
        setColors();

    }

    private void startBoard() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                board1[i][j] = "black";
                board2[i][j] = "black";
            }
        }
    }

    public String[][] getBoard1() {
        return board1;
    }

    public String[][] getBoard2() {
        return board2;
    }

    public List<Piece> initialPieces() {
        if (this.started) {
            return null;
        }
        Random r = new Random();
        int aux = r.nextInt(7);
        this.player1queuePieces.add(new Piece(pieceTypes.get(aux), colors.get(aux)));

        aux = r.nextInt(7);

        this.player1queuePieces.add(new Piece(pieceTypes.get(aux), colors.get(aux)));
        //Mesma peça para os 2 jogadores
        this.player2queuePieces.add(player1queuePieces.get(0));
        this.player2queuePieces.add(player1queuePieces.get(1));

        this.started = true;

        return this.player1queuePieces;
    }

    public List<Piece> p1NewPiece() {
        Random r = new Random();
        int aux = r.nextInt(7);
        
        //Remove a primeira peça do player1, pq ja caiu
        this.player1queuePieces.remove(0);

        //adciona nova peça
        this.player1queuePieces.add(new Piece(pieceTypes.get(aux), colors.get(aux)));
        //Mesma peça tbm a fila do player 2 
        this.player2queuePieces.add(new Piece(pieceTypes.get(aux), colors.get(aux)));

        return this.player1queuePieces;
    }

    public List<Piece> p2NewPiece() {
        Random r = new Random();
        int aux = r.nextInt(7);
        //Remove a primeira peça do player2, pq ja caiu
        this.player2queuePieces.remove(0);

        //adciona nova peça
        this.player2queuePieces.add(new Piece(pieceTypes.get(aux), colors.get(aux)));
        //Mesma peça tbm a fila do player 1 
        this.player1queuePieces.add(new Piece(pieceTypes.get(aux), colors.get(aux)));

        return this.player2queuePieces;
    }

    public void uptadeBoard1(String[][] board) {
        this.lastPieceCoordenate1 = null;
        for (int i = 0; i < this.row; i++) {
            System.arraycopy(board[i], 0, this.board1[i], 0, this.col);
        }
    }

    public void uptadeTempBoard1(Piece piece) {
        if (this.lastPieceCoordenate1 != null) {
            for (int currentRow = 0; currentRow < this.lastPieceCoordenate1.getActivePiece().length; currentRow++) {
                for (int currentCol = 0; currentCol < this.lastPieceCoordenate1.getActivePiece().length; currentCol++) {
                    if (this.lastPieceCoordenate1.getActivePiece()[currentRow][currentCol] != 0) {
                        this.board1[this.lastPieceCoordenate1.getY() + currentRow][this.lastPieceCoordenate1.getX() + currentCol] = "black";
                    }
                }
            }
        }
        
        this.lastPieceCoordenate1 = piece;
        
        for (int currentRow = 0; currentRow < piece.getActivePiece().length; currentRow++) {
            for (int currentCol = 0; currentCol < piece.getActivePiece().length; currentCol++) {
                if (piece.getActivePiece()[currentRow][currentCol] != 0) {
                    this.board1[piece.getY() + currentRow][piece.getX() + currentCol] = piece.getColor();
                }
            }
        }
    }

    public void uptadeBoard2(String[][] board) {
        this.lastPieceCoordenate2 = null;
        for (int i = 0; i < this.row; i++) {
            System.arraycopy(board[i], 0, this.board2[i], 0, this.col);
        }
    }

    public void uptadeTempBoard2(Piece piece) {
        if (this.lastPieceCoordenate2 != null) {
            for (int currentRow = 0; currentRow < this.lastPieceCoordenate2.getActivePiece().length; currentRow++) {
                for (int currentCol = 0; currentCol < this.lastPieceCoordenate2.getActivePiece().length; currentCol++) {
                    if (this.lastPieceCoordenate2.getActivePiece()[currentRow][currentCol] != 0) {
                        this.board2[this.lastPieceCoordenate2.getY() + currentRow][this.lastPieceCoordenate2.getX() + currentCol] = "black";
                    }
                }
            }
        }
        
        this.lastPieceCoordenate2 = piece;
        
        for (int currentRow = 0; currentRow < piece.getActivePiece().length; currentRow++) {
            for (int currentCol = 0; currentCol < piece.getActivePiece().length; currentCol++) {
                if (piece.getActivePiece()[currentRow][currentCol] != 0) {
                    this.board2[piece.getY() + currentRow][piece.getX() + currentCol] = piece.getColor();
                }
            }
        }
    }

    private void setColors() {
        colors.add("cyan");
        colors.add("orange");
        colors.add("purple");
        colors.add("blue");
        colors.add("green");
        colors.add("yellow");
        colors.add("red");
    }

    public List<Piece> getPlayer1queuePieces() {
        return player1queuePieces;
    }

    public List<Piece> getPlayer2queuePieces() {
        return player2queuePieces;
    }

    private void setPieceTypes() {
        List<int[][]> I = new ArrayList<int[][]>();
        int[][] i1 = new int[][]{{0, 0, 0, 0},
        {1, 1, 1, 1},
        {0, 0, 0, 0},
        {0, 0, 0, 0}
        };
        I.add(i1);

        int[][] i2 = new int[][]{{0, 0, 1, 0},
        {0, 0, 1, 0},
        {0, 0, 1, 0},
        {0, 0, 1, 0}
        };
        I.add(i2);

        // J
        List<int[][]> J = new ArrayList<int[][]>();

        int[][] j1 = new int[][]{{1, 0, 0},
        {1, 1, 1},
        {0, 0, 0}
        };
        J.add(j1);

        int[][] j2 = new int[][]{{0, 1, 1},
        {0, 1, 0},
        {0, 1, 0}
        };
        J.add(j2);

        int[][] j3 = new int[][]{{0, 0, 0},
        {1, 1, 1},
        {0, 0, 1}
        };
        J.add(j3);

        int[][] j4 = new int[][]{{0, 1, 0},
        {0, 1, 0},
        {1, 1, 0}
        };
        J.add(j4);

        // L
        List<int[][]> L = new ArrayList<int[][]>();

        int[][] l1 = new int[][]{{0, 0, 1},
        {1, 1, 1},
        {0, 0, 0}
        };
        L.add(l1);

        int[][] l2 = new int[][]{{0, 1, 0},
        {0, 1, 0},
        {0, 1, 1}
        };
        L.add(l2);

        int[][] l3 = new int[][]{{0, 0, 0},
        {1, 1, 1},
        {1, 0, 0}
        };
        L.add(l3);

        int[][] l4 = new int[][]{{1, 1, 0},
        {0, 1, 0},
        {0, 1, 0}
        };
        L.add(l4);

        // O
        List<int[][]> O = new ArrayList<int[][]>();

        int[][] o = new int[][]{{0, 0, 0, 0},
        {0, 1, 1, 0},
        {0, 1, 1, 0},
        {0, 0, 0, 0}
        };
        O.add(o);

        // S
        List<int[][]> S = new ArrayList<int[][]>();

        int[][] s1 = new int[][]{{0, 1, 1},
        {1, 1, 0},
        {0, 0, 0}
        };
        S.add(s1);

        int[][] s2 = new int[][]{{0, 1, 0},
        {0, 1, 1},
        {0, 0, 1}
        };
        S.add(s2);

        //T
        List<int[][]> T = new ArrayList<int[][]>();

        int[][] t1 = new int[][]{{0, 1, 0},
        {1, 1, 1},
        {0, 0, 0}
        };
        T.add(t1);

        int[][] t2 = new int[][]{{0, 1, 0},
        {0, 1, 1},
        {0, 1, 0}
        };
        T.add(t2);

        int[][] t3 = new int[][]{{0, 0, 0},
        {1, 1, 1},
        {0, 1, 0}
        };
        T.add(t3);

        int[][] t4 = new int[][]{{0, 1, 0},
        {1, 1, 0},
        {0, 1, 0}
        };
        T.add(t4);

        List<int[][]> Z = new ArrayList<int[][]>();

        int[][] z1 = new int[][]{{1, 1, 0},
        {0, 1, 1},
        {0, 0, 0}
        };
        Z.add(z1);

        int[][] z2 = new int[][]{{0, 0, 1},
        {0, 1, 1},
        {0, 1, 0}
        };
        Z.add(z2);

        this.pieceTypes.add(I);
        this.pieceTypes.add(J);
        this.pieceTypes.add(L);
        this.pieceTypes.add(O);
        this.pieceTypes.add(S);
        this.pieceTypes.add(T);
        this.pieceTypes.add(Z);
    }
}
