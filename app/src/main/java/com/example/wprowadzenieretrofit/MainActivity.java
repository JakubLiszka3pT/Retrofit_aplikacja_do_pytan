package com.example.wprowadzenieretrofit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private List<Pytanie> pytanie;
    TextView textViewPytanie;
    RadioGroup radioGroup;
    int  radioButtonID[] = new int[]{
            R.id.radioButton,
            R.id.radioButton2,
            R.id.radioButton3
    };
    RadioButton radioButton1;
    RadioButton radioButton2;
    RadioButton radioButton3;

    Button  buttonDalej;
    Button  buttonPodzielsie;
    int aktualnepytanie = 0;
    int sumapunkty = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        textViewPytanie = findViewById(R.id.textview_tresc_pytania);
        radioGroup = findViewById(R.id.radio_group);
        radioButton1 = findViewById(R.id.radioButton);
        radioButton2 = findViewById(R.id.radioButton2);
        radioButton3 = findViewById(R.id.radioButton3);
        buttonDalej = findViewById(R.id.button_dalej);
        buttonPodzielsie = findViewById(R.id.button_podzielsie);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://my-json-server.typicode.com/JakubLiszka3pT/Retrofit_pytania/") // link musi zawierać ostatni /
                .addConverterFactory(GsonConverterFactory.create()).build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<Pytanie>> call = jsonPlaceHolderApi.getPytanie();
        call.enqueue(
                new Callback<List<Pytanie>>() {
                    @Override
                    public void onResponse(Call<List<Pytanie>> call, Response<List<Pytanie>> response) {
                        if(!response.isSuccessful()){
                            Toast.makeText(MainActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        pytanie = response.body();
                        Toast.makeText(MainActivity.this, pytanie.get(0).getTrescPytania(), Toast.LENGTH_SHORT).show();
                        wyswietlPytanie(0);
                    }

                    @Override
                    public void onFailure(Call<List<Pytanie>> call, Throwable t) {
                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        buttonDalej.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sprawdzOdp(aktualnepytanie)){
                    Toast.makeText(MainActivity.this,    "dobrza", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "ŹLa", Toast.LENGTH_SHORT).show();
                }
                if (aktualnepytanie<pytanie.size()-1){
                    sumapunkty++;
                    aktualnepytanie++;
                    wyswietlPytanie(aktualnepytanie);
                }else {
                    //TODO:KONIEC TESTI
                    //
                    radioGroup.setVisibility(View.INVISIBLE);
                    textViewPytanie.setText("Koniec testu" + sumapunkty);
                    buttonDalej.setVisibility(View.INVISIBLE);
                    buttonPodzielsie.setVisibility(View.VISIBLE);
                }
            }
        });
        buttonPodzielsie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentWyslij = new Intent();
                intentWyslij.setAction(Intent.ACTION_SEND);
                intentWyslij.putExtra(Intent.EXTRA_TEXT, "Otrzymano " + sumapunkty+" punktów");
                intentWyslij.setType("text/plain");
                Intent intentUdostepniona = Intent.createChooser(intentWyslij, "Punkty");
                startActivity(intentUdostepniona);
            }
        });

    }

    private boolean sprawdzOdp(int aktualnepytanie) {
        Pytanie pytanie1 = pytanie.get(aktualnepytanie);
        if (radioGroup.getCheckedRadioButtonId() == radioButtonID[pytanie1.getPoprawna()]){
            return true;
        }
        return false;
    }

    private void wyswietlPytanie(int ktore){
        Pytanie pytanie1 = pytanie.get(ktore);
        textViewPytanie.setText(pytanie1.getTrescPytania());
        radioButton1.setText(pytanie1.getOdpa());
        radioButton2.setText(pytanie1.getOdpb());
        radioButton3.setText(pytanie1.getOdpc());
        radioButton1.setChecked(false);
        radioButton2.setChecked(false);
        radioButton3.setChecked(false);
    }

}