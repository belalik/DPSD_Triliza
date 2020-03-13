package com.example.dpsdtriliza;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

// see https://stackoverflow.com/questions/21814825/you-need-to-use-a-theme-appcompat-theme-or-descendant-with-this-activity
// but used comment from Harmeet Singh so it can extend AppCompatActivity (also see styles.xml (mainly) and manifest where it is being used )
public class BlankActivity extends AppCompatActivity {

    TextView winnerTextview;
    int winner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blank_activity);

        Intent intent = getIntent();
        winner = intent.getIntExtra("whoWon", 0);

        //winnerTextview = findViewById(R.id.name_winner_textview);
        printResult(winner);

    }



    public void changeActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void printResult(int result) {

        if (result == 0) {

        }
        else {
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Κέρδισε ο παίκτης "+result+" !!!", Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction("OK", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(BlankActivity.this, "Παίξτε ξανά...", Toast.LENGTH_SHORT).show();
                }
            });
            snackbar.show();
        }

    }
}
