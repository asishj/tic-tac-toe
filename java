package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];

    private int roundCount, player1points, player2points, activityCount = 1;
    private boolean player1turn = true;
    private TextView textViewPlayer1;
    private TextView textViewPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewPlayer1 = findViewById(R.id.textView_p1);
        textViewPlayer2 = findViewById(R.id.textView_p2);

        textViewPlayer1.setBackgroundColor(Color.rgb(220,20,60));

        for (int i = 0; i<3;i++) {
            for ( int j=0; j<3; j++) {
                String buttonID = "button_"+i+j;
                int resID = getResources().getIdentifier(buttonID, "id" , getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);

            }
        }

        ImageButton resetButton = findViewById(R.id.resetButton_id);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              resetGame();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }

        if (player1turn) {
            ((Button) v).setText("X");
        }
        else {
            ((Button) v).setText("O");
        }

        roundCount++;
        activityStatus();
        if(checkForWin()){
            if (player1turn) {
                player1wins();
            }
            else {
                player2wins();
            }
        } else if (roundCount == 9) {
            draw();
        } else {
            player1turn = !player1turn;
        }
    }

    public boolean checkForWin() {
        String[][] field = new String[3][3];
        for (int a = 0; a < 3; a++) {
            for (int b = 0; b < 3; b++) {
                field[a][b] = buttons[a][b].getText().toString();
            }
        }

        for (int c = 0; c < 3; c++) {
            if (field[c][0].equals(field[c][1]) && field[c][0].equals(field[c][2]) && !field[c][0].equals("")) {
                return true;
            }
        }
        for (int c = 0; c < 3; c++) {
            if (field[0][c].equals(field[1][c]) && field[0][c].equals(field[2][c]) && !field[0][c].equals("")) {
                return true;
            }
        }
        if (field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2]) && !field[0][0].equals("")) {
            return true;
        }
        if (field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0]) && !field[0][2].equals("")) {
            return true;
        }
        return false;
    }
    public void player1wins() {
    player1points++;
        Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_SHORT).show();
        updatePointsTable();
        resetBoard();
    }

    public void player2wins() {
        player2points++;
        Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_SHORT).show();
        updatePointsTable();
        resetBoard();
    }

    public void draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    public void updatePointsTable() {
      textViewPlayer1.setText("Player 1: "+player1points);
      textViewPlayer2.setText("Player 2: "+player2points);
    }

    public void resetBoard() {
       for (int d=0; d<3; d++){
           for (int e=0; e<3; e++){
               buttons[d][e].setText("");
           }
       }
       roundCount = 0;
       activityCount = 1;
       activityNum();
       player1turn = true;
    }

    public void resetGame() {
        resetBoard();
        player1points = 0;
        player2points = 0;
        updatePointsTable();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("Player1Points",player1points);
        outState.putInt("Player2Points",player2points);
        outState.putInt("RoundCount",roundCount);
        outState.putBoolean("Player1Turn",player1turn);
        outState.putInt("ActivityCount",activityCount);
        activityNum();
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        player1points = savedInstanceState.getInt("Player1Points");
        player2points = savedInstanceState.getInt("Player2Points");
        roundCount = savedInstanceState.getInt("RoundCount");
        player1turn = savedInstanceState.getBoolean("Player1Turn");
        activityCount = savedInstanceState.getInt("ActivityCount");
        activityNum();
    }

    public void activityStatus() {
        activityCount++;
        if (activityCount%2 == 0) {
          textViewPlayer1.setBackgroundColor(Color.rgb(63, 81, 181));
            textViewPlayer2.setBackgroundColor(Color.rgb(220,20,60));
        }
        else {
            textViewPlayer1.setBackgroundColor(Color.rgb(220,20,60));
            textViewPlayer2.setBackgroundColor(Color.rgb(63, 81, 181));
        }
    }

    public void activityNum() {
        if (activityCount%2 == 0) {
            textViewPlayer1.setBackgroundColor(Color.rgb(63, 81, 181));
            textViewPlayer2.setBackgroundColor(Color.rgb(220,20,60));
        }
        else {
            textViewPlayer1.setBackgroundColor(Color.rgb(220,20,60));
            textViewPlayer2.setBackgroundColor(Color.rgb(63, 81, 181));
        }
    }
}
