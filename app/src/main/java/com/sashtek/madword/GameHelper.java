package com.sashtek.madword;

import android.content.Context;
import android.widget.Button;
import android.widget.TextView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;
import android.view.View;
import android.widget.EditText;

public class GameHelper {
    private int points = 100;
    private String secretWord;
    private long startTime, endTime;
    private int wrongGuesses = 0;
    private static final String API_URL = "https://random-word-api.herokuapp.com/word?number=1";

    public GameHelper() {
        fetchRandomWord();
        startTime = System.currentTimeMillis();
    }

    //Get a Random Word
    private void fetchRandomWord() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(API_URL)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    secretWord = responseBody.substring(2, responseBody.length() - 2);
                }
            }
        });
    }

    // Submit Guess
    public void submitGuess(String guess, TextView textViewResult, TextView textViewPoints, Button buttonGuessSupport, Button buttonNew, Button buttonSubmitGuess) {

        if (guess.equalsIgnoreCase(secretWord)) {
            endTime = System.currentTimeMillis();
            long timeTaken = (endTime - startTime) / 1000;  // Time taken in seconds
            textViewResult.setText("Correct! Time taken: " + timeTaken + " seconds.");
            buttonGuessSupport.setVisibility(View.GONE);
            buttonNew.setVisibility(View.VISIBLE);
            buttonSubmitGuess.setVisibility(View.GONE);

        } else {
            points -= 10;
            textViewPoints.setText("Points: " + points);
            textViewResult.setText("Wrong! Try again.");
            wrongGuesses++;
            if (points <= 0) {
                textViewResult.setText("You've failed! The word was: " + secretWord);
                // Reset points and get a new word
                points = 100;
                fetchRandomWord();
                startTime = System.currentTimeMillis();
                buttonGuessSupport.setVisibility(View.GONE);
                buttonNew.setVisibility(View.VISIBLE);
                buttonSubmitGuess.setVisibility(View.GONE);
            }
        }
    }

    // Guess a Letter
    public void submitLetter(String letter, TextView textViewLetterResult, TextView textViewPoints) {
        if (letter.length() == 1) {
            int count = 0;
            for (char c : secretWord.toCharArray()) {
                if (c == letter.charAt(0)) {
                    count++;
                }
            }
            textViewLetterResult.setText("Occurrences of " + letter + ": " + count);
            points -= 5;
            textViewPoints.setText("Points: " + points);
        } else {
            textViewLetterResult.setText("Please enter a single letter.");
        }
    }

    // Get Word Length
    public void getWordLength(TextView textViewWordLengthResult, TextView textViewPoints) {
        textViewWordLengthResult.setText("The word has " + secretWord.length() + " letters.");
        points -= 5;
        textViewPoints.setText("Points: " + points);
    }

    // Get the Tip
    public void getTip(TextView textViewTipResult, TextView textViewPoints) {
        if (wrongGuesses >= 5) {
            String tip = generateHint(secretWord);
            textViewTipResult.setText(tip);
            points -= 10;
            textViewPoints.setText("Points: " + points);
        } else {
            textViewTipResult.setText("You can only get a tip after 5 wrong attempts.");
        }
    }

    // Tip Generator
    private String generateHint(String word) {
        return "Hint: The word starts with '" + word.charAt(0) + "' and ends with '" + word.charAt(word.length() - 1) + "'.";
    }

    // New Game/ Reset
    public void resetGame(TextView textViewResult, EditText editTextGuess, TextView textViewLetterResult, TextView textViewWordLengthResult, TextView textViewTipResult, EditText editTextLetter, TextView textViewPoints, Button buttonNew, Button buttonGuessSupport, Button buttonSubmitGuess) {

        textViewResult.setText("");
        editTextGuess.setText("");
        textViewLetterResult.setText("");
        textViewWordLengthResult.setText("");
        textViewTipResult.setText("");
        editTextLetter.setText("");
        textViewPoints.setText("Points: 100");
        points = 100;
        wrongGuesses = 0;
        startTime = System.currentTimeMillis();
        fetchRandomWord();
        buttonNew.setVisibility(View.GONE);
        buttonGuessSupport.setVisibility(View.VISIBLE);
        buttonSubmitGuess.setVisibility(View.VISIBLE);
    }
}

