package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class gameDisplay extends AppCompatActivity {

    private ticTacToeBoard ticTacToeBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_display);

        Button restart = findViewById(R.id.restart);
        Button exit = findViewById(R.id.exit);
        TextView playerTurn = findViewById(R.id.player_display);

        restart.setVisibility(View.GONE);
        exit.setVisibility(View.GONE);

        String[] playerNames = getIntent().getStringArrayExtra("Player_Names");

        if (playerNames != null){
            playerTurn.setText((playerNames[0] + "'s Turn"));
        }

        ticTacToeBoard = findViewById(R.id.ticTacToeBoard2);

        ticTacToeBoard.setUpGame(restart, exit, playerTurn, playerNames);
    }

    public void onRestartButtonClick(View view){
        ticTacToeBoard.resetGame();
        ticTacToeBoard.invalidate();
    }

    public void onExitButtonClick(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}