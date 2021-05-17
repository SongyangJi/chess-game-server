package com.jsy.chessgameserver.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @Author: Song yang Ji
 * @ProjectName: chess-game-server
 * @Version 1.0
 * @Description:
 */

@Component
@Scope("prototype")
public class ChessBoard {
    private static final int SIZE = 15;
    private final int[][] g;

    public ChessBoard() {
        g = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            Arrays.fill(g[0], -1);
        }
    }


    boolean occupy(int x, int y, int color) {
        if (g[x][y] != -1) {
            return false;
        }
        g[x][y] = color;
        return true;
    }


    boolean checkWin(int x, int y) {
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) return false;
        if (g[x][y] == -1) return false;
        return checkHorizontal(x, y) || checkVertical(x,y)
                || checkDiagonal(x, y) || checkAntiDiagonal(x, y);
    }

    private boolean checkHorizontal(int x, int y) {
        int cnt = 0;
        int color = g[x][y];
        int j = y;
        while (j >= 0) {
            if (g[x][j] == color) {
                j--;
                cnt++;
            } else {
                break;
            }
        }
        j = y + 1;
        while (j < SIZE) {
            if (g[x][j] == color) {
                j++;
                cnt++;
            } else {
                break;
            }
        }
        return cnt >= 5;
    }

    private boolean checkVertical(int x, int y) {
        int cnt = 0;
        int color = g[x][y];
        int i = x;
        while (i >= 0) {
            if (g[i][y] == color) {
                i--;
                cnt++;
            } else {
                break;
            }
        }
        i = x + 1;
        while (i < SIZE) {
            if (g[i][y] == color) {
                i++;
                cnt++;
            } else {
                break;
            }
        }
        return cnt >= 5;
    }


    private boolean checkDiagonal(int x, int y) {
        int cnt = 0;
        int color = g[x][y];
        int i = x, j = y;
        while (i >= 0 && j >= 0) {
            if (g[i][j] == color) {
                i--;
                j--;
                cnt++;
            } else {
                break;
            }
        }

        i = x + 1;
        j = y + 1;
        while (i < SIZE && j < SIZE) {
            if (g[i][j] == color) {
                i++;
                j++;
                cnt++;
            } else {
                break;
            }
        }
        return cnt >= 5;
    }

    private boolean checkAntiDiagonal(int x, int y) {
        int cnt = 0;
        int color = g[x][y];
        int i = x, j = y;
        while (i >= 0 && j < SIZE) {
            if (g[i][j] == color) {
                i--;
                j++;
                cnt++;
            } else {
                break;
            }
        }

        i = x + 1;
        j = y - 1;
        while (i < SIZE && j >= 0) {
            if (g[i][j] == color) {
                i++;
                j--;
                cnt++;
            } else {
                break;
            }
        }
        return cnt >= 5;
    }

}


