package com.palavras.unicap.palavrinhas.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.google.firebase.FirebaseApp;
import com.palavras.unicap.palavrinhas.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.botao_jogar)
    Button botaoJogar;

    @BindView(R.id.botao_instrucoes)
    Button botaoInstrucoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.botao_jogar)
    public void jogar(){
        Intent intent = new Intent(MainActivity.this, MenuActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }


    @OnClick(R.id.botao_instrucoes)
    public void instrucoes(){
        Intent intent = new Intent(MainActivity.this, InstrucoesActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
