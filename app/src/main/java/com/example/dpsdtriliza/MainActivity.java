package com.example.dpsdtriliza;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

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

    String player1Name;
    String player2Name;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Todo - check 4.30 onwards, from here: https://www.youtube.com/watch?v=oh4YOj9VkVE
        // to actually do something with the different options in toolbar.

        //ActionBar actionBar = getSupportActionBar();
        //actionBar.setTitle(getResources().getString(R.string.app_name));

        // this information is kept in build.gradle file.
        //actionBar.setSubtitle("ver. "+getResources().getString(R.string.app_version));

        // old code
        //String title = getResources().getString(R.string.app_name);
        //setTitle(title + "    --    ver. "+getResources().getString(R.string.app_version));

        Toolbar toolbar = findViewById(R.id.toolbar);
        //toolbar.getOverflowIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        toolbar.setSubtitle("ver. "+getResources().getString(R.string.app_version));
        setSupportActionBar(toolbar);


        textViewPlayer1 = findViewById(R.id.text_view_player1);
        textViewPlayer2 = findViewById(R.id.text_view_player2);

        Intent intent = getIntent();
        Bundle extras = getIntent().getExtras();
        player1Name = extras.getString("PLAYER1NAME");
        player2Name = extras.getString("PLAYER2NAME");

        textViewPlayer1.setText(player1Name+": 0");
        textViewPlayer2.setText(player2Name+": 0");

        colorPlayers();

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
                resetGame();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);
        return true;
        //return super.onCreateOptionsMenu(menu);
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
                //changeActivity(1, v);
            }
            else {
                //changeActivity(2, v);

                player2Wins();
            }
        } else if (roundCount == 9) {
            draw();
        } else {
            //player1Turn = !player1Turn;
        }

        /**
         * at the end of round, color player appropriately - also takes care of draw.
         */
        player1Turn = !player1Turn;
        colorPlayers();
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

        Snackbar snackbar = Snackbar.make(findViewById(R.id.rootLayout), "Κέρδισε ο "+player1Name+" !!!", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePointsText();
                resetBoard(false);
                Toast.makeText(MainActivity.this, "Παίξτε ξανά...", Toast.LENGTH_SHORT).show();
            }
        });
        snackbar.show();


    }

    /**
     * player 2 wins:
     *   update player 2 points, print snackbar and:
     *      call {@link #updatePointsText()}, call {@link #resetBoard(boolean)}
     */
    private void player2Wins() {

        player2Points++;

        Snackbar snackbar = Snackbar.make(findViewById(R.id.rootLayout), "Κέρδισε ο "+player2Name+" !!!", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePointsText();
                resetBoard(false);
                Toast.makeText(MainActivity.this, "Παίξτε ξανά...", Toast.LENGTH_SHORT).show();
            }
        });
        snackbar.show();

        //Toast.makeText(this, "Κέρδισε ο παίκτης 2 !!!", Toast.LENGTH_SHORT).show();
        //updatePointsText();
        //resetBoard();
    }

    /**
     * if the game is a draw, simply write a toast and reset the board
     */
    private void draw() {
        Toast.makeText(this, "Ισοπαλία !!!", Toast.LENGTH_SHORT).show();
        resetBoard(false);
    }


    private void updatePointsText() {
        textViewPlayer1.setText(player1Name+": "+player1Points);
        textViewPlayer2.setText(player2Name+": "+player2Points);
    }

    /**
     * method to reset board - used at the end of every round (hence param in false)
     * or in the case of a new game when param is true.
     * @param newGame should be true only in the case of a new game starting, after clicking
     *                'reset' button, upon which playerTurn is initialised to true, so that
     *                player one will play first.
     */
    private void resetBoard(boolean newGame) {

        for (int i=0; i<3; i++) {
            for (int j = 0; j < 3; j++) {
               buttons[i][j].setText("");
               buttons[i][j].setTextColor(Color.BLACK);
            }
        }

        // na to dw kai meta - prepei na paizoun enallax
        roundCount = 0;

        /**
         * Considering that a draw is after 9 moves without a winner, whoever played last
         * also played first (9 moves in total).  Hence the other player should now play first.
         */
//        if (afterDraw) {
//            player1Turn = !player1Turn;
//        }
        /**
         * If we start a new game after reset, then player 1 plays first by definition.
         */
        if (newGame) {
            player1Turn = true;
        }


    }

    private void colorPlayers() {
        if (player1Turn) {
            textViewPlayer1.setTextColor(Color.GREEN);
            textViewPlayer2.setTextColor(Color.BLACK);
        }
        else {
            textViewPlayer1.setTextColor(Color.BLACK);
            textViewPlayer2.setTextColor(Color.GREEN);
        }
    }

    private void resetGame() {
        player1Points = 0;
        player2Points = 0;
        updatePointsText();
        resetBoard(true);
        colorPlayers();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putInt("player1Points", player1Points);
        outState.putInt("player2Points", player2Points);
        outState.putBoolean("player1Turn", player1Turn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundCount");
        player1Points = savedInstanceState.getInt("player1Points");
        player2Points = savedInstanceState.getInt("player2Points");
        player1Turn = savedInstanceState.getBoolean("player1Turn");

    }


    /**
     * Not used at the time being - tried to use it in order to call {@link BlankActivity BlankActivity.class}
     * @param player
     * @param view
     */
    public void changeActivity(int player, View view) {
        Intent intent = new Intent(this, BlankActivity.class);
        String key = "whoWon";

        intent.putExtra(key, player);

        startActivity(intent);
    }
}
