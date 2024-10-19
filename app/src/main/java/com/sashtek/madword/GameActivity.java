package com.sashtek.madword;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;

public class GameActivity extends AppCompatActivity {
    private TextView textViewPoints, textViewResult, textViewLetterResult, textViewWordLengthResult, textViewTipResult, textViewWelcome;
    private EditText editTextGuess, editTextLetter;
    private Button buttonSubmitGuess, buttonGuessSupport, buttonSubmitLetter, buttonWordLength, buttonGetTip, buttonNew;
    private GameHelper gameHelper;

    private boolean isSupportVisible = false;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //GameHelper Class
        gameHelper = new GameHelper();

        //Greetings
        sharedPreferences = getSharedPreferences("MADWordPrefs", MODE_PRIVATE);
        textViewWelcome = findViewById(R.id.textViewWelcome);
        String userName = sharedPreferences.getString("userName", "Player");
        textViewWelcome.setText("Welcome, " + userName + "!");

        //View Points and Result
        textViewPoints = findViewById(R.id.textViewPoints);
        textViewResult = findViewById(R.id.textViewResult);

        // Submit Guess
        editTextGuess = findViewById(R.id.editTextGuess);
        buttonSubmitGuess = findViewById(R.id.buttonSubmitGuess);
        buttonSubmitGuess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String guess = editTextGuess.getText().toString();
                gameHelper.submitGuess(guess, textViewResult, textViewPoints, buttonGuessSupport, buttonNew, buttonSubmitGuess);
            }
        });

        // Guess a Letter
        editTextLetter = findViewById(R.id.editTextLetter);
        buttonSubmitLetter = findViewById(R.id.buttonSubmitLetter);
        textViewLetterResult = findViewById(R.id.textViewLetterResult);
        buttonSubmitLetter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String letter = editTextLetter.getText().toString();
                gameHelper.submitLetter(letter, textViewLetterResult, textViewPoints);
            }
        });

        // Get Word Length
        textViewWordLengthResult = findViewById(R.id.textViewWordLengthResult);
        buttonWordLength = findViewById(R.id.buttonWordLength);
        buttonWordLength.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameHelper.getWordLength(textViewWordLengthResult, textViewPoints);
            }
        });

        // Get the Tip
        textViewTipResult = findViewById(R.id.textViewTipResult);
        buttonGetTip = findViewById(R.id.buttonGetTip);
        buttonGetTip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameHelper.getTip(textViewTipResult, textViewPoints);
            }
        });

        // Get Support/ Clues
        buttonGuessSupport = findViewById(R.id.buttonGuessSupport);
        buttonGuessSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              isSupportVisible = !isSupportVisible;
              int visibility = isSupportVisible ? View.VISIBLE : View.GONE;
              editTextLetter.setVisibility(visibility);
              buttonSubmitLetter.setVisibility(visibility);
              textViewLetterResult.setVisibility(visibility);
              buttonWordLength.setVisibility(visibility);
              textViewWordLengthResult.setVisibility(visibility);
              buttonGetTip.setVisibility(visibility);
              textViewTipResult.setVisibility(visibility);
              buttonGuessSupport.setText(isSupportVisible ? "Hide Clues" : "Clues");
            }
        });

        // New Game/ Reset
        buttonNew = findViewById(R.id.buttonNew);
        buttonNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameHelper.resetGame(textViewResult, editTextGuess, textViewLetterResult, textViewWordLengthResult, textViewTipResult, editTextLetter, textViewPoints, buttonNew, buttonGuessSupport, buttonSubmitGuess);
            }
        });
    }
}



