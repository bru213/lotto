package com.example.lotto;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private EditText num1;
    private EditText num2;
    private EditText num3;
    private EditText num4;
    private EditText num5;
    private EditText num6;
    private TextView los1;
    private TextView los2;
    private TextView los3;
    private TextView los4;
    private TextView los5;
    private TextView los6;
    private TextView wynikText;
    private TextView losowania;
    private Button losuj;
    private Button resetuj;
    private ArrayList<Integer> podane_liczby;
    private int licznik;
    private int wynik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        podane_liczby = new ArrayList<>();
        licznik = 0;
        wynik = 0;

        num1 = findViewById(R.id.num1);
        num2 = findViewById(R.id.num2);
        num3 = findViewById(R.id.num3);
        num4 = findViewById(R.id.num4);
        num5 = findViewById(R.id.num5);
        num6 = findViewById(R.id.num6);

        los1 = findViewById(R.id.wylosowana1);
        los2 = findViewById(R.id.wylosowana2);
        los3 = findViewById(R.id.wylosowana3);
        los4 = findViewById(R.id.wylosowana4);
        los5 = findViewById(R.id.wylosowana5);
        los6 = findViewById(R.id.wylosowana6);

        losuj = findViewById(R.id.losuj);
        resetuj = findViewById(R.id.resetuj);

        wynikText = findViewById(R.id.wynik);
        losowania = findViewById(R.id.losowania);

        losuj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                if(walidacja()){
                    ArrayList<Integer> wylosowane = losuj();
                    los1.setText(String.valueOf(wylosowane.get(0)));
                    los2.setText(String.valueOf(wylosowane.get(1)));
                    los3.setText(String.valueOf(wylosowane.get(2)));
                    los4.setText(String.valueOf(wylosowane.get(3)));
                    los5.setText(String.valueOf(wylosowane.get(4)));
                    los6.setText(String.valueOf(wylosowane.get(5)));

                    wynik += wynik(wylosowane);
                    wynikText.setText("Liczba trafień: "+wynik);

                    licznik++;
                    losowania.setText("Liczba losowań: "+licznik);

                }else{
                    podane_liczby.clear();
                }
            }
        });

        resetuj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                podane_liczby.clear();
                wynik = 0;
                wynikText.setText("Liczba trafień: ");
                licznik = 0;
                losowania.setText("Liczba losowań: 0");

                num1.setText("");
                num2.setText("");
                num3.setText("");
                num4.setText("");
                num5.setText("");
                num6.setText("");

                los1.setText("?");
                los2.setText("?");
                los3.setText("?");
                los4.setText("?");
                los5.setText("?");
                los6.setText("?");
            }
        });
    }
    private boolean walidacja(){
        if(!String.valueOf(num1.getText()).isEmpty() && !String.valueOf(num2.getText()).isEmpty() && !String.valueOf(num3.getText()).isEmpty() && !String.valueOf(num4.getText()).isEmpty() && !String.valueOf(num5.getText()).isEmpty() && !String.valueOf(num6.getText()).isEmpty()){
            podane_liczby.add(Integer.parseInt(String.valueOf(num1.getText())));
            podane_liczby.add(Integer.parseInt(String.valueOf(num2.getText())));
            podane_liczby.add(Integer.parseInt(String.valueOf(num3.getText())));
            podane_liczby.add(Integer.parseInt(String.valueOf(num4.getText())));
            podane_liczby.add(Integer.parseInt(String.valueOf(num5.getText())));
            podane_liczby.add(Integer.parseInt(String.valueOf(num6.getText())));

        }else{
            Toast.makeText(this, "Podano puste pola!", Toast.LENGTH_SHORT).show();
            return false;
        }
        for(int i=0; i<6; i++){
            if(podane_liczby.get(i) < 1 || podane_liczby.get(i) > 49){
                Toast.makeText(this, "Podano liczby poza zakresem!", Toast.LENGTH_SHORT).show();
                return false;
            }
            for(int j=0; j<6;j++){
                if(j==i){
                    continue;
                }
                if(podane_liczby.get(i).equals(podane_liczby.get(j))){
                    Toast.makeText(this, "Podano takie same liczby!", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        }
        return true;
    }

    private ArrayList<Integer> losuj(){
        ArrayList<Integer> lista = new ArrayList<>();
        Random random = new Random();
        for(int i=0; i<6; i++){
            int randomNumber = random.nextInt(49)+1;
            while(lista.contains(randomNumber)){
                randomNumber = random.nextInt(49)+1;
            }
            lista.add(randomNumber);
        }

        return lista;
    }

    private int wynik(ArrayList<Integer> wylosowane){
        int trafienia = 0;
        for(int i=0; i<6; i++){
            for(int j=0; j<6; j++){
                if(wylosowane.get(i) == podane_liczby.get(j)){
                    trafienia++;
                }
            }
        }
        return trafienia;
    }
}