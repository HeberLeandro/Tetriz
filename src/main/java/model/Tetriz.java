/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 *
 * @author eletr
 */
public class Tetriz {

    private String[][] board1;
    private String[][] board2;
    private final int row = 20;
    private final int col = 10;
    private final List<List<int[][]>> pieceTypes;
    private List<String> colors;

    public Tetriz() {
        this.colors = new ArrayList<>();
        this.pieceTypes = new ArrayList<>();
        this.board1 = new String[row][col];
        this.board2 = new String[row][col];
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

    public Piece getPiece() {
        Random r = new Random();
        int aux = r.nextInt(7);
        System.out.println(aux);
        Piece p = new Piece(pieceTypes.get(aux), colors.get(aux));
        return p;
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
        
        System.out.println(pieceTypes.size());
        this.pieceTypes.add(I);
        this.pieceTypes.add(J);
        this.pieceTypes.add(L);
        this.pieceTypes.add(O);
        this.pieceTypes.add(S);
        this.pieceTypes.add(T);
        this.pieceTypes.add(Z);
    }
    
    public static void main(String[] args) {
        Tetriz t = new Tetriz();
        
        String[][] teste = t.getBoard1();
        
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print(teste[i][j]+" ");
            }
            System.out.print("\n \n");
        }
    }

}
