package com.example.dpsdtriliza;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BeginTriliza extends AppCompatActivity {

    boolean namesOk = false;

    EditText etPlayer1Name;
    EditText etPlayer2Name;
    Button playButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.begin_triliza);

        etPlayer1Name = findViewById(R.id.player1_name);
        etPlayer2Name = findViewById(R.id.player2_name);
        playButton = findViewById(R.id.play_button);

        //int x = 5/0;

    }

    public void startPlaying(View view) {
        namesOk = checkNames();

        if (namesOk) {
            Intent intent = new Intent(this, MainActivity.class);
            String player1 = etPlayer1Name.getText().toString();
            String player2 = etPlayer2Name.getText().toString();

            intent.putExtra("PLAYER1NAME", player1);
            intent.putExtra("PLAYER2NAME", player2);

            startActivity(intent);
        }

    }


    public boolean checkNames() {

        String player1 = etPlayer1Name.getText().toString();
        String player2 = etPlayer2Name.getText().toString();

        if(TextUtils.isEmpty(player1)) {
            etPlayer1Name.setError("Cannot be empty");
            return false;
        }
        else if (TextUtils.isEmpty(player2)) {
            etPlayer2Name.setError("Cannot be empty");
            return false;
        }
        else {
            return true;
        }

    }


}
