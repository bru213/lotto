package com.example.lottosimulator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private EditText[] userInputs = new EditText[6];
    private TextView[] drawnNumbers = new TextView[6];
    private TextView matchCountText, drawCountText;
    private int drawCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userInputs[0] = findViewById(R.id.userInput1);
        userInputs[1] = findViewById(R.id.userInput2);
        userInputs[2] = findViewById(R.id.userInput3);
        userInputs[3] = findViewById(R.id.userInput4);
        userInputs[4] = findViewById(R.id.userInput5);
        userInputs[5] = findViewById(R.id.userInput6);

        drawnNumbers[0] = findViewById(R.id.drawnNumber1);
        drawnNumbers[1] = findViewById(R.id.drawnNumber2);
        drawnNumbers[2] = findViewById(R.id.drawnNumber3);
        drawnNumbers[3] = findViewById(R.id.drawnNumber4);
        drawnNumbers[4] = findViewById(R.id.drawnNumber5);
        drawnNumbers[5] = findViewById(R.id.drawnNumber6);

        matchCountText = findViewById(R.id.matchCount);
        drawCountText = findViewById(R.id.drawCount);

        Button drawButton = findViewById(R.id.drawButton);
        Button resetButton = findViewById(R.id.resetButton);

        drawButton.setOnClickListener(v -> drawNumbers());
        resetButton.setOnClickListener(v -> resetGame());
    }

    private void drawNumbers() {
        List<Integer> userNumbers = getUserSelection();
        if (userNumbers == null) return;

        List<Integer> drawn = new ArrayList<>();
        List<Integer> pool = new ArrayList<>();
        for (int i = 1; i <= 49; i++) pool.add(i);
        Collections.shuffle(pool);
        for (int i = 0; i < 6; i++) drawn.add(pool.get(i));

        displayDrawnNumbers(drawn);

        int matches = 0;
        for (int num : userNumbers) {
            if (drawn.contains(num)) matches++;
        }
        updateMatchCount(matches);
        updateDrawCount();
    }

    private List<Integer> getUserSelection() {
        List<Integer> numbers = new ArrayList<>();
        Set<Integer> uniqueCheck = new HashSet<>();
        for (EditText input : userInputs) {
            String text = input.getText().toString();
            if (text.isEmpty()) {
                Toast.makeText(this, "Wprowadź wszystkie liczby!", Toast.LENGTH_SHORT).show();
                return null;
            }
            int num = Integer.parseInt(text);
            if (num < 1 || num > 49 || !uniqueCheck.add(num)) {
                Toast.makeText(this, "Liczby muszą być unikalne i z zakresu 1-49!", Toast.LENGTH_SHORT).show();
                return null;
            }
            numbers.add(num);
        }
        return numbers;
    }

    private void displayDrawnNumbers(List<Integer> drawn) {
        for (int i = 0; i < 6; i++) {
            drawnNumbers[i].setText(String.valueOf(drawn.get(i)));
        }
    }

    private void updateMatchCount(int matches) {
        matchCountText.setText("Liczba trafień: " + matches);
    }

    private void updateDrawCount() {
        drawCount++;
        drawCountText.setText("Liczba losowań: " + drawCount);
    }

    private void resetGame() {
        for (EditText input : userInputs) input.setText("");
        for (TextView tv : drawnNumbers) tv.setText("?");
        matchCountText.setText("Liczba trafień: 0");
        drawCount = 0;
        drawCountText.setText("Liczba losowań: 0");
    }
}