package com.example.tictactoe;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class gameLogic {
    private int[][] gameBoard;

    private Button restartBtn;
    private Button exitBtn;
    private TextView playerTurn;
    private String[] playerNames = {"Player 1", "Player 2"};

    private int[] winType = {-1, -1, -1};

    private int player = 1;

    gameLogic(){
        gameBoard = new int[3][3];
        for (int row=0; row<3; row++){
            for (int column=0; column<3; column++){
                gameBoard[row][column] = 0;
            }
        }
    }

    public boolean updateGameBoard(int row, int column){
        if (gameBoard[row-1][column-1] == 0){
            gameBoard[row-1][column-1] = player;

            if (player == 1){
                playerTurn.setText((playerNames[1]) + "'s Turn");
            }else {
                playerTurn.setText((playerNames[0]) + "'s Turn");
            }

            return true;
        }else {
            return false;
        }
    }

    public boolean winnerCheck(){
        boolean isWinner = false;
        //Horizontal Check
        for (int row=0; row<3; row++){
            if (gameBoard[row][0] == gameBoard[row][1] && gameBoard[row][0] == gameBoard[row][2] && gameBoard[row][0] != 0) {
                winType = new int[] {row, 0, 1};
                isWinner = true;
            }
        }

        //Vertical Check
        for (int column=0; column<3; column++){
            if (gameBoard[column][0] == gameBoard[column][1] && gameBoard[column][0] == gameBoard[column][2] && gameBoard[column][0] != 0) {
                winType = new int[] {0, column, 2};
                isWinner = true;
            }
        }

        //negative diagonal check
        if (gameBoard[0][0] == gameBoard[1][1] && gameBoard[0][0] == gameBoard[2][2] && gameBoard[0][0] != 0) {
            winType = new int[] {0, 2, 3};
            isWinner = true;
        }

        //positive diagonal check
        if (gameBoard[2][0] == gameBoard[1][1] && gameBoard[2][0] == gameBoard[0][2] && gameBoard[2][0] != 0) {
            winType = new int[] {2, 2, 4};
            isWinner = true;
        }

        int boardFilled = 0;

        for (int row=0; row<3; row++){
            for (int column=0; column<3; column++){
                if (gameBoard[row][column] != 0){
                    boardFilled += 1;
                }
            }
        }

        if (isWinner){
            restartBtn.setVisibility(View.VISIBLE);
            exitBtn.setVisibility(View.VISIBLE);
            playerTurn.setText((playerNames[player-1] + " Won!!"));
            return true;
        } else if (boardFilled == 9) {
            restartBtn.setVisibility(View.VISIBLE);
            exitBtn.setVisibility(View.VISIBLE);
            playerTurn.setText("Tie Game!!");
            return true;
        }else {
            return false;
        }
    }

    public void resetGame(){
        for (int row=0; row<3; row++){
            for (int column=0; column<3; column++){
                gameBoard[row][column] = 0;
            }
        }

        player = 1;

        restartBtn.setVisibility(View.GONE);
        exitBtn.setVisibility(View.GONE);
        playerTurn.setText((playerNames[0] + "'s Turn"));

    }

    public void setRestartBtn(Button restartBtn){
        this.restartBtn = restartBtn;
    }

    public void setExitBtn(Button exitBtn){
        this.exitBtn = exitBtn;
    }

    public void setPlayerTurn(TextView playerTurn){
        this.playerTurn = playerTurn;
    }

    public void setPlayerNames(String[] playerNames) {
        this.playerNames = playerNames;
    }

    public int[][] getGameBoard(){
        return gameBoard;
    }

    public void setPlayer(int player){
        this.player = player;
    }

    public int getPlayer(){
        return player;
    }

    public int[] getWinType(){
        return winType;
    }
}
