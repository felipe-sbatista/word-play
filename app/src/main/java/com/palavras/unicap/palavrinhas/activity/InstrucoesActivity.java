package com.palavras.unicap.palavrinhas.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.palavras.unicap.palavrinhas.R;

public class InstrucoesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instrucoes);
//        SharedPreferences preferences = getSharedPreferences("user_preferences", MODE_PRIVATE);
//        preferences.getString("teste", "felipe");
    }
}
