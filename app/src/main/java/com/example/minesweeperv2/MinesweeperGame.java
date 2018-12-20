//handles the logic of the game

package com.example.minesweeperv2;

import android.graphics.Canvas;
import android.util.Log;

import java.sql.Array;
import java.util.ArrayList;

public class MinesweeperGame {
    private Tile[][] array;
    private int virtualBombs;
    private int canvasSize;

    public MinesweeperGame(int canvasSize, int numBombs) {
        array = new Tile[canvasSize][canvasSize];
        virtualBombs = numBombs;
        this.canvasSize = canvasSize;
        for (int i = 0; i < canvasSize; i++) {
            for (int j = 0; j < canvasSize; j++) {
                array[i][j] = new Tile(i, j);
            }
        }
    }
    // randomize the bombs
    //or if numBombs == 0, when there are no bombs left to place, then the constructor ends


    // probability method for the bombs
    // if (!array[i][j].ifHasBomb()) {
    //int x = (int) (Math.random() * canvasSize);
    // if (x == 1) {
    // array[i][j].setHasBomb(true);
    //numBombs--;


    public Tile[][] getArray() {
        return array;
    }

    public void randomizeBombsAndSetNumbers() {
        while (virtualBombs > 0) {
            int x = (int) (Math.random() * canvasSize);
            int y = (int) (Math.random() * canvasSize);

            if (!array[y][x].ifHasBomb()) {
                array[y][x].setHasBomb(true);
                virtualBombs--;
            }
        }
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {

                if (array[i][j].ifHasBomb() == false) ;
                {
                    array[i][j].setNumber(calculateNumber(i, j));
                }
            }
        }
    }

    //checks if the game is lost and prompts the

    public boolean gameWon() {

        boolean x = true;

        for (int i = 0; i < canvasSize; i++) {
            for (int j = 0; j < canvasSize; j++) {

                if (array[i][j].ifHasBomb() == true) {
                    if (array[i][j].ifHasFlag() == true) {
                        x = true;
                    } else {
                        x = false;
                    }
                }

            }
        }

        if (x == true) {
            return true;
            // prompt victory fragment screen
        } else {
            return false;
            //continue game
        }
    }


    public void gameLost() {
        //prompt losing screen fragment


    }


    //if flag is drawn on canvas then set same ifHasFlag to true on the tile with corresponding location
    //if flag is undrawn on canvas then set same ifHasFlag to false on the tile with corresponding location

    public void ifFlagDrawn(int row, int col) {
        array[row][col].setHasFlag(true);
    }


    public void ifFlagUndrawn(int row, int col) {
        array[row][col].setHasFlag(false);
    }


    //check if tile has or doesn't have bomb then:
    // logic for that
    //if bomb:
    //just draw the bomb pic on top and then prompt TheEndScreenFragment.java
    //if not bomb, check surrounding tiles
    //if surrounding tiles have no bombs then:
    //draw a brown rectangle on top of the green one
    //if not bomb, check surrounding tiles
    //if surrounding tiles have bombs then:


    //reveals the tile and tiles around it if needed
    public void revealTileAndTilesAround(int row, int col) {

        //array[row][col].setRevealed(true);
        outerLoop:
        for (int i = col + 1; i >= col - 1; i--) {
            for (int j = row + 1; j >= row - 1; j--) {
                if (!((((i < 0) || (i > canvasSize - 1))) || ((j < 0) || (j > (canvasSize - 1))))) {
                    Log.e("why", "" + array[j][i].getNumber());
                    if (array[row][col].ifHasBomb()) {
                        revealAllBombs();
                        gameLost();
                        break outerLoop;
                    }else if ((array[j][i].getNumber()) == 0 && !array[j][i].isRevealed() && !array[j][i].ifHasBomb()) {
                        array[j][i].setRevealed(true);
                        revealTileAndTilesAround(j, i);
                    } else {
                        array[row][col].setRevealed(true);
                    }
                }
            }
        }
    }




    private void revealAllBombs() {
        for (int row = 0; row < array.length; row++) {
            for (int col = 0; col < array[0].length; col++) {
                if (array[row][col].ifHasBomb()) {
                    array[row][col].setRevealed(true);
                }
            }
        }
    }

    // revealing a square on a single click

    public void onSingleTapClickReveal(int row, int col) {


        if (array[row][col].ifHasFlag() == false) {
            revealTileAndTilesAround(row, col);
        }

    }


    //Number(Tile tile) method to calculate the number image that should be displayed
    public int calculateNumber(int row, int col) {
        int count = 0;


        for (int j = row - 1; j <= row + 1; j++) {
            for (int i = col - 1; i <= col + 1; i++) {
                if (!((i < 0) || (i > canvasSize - 1) || (j < 0) || (j > (canvasSize - 1))) &&
                        array[j][i].ifHasBomb() == true) {
                    count++;
                }

                //for (int i = col - 1; i < col + 2; i++) {
                //if (((i >= 0 && i <= canvasSize - 1) && row > 0) && array[row - 1][i].ifHasBomb()) {
                //  count++;
                //   }
                // }

                //   for (int i = col - 1; i < col + 2; i++) {
                //  if (((i >= 0 && i <= canvasSize - 1) && row < canvasSize - 1) && array[row + 1][i].ifHasBomb()) {
                //    count++;
                //    }
                // }

                // if (col > 0 && array[row][col - 1].ifHasBomb()) {
                // count++;
                // }
//
                // if (col < canvasSize - 1 && array[row][col + 1].ifHasBomb()) {
                //  count++;
                // }


            }
        }
        return count;


    }
}




