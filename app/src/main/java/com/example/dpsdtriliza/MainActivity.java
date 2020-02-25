package com.example.dpsdtriliza;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import static android.R.color.primary_text_dark;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];

    private boolean player1Turn = true;

    private int roundCount;

    private int player1Points;

    private int player2Points;

    private TextView textViewPlayer1;

    private TextView textViewPlayer2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewPlayer1 = findViewById(R.id.text_view_player1);
        textViewPlayer2 = findViewById(R.id.text_view_player2);

        for (int i=0; i<3; i++) {

            for (int j=0; j<3; j++) {

                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        // if button doesn't contain empty string, do nothing
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }

        if (player1Turn) {
            ((Button) v).setText("X");
        }
        else {
            ((Button) v).setText("O");
        }

        roundCount++;

        if (checkForWin()) {
            if (player1Turn) {
                player1Wins();
            }
            else {
                player2Wins();
            }
        } else if (roundCount == 9) {
            draw();
        } else {
            player1Turn = !player1Turn;
        }

    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];


        for (int i=0; i<3; i++) {

            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        // check rows
        for (int i=0; i<3; i++) {
            if (field[i][0].equals(field[i][1])
                && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                buttons[i][0].setTextColor(Color.RED);
                buttons[i][1].setTextColor(Color.RED);
                buttons[i][2].setTextColor(Color.RED);
                return true;
            }
        }

        // check columns
        for (int i=0; i<3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                buttons[0][i].setTextColor(Color.RED);
                buttons[1][i].setTextColor(Color.RED);
                buttons[2][i].setTextColor(Color.RED);
                return true;
            }
        }

        // one diagonal
        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            buttons[0][0].setTextColor(Color.RED);
            buttons[1][1].setTextColor(Color.RED);
            buttons[2][2].setTextColor(Color.RED);
            return true;
        }

        // the other diagonal
        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            buttons[0][2].setTextColor(Color.RED);
            buttons[1][1].setTextColor(Color.RED);
            buttons[2][0].setTextColor(Color.RED);
            return true;
        }

        return false;
    }

    private void player1Wins() {

        player1Points++;

        //Snackbar snackbar = Snackbar.make(findViewById(R.id.rootLayout), "Κέρφισε ο παίκτης 1 !!!", 5000);
        Snackbar snackbar = Snackbar.make(findViewById(R.id.rootLayout), "Κέρδισε ο παίκτης 1 !!!", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePointsText();
                resetBoard();
                Toast.makeText(MainActivity.this, "Παίξτε ξανά...", Toast.LENGTH_SHORT).show();
            }
        });
        snackbar.show();
        //Toast.makeText(this, "Κέρδισε ο παίκτης 1 !!!", Toast.LENGTH_SHORT).show();
        //updatePointsText();
        //resetBoard();
    }

    private void player2Wins() {

        player2Points++;
        Toast.makeText(this, "Κέρδισε ο παίκτης 2 !!!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void draw() {
        Toast.makeText(this, "Ισοπαλία !!!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }


    private void updatePointsText() {
        textViewPlayer1.setText("Παίκτης 1: "+player1Points);
        textViewPlayer2.setText("Παίκτης 2: "+player2Points);
    }

    private void resetBoard() {

        for (int i=0; i<3; i++) {
            for (int j = 0; j < 3; j++) {
               buttons[i][j].setText("");
               buttons[i][j].setTextColor(Color.BLACK);
            }
        }

        roundCount = 0;
        player1Turn = true;
    }
}
