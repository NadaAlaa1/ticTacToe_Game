package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class playerSetup extends AppCompatActivity {

    private EditText player1;
    private EditText player2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_setup);
        player1 = findViewById(R.id.editTextTextPersonName);
        player2 = findViewById(R.id.editTextTextPersonName2);
    }
    public void onStartButtonClick(View view){
        String player1Name = player1.getText().toString();
        String player2Name = player2.getText().toString();
        Intent intent = new Intent(this, gameDisplay.class);
        intent.putExtra("Player_Names", new  String[] {player1Name, player2Name});
        startActivity(intent);
    }
}